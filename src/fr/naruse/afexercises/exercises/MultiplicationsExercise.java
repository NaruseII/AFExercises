package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.text.DecimalFormat;

public class MultiplicationsExercise extends AbstractExercise {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.#");

    private double currentMultiplier;
    private double currentMultiplied;
    private double currentAnswer;

    public MultiplicationsExercise() {
        super("Armée de l'Air - Multiplications", 800, 400);

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if(count == 0){
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi "+grade+"/"+totalCount+" multiplications. "+(mistake.equals("") ? "" : "Voici vos erreurs:\n"+mistake.replace("\n\n","")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        currentMultiplied = random.nextInt(500);
        currentMultiplier = random.nextInt(7);
        if(random.nextInt(10) <= 2){
            currentMultiplier += Double.parseDouble(decimalFormat.format(random.nextDouble()).replace(",","."));
        }

        mainLabel.setText(currentMultiplied+" * " +currentMultiplier+" =");
        currentAnswer = currentMultiplied*currentMultiplier;

        schedule(90);
        setHeadLabelText("Tu as 1m30 entre chaque multiplication. N'utilises pas ta calculette ! ("+(count+1)+"/"+totalCount+" restantes)");
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
            mistake += "\n------------------------------------\n"+currentMultiplied+" * " +currentMultiplier+" : \n" +
                    "\tVotre réponse: "+mainTextField.getText()+"\n" +
                    "\tRéponse attendue: "+s;
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
