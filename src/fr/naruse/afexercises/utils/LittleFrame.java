package fr.naruse.afexercises.utils;

import fr.naruse.afexercises.main.Main;

import javax.swing.*;

public class LittleFrame {

    private final JLabel label = new JLabel("Chargement...");
    private final JLabel label2 = new JLabel("Chargement...");
    private final JLabel label3 = new JLabel();
    private final JFrame jFrame = new JFrame();

    public LittleFrame(String title) {
        jFrame.setTitle(title);
        jFrame.setSize(500, 200);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setIconImage(Main.LOGO);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        label.setFont(label.getFont().deriveFont(17f));
        jFrame.add(label);
        label2.setFont(label2.getFont().deriveFont(17f));
        jFrame.add(label2);
        label3.setFont(label3.getFont().deriveFont(17f));
        jFrame.add(label3);
        jFrame.setVisible(true);
    }

    public void hide(){
        jFrame.setVisible(false);
    }

    public void setText(String text){
        label.setBounds(jFrame.getWidth()/2-label.getGraphics().getFontMetrics().stringWidth(text)/2, 15, jFrame.getWidth(), 25);
        label.setText(text);
    }

    public void setText2(String text){
        label2.setBounds(jFrame.getWidth()/2-label2.getGraphics().getFontMetrics().stringWidth(text)/2, 40, jFrame.getWidth(), 25);
        label2.setText(text);
    }

    public void setText3(String text){
        label3.setBounds(jFrame.getWidth()/2-label3.getGraphics().getFontMetrics().stringWidth(text)/2, 40, jFrame.getWidth(), 25);
        label3.setText(text);
    }
}
