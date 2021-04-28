package fr.naruse.afexercises.sound;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Simple Clip like player for OGG's. Code is mostly taken from the example
 * provided with JOrbis.
 *
 * @author kevin
 */
public class OggClip {
    public static ArrayList<OggClip> currentPlayers = new ArrayList<>();

    private final int BUFSIZE = 4096 * 2;
    private int convsize = this.BUFSIZE * 2;
    private byte[] convbuffer = new byte[this.convsize];
    private SyncState oy;
    private StreamState os;
    private Page og;
    private Packet op;
    private Info vi;
    private Comment vc;
    private DspState vd;
    private Block vb;
    private SourceDataLine outputLine;
    private int rate;
    private int channels;
    private BufferedInputStream bitStream = null;
    private byte[] buffer = null;
    private int bytes = 0;
    private Thread player = null;

    private float balance;
    private float gain = -1;
    private boolean paused;
    private float oldGain;

    /**
     * Create a new clip based on a reference into the class path
     *
     * @param ref The reference into the class path which the ogg can be read
     *            from
     * @throws IOException Indicated a failure to find the resource
     */
    public OggClip(String ref) throws IOException {
        try {
            this.init(Thread.currentThread().getContextClassLoader().getResourceAsStream(ref));
        } catch (IOException e) {
            throw new IOException("Couldn't find: " + ref);
        }
        currentPlayers.add(this);
    }

    /**
     * Create a new clip based on a reference into the class path
     *
     * @param in The stream from which the ogg can be read from
     * @throws IOException Indicated a failure to read from the stream
     */
    public OggClip(InputStream in) throws IOException {
        this.init(in);
        currentPlayers.add(this);
    }

    public static void stopAll() {
        for (OggClip ogg : OggClip.currentPlayers) {
            if (ogg != null) {
                ogg.stop();
            }
        }
    }

    /**
     * Set the default gain value (default volume)
     */
    public void setDefaultGain() {
        this.setGain(-1);
    }

    /**
     * Attempt to set the global gain (volume ish) for the play back. If the
     * control is not supported this method has no effect. 1.0 will set maximum
     * gain, 0.0 minimum gain
     *
     * @param gain The gain value
     */
    public void setGain(float gain) {
        if (gain != -1) {
            if (gain < 0 || gain > 1) {
                throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
            }
        }

        this.gain = gain;

        if (this.outputLine == null) {
            return;
        }

        try {
            FloatControl control = (FloatControl) this.outputLine.getControl(FloatControl.Type.MASTER_GAIN);
            if (gain == -1) {
                control.setValue(0);
            } else {
                float max = control.getMaximum();
                float min = control.getMinimum(); // negative values all seem to
                // be zero?
                float range = max - min;

                control.setValue(min + range * gain);
            }
        } catch (IllegalArgumentException e) {
            // gain not supported
            e.printStackTrace();
        }
    }

    /**
     * Attempt to set the balance between the two speakers. -1.0 is full left
     * speak, 1.0 if full right speaker. Anywhere in between moves between the
     * two speakers. If the control is not supported this method has no effect
     *
     * @param balance The balance value
     */
    public void setBalance(float balance) {
        this.balance = balance;

        if (this.outputLine == null) {
            return;
        }

        try {
            FloatControl control = (FloatControl) this.outputLine.getControl(FloatControl.Type.BALANCE);
            control.setValue(balance);
        } catch (IllegalArgumentException e) {
            // balance not supported
        }
    }

    /**
     * Check the state of the play back
     *
     * @return True if the playback has been stopped
     */
    private boolean checkState() {
        while (this.paused && this.player != null) {
            synchronized (this.player) {
                if (this.player != null) {
                    try {
                        this.player.wait();
                    } catch (InterruptedException e) {
                        // ignored
                    }
                }
            }
        }

        return this.stopped();
    }

    /**
     * Pause the play back
     */
    public void pause() {
        this.paused = true;
        this.oldGain = this.gain;
        this.setGain(0);
    }

    /**
     * Check if the stream is paused
     *
     * @return True if the stream is paused
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Resume the play back
     */
    public void resume(boolean loop) {
        if (!this.paused) {
            if (loop) {
                this.loop();
            } else {
                this.play();
            }
            return;
        }

        this.paused = false;

        synchronized (this.player) {
            if (this.player != null) {
                this.player.notify();
            }
        }
        this.setGain(this.oldGain);
    }

    /**
     * Check if the clip has been stopped
     *
     * @return True if the clip has been stopped
     */
    public boolean stopped() {
        return this.player == null || !this.player.isAlive();
    }

    /**
     * Initialise the ogg clip
     *
     * @param in The stream we're going to read from
     * @throws IOException Indicates a failure to read from the stream
     */
    private void init(InputStream in) throws IOException {
        if (in == null) {
            throw new IOException("Couldn't find input source");
        }
        this.bitStream = new BufferedInputStream(in);
        this.bitStream.mark(Integer.MAX_VALUE);
    }

