package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GradesExercise extends AbstractMCQExercise {
    public static final String[] GRADES_NAME = new String[]{
            "Aviateur", "Aviateur de 1er classe", "Caporal", "Caporal-Chef", // Militaire du rang
            "Sergent", "Sergent-Chef", // Sous officiers subalternes
            "Adjudant", "Adjudant-Chef", "Major", // Sous officiers supérieurs
            "Aspirant de l'Ecole de l'Air", "EOPN", // Elèves officiers
            "Sous-Lieutenant", "Lieutenant", "Capitaine", // Officiers subalternes
            "Commandant", "Lieutenant-Colonel", "Colonel", // Officiers supérieurs
            "Général de Brigade Aérienne", "Général de Division Aérienne", "Général de Corps Aérien", "Général d'Armée Aérienne"}; // Officiers généraux
    public GradesExercise() {
        super("Armée de l'Air - Grades | QCM", 20, GRADES_NAME.length);
    }

    @Override
    protected List<MCQ> buildMCQs() {
        List<MCQ> list = new ArrayList<>();
        for (int i = 0; i < GRADES_NAME.length; i++) {
            String grade = GRADES_NAME[i];
            int finalI = i;
            list.add(new MCQ("A quel grade correspond cet insigne ?", grade, GRADES_NAME){
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image image = Main.GRADES[finalI];
                    int width = image.getWidth(null)*2;
                    int height = image.getHeight(null)*2;
                    g.drawImage(image, getWidth()/2-width/2, getHeight()/2-height/2, width, height, null);
                }
            });
        }
        return list;
    }

    @Override
    protected void addComponents(JPanel panel) {

    }

    @Override
    protected void timerEndEvent() {

    }

    @Override
    protected void enterPressedEvent() {

    }

    @Override
    protected void checkAnswer() {

    }
}
