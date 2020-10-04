package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.exercises.enigmas.Enigma;
import fr.naruse.afexercises.exercises.enigmas.EnigmaType;
import fr.naruse.afexercises.main.Main;

import javax.swing.*;

public class EnigmasExercise extends AbstractExercise {

    private Enigma currentEnigma;

    public EnigmasExercise() {
        super("Armée de l'Air - Enigmes", 800, 400);

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if(count == 0){
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi "+grade+"/"+totalCount+" énigmes. "+(mistake.equals("") ? "" : "Voici vos erreurs:\n"+mistake.replace("\n\n","")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        currentEnigma = Enigma.getRandomEnigma(EnigmaType.MATH);

        setMainLabel(currentEnigma.getStatement());

        schedule(60*3);
        setHeadLabelText("Tu as 3m entre chaque énigmes. N'utilises pas ta calculette ! ("+(count+1)+"/"+totalCount+" restantes)");
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
            mistake += "\n------------------------------------\n"+(count+1)+" : \n" +
                    "\tVotre réponse: "+mainTextField.getText()+"\n" +
                    "\tRéponse attendue: "+s;
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
