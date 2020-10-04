package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractMCQExercise extends AbstractExercise {
    protected List<MCQ> mcqList;
    private Iterator<MCQ> iterator;
    protected MCQ currentMCQ;

    private int schedule;

    public AbstractMCQExercise(String title, int schedule, int questionCount) {
        super(title, 1024, 720);
        this.count = questionCount;
        this.totalCount = questionCount;
        this.schedule = schedule;
        this.mainTextField.setVisible(false);
        this.mcqList = buildMCQs();
        Collections.shuffle(mcqList);
        this.nextQuestion();
    }

    public AbstractMCQExercise(String title) {
        this(title, 15, 4);
    }

    public boolean nextMCQ(){
        if(currentMCQ != null){
            currentMCQ.disable();
        }else{
            iterator = mcqList.iterator();
        }
        if(!iterator.hasNext()){
            currentMCQ = null;
            return false;
        }
        currentMCQ = iterator.next();
        currentMCQ.registerComponent((JPanel) getContentPane());
        repaint();
        return true;
    }

    @Override
    protected void nextQuestion() {
        if(count == 0){
            schedule(-1);
            JOptionPane.showMessageDialog(this, "Exercice terminé ! Vous avez réussi "+grade+"/"+totalCount+" questions."+(mistake.equals("") ? "" : "Voici vos erreurs:\n"+mistake.replace("\n\n","")));
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
            return;
        }
        count--;

        schedule(schedule);
        setHeadLabelText("Tu as "+schedule+"s entre chaque questions. ("+(count+1)+"/"+totalCount+" restantes)");
        if(!nextMCQ()){
            count = 0;
            nextQuestion();
            return;
        }

        setMainLabel(currentMCQ.question, 170);
    }

    @Override
    protected void customPaintComponent(Graphics g) {
        if(currentMCQ != null){
            currentMCQ.paintComponent(g);
        }
    }

    protected abstract List<MCQ> buildMCQs();

    public class MCQ implements ActionListener {
        protected final String question;
        protected final String[] answers;
        private final JButton[] buttons;
        private final QuestionChoiceType goodAnswer;
        private boolean isDisabled = false;
        public MCQ(String question, String answer1, String answer2, String answer3, String answer4, QuestionChoiceType goodAnswer) {
            this.question = question;
            this.answers = new String[]{answer1, answer2, answer3, answer4};
            this.buttons = new JButton[]{new JButton(answer1), new JButton(answer2), new JButton(answer3), new JButton(answer4)};
            this.goodAnswer = goodAnswer;
        }

        public MCQ(String question, String answer, String[] randomAnswers) {
            this.question = question;
            this.answers = new String[4];
            List<String> list = new ArrayList<>();
            list.add(answer);
            while (list.size() < 4){
                String s = randomAnswers[random.nextInt(randomAnswers.length)];
                if(!answer.equals(s)){
                    list.add(s);
                }
            }
            Collections.shuffle(list);
            int goodAnswerId = 0;
            for (int i = 0; i < list.size(); i++) {
                answers[i] = list.get(i);
                if(list.get(i).equals(answer)){
                    goodAnswerId = i;
                }
            }
            this.buttons = new JButton[]{new JButton(answers[0]), new JButton(answers[1]), new JButton(answers[2]), new JButton(answers[3])};
            this.goodAnswer = QuestionChoiceType.byId(goodAnswerId);
        }

        public void disable(){
            for (JButton button : buttons) {
                button.setVisible(false);
            }
            isDisabled = true;
        }

        public void paintComponent(Graphics g){

        }

        public void buttonPushed(QuestionChoiceType type){
            if(type == goodAnswer){
                grade++;
            }else{
                mistake += "\n------------------------------------\n"+question+" : \n" +
                        "\tVotre réponse: "+buttons[type.getId()].getText()+"\n" +
                        "\tRéponse attendue: "+answers[goodAnswer.getId()];
            }
            nextQuestion();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isDisabled){
                return;
            }
            if(e.getSource() == buttons[0]){
                buttonPushed(QuestionChoiceType.ONE);
            }else if(e.getSource() == buttons[1]){
                buttonPushed(QuestionChoiceType.TWO);
            }else if(e.getSource() == buttons[2]){
                buttonPushed(QuestionChoiceType.THREE);
            }else if(e.getSource() == buttons[3]){
                buttonPushed(QuestionChoiceType.FOUR);
            }
        }

        public void registerComponent(JPanel panel){
            buttons[0].setBounds(getWidth()/4-100-50, getHeight()-150, 200, 25);
            buttons[1].setBounds(getWidth()/4+100+7-50, getHeight()-150, 200, 25);
            buttons[2].setBounds(getWidth()/4+200+110+3-50, getHeight()-150, 200, 25);
            buttons[3].setBounds(getWidth()/4+200*2+110+10-50, getHeight()-150, 200, 25);

            for (JButton button : buttons) {
                button.addActionListener(this);
                panel.add(button);
            }
        }
    }

    enum QuestionChoiceType{
        ONE(0), TWO(1), THREE(2), FOUR(3);

        private int id;
        QuestionChoiceType(int i) {
            this.id = i;
        }

        public int getId() {
            return id;
        }

        public static QuestionChoiceType byId(int id){
            switch (id){
                case 0: return ONE;
                case 1: return TWO;
                case 2: return THREE;
                case 3: return FOUR;
            }
            return null;
        }
    }
}
