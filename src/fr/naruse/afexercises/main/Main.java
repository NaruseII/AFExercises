package fr.naruse.afexercises.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

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

    public static void main(String[] args){
        loadImages();
        MAIN_FRAME = new MainFrame();
    }

    private static void loadImages() {
        try {
            LOGO = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/logo_armee_air_espace.png"));
            A400M = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/a400m.jpg"));
            C130_HERCULES = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/hercules.jpg"));
            CASA_235 = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/casa235.jpg"));
            C160_TRANSALL = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/transall.jpg"));
            TBM_700 = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/tbm.jpg"));
            SDCA = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/sdca.jpg"));
            C135 = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/c135.jpg"));
            RAFALE = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/rafale.jpg"));
            MIRAGE_2000_A = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/mirage2000a.jpg"));
            MIRAGE_2000_C = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/mirage2000c.jpg"));
            MIRAGE_2000_DN = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("resources/mirage2000dn.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
