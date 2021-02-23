package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlanesExercise extends AbstractExercise {
    private String currentAnswer;
    private Image currentImage;
    private List<String> list = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();

    public static final String[] PLANES = new String[]{
            "MIG-29", "MIG-31", "MIG-35", "SU-27", "SU-30", "SU-35", // Russia
            "a400m", "c135", "casa235", "hercules", "mirage 2000", "rafale", "sdca", "tbm", "transall"//FRANCE
            };

    public PlanesExercise() {
        super("Armée de l'Air - Aéronefs | Noms", -1, -1);
        this.count = PLANES.length;
        this.totalCount = PLANES.length;

        for (int i = 0; i < PLANES.length; i++) {
            map.put(PLANES[i], i);
            list.add(PLANES[i]);
        }
        Collections.shuffle(list);

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if (count == 0) {
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi " + grade + "/" + totalCount + " questions. " + (mistake.equals("") ? "" : "Voici vos erreurs:\n" + mistake.replace("\n\n", "")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        mainLabel.setText("");
        currentAnswer = list.get(random.nextInt(list.size()));
        list.remove(currentAnswer);
        currentImage = Main.PLANES[map.get(currentAnswer)];

        schedule(30);
        setHeadLabelText("Tu as 30s entre chaque question. (" + (count + 1) + "/" + totalCount + " restantes)");
        repaint();
    }


    @Override
    protected void customPaintComponent(Graphics g) {
        super.customPaintComponent(g);
        if(currentImage != null){
            int width = currentImage.getWidth(null);
            int height = currentImage.getHeight(null);
            g.drawImage(currentImage, getWidth()/2-width/2, getHeight()/2-height/2, width, height, null);
        }
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
        if (!mainTextField.getText().equalsIgnoreCase("")) {
            checkAnswer();
        }
    }

    @Override
    protected void checkAnswer() {
        if (mainTextField.getText().replace("é", "e").toUpperCase().replace("-", " ").equalsIgnoreCase(currentAnswer.replace("é", "e").toUpperCase().replace("-", " "))) {
            grade++;
        } else {
            mistake += "\n------------------------------------\n" + count+": \n" +
                    "\tVotre réponse: " + mainTextField.getText() + "\n" +
                    "\tRéponse attendue: " + currentAnswer;
        }
        mainTextField.setText("");
        nextQuestion();
    }

    @Override
    public int getAddedY() {
        return -1;
    }
}
