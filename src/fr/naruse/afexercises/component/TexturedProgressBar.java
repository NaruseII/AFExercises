package fr.naruse.afexercises.component;

import javax.swing.*;
import java.awt.*;

public class TexturedProgressBar extends JComponent {
    private int maximum = 100, value = 0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!isVisible()){
            return;
        }
        if(value == 0){
            g.setColor(getBackground());
            g.fillRect(getX(), getY(), getWidth(), getHeight());
            return;
        }
        if(value >= maximum){
            g.setColor(new Color(194, 57, 16));
            g.fillRect(getX(), getY(), getWidth(), getHeight());
            return;
        }
        int width = value*getWidth()/maximum;
        g.setColor(new Color(194, 57, 16));
        g.fillRect(getX(), getY(), width, getHeight());
        g.setColor(getBackground());
        g.fillRect(getX()+width, getY(), getWidth()-width, getHeight());
    }

    public int getMaximum() {
        return maximum;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        repaint();
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
        repaint();
    }
}
