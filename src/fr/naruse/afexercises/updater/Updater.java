package fr.naruse.afexercises.updater;

import fr.naruse.afexercises.main.Main;
import fr.naruse.afexercises.utils.LittleFrame;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Updater extends JFrame {
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    public static void tryUpdate() {
        service.submit(() -> {
            try {
                System.out.println("[Updater] Launching update...");
                File runningJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                System.out.println("[Updater] Jar found : "+runningJar.getAbsolutePath());
                URL url = new URL("https://github.com/NaruseII/AFExercises/blob/master/out/artifacts/AFExercices_jar/Exercices%20Arm%C3%A9e%20de%20l'Air%20-%20Tests%20EOPN.jar?raw=true");
                if(needToUpdate()){
                    Main.MAIN_FRAME.setVisible(false);
                    LittleFrame littleFrame = new LittleFrame("Updater");
                    littleFrame.setText("Mise à jour en cours...");
                    downloadFile(url, runningJar, true, littleFrame);
                    littleFrame.hide();

                    JOptionPane.showMessageDialog(Main.MAIN_FRAME, "Une mise a été téléchargée. Veuillez relancer le programme.\n(Celui-ci va se fermer automatiquement)");
                    System.exit(0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(Main.MAIN_FRAME, "Impossible de mettre à jour le programme : "+e.getCause());
                e.printStackTrace();
            }
        });

    }

    private static boolean needToUpdate() {
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("resources/version.txt"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String currentVersion = bufferedReader.readLine();

            URL url = new URL("https://raw.githubusercontent.com/NaruseII/AFExercises/master/src/resources/version.txt");
            Scanner scanner = new Scanner(url.openStream());
            String onlineVersion = scanner.nextLine();

            System.out.println("[Updater] Local version: "+currentVersion);
            System.out.println("[Updater] Online version: "+onlineVersion);

            if(currentVersion.equals(onlineVersion)){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private static boolean downloadFile(URL host, File dest, boolean log, LittleFrame littleFrame) {
        try (BufferedInputStream in = new BufferedInputStream(host.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            long length = 0;
            long realFileSize = fileSize(host);
            String fileSize = byteToMB(realFileSize);

            int i = 0;

            littleFrame.setText2(byteToMB(length)+" MB / "+fileSize+" MB");
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                length += 1024;
                System.out.println("[Updater] "+byteToMB(length)+" MB / "+fileSize+" MB");

                if(i >= 10){
                    i = 0;
                    littleFrame.setText2(byteToMB(length)+" MB / "+fileSize+" MB");
                }else{
                    i++;
                }
            }
            littleFrame.setText2(byteToMB(length)+" MB / "+fileSize+" MB");
            System.out.println("[Updater] Update ended.");
        } catch (IOException e) {
            if(log){
                e.printStackTrace();
                JOptionPane.showMessageDialog(Main.MAIN_FRAME, "Une erreur s'est produite, veuillez vérifier votre connexion !");
            }
            return false;
        }
        return true;
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static String byteToMB(long bytes){
        String result = df.format(bytes*0.000001);
        return result;
    }

    private static long fileSize(URL url){
        try {
            URLConnection connection = url.openConnection();
            int fileLength = connection.getContentLength();
            return fileLength;
        } catch (Exception e) {
            return -1;
        }
    }
}
