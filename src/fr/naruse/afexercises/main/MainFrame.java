package fr.naruse.afexercises.main;

import fr.naruse.afexercises.exercises.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    private final JButton info = new JButton("Informations");
    private final JButton acronymsAndDefinitions = new JButton("Sigles et Définitions");
    private final JButton divisions = new JButton("Divisions");
    private final JButton multiplications = new JButton("Multiplications");
    private final JButton enigmas = new JButton("Enigmes");
    private final JButton transportAircraft = new JButton("Avions de Transport & Stratégiques");
    private final JButton fighterJet = new JButton("Avions de Chasse");

    public MainFrame() {
        setTitle("Exercices de préparation aux tests EOPN de l'Armée de L'air");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Main.LOGO);
        setLayout(null);
        setResizable(false);

        acronymsAndDefinitions.setBounds(25, 10, 200, 25);
        acronymsAndDefinitions.addActionListener(this);
        add(acronymsAndDefinitions);
        divisions.setBounds(25, 10+25+10, 200, 25);
        divisions.addActionListener(this);
        add(divisions);
        multiplications.setBounds(25, 10+(25+10)*2, 200, 25);
        multiplications.addActionListener(this);
        add(multiplications);
        enigmas.setBounds(25, 10+(25+10)*3, 200, 25);
        enigmas.addActionListener(this);
        add(enigmas);
        transportAircraft.setBounds(25, 10+(25+10)*4, 200, 25);
        transportAircraft.addActionListener(this);
        add(transportAircraft);
        fighterJet.setBounds(500-200-25, 10, 200, 25);
        fighterJet.addActionListener(this);
        add(fighterJet);

        info.setBounds(500-150-20, 250-50, 150, 15);
        info.addActionListener(this);
        add(info);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        if(e.getSource() == info){
            new InfoFrame();
        }else if(e.getSource() == acronymsAndDefinitions){
            new AcronymsAndDefinitionsExercise();
        }else if(e.getSource() == divisions){
            new DivisionsExercise();
        }else if(e.getSource() == multiplications){
            new MultiplicationsExercise();
        }else if(e.getSource() == enigmas){
            new EnigmasExercise();
        }else if(e.getSource() == transportAircraft){
           new TransportAndStrategicAircraftExercise();
        }else if(e.getSource() == fighterJet){
            new FighterJetExercise();
        }
    }
}
