package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Grades2Exercise extends AbstractExercise {

    private String currentAnswer;
    private Image currentImage;
    private List<String> list = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();

    public Grades2Exercise() {
        super("Armée de l'Air - Grades | Noms", 1024, 720);
        this.count = GradesExercise.GRADES_NAME.length;
        this.totalCount = GradesExercise.GRADES_NAME.length;

        for (int i = 0; i < GradesExercise.GRADES_NAME.length; i++) {
            map.put(GradesExercise.GRADES_NAME[i], i);
            list.add(GradesExercise.GRADES_NAME[i]);
        }
        Collections.shuffle(list);

        nextQuestion();
    }

    @Override
    protected void nextQuestion() {
        if (count == 0) {
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi " + grade + "/" + totalCount + " multiplications. " + (mistake.equals("") ? "" : "Voici vos erreurs:\n" + mistake.replace("\n\n", "")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        mainLabel.setText("");
        currentAnswer = list.get(random.nextInt(list.size()));
        list.remove(currentAnswer);
        currentImage = Main.GRADES[map.get(currentAnswer)];

        schedule(20);
        setHeadLabelText("Tu as 20s entre chaque question. (" + (count + 1) + "/" + totalCount + " restantes)");
        repaint();
    }


    @Override
    protected void customPaintComponent(Graphics g) {
        super.customPaintComponent(g);
        if(currentImage != null){
            int width = currentImage.getWidth(null)*2;
            int height = currentImage.getHeight(null)*2;
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
        return 100;
    }
}
