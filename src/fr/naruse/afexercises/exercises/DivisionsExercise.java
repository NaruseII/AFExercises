package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.text.DecimalFormat;

public class DivisionsExercise extends AbstractExercise {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.#");

    private int divisionsCount = 4;
    private int grade = 0;
    private String mistake = "";

    private double currentDivider;
    private double currentDivided;
    private double currentAnswer;

    public DivisionsExercise() {
        super("Armée de l'Air - Divisions", 800, 400);

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if(divisionsCount == 0){
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi "+grade+"/4 divisions."+(mistake.equals("") ? "" : "Voici vos erreurs:\n"+mistake.replace("\n\n","")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        divisionsCount--;

        currentDivided = random.nextInt(500);
        currentDivider = random.nextInt(8)+1;
        if(random.nextInt(10) <= 2){
            currentDivider += Double.parseDouble(decimalFormat.format(random.nextDouble()).replace(",","."));
        }

        mainLabel.setText(currentDivided+" / " +currentDivider+" =");
        currentAnswer = currentDivided/currentDivider;

        schedule(90);
        setHeadLabelText("Tu as 1m30 entre chaque divisions. N'utilises pas ta calculette ! ("+(divisionsCount+1)+"/4 restantes)");
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
        if(!mainTextField.getText().equalsIgnoreCase("")){
            checkAnswer();
        }
    }

    @Override
    protected void checkAnswer() {
        String s = decimalFormat.format(currentAnswer).replace(",",".");
        if(mainTextField.getText().toUpperCase().equalsIgnoreCase(s)){
            grade++;
        }else{
            mistake += "\n------------------------------------\n"+currentDivided+" / " +currentDivider+" : \n" +
                    "\tVotre réponse: "+mainTextField.getText()+"\n" +
                    "\tRéponse attendue: "+s;
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
