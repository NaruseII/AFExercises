package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TransportAndStrategicAircraftExercise extends AbstractMCQExercise {

    public TransportAndStrategicAircraftExercise() {
        super("Armée de l'Air - Avions de Transport & Stratégiques");
        nextQuestion();
    }

    @Override
    protected List<MCQ> buildMCQs() {
        List<MCQ> list = new ArrayList<>();
        list.add(new MCQ("Quel est cet avion ?", "Rafale",  "A400M", "A380", "TBM 700", QuestionChoiceType.TWO) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.A400M, getWidth()/2-Main.A400M.getWidth(null)/2, 50, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "Casa 235",  "A380", "C-130 Hercules", "E3-F SDCA", QuestionChoiceType.THREE) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.C130_HERCULES, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "C-160 Transall",  "A400M", "C-135 FR", "Casa 235", QuestionChoiceType.FOUR) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.CASA_235, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "Mirage 2000C",  "A400M", "C-160 Transall", "Casa 235", QuestionChoiceType.THREE) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.C160_TRANSALL, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "TBM 700",  "A400M", "Rafale", "Alpha Jet", QuestionChoiceType.ONE) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.TBM_700, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "A400M",  "B747", "C-160 Transall", "E3-F SDCA", QuestionChoiceType.FOUR) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.SDCA, getWidth()/2-710/2, 50, 710, 444, null);
            }
        });
        list.add(new MCQ("Quel est cet avion ?", "E3-F SDCA",  "C-135 FR", "MQ-9 Reaper", "C-160 Transall", QuestionChoiceType.TWO) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Main.C135, getWidth()/2-710/2, 50, 710, 444, null);
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
