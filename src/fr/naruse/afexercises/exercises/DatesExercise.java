package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.util.*;

public class DatesExercise extends AbstractExercise {

    private String[] dates = new String[]{
            "1783",
            "1880",
            "1903",
            "1906",
            "1907",
            "25 juillet 1909",
            "23 septembre 1913",
            "1913",
            "1914",
            "1915",
            "1916",
            "Quels chasseurs ont été construit pour contrer les Fokker E3 ?",
            "Qu'est-ce que le Vickers Vimy ?",
            "As pendant la 1ère guerre mondiale", //
            "1919",
            "1921",
            "1924",
            "1927",
            "1930",
            "1932",
            "1934",
            "1935",
            "1936",
            "Meilleur avion français pendant la WW2 pour contrer les Bf 109",
            "Avion Attaque de Pearl Harbour",
            "Bombardier utilisé dans l’opération Doolittle contre le Japon",
            "Avion US dans la marine",
            "Bombardier puissant utilisé contre l’Allemagne",
            "Bombardier britanique reconnaissable",
            "Preuve de l’efficacité de l’aéronavale britannique",
            "Radar employé pour la 1ere fois",
            "1944",
            "As de la deuxième guerre mondiale",
            "6 aout 1945",
            "1945",
            "1947",
            "1948",
            "1953",
            "Années 50",
            "Année 60",
            "1969",
            "Que signifie V/stol",
            "Quel est le calibre du canon qui équipe le rafale"

    };
    private String[] definitions = new String[]{
            "1er vol ballon frères Montgolfier",
            "1er avion Clément Ader",
            "1er vol contrôlé frères Orville Wibur Wright Flyer",
            "1er vol controlé français Dumont",
            "1er vol hélicoptère Paul Cornu",
            "Traversée Manche Louis Blériot XI",
            "Traversée méditerranée Roland Garros",
            "1er Looping vol dos Adolphe Pégoud "+" Avro 504 triplan britanique",
            "Vickers gunbus 1er chasseur reconnaissance britanique",
            "Fokker E3 1er chasseur allemand tir à travers l'hélice",
            "Marcel Dassault hélice éclair",
            "SPAD VII "+" S.E.5",
            "Bombardier Lourd fin de la guerre transformé en civil",
            "Baron Rouge Fokker DRI "+"Georges Guynemer escadrille Cigogne "+"Billy Bishop canadien "+"René Fonk "+"Charles Nungesser ", //
            "Traversée Atlantique Irlande Groenland",
            "Traversée Cordillère des Andes Adrienne Bolland",
            "Tour Monde 5 mois",
            "Tentative traversée Atlantique Nord Nungesser Coli Oiseau Blanc " + " Traversée Atlantique Nord Charles Lindbergh Spirit of Saint Louis",
            "Traversée Atlantique Nord Costes Bellonte Breguet XIX " + " Traversé aéropostale Atlantique Sud par Mermoz en Laté 28 hydravion "+" Junkers Ju 52"+ " Exploit Henry Guillaumet Andes Potez 25",
            "1ere traversée féminine Atlantique Amélia Earhart",
            "Indépendance Armée Air",
            "1er vol DC-3" + " Messerschmitt Bf 109 2eme avion plus produit "+" Hawker Hurricane déployé bataille angleterre",
            "Spitfire "+ "Disparition Mermoz Atlantique Laté 300 "+" 1ere traversée atlantique sud aviatrice Maryse Bastié",
            "Dewoitine D 520",
            "A6M Zero",
            "B-25 Mitchell",
            "Chance Vought F4U Corsair",
            "Avro Lancaster",
            "DH 98 Mosquito",
            "Swordfish Biplan",
            "Bataille Angleterre",
            "Disparition Antoine de Saint Exupéry "+ " Fondation Organisation Aviation Civile International "+"1er chasseur réaction Messerschmitt Me 262 "+ "Globster Meteor angleterre",
            "Erich Hartmann allemand 352 victoires "+" Pierre Closterman français "+" Richard Bong américain "+" Grégory Boyington américain",
            "1ere bombe nucléair hiroshima",
            "Charte Nations Unies",
            "1er franchissement mur du son Chuck Yeager Bell X-1",
            "Vickers Viscount 1er avion turbopropulseur",
            "Création patrouille de france",
            "Avro Vulcan "+" B-52 "+" F-80 "+" F-86 "+" Mig-15",
            "B-58 bombardier supersonique "+" Lockheed U2 30km vertical "+ "missile air-air "+" MD F-4 Phantom II",
            "1er vol du concorde",
            "Vertical short take-off landing",
            "30mm"
    };

    private int currentQuestion = 0;

    private Iterator<Integer> questionsIterator;

    public DatesExercise(boolean reversed) {
        super("Armée de l'Air - Dates "+(reversed ? "(Evénement -> Date)" : "(Date -> Evénement)"), 800, 400);

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
