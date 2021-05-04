package fr.naruse.afexercises.sound;

import fr.naruse.afexercises.main.Main;
import fr.naruse.afexercises.utils.LittleFrame;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.SequenceInputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundMerger {

    public SoundMerger(int interval, int count)  {
        LittleFrame littleFrame = new LittleFrame("SECPIL Sound Creator");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                File folder = new File(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile(), "secpilAudioTests");
                if(folder.exists() && folder.listFiles() != null){
                    for (File file : folder.listFiles()) {
                        file.delete();
                    }
                }
                folder.mkdirs();

                Random random = new Random();

                System.out.println("Working...");
                for (int i = 1; i < count+1; i++) {
                    System.out.println("  "+i+"/"+count);
                    String name = UUID.randomUUID().toString();

                    littleFrame.setText("Travail en cours... "+i+"/"+count);
                    littleFrame.setText2("Calcul de '"+name+".wav'...");

                    AudioInputStream[] clips = new AudioInputStream[]{
                            AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/1.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/2.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/3.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/4.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/5.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/6.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/7.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/8.wav")))
                            ,AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/9.wav")))};
                    AudioInputStream beep = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/beep.wav")));
                    AudioInputStream zero = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/0.wav")));

                    AudioEnumeration enumeration = new AudioEnumeration();
                    long length = 0;
                    int result = 0;
                    for (int j = 0; j < 36; j++) {

                        int o = random.nextInt(clips.length);
                        AudioInputStream audioInputStream = clips[o];
                        enumeration.add(audioInputStream);
                        length += audioInputStream.getFrameLength();
                        result += o+1;
                        clips[o] = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/"+(o+1)+".wav")));

                        if(interval > 0){
                            for (int i1 = 0; i1 < interval-1; i1++) {
                                AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/one_second.wav")));
                                enumeration.add(stream);
                                length += stream.getFrameLength();
                            }
                        }
                    }

                    enumeration.add(beep);
                    length += beep.getFrameLength();

                    String resultString = result+"";

                    for (int k = 0; k < resultString.length(); k++) {
                        char c = resultString.charAt(k);
                        if(c == '0'){
                            enumeration.add(zero);
                            length += zero.getFrameLength();
                        }else{
                            AudioInputStream stream = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/"+Integer.valueOf(c+"")+".wav")));
                            enumeration.add(stream);
                            length += stream.getFrameLength();
                        }
                    }

                    AudioInputStream appendedFiles = new AudioInputStream(new SequenceInputStream(enumeration), clips[0].getFormat(), length*2);

                    AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(folder, name+".wav"));
                    System.out.println("    Creating '"+name+".wav'... [Result: "+resultString+"]");
                }

                littleFrame.hide();
                JOptionPane.showMessageDialog(Main.MAIN_FRAME, "Travail terminé. Sons stockés dans '"+folder.getAbsolutePath()+"'.");
                Main.MAIN_FRAME.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    public static class AudioEnumeration implements Enumeration<AudioInputStream> {

        private final List<AudioInputStream> list = new ArrayList<>();
        private Iterator<AudioInputStream> iterator = null;

        public void add(AudioInputStream stream){
            list.add(stream);
        }

        public AudioInputStream get(int index){
            return list.get(index);
        }

        @Override
        public boolean hasMoreElements() {
            if(iterator == null){
                iterator = list.iterator();
            }
            return iterator == null || !iterator.hasNext() ? false : true;
        }

        @Override
        public AudioInputStream nextElement() {
            if(iterator == null){
                iterator = list.iterator();
            }
            return iterator.next();
        }
    }

}
