package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.util.*;

public class EnglishVocabularyExercise extends AbstractExercise {

    private String[] dates = new String[]{
            "matches",
            "tobacconist",
            "put out a fire",
            "thirsty",
            "harvest",
            "abroad",
            "buoy",
            "strike",
            "strick"
    };
    private String[] definitions = new String[]{
            "allumettes",
            "bureau de tabac",
            "éteindre un feu",
            "assoiffé",
            "récolte",
            "partir à l'étranger",
            "bouée",
            "grève",
            "frapper"
    };

    private int currentQuestion = 0;

    private Iterator<Integer> questionsIterator;

    public EnglishVocabularyExercise(boolean reversed) {
        super("Armée de l'Air - Anglais Vocabulaire (Traduction)", 800, 400);

        if(reversed){
            String[] dates2 = Arrays.copyOf(dates, dates.length);
            dates = definitions;
            definitions = dates2;
        }

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
            writeErrors();
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;
        int id = questionsIterator.next();
        currentQuestion = id;

        mainLabel.setText(dates[id] + ":");
        setHeadLabelText("Questions restantes: " + (count + 1));
        schedule(120);
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
