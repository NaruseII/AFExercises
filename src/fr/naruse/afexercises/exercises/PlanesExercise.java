package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlanesExercise extends AbstractExercise {
    private String currentAnswer;
    private Image currentImage;
    private List<String> list = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();

    /*public static final String[] PLANES = new String[]{
            "MIG-29", "MIG-31", "MIG-35", "SU-27", "SU-30", "SU-35", // Russia
            "a400m", "c135", "casa235", "hercules", "mirage 2000", "rafale", "sdca", "tbm", "transall"//FRANCE
            };*/
    public static final String[] PLANES = new String[]{
            "f-22", "f-15", "f-16", "f-18", // USA
            "MIG-31", "SU-27", "SU-30", "SU-33", "SU-35", "MIG-35",// Russia
            "mirage 2000", "rafale", //FRANCE
            "JAS-39-Gripen", //SUEDE
            "eurofighter typhoon", //OTHER
            "Jh-7", "j-11", "j-10", "j-15" //CHINE
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
            AtomicInteger width = new AtomicInteger(currentImage.getWidth(null));
            AtomicInteger height = new AtomicInteger(currentImage.getHeight(null));
            resize(width, height);
            g.drawImage(currentImage, getWidth()/2-width.get()/2, getHeight()/2-height.get()/2, width.get(), height.get(), null);
        }
    }

    private void resize(AtomicInteger width, AtomicInteger height){
        while (width.get() > getWidth()-120 || height.get() > getHeight()-120){
            width.set((int) (width.get()*0.95));
            height.set((int) (height.get()*0.95));
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
