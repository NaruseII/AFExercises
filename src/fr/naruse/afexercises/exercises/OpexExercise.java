package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.util.*;

public class OpexExercise extends AbstractExercise {

    private String[] dates = new String[]{
            "2013 -> 2014",
            "2013 -> 2016",
            "1er aout 2014 -> en cours",
            "20 septembre 2014 -> en cours",
            "Nuit du 13 au 14 avril 2018",
            "19 mars 2011 -> 31 octobre 2011",
            "1971 -> 1971"
    };
    private String[] definitions = new String[]{
            "Opération Serval soutient mali rebelles islamistes",
            "Operation Sangaris désarm seleka anti balaka république centreafricaine",
            "Opération Barkhane menace terroriste sahel formation armée locale",
            "Opération Chammal Irak Syrie etat islamique",
            "Opération Hamilton contre Bachar el assad contre attaque chimique",
            "Opération Harmattan intervention libye guerre civile",
            "Opération Omega aide humanitaire génocide bangladesh"
    };

    private int currentQuestion = 0;

    private Iterator<Integer> questionsIterator;

    public OpexExercise() {
        super("Armée de l'Air - Opex", 800, 400);

        count = dates.length;
        totalCount = count;

        mainLabel.setText("Calcul des questions en cours...");

        List<Integer> questionsId = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int id = random.nextInt(dates.length);
            if (questionsId.contains(id)) {
                i--;
                continue;
            }
            questionsId.add(id);
        }
        Collections.shuffle(questionsId);
        questionsIterator = questionsId.iterator();

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if (count == 0 || !questionsIterator.hasNext()) {
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi " + grade + "/" + totalCount + " questions." + (mistake.equals("") ? "" : "Voici vos erreurs:\n" + mistake.replace("\n\n", "")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;
        int id = questionsIterator.next();
        currentQuestion = id;

        mainLabel.setText(dates[id] + ":");
        setHeadLabelText("Questions restantes: " + (count + 1));
        schedule(60);
    }

    @Override
    protected void addComponents(JPanel panel) {

    }

    @Override
    protected void checkAnswer() {
        String response = mainTextField.getText().replace("é", "e").replace("ô", "o").replace("è", "e").toUpperCase();
        String trueResponse = definitions[currentQuestion].replace("é", "e").replace("ô", "o").replace("è", "e").toUpperCase();
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
            mistake += "\n------------------------------------\n" + dates[currentQuestion] + " : \n" +
                    "\tVotre réponse: " + mainTextField.getText() + "\n" +
                    "\tRéponse attendue: " + definitions[currentQuestion]+"\n"+
                    "\tErreurs: "+ stringBuilder.substring(2);
        }
        mainTextField.setText("");
        nextQuestion();
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
}
