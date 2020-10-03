package fr.naruse.afexercises.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class InfoFrame extends JFrame implements ActionListener {

    private final JButton exit = new JButton("Retour");

    public InfoFrame() {
        setTitle("Exercices de préparation aux tests EOPN de l'Armée de L'air");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Main.LOGO);
        setLayout(null);
        setResizable(false);

        exit.setBounds(500-150-20, 250-50, 150, 15);
        exit.addActionListener(this);
        add(exit);

        setVisible(true);

        setMainLabel("Ce programme n'est en aucun cas relié de près ou de loin à l'Armée de l'Air. " +
                "Il a été créé par un passionné pour des passionnés." +
                " \nUn tel programme ne peut substituer les véritables aides d'apprentissage fournies\npar un conseiller proféssionnel de l'Armée." +
                " L'usage de ce programme doit rester personnel bien que gratuit. Le code source de celui-ci est disponible à cette addresse: \n<URL>https://github.com/NaruseII</URL>");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit){
            setVisible(false);
            Main.MAIN_FRAME.setVisible(true);
        }
    }

    private List<JLabel> labelList = new ArrayList<>();
    protected void setMainLabel(String statement) {
        for (JLabel jLabel : labelList) {
            jLabel.setVisible(false);
        }
        labelList.clear();
        if(statement == null){
            return;
        }

        List<String> list = new ArrayList<>();
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : statement.toCharArray()) {
            builder.append(c);
            if((i >= 50 && c == ' ') || c == '\n'){
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
            jLabel.setVisible(true);
            jLabel.setFont(jLabel.getFont().deriveFont(17f));
            if(s.contains("<URL>")){
                s = s.replace("<URL>", "").replace("</URL>", "");
                String finalS = s;
                jLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().browse(new URI(finalS));
                        } catch (Exception ex) {

                        }
                    }
                });
                jLabel.setForeground(new Color(172, 56, 21));
                jLabel.setText(s);
            }
            add(jLabel);
            jLabel.setBounds(getWidth()/2-jLabel.getGraphics().getFontMetrics().stringWidth(s)/2, getHeight()/3*2+20-5-(18*heightPlus), 800, 25);
            labelList.add(jLabel);
            heightPlus--;
        }
    }
}
