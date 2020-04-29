package ca.nscc.Interface;

import ca.nscc.Classes.Cleric;
import ca.nscc.Classes.Warrior;
import ca.nscc.Classes.Wizard;
import ca.nscc.Monster;

import javax.swing.*;
import java.awt.*;

public class BattleScreen extends JPanel {
    private JPanel panel;
    private String playerClass;
    private JButton playAgain;

    public BattleScreen(Creation creationScreen) {
        this.setLayout(null);
        Monster monster = new Monster("Subpar Chef", 100, 20, 1, 40);

        //Gets all 3 classes from the creationScreen
        Cleric cleric = creationScreen.getCleric();
        Warrior warrior = creationScreen.getWarrior();
        Wizard wizard = creationScreen.getWizard();

        JLabel title = new JLabel("Battle To The Death!");
        title.setFont(new Font("Californian FB", Font.PLAIN, 60));
        title.setForeground(new Color(46, 48, 58));
        title.setBounds(75,15,600,100);

        JLabel playerTitle = new JLabel();
        JLabel playerClassImage = new JLabel();

        String selectedClass = "";

        JLabel monsterTitle = new JLabel("Subpar Chef");
        JLabel monsterClassImage = new JLabel();
        JTextArea textfield = new JTextArea();

        textfield.setEditable(false);
        textfield.setFont(new Font("Californian FB", Font.PLAIN, 16));
        textfield.setBounds(85,325,500, 200);

        //Checks if the cleric object has been created. If not discard the error and continue
        try {
            if (cleric.checkActive()) {
                selectedClass = "cleric";
                playerTitle.setText("Player: " + cleric.getClassName());
                textfield.setText(createCharacterString(cleric) + createMonsterString(monster));
            }
        } catch (Exception ignored) {}

        //Checks if the warrior object has been created. If not discard the error and continue
        try {
            if (warrior.checkActive()) {
                selectedClass = "warrior";
                playerTitle.setText("Player: " + warrior.getClassName());
                textfield.setText(createCharacterString(warrior) + createMonsterString(monster));
            }
        } catch (Exception ignored ) {}

        //Checks if the wizard object has been created. If not discard the error and continue
        try {
            if (wizard.checkActive()) {
                selectedClass = "wizard";
                playerTitle.setText("Player: " + wizard.getClassName());
                textfield.setText(createCharacterString(wizard) + createMonsterString(monster));
            }
        } catch (Exception ignored) {}

        playerTitle.setFont(new Font("Californian FB", Font.PLAIN, 20));
        playerTitle.setForeground(new Color(46, 48, 58));
        playerTitle.setBounds(95,115,150,30);

        playerClassImage.setIcon(new ImageIcon(getClass().getResource("/" + selectedClass + ".png")));
        playerClassImage.setBounds(95,125,225,200);

        monsterTitle.setFont(new Font("Californian FB", Font.PLAIN, 20));
        monsterTitle.setForeground(new Color(46, 48, 58));
        monsterTitle.setBounds(395,115,150,30);

        monsterClassImage.setIcon(new ImageIcon(getClass().getResource("/chef.png")));
        monsterClassImage.setBounds(395,120,225,200);

        playAgain = new JButton("Play Again");
        playAgain.setFont(new Font("Californian FB", Font.PLAIN, 18));
        playAgain.setBounds(250,550,175,25);

        this.add(playAgain);
        this.add(textfield);
        this.add(monsterTitle);
        this.add(monsterClassImage);
        this.add(playerClassImage);
        this.add(playerTitle);
        this.add(title);
        this.setBounds(100,100,700,700);
        this.setBackground(new Color(131,151,215));
    }

    //Creates character information string for warrior
    public String createCharacterString(Warrior warrior) {
        return warrior.getCharacterName() + "\n------------\nClass: "
                + warrior.getClassName() + "\n" + warrior.getStats() + "\n" + warrior.getWeaponStats();
    }

    //Creates character information string for cleric
    public String createCharacterString(Cleric cleric) {
        return cleric.getCharacterName() + "\n------------\nClass: "
                + cleric.getClassName() + "\n" + cleric.getStats() + "\n" + cleric.getWeaponStats();
    }

    //Creates character information string for wizard
    public String createCharacterString(Wizard wizard) {
        return wizard.getCharacterName() + "\n------------\nClass: "
                + wizard.getClassName() + "\n" + wizard.getStats() + "\n" + wizard.getWeaponStats();
    }

    public String createMonsterString(Monster monster) {
        return "\n\n" + monster.getCharacterName() + "\n------------\n" + monster.getStats();
    }

    public JButton getDisplayBtn() {
        return playAgain;
    }
}
