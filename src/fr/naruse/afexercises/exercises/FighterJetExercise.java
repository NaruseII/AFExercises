package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FighterJetExercise extends AbstractMCQExercise {

    public FighterJetExercise() {
        super("Armée de l'Air - Avions de Chasse");
    }

    @Override
    protected List<MCQ> buildMCQs() {
        List<MCQ> list = new ArrayList<>();
        list.add(new MCQ("Quel est cet avion ?", "Rafale",  "Mirage 2000-5", "Mirage 2000-C", "A400M", QuestionChoiceType.ONE) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.RAFALE, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "Mirage 2000-C",  "Mirage 2000-D/N", "Rafale", "SU-30", QuestionChoiceType.ONE) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.MIRAGE_2000_C, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "Mirage 2000-D/N",  "Rafale", "F-22", "Super Etendard", QuestionChoiceType.ONE) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.MIRAGE_2000_DN, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "Mirage 2000-15",  "Casa 235", "Alpha Jet", "Mirage 2000-5", QuestionChoiceType.FOUR) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.MIRAGE_2000_A, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
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
