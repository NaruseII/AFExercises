package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.util.*;

public class Grades3Exercise extends AbstractExercise {

    private static final Map<Integer, String> GOOD_ANSWERS = new HashMap<>();

    static {
        for (int i = 0; i < GradesExercise.GRADES_NAME.length; i++) {
            GOOD_ANSWERS.put(i, GradesExercise.GRADES_NAME[i]);
        }
    }

    private String currentAnswer;

    public Grades3Exercise() {
        super("Armée de l'Air - Grades | Ordre", 800, 400);
        this.count = GradesExercise.GRADES_NAME.length;
        this.totalCount = GradesExercise.GRADES_NAME.length;

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if (count == 0) {
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi " + grade + "/" + totalCount + " multiplications. " + (mistake.equals("") ? "" : "Voici vos erreurs:\n" + mistake.replace("\n\n", "")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        setMainLabel("Quel est le grade n°"+(21-count)+" dans la hiérarchie ? (en partant du plus bas au plus haut)");
        currentAnswer = GOOD_ANSWERS.get(21-count-1);

        schedule(20);
        setHeadLabelText("Tu as 20s entre chaque question. (" + (count + 1) + "/" + totalCount + " restantes)");
        repaint();
    }


    @Override
    protected void addComponents(JPanel panel) {

    }

    @Override
    protected void timerEndEvent() {
        checkAnswer();
    }

    @Override
    protected void enterPressedEvent() {
        if (!mainTextField.getText().equalsIgnoreCase("")) {
            checkAnswer();
        }
    }

    @Override
    protected void checkAnswer() {
        if (mainTextField.getText().replace("é", "e").toUpperCase().replace("-", " ").equalsIgnoreCase(currentAnswer.replace("é", "e").toUpperCase().replace("-", " "))) {
            grade++;
        } else {
            mistake += "\n------------------------------------\n" + count+": \n" +
                    "\tVotre réponse: " + mainTextField.getText() + "\n" +
                    "\tRéponse attendue: " + currentAnswer;
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
