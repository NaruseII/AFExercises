package fr.naruse.afexercises.main;

import fr.naruse.afexercises.exercises.GradesExercise;
import fr.naruse.afexercises.exercises.PlanesExercise;
import fr.naruse.afexercises.updater.Updater;
import fr.naruse.afexercises.utils.LittleFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

public class Main {

    public static MainFrame MAIN_FRAME;

    public static Image LOGO;
    public static Image[] GRADES;
    public static Image[] PLANES;
    public static final InputStream[] SPECIL_AUDIO = new InputStream[9];

    public static void main(String[] args) throws InterruptedException {
        LittleFrame littleFrame = new LittleFrame("Chargement...");

        System.out.println("Loading resources...");
        littleFrame.setText("Chargement des ressources...");

        loadImages(littleFrame);

        littleFrame.setText("Chargement terminé!");
        littleFrame.setText2((1+GRADES.length+PLANES.length+SPECIL_AUDIO.length)+" ressources chargées");
        System.out.println("Loaded "+(1+GRADES.length+PLANES.length+SPECIL_AUDIO.length)+" resources");

        Thread.sleep(3000);
        littleFrame.hide();

        MAIN_FRAME = new MainFrame();
        if(args.length == 0 || !args[0].equalsIgnoreCase("skipUpdate")){
            Updater.tryUpdate();
        }else{
            System.out.println("Update skipped.");
        }
    }

    private static void loadImages(LittleFrame littleFrame) {
        try{
            System.out.println("  Loading 'logo_armee_air_espace.png'");
            littleFrame.setText2("Chargement de 'logo_armee_air_espace.png'");
            LOGO = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/logo_armee_air_espace.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            GRADES = new Image[GradesExercise.GRADES_NAME.length];
            for (int i = 0; i < GradesExercise.GRADES_NAME.length; i++) {
                String grade = GradesExercise.GRADES_NAME[i];
                System.out.println("  Loading '"+grade.toLowerCase().replace(" ", "-")+".png'");
                littleFrame.setText2("Chargement de '"+grade.toLowerCase().replace(" ", "-")+".png'");
                GRADES[i] = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/grades/"+grade.toLowerCase().replace(" ", "-")+".png"));
            }
            PLANES = new Image[PlanesExercise.PLANES.length];
            for (int i = 0; i < PlanesExercise.PLANES.length; i++) {
                String grade = PlanesExercise.PLANES[i];
                System.out.println("  Loading '"+grade.toLowerCase().replace(" ", "-")+".jpg'");
                littleFrame.setText2("Chargement de '"+grade.toLowerCase().replace(" ", "-")+".jpg'");
                PLANES[i] = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/"+grade.replace(" ", "-")+".jpg"));
            }
            for (int i = 1; i < SPECIL_AUDIO.length+1; i++) {
                System.out.println("  Loading '"+i+".ogg'");
                littleFrame.setText2("Chargement de '"+i+".ogg'");
                SPECIL_AUDIO[i-1] = Main.class.getClassLoader().getResourceAsStream("resources/sounds/"+i+".ogg");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
