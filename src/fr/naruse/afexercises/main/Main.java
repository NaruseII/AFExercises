package fr.naruse.afexercises.main;

import fr.naruse.afexercises.exercises.GradesExercise;
import fr.naruse.afexercises.exercises.PlanesExercise;
import fr.naruse.afexercises.updater.Updater;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {

    public static MainFrame MAIN_FRAME;

    public static Image LOGO;
    public static Image[] GRADES;
    public static Image[] PLANES;

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
            LOGO = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/logo_armee_air_espace.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                GRADES = new Image[GradesExercise.GRADES_NAME.length];
                for (int i = 0; i < GradesExercise.GRADES_NAME.length; i++) {
                    String grade = GradesExercise.GRADES_NAME[i];
                    GRADES[i] = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/grades/"+grade.toLowerCase().replace(" ", "-")+".png"));
                }
                PLANES = new Image[PlanesExercise.PLANES.length];
                for (int i = 0; i < PlanesExercise.PLANES.length; i++) {
                    String grade = PlanesExercise.PLANES[i];
                    PLANES[i] = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/planes/"+grade.toLowerCase().replace(" ", "")+".jpg"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
