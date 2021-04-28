package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;
import fr.naruse.afexercises.sound.OggClip;

import javax.swing.*;
import java.io.IOException;
public class SPECILAudioExercise extends AbstractExercise {

    private int currentAnswer;

    public SPECILAudioExercise() {
        super("Armée de l'Air - SPECIL Audio", 800, 400);
        count = 15;
        totalCount = 15;
        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if (count == 0) {
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! \nLa réponse finale était " + currentAnswer);
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        int number = random.nextInt(9) + 1;

        mainLabel.setText("+" + number + " =");
        currentAnswer += number;

        schedule(3);
        setHeadLabelText("Tu as 1m30 entre chaque multiplication. N'utilises pas ta calculette ! (" + (count + 1) + "/" + totalCount + " restantes)");

        if(isShutdown){
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    OggClip oggClip = new OggClip(Main.SPECIL_AUDIO[number-1]);
                    oggClip.play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
        String response = mainTextField.getText().replace("é", "e").replace("ô", "o").replace("è", "e").toUpperCase();
        String trueResponse = currentAnswer+"";
        boolean correct = true;
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : trueResponse.split(" ")) {
            if(!response.contains(s)){
                correct = false;
                stringBuilder.append(", "+s);
            }
        }
        if (correct) {
            grade++;
        } else {
            mistake += "\n------------------------------------\n" + "" + " : \n" +
                    "\tVotre réponse: " + mainTextField.getText() + "\n" +
                    "\tRéponse attendue: " + currentAnswer+"\n"+
                    "\tErreurs: "+ stringBuilder.substring(2);
        }
        mainTextField.setText("");
        nextQuestion();
    }
}
