package fr.naruse.afexercises.exercises;

import fr.naruse.afexercises.component.TexturedProgressBar;
import fr.naruse.afexercises.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.Timer;

public abstract class AbstractExercise extends JFrame {

    protected final TexturedProgressBar progressBar = new TexturedProgressBar();
    protected final Timer timer = new Timer();
    protected final Random random = new Random();

    protected final JLabel headLabel = new JLabel();
    protected final JLabel mainLabel = new JLabel();
    protected final JTextField mainTextField = new JTextField();

    protected int grade = 0;
    protected String mistake = "";
    protected int count = 4, totalCount = 4;

    private boolean isShutdown = false;

    public AbstractExercise(String title, int width, int height) {
        setTitle(title);
        if(width == -1){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = screenSize.width;
            height = screenSize.height;
        }
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                Main.MAIN_FRAME.setVisible(true);
                isShutdown = true;
            }
        });
        setIconImage(Main.LOGO);
        setLayout(null);
        setResizable(false);

        setContentPane(new AbstractExercisePanel(width, height));

        setVisible(true);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if(e.getKeyCode() == 10 && isVisible()){
                enterPressedEvent();
            }
            return false;
        });
    }

    protected abstract void nextQuestion();

    protected abstract void addComponents(JPanel panel);

    protected abstract void timerEndEvent();

    protected abstract void enterPressedEvent();

    protected abstract void checkAnswer();

    protected void writeErrors(){
        if(mistake.replace("\n\n", "").equalsIgnoreCase("")){
            return;
        }
        Date date = Date.from(Instant.now());
        File ff = new File("logs");
        File f = new File(ff, "["+getClass().getSimpleName()+"] day "+date.getDate()+" "+date.getHours()+"h"+date.getMinutes()+".txt");
        try {
            ff.mkdir();
            f.createNewFile();
            FileWriter wirter = new FileWriter(f);
            wirter.write(mistake.replace("\n\n", ""));
            wirter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void customPaintComponent(Graphics g){ }

    private int millis = 0;
    private TimerTask taskInUse;
    protected void schedule(int delay){
        if(isShutdown){
            return;
        }
        if(taskInUse != null){
            taskInUse.cancel();
        }
        if(delay == -1){
            progressBar.setMaximum(delay);
            progressBar.setValue(0);
            return;
        }

        delay *= 1000;
        if(millis == 0){
            progressBar.setMaximum(delay);
            progressBar.setValue(0);
        }
        timer.purge();
        millis = delay;

        timer.scheduleAtFixedRate(taskInUse = new TimerTask() {
            @Override
            public void run() {
                if(millis > 0){
                    millis -= 1;
                    progressBar.setValue(millis);
                }else{
                    millis = 0;
                    progressBar.setValue(0);
                    timerEndEvent();
                    cancel();
                }
            }
        }, 1, 1);
    }

    protected void setHeadLabelText(String text){
        if(text == null){
            headLabel.setVisible(false);
            return;
        }
        headLabel.setText(text);
        headLabel.setBounds(getWidth()/2-headLabel.getGraphics().getFontMetrics().stringWidth(text)/2, 15, getWidth(), 25);
        headLabel.setVisible(true);
    }


    protected void setMainLabel(String statement) {
        setMainLabel(statement, 0);
    }

    private List<JLabel> labelList = new ArrayList<>();
    protected void setMainLabel(String statement, int downHeight) {
        for (JLabel jLabel : labelList) {
            jLabel.setVisible(false);
        }
        labelList.clear();
        if(statement == null){
            mainLabel.setVisible(false);
            return;
        }

        List<String> list = new ArrayList<>();
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : statement.toCharArray()) {
            builder.append(c);
            if(i >= 50 && c == ' '){
                i = 0;
                list.add(builder.toString());
                builder = new StringBuilder();
            }
            i++;
        }
        list.add(builder.toString());

        int heightPlus = list.size();
        for (String s : list) {
            JLabel jLabel = new JLabel();
            jLabel.setText(s);
            jLabel.setBounds(getWidth()/2-headLabel.getGraphics().getFontMetrics().stringWidth(s)/2, getHeight()/2-5-(23*heightPlus)+downHeight, getWidth(), 25);
            jLabel.setVisible(true);
            jLabel.setFont(jLabel.getFont().deriveFont(20f));
            getContentPane().add(jLabel);
            labelList.add(jLabel);
            heightPlus--;
        }
        mainLabel.setVisible(false);
    }

    public int getAddedY(){
        return 0;
    }

    private class AbstractExercisePanel extends JPanel {

        public AbstractExercisePanel(int width, int height) {
            setLayout(null);
            progressBar.setBounds(18, height-60, width-25-20, 15);
            progressBar.setBackground(new Color(88, 85, 85));
            progressBar.setVisible(true);
            add(progressBar);

            headLabel.setFont(headLabel.getFont().deriveFont(18f));
            headLabel.setVisible(false);
            add(headLabel);

            mainLabel.setText("...");
            mainLabel.setBounds(120, height/2-40, 900, 25);
            mainLabel.setVisible(true);
            mainLabel.setFont(mainLabel.getFont().deriveFont(20f));
            add(mainLabel);

            mainTextField.setBounds(width/2-200, getAddedY() == -1 ? height-100 : height/2+80+getAddedY(), 400, 25);
            mainTextField.setFont(mainTextField.getFont().deriveFont(17f));
            mainTextField.setVisible(true);
            add(mainTextField);

            addComponents(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            progressBar.paintComponent(g);
            customPaintComponent(g);
        }
    }
}