    /**
     * Play the clip once
     */
    public void play() {
        this.stop();

        try {
            this.bitStream.reset();
        } catch (IOException e) {
            // ignore if no mark
        }

        this.player = new Thread() {
            @Override
            public void run() {
                try {
                    OggClip.this.playStream(Thread.currentThread());
                } catch (InternalException e) {
                    e.printStackTrace();
                }

                try {
                    OggClip.this.bitStream.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        this.player.setDaemon(true);
        this.player.start();
    }

    /**
     * Loop the clip - maybe for background music
     */
    public void loop() {
        this.stop();

        try {
            this.bitStream.reset();
        } catch (IOException e) {
            // ignore if no mark
        }

        this.player = new Thread() {
            @Override
            public void run() {
                while (OggClip.this.player == Thread.currentThread()) {
                    try {
                        OggClip.this.playStream(Thread.currentThread());
                    } catch (InternalException e) {
                        e.printStackTrace();
                        OggClip.this.player = null;
                    }

                    try {
                        OggClip.this.bitStream.reset();
                    } catch (IOException ignored) {
                    }
                }
            }

        };
        this.player.setDaemon(true);
        this.player.start();
    }

    /**
     * Stop the clip playing
     */
    public void stop() {
        if (this.stopped()) {
            return;
        }

        this.player = null;

        if (outputLine != null) this.outputLine.drain();
    }

    /**
     * Close the stream being played from
     */
    public void close() {
        try {
            if (this.bitStream != null) {
                this.bitStream.close();
            }
        } catch (IOException ignored) {
        }
    }

    /*
     * Taken from the JOrbis Player
     */
    private void initJavaSound(int channels, int rate) {
        try {
            AudioFormat audioFormat = new AudioFormat(rate, 16, channels, true, // PCM_Signed
                    false // littleEndian
            );
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            if (!AudioSystem.isLineSupported(info)) {
                throw new Exception("Line " + info + " not supported.");
            }

            try {
                this.outputLine = (SourceDataLine) AudioSystem.getLine(info);
                // outputLine.addLineListener(this);
                this.outputLine.open(audioFormat);
                onAudioStart();
            } catch (LineUnavailableException ex) {
                throw new Exception("Unable to open the sourceDataLine: " + ex);
            } catch (IllegalArgumentException ex) {
                throw new Exception("Illegal Argument: " + ex);
            }

            this.rate = rate;
            this.channels = channels;

            this.setBalance(this.balance);
            this.setGain(this.gain);
        } catch (Exception ee) {
            System.out.println(ee);
        }
    }

    public void onAudioStart() {
    }

    /*
     * Taken from the JOrbis Player
     */
    private void getOutputLine(int channels, int rate) {
        if (this.outputLine == null || this.rate != rate || this.channels != channels) {
            if (this.outputLine != null) {
                this.outputLine.drain();
                this.outputLine.stop();
                this.outputLine.close();
            }
            this.initJavaSound(channels, rate);
            this.outputLine.start();
        }
    }

    /*
     * Taken from the JOrbis Player
     */
    private void initJOrbis() {
        this.oy = new SyncState();
        this.os = new StreamState();
        this.og = new Page();
        this.op = new Packet();

        this.vi = new Info();
        this.vc = new Comment();
        this.vd = new DspState();
        this.vb = new Block(this.vd);

        this.buffer = null;
        this.bytes = 0;

        this.oy.init();
    }

    /*
     * Taken from the JOrbis Player
     */
    private void playStream(Thread me) throws InternalException {
        boolean chained = false;

        this.initJOrbis();

        while (true) {
            if (this.checkState()) {
                return;
            }

            int eos = 0;

            int index = this.oy.buffer(this.BUFSIZE);
            this.buffer = this.oy.data;
            try {
                this.bytes = this.bitStream.read(this.buffer, index, this.BUFSIZE);
            } catch (Exception e) {
                throw new InternalException(e);
            }
            this.oy.wrote(this.bytes);

            if (chained) {
                chained = false;
            } else {
                if (this.oy.pageout(this.og) != 1) {
                    if (this.bytes < this.BUFSIZE) {
                        break;
                    }
                    throw new InternalException("Input does not appear to be an Ogg bitstream.");
                }
            }
            this.os.init(this.og.serialno());
            this.os.reset();

            this.vi.init();
            this.vc.init();

            if (this.os.pagein(this.og) < 0) {
                // error; stream version mismatch perhaps
                throw new InternalException("Error reading first page of Ogg bitstream data.");
            }

            if (this.os.packetout(this.op) != 1) {
                // no page? must not be vorbis
                throw new InternalException("Error reading initial header packet.");
            }

            if (this.vi.synthesis_headerin(this.vc, this.op) < 0) {
                // error case; not a vorbis header
                throw new InternalException("This Ogg bitstream does not contain Vorbis audio data.");
            }

            int i = 0;

            while (i < 2) {
                while (i < 2) {
                    if (this.checkState()) {
                        return;
                    }

                    int result = this.oy.pageout(this.og);
                    if (result == 0) {
                        break; // Need more data
                    }
                    if (result == 1) {
                        this.os.pagein(this.og);
                        while (i < 2) {
                            result = this.os.packetout(this.op);
                            if (result == 0) {
                                break;
                            }
                            if (result == -1) {
                                throw new InternalException("Corrupt secondary header.  Exiting.");
                            }
                            this.vi.synthesis_headerin(this.vc, this.op);
                            i++;
                        }
                    }
                }

                index = this.oy.buffer(this.BUFSIZE);
                this.buffer = this.oy.data;
                try {
                    this.bytes = this.bitStream.read(this.buffer, index, this.BUFSIZE);
                } catch (Exception e) {
                    throw new InternalException(e);
                }
                if (this.bytes == 0 && i < 2) {
                    throw new InternalException("End of file before finding all Vorbis headers!");
                }
                this.oy.wrote(this.bytes);
            }

            this.convsize = this.BUFSIZE / this.vi.channels;

            this.vd.synthesis_init(this.vi);
            this.vb.init(this.vd);

            float[][][] _pcmf = new float[1][][];
            int[] _index = new int[this.vi.channels];

            this.getOutputLine(this.vi.channels, this.vi.rate);

            while (eos == 0) {
                while (eos == 0) {
                    if (this.player != me) {
                        return;
                    }

                    int result = this.oy.pageout(this.og);
                    if (result == 0) {
                        break; // need more data
                    }
                    if (result == -1) { // missing or corrupt data at this page
                        // position
                        // System.err.println("Corrupt or missing data in
                        // bitstream;
                        // continuing...");
                    } else {
                        this.os.pagein(this.og);

                        if (this.og.granulepos() == 0) { //
                            chained = true; //
                            eos = 1; //
                            break; //
                        } //

                        while (true) {
                            if (this.checkState()) {
                                return;
                            }

                            result = this.os.packetout(this.op);
                            if (result == 0) {
                                break; // need more data
                            }
                            if (result == -1) { // missing or corrupt data at
                                // this page position
                                // no reason to complain; already complained
                                // above

                                // System.err.println("no reason to complain;
                                // already complained above");
                            } else {
                                // we have a packet. Decode it
                                int samples;
                                if (this.vb.synthesis(this.op) == 0) { // test for
                                    // success!
                                    this.vd.synthesis_blockin(this.vb);
                                }
                                while ((samples = this.vd.synthesis_pcmout(_pcmf, _index)) > 0) {
                                    if (this.checkState()) {
                                        return;
                                    }

                                    float[][] pcmf = _pcmf[0];
                                    int bout = Math.min(samples, this.convsize);

                                    // convert doubles to 16 bit signed ints
                                    // (host order) and
                                    // interleave
                                    for (i = 0; i < this.vi.channels; i++) {
                                        int ptr = i * 2;
                                        // int ptr=i;
                                        int mono = _index[i];
                                        for (int j = 0; j < bout; j++) {
                                            int val = (int) (pcmf[i][mono + j] * 32767.);
                                            if (val > 32767) {
                                                val = 32767;
                                            }
                                            if (val < -32768) {
                                                val = -32768;
                                            }
                                            if (val < 0) {
                                                val = val | 0x8000;
                                            }
                                            this.convbuffer[ptr] = (byte) val;
                                            this.convbuffer[ptr + 1] = (byte) (val >>> 8);
                                            ptr += 2 * this.vi.channels;
                                        }
                                    }
                                    this.outputLine.write(this.convbuffer, 0, 2 * this.vi.channels * bout);
                                    this.vd.synthesis_read(bout);
                                }
                            }
                        }
                        if (this.og.eos() != 0) {
                            eos = 1;
                        }
                    }
                }

                if (eos == 0) {
                    index = this.oy.buffer(this.BUFSIZE);
                    this.buffer = this.oy.data;
                    try {
                        this.bytes = this.bitStream.read(this.buffer, index, this.BUFSIZE);
                    } catch (Exception e) {
                        throw new InternalException(e);
                    }
                    if (this.bytes == -1) {
                        break;
                    }
                    this.oy.wrote(this.bytes);
                    if (this.bytes == 0) {
                        eos = 1;
                    }
                }
            }

            this.os.clear();
            this.vb.clear();
            this.vd.clear();
            this.vi.clear();
        }

    }

    private static class InternalException extends Exception {
        public InternalException(Exception e) {
            super(e);
        }

        public InternalException(String msg) {
            super(msg);
        }
    }
}
