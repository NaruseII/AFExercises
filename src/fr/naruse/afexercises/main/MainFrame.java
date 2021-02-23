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
    private final JButton grades = new JButton("Grades");
    private final JButton mentalArithmetic = new JButton("Calcul Mental");
    private final JButton advancedMentalArithmetic = new JButton("Calcul Mental Avancé");
    private final JButton mentalMultiplications = new JButton("Multiplications Mentales");
    private final JButton grades2 = new JButton("Noms des Grades");
    private final JButton grades3 = new JButton("Grades Ordre");
    private final JButton dates = new JButton("Dates (Date -> Evènement)");
    private final JButton dates2 = new JButton("Dates (Evènement -> Date)");
    private final JButton opex = new JButton("Opex");
    private final JButton planes = new JButton("Aéronefs");

    public MainFrame() {
        setTitle("Exercices de préparation aux tests EOPN de l'Armée de L'air");
        setSize(500, 280+(25+15)*2+5);
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
        /*enigmas.setBounds(25, 10+(25+10)*3, 200, 25);
        enigmas.addActionListener(this);
        add(enigmas);
        transportAircraft.setBounds(25, 10+(25+10)*4, 200, 25);
        transportAircraft.addActionListener(this);
        add(transportAircraft);*/
        grades2.setBounds(25, 10+(25+10)*3, 200, 25);
        grades2.addActionListener(this);
        add(grades2);
        dates.setBounds(25, 10+(25+10)*4, 200, 25);
        dates.addActionListener(this);
        add(dates);
        opex.setBounds(25, 10+(25+10)*5, 200, 25);
        opex.addActionListener(this);
        add(opex);


        /*fighterJet.setBounds(500-200-25, 10, 200, 25);
        fighterJet.addActionListener(this);
        add(fighterJet);*/
        grades.setBounds(500-200-25, 10, 200, 25);
        grades.addActionListener(this);
        add(grades);
        mentalArithmetic.setBounds(500-200-25, 10+(25+10)*1, 200, 25);
        mentalArithmetic.addActionListener(this);
        add(mentalArithmetic);
        advancedMentalArithmetic.setBounds(500-200-25, 10+(25+10)*2, 200, 25);
        advancedMentalArithmetic.addActionListener(this);
        add(advancedMentalArithmetic);
        mentalMultiplications.setBounds(500-200-25, 10+(25+10)*3, 200, 25);
        mentalMultiplications.addActionListener(this);
        add(mentalMultiplications);
        grades3.setBounds(500-200-25, 10+(25+10)*4, 200, 25);
        grades3.addActionListener(this);
        add(grades3);
        dates2.setBounds(500-200-25, 10+(25+10)*5, 200, 25);
        dates2.addActionListener(this);
        add(dates2);
        planes.setBounds(500-200-25, 10+(25+10)*6, 200, 25);
        planes.addActionListener(this);
        add(planes);

        info.setBounds(500-150-20, 280-50+25*3, 150, 15);
        info.addActionListener(this);
        add(info);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Main.PLANES[0] == null){
            JOptionPane.showMessageDialog(this, "Le programme est en train de chager ses resousrces ! Patientez quelques secondes...");
            return;
        }
        setVisible(false);
        if(e.getSource() == info){
            new InfoFrame();
        }else if(e.getSource() == acronymsAndDefinitions){
            new AcronymsAndDefinitionsExercise();
        }else if(e.getSource() == divisions){
            new DivisionsExercise();
        }else if(e.getSource() == multiplications){
            new MultiplicationsExercise();
        }else if(e.getSource() == grades){
            new GradesExercise();
        }else if(e.getSource() == mentalArithmetic){
            new MentalArithmeticExercise(false);
        }else if(e.getSource() == advancedMentalArithmetic){
            new MentalArithmeticExercise(true);
        }else if(e.getSource() == mentalMultiplications){
            new MentalMultiplicationExercise();
        }else if(e.getSource() == grades2){
            new Grades2Exercise();
        }else if(e.getSource() == grades3){
            new Grades3Exercise();
        }else if(e.getSource() == dates){
            new DatesExercise(false);
        }else if(e.getSource() == dates2){
            new DatesExercise(true);
        }else if(e.getSource() == opex){
            new OpexExercise();
        }else if(e.getSource() == planes){
            new PlanesExercise();
        }
    }
}
