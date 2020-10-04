package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AcronymsAndDefinitionsExercise extends AbstractExercise {

    private final String[] acronyms = new String[] {"FMI", "PIM", "PAMAS G1", "FAMAS", "EIVV", "EVAA", "FMGO", "EPAA", "CFAMI", "EISPN", "EIV",
            "ETR", "CIET", "CSR", "COEA", "BITD", "ETO", "OAB", "CLEAM", "SCALP", "ASMP-A"};
    private final String[] definitions = new String[] {"Formation Militaire Initiale", "Période d'Incorporation Militaire",
            "Pistolet Automatique 9mm de la Manufacture d'Arme de Saint Etienne", "Fusil d'Assaut de la Manufacture d'Arme de Saint Etienne",
            "Escadron d'Instruction du Vol à Voile", "Ecole de Voltige de l'Armée de l'Air", "Formation Militaire Générale de l'Officier",
            "Ecole de Pilotage de l'Armée de l'Air", "Centre de Formation Aéronautique Militaire Initiale", "Escadron d'Instruction au Sol du Personnel Navigant",
            "Escadron d'Instruction en Vol", "Escadron de Transformation Rafale", "Centre d'Instruction des Equipages de Transport", "Centre de Simulation Rafale",
            "Cours des Officiers de l'Ecole de l'Air", "Basic Instrument Training Device", "Ecole de Transition Opérationnelle",
            "Outil d'Apprentissage Basique", "Centre d'Expertise Aérienne Militaire", "Système de Croisière conventionnel Autonome Longue Portée",
            "Air-Sol Moyenne Portée Amélioré"};

    private int currentQuestion = 0;

    private Iterator<Integer> questionsIterator;

    public AcronymsAndDefinitionsExercise() {
        super("Armée de l'Air - Sigles et Définitions", 800, 400);

        count = 8;
        totalCount = 8;

        mainLabel.setText("Calcul des questions en cours...");

        List<Integer> questionsId = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int id = random.nextInt(acronyms.length);
            if(questionsId.contains(id)){
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
        if(count == 0 || !questionsIterator.hasNext()){
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi "+grade+"/"+totalCount+" questions."+(mistake.equals("") ? "" : "Voici vos erreurs:\n"+mistake.replace("\n\n","")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;
        int id = questionsIterator.next();
        currentQuestion = id;

        mainLabel.setText(acronyms[id]+":");
        setHeadLabelText("Questions restantes: "+(count+1));
        schedule(30);
    }

    @Override
    protected void addComponents(JPanel panel) {

    }

    @Override
    protected void checkAnswer() {
        if(mainTextField.getText().toUpperCase().equalsIgnoreCase(definitions[currentQuestion].toUpperCase())){
            grade++;
        }else{
            mistake += "\n------------------------------------\n"+acronyms[currentQuestion]+" : \n" +
                    "\tVotre réponse: "+mainTextField.getText()+"\n" +
                    "\tRéponse attendue: "+definitions[currentQuestion];
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
        if(!mainTextField.getText().equalsIgnoreCase("")){
            checkAnswer();
        }
    }
}
