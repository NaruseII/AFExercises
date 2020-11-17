package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.text.DecimalFormat;

public class MentalMultiplicationExercise extends AbstractExercise {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.#");

    private int currentMultiplier;
    private int currentMultiplied;
    private int currentAnswer;

    public MentalMultiplicationExercise() {
        super("Armée de l'Air - Multiplications Mentales", 800, 400);
        this.count = 12;
        this.totalCount = 12;
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

        currentMultiplied = random.nextInt(8)+2;
        currentMultiplier = random.nextInt(8)+2;

        mainLabel.setText(currentMultiplied + " * " + currentMultiplier + " =");
        currentAnswer = currentMultiplied * currentMultiplier;

        schedule(10);
        setHeadLabelText("Tu as 10s entre chaque multiplication. N'utilises pas ta calculette ! (" + (count + 1) + "/" + totalCount + " restantes)");
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
        String s = decimalFormat.format(currentAnswer).replace(",", ".");
        if (mainTextField.getText().toUpperCase().equalsIgnoreCase(s)) {
            grade++;
        } else {
            mistake += "\n------------------------------------\n" + currentMultiplied + " * " + currentMultiplier + " : \n" +
                    "\tVotre réponse: " + mainTextField.getText() + "\n" +
                    "\tRéponse attendue: " + s;
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
