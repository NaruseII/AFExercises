package fr.naruse.afexercises.updater;

import fr.naruse.afexercises.component.TexturedProgressBar;
import fr.naruse.afexercises.exercises.AbstractExercise;
import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TimerTask;
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
                    Updater updater = new Updater();
                    downloadFile(url, runningJar, true, updater);
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

    private static boolean downloadFile(URL host, File dest, boolean log, Updater updater) {
        try (BufferedInputStream in = new BufferedInputStream(host.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            long length = 0;
            long realFileSize = fileSize(host);
            String fileSize = byteToMB(realFileSize);

            int i = 0;

            updater.setMainLabel(byteToMB(length)+" MB / "+fileSize+" MB");
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                length += 1024;
                System.out.println("[Updater] "+byteToMB(length)+" MB / "+fileSize+" MB");
                updater.updateProgressBar((int) (Double.parseDouble(byteToMB(length).replace(",", ""))*1000000), (int) (Double.parseDouble(fileSize.replace(",", ""))*1000000));

                if(i >= 10){
                    i = 0;
                    updater.setMainLabel(byteToMB(length)+" MB / "+fileSize+" MB");
                }else{
                    i++;
                }
            }
            updater.setMainLabel(byteToMB(length)+" MB / "+fileSize+" MB");
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


    protected final TexturedProgressBar progressBar = new TexturedProgressBar();

    protected final JLabel mainLabel = new JLabel();

    public Updater() {
        setTitle("Armée de l'Air - Updater");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                Main.MAIN_FRAME.setVisible(true);
            }
        });
        setIconImage(Main.LOGO);
        setLayout(null);
        setResizable(false);

        setContentPane(new AbstractUpdaterPanel(800, 400));

        setVisible(true);
    }

    public void updateProgressBar(int value, int max){
        if(progressBar.getMaximum() != max){
            progressBar.setMaximum(max);
        }
        if(progressBar.getValue() != value){
            progressBar.setValue(value);
        }
    }

    protected void setMainLabel(String statement) {
        setMainLabel(statement, 0);
    }

    private java.util.List<JLabel> labelList = new ArrayList<>();
    protected void setMainLabel(String statement, int downHeight) {
        for (JLabel jLabel : labelList) {
            jLabel.setVisible(false);
        }
        labelList.clear();
        if(statement == null){
            mainLabel.setVisible(false);
            return;
        }

        List<String> list = new ArrayList<>();
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : statement.toCharArray()) {
            builder.append(c);
            if(i >= 60 && c == ' '){
                i = 0;
                list.add(builder.toString());
                builder = new StringBuilder();
            }
            i++;
        }
        list.add(builder.toString());

        int heightPlus = list.size();
        for (String s : list) {
            JLabel jLabel = new JLabel();
            jLabel.setText(s);
            jLabel.setBounds(getWidth()/2-mainLabel.getGraphics().getFontMetrics().stringWidth(s)/2, getHeight()/2-5-(23*heightPlus)+downHeight, 800, 25);
            jLabel.setVisible(true);
            jLabel.setFont(jLabel.getFont().deriveFont(20f));
            getContentPane().add(jLabel);
            labelList.add(jLabel);
            heightPlus--;
        }
        mainLabel.setVisible(false);
    }

    private class AbstractUpdaterPanel extends JPanel {

        public AbstractUpdaterPanel(int width, int height) {
            setLayout(null);
            progressBar.setBounds(18, height-60, width-25-20, 15);
            progressBar.setBackground(new Color(88, 85, 85));
            progressBar.setVisible(true);
            add(progressBar);

            mainLabel.setText("Chargement...");
            mainLabel.setBounds(120, height/2-40, 500, 25);
            mainLabel.setVisible(true);
            mainLabel.setFont(mainLabel.getFont().deriveFont(20f));
            add(mainLabel);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            progressBar.paintComponent(g);
        }
    }
}
