package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;

public class SECPILExercise extends AbstractExercise {

    private int currentAnswer;

    public SECPILExercise() {
        super("Armée de l'Air - SECPIL", 800, 400);
        count = 15;
        totalCount = 15;
        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if(count == 0){
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi " + grade + "/" + totalCount + " questions." + (mistake.equals("") ? "" : "Voici vos erreurs:\n" + mistake.replace("\n\n", "")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        int number = random.nextInt(10)+1;

        mainLabel.setText("+" +number+" =");
        currentAnswer += number;

        schedule(5);
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
