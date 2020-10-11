package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.text.DecimalFormat;

public class MentalArithmeticExercise extends AbstractExercise {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.#");

    private String currentArithmetic;
    private int currentAnswer;
    private final ScriptEngineManager mgr = new ScriptEngineManager();
    private final ScriptEngine engine = mgr.getEngineByName("JavaScript");
    private final boolean isAdvanced;

    public MentalArithmeticExercise(boolean isAdvanced) {
        super("Armée de l'Air - Calcul Menta"+(isAdvanced ? " Avancé" : ""), 800, 400);

        this.isAdvanced = isAdvanced;
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

        int limit = 10;
        if(isAdvanced){
            limit = 31;
        }

        currentArithmetic = random.nextInt(limit)+ " + "+random.nextInt(limit)+" + "+random.nextInt(limit)+" + "+random.nextInt(limit);

        mainLabel.setText(currentArithmetic+" =");
        try {
            currentAnswer = (int) engine.eval(currentArithmetic);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        int schedule = 20;
        if(isAdvanced){
            schedule = 30;
        }
        schedule(schedule);
        setHeadLabelText("Tu as "+schedule+"s entre chaque calcul. N'utilises pas ta calculette ! ("+(count+1)+"/"+totalCount+" restantes)");
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
        String s = currentAnswer+"";
        if(mainTextField.getText().toUpperCase().trim().equalsIgnoreCase(s)){
            grade++;
        }else{
            mistake += "\n------------------------------------\n"+currentArithmetic+" : \n" +
                    "\tVotre réponse: "+mainTextField.getText()+"\n" +
                    "\tRéponse attendue: "+s;
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
