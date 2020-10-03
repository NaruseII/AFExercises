package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.exercises.enigmas.Enigma;
import fr.naruse.afexercises.exercises.enigmas.EnigmaType;
import fr.naruse.afexercises.main.Main;

import javax.swing.*;

public class EnigmasExercise extends AbstractExercise {

    private int enigmasCount = 4;
    private int grade = 0;
    private String mistake = "";

    private Enigma currentEnigma;

    public EnigmasExercise() {
        super("Armée de l'Air - Enigmes", 800, 400);

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if(enigmasCount == 0){
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi "+grade+"/4 énigmes. "+(mistake.equals("") ? "" : "Voici vos erreurs:\n"+mistake.replace("\n\n","")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        enigmasCount--;

        currentEnigma = Enigma.getRandomEnigma(EnigmaType.MATH);

        setMainLabel(currentEnigma.getStatement());

        schedule(60*3);
        setHeadLabelText("Tu as 3m entre chaque énigmes. N'utilises pas ta calculette ! ("+(enigmasCount+1)+"/4 restantes)");
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
        String s = currentEnigma.getResponse();
        if(mainTextField.getText().toUpperCase().equalsIgnoreCase(s)){
            grade++;
        }else{
            mistake += "\n------------------------------------\n"+(enigmasCount+1)+" : \n" +
                    "\tVotre réponse: "+mainTextField.getText()+"\n" +
                    "\tRéponse attendue: "+s;
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
