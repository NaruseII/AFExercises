package fr.naruse.afexercises.main;

import fr.naruse.afexercises.exercises.GradesExercise;
import fr.naruse.afexercises.updater.Updater;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {

    public static MainFrame MAIN_FRAME;

    public static Image LOGO;
    public static Image A400M;
    public static Image C130_HERCULES;
    public static Image CASA_235;
    public static Image C160_TRANSALL;
    public static Image TBM_700;
    public static Image SDCA;
    public static Image C135;
    public static Image RAFALE;
    public static Image MIRAGE_2000_A;
    public static Image MIRAGE_2000_C;
    public static Image MIRAGE_2000_DN;
    public static Image[] GRADES;

    public static void main(String[] args){
        loadImages();
        MAIN_FRAME = new MainFrame();
        if(args.length == 0 || !args[0].equalsIgnoreCase("skipUpdate")){
            Updater.tryUpdate();
        }else{
            System.out.println("Update skipped.");
        }
    }

    private static void loadImages() {
        try{
            LOGO = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/logo_armee_air_espace.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                A400M = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/a400m.jpg"));
                C130_HERCULES = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/hercules.jpg"));
                CASA_235 = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/casa235.jpg"));
                C160_TRANSALL = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/transall.jpg"));
                TBM_700 = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/tbm.jpg"));
                SDCA = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/sdca.jpg"));
                C135 = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/c135.jpg"));
                RAFALE = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/rafale.jpg"));
                MIRAGE_2000_A = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/mirage2000a.jpg"));
                MIRAGE_2000_C = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/mirage2000c.jpg"));
                MIRAGE_2000_DN = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/mirage2000dn.jpg"));
                GRADES = new Image[GradesExercise.GRADES_NAME.length];
                for (int i = 0; i < GradesExercise.GRADES_NAME.length; i++) {
                    String grade = GradesExercise.GRADES_NAME[i];
                    GRADES[i] = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/grades/"+grade.toLowerCase().replace(" ", "-")+".png"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
