package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.util.*;

public class CultureExercise extends AbstractExercise {

    private String[] dates = new String[]{
            "En quelle année Neil Amstrong posa t-il les pieds sur la Lune ? ",
            "Quel rapport désigne-t-on par \"finesse de l'avion\" ?",
            "En armement, quelle est la signification française du sigle AASM ?",
            "Avion américain conçu contre la menace nazi",
            "Son nom \"officiel\" était \"1st American Volunteer Group\".",
            "Quel danger y a-t-il de voler en air agité à la vitesse minimale autorisée pour des conditions normales ?",
            "L'expansion allemande est arrêtée à Stalingrad en",
            "Combien la France a-t-elle de sièges au parlement européen ?",
            "Quel spationaute français a fait 112 tours de la terre dans la navette ' Discovery ', entre le 17 et le 24 juin 1985 ?",
            "Le MiG-23 a comme tous les avions ex-Soviétiques, un nom de code OTAN, lequel ?",
            "Sur quel avion la PAF n’a pas volé ?",
            "Quel pilote américain est titulaire du plus grand nombre de victoires pendant la 2ème guerre mondiale ?",
            "Quelle est la signification du mot “Barkhane”, nom de l’opération militaire lancée au Sahel en 2014 ?",
            "A quelle date est arrivé le premier KC-130J en France ?",
            "Qui a dirigé la Patrouille de France en 2010 ?",
            "Mis en service en 1938, c'est, avec 1000 exemplaires produits, le chasseur le plus utilisé par l'armée française; mais il est dépassé - surtout en vitesse - par le Messerschmitt allemand. De quel avion s'agit-il ?",
            "Comment appelle-t-on le point d'application de la résultante des poids, c'est-à-dire du poids total ?",
            "Qu’est ce que l’Immelmann ?",
            "Les pouvoirs exceptionnels du président de la République sont:",
            "Quel est le rapport entre la traction et la traînée en vol de croisière rectiligne horizontal à vitesse constante ?",
            "L’été 2016 a vu la création de",
            "Le suffrage universel pour l'élection du Président de la République date de",
            "Comment réagit un avion stabilisé en vol de croisière horizontal lorsqu'on diminue la puissance du moteur ?",
            "Quel est le prix unitaire d'un B-2 'Spirit' ?",
            "Le service Militaire Volontaire est organisé autour d’un commandement situé à Arcueil et de trois centres respectivement localisés à :",
            "Conçu comme le précédent en 1934-35, ce bombardier, également dépassé, constitue l'ossature de l'aviation française de bombardement à la déclaration de guerre. De quel avion s'agit-il ?",
            "La vitesse de décrochage d'un avion est-elle influencée par le poids de l'avion ?",
            " Sur quel avion la patrouille de France a t'elle volé en premier ?",
            "Cmbien coute un f-22 raptor",
            "Mis en service en septembre 1939, il constitue - avec le précédent - l'ossature de l'aviation de chasse au début de l'année 40. De quel avion s'agit-il ?",
            "Le décret n° 2015-853 du 13 juillet 2015 porte sur la création de la",
            "Z comme",
            "Comment traduit-on en anglais le mot “verrière”, élément constitutif d’un avion de chasse ?",
            "Quel est le plus grand département français (France métropolitaine)?",
            "Cet avion avait la particularité de ne pas être armé vers l'avant mais vers l'arrière. Il était équipé d'une tourelle située derrière le cockpit ! Cet avion fut très vite retiré du service en tant que chasseur de jour. Il vit sa fin de service actif en 1942 ensuite",
            "Sur quelle base sont basés les 64th Agressor Squadron et 65th Agressor Squadron ?",
            "Dans le contrat opérationnel de l’armée de Terre, les missions permanentes sont regroupées en",
            "Mis en service en 1938, ce bombardier moyen, rapide, était l'un des appareils de conception moderne de l'aviation française. De quel avion s'agit-il ?",
            "Qu'est ce que le F-34 ?",
            "Depuis 1979, le parlement européen comprend combien de membres",
            "Quelle est la particularité du futur F-35 ?",
            "De quoi dépend le rapport entre la portance et la traînée pour un profil d'aile déterminé ?",
            "L’opération Omega au printemps 2015?",
            "Comment réagit un avion stabilisé en vol de croisière horizontal lorsqu'on diminue la puissance du moteur ?",
            "Qu'entend-on par angle d'attaque ?",
            "De quel bombardier de Boeing ont été larguées les bombes nucléaires sur Hiroshima et Nagasaki ?",
            "En quelle année le régiment de chasse «Normandie Niemen» a-t-il vu le jour ?",
            "En France la majorité à 18 ans a été votée en"
    };
    private String[] definitions = new String[]{
            "1969",
            "portance trainée",
            "Armement Air-Sol Modulaire",
            "Lockheed p-38 Lightning",
            "Les Tigres Volants",
            "Décrochage des filets d'air",
            "1942",
            "74",
            "Patrick Baudry",
            "Flogger",
            "Sportavia",
            "Richard Bong",
            "Dune de sable croissant",
            "19 septembre 2019",
            "Virginie Guyot",
            "Morane-Saulnier MS 406",
            "Centre de gravité",
            "acrobatie aérienne",
            "pleins pouvoirs article 16",
            "traction et traînée égales",
            "académie des Forces Spéciales.",
            "1962",
            "descendre même vitesse",
            "2.2 milliards dollars",
            "Montigny-lès-Metz Brétigny-sur-Orge La Rochelle",
            "Bloch MB 210",
            "Oui augmente avec poids",
            "Mystère IV",
            "360 millions dollars",
            "Bloch MB 152",
            "Médaille protection militaire territoire",
            "Zulu",
            "Canopy",
            "Gironde",
            "Boulton Paul Defiant",
            "Nellis AFB",
            "Alerte Protection Prévention",
            "Lioré et Olivier LéO 451",
            "carburéacteur",
            "766",
            "Il est STOVL",
            "Angle d'attaque",
            "reconversion blessé psychologique",
            "descend conservant même vitesse",
            "L'angle formé par la corde du profil de l'aile et la direction de l'écoulement des filets d'air",
            "B-29 Superfortress",
            "1942",
            "1974"
    };

    private int currentQuestion = 0;

    private Iterator<Integer> questionsIterator;

    public CultureExercise() {
        super("Armée de l'Air - Culture", 800, 400);

        count = dates.length;
        totalCount = count;

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


        setMainLabel(dates[id] + ":");
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
