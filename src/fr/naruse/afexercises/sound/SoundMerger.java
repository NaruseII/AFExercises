package fr.naruse.afexercises.sound;

import fr.naruse.afexercises.main.Main;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.SequenceInputStream;
import java.util.*;

public class SoundMerger {

    public SoundMerger() throws Exception {
        File folder = new File(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile(), "secpilAudioTests");
        if(folder.exists() && folder.listFiles() != null){
            for (File file : folder.listFiles()) {
                file.delete();
            }
        }
        folder.mkdirs();

        Random random = new Random();

        System.out.println("Working...");
        for (int i = 1; i < 101; i++) {
            System.out.println("  "+i+"/100");

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
                    AudioInputStream strem = AudioSystem.getAudioInputStream(new BufferedInputStream(Main.class.getClassLoader().getResourceAsStream("resources/sounds/"+Integer.valueOf(c+"")+".wav")));
                    enumeration.add(strem);
                    length += strem.getFrameLength();
                }
            }

            AudioInputStream appendedFiles = new AudioInputStream(new SequenceInputStream(enumeration), clips[0].getFormat(), length*2);

            String name = UUID.randomUUID().toString();
            AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(folder, name+".wav"));
            System.out.println("    Creating '"+name+".wav'... [Result: "+resultString+"]");
        }

        JOptionPane.showMessageDialog(Main.MAIN_FRAME, "Travail terminé. Sons stockés dans '"+folder.getAbsolutePath()+"'.");
        Main.MAIN_FRAME.setVisible(true);
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
