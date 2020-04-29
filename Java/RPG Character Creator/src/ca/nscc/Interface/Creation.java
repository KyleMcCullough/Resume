package ca.nscc.Interface;

import ca.nscc.Classes.Cleric;
import ca.nscc.Classes.Warrior;
import ca.nscc.Classes.Wizard;
import ca.nscc.Weapons.Dagger;
import ca.nscc.Weapons.Mace;
import ca.nscc.Weapons.Sword;
import ca.nscc.Weapons.Weapon;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Creation extends JPanel {
    private JPanel panel;
    private boolean nextScreen = true;
    private JButton startBattleButton;
    private JRadioButton warriorButton, wizardButton, clericButton, daggerButton, swordButton, maceButton;
    private JLabel classImage, weaponImage;
    private JTextArea characterTextArea, weaponTextArea;
    private String[] classDetails = new String[]{"Warriors have high defence and hit points, but lack in agility.",
            "Wizards have High base attack and agility, just their defence and hit points lack.",
            "Clerics have high agility, but lack in other sections due to being a support role."};

    private String[] weaponDetails = new String[]{"The dagger is a very fast and lightweight weapon, it also has low damage.",
            "The sword is average on both sides. It is moderately heavy and does medium damage.",
            "The mace specializes in high damage, causing the weapon to be significantly heavy."};
    private JTextField hitPoints, defence, agility, baseAttack, attackModifier, weight, characterName;
    private Warrior displayWarrior = new Warrior(1, "DisplayWarrior");
    private Wizard displayWizard = new Wizard(1, "DisplayWizard");
    private Cleric displayCleric = new Cleric(1, "DisplayCleric");
    private Mace displayMace = new Mace();
    private Sword displaySword = new Sword();
    private Dagger displayDagger = new Dagger();
    private Cleric cleric;
    private Wizard wizard;
    private Warrior warrior;

    public Creation() {
        panel = new JPanel();
        setLayout(null);

        //Title and character Name
        JLabel title = new JLabel("Character Generator");
        title.setFont(new Font("Californian FB", Font.PLAIN, 32));
        title.setForeground(new Color(46, 48, 58));
        title.setBounds(50,15,400,30);

        JLabel nameLabel = new JLabel("Enter Name");
        nameLabel.setFont(new Font("Californian FB", Font.PLAIN, 22));
        nameLabel.setBounds(50,55,400,30);

        characterName = new JTextField();
        characterName.setBounds(170,55,150,30);

        //Class selection
        JLabel characterType = new JLabel("Character Type");
        characterType.setFont(new Font("Californian FB", Font.PLAIN, 22));
        characterType.setBounds(50,105,400,30);

        classImage = new JLabel("Select A Class");
        classImage.setBounds(200,100,225,200);

        warriorButton = new JRadioButton("Warrior");
        wizardButton = new JRadioButton("Wizard");
        clericButton = new JRadioButton("Cleric");

        ButtonGroup classSelect = new ButtonGroup();
        classSelect.add(warriorButton);
        classSelect.add(wizardButton);
        classSelect.add(clericButton);

        warriorButton.setBounds(50,150,100,30);
        wizardButton.setBounds(50,190,100,30);
        clericButton.setBounds(50,230,100,30);

        warriorButton.setFont(new Font("Serif", Font.PLAIN, 18));
        wizardButton.setFont(new Font("Serif", Font.PLAIN, 18));
        clericButton.setFont(new Font("Serif", Font.PLAIN, 18));

        warriorButton.setBackground(null);
        wizardButton.setBackground(null);
        clericButton.setBackground(null);

        characterTextArea = new JTextArea();
        characterTextArea.setLineWrap(true);
        characterTextArea.setEditable(false);
        characterTextArea.setForeground((new Color(131,151,215)));
        characterTextArea.setFont(new Font("Californian FB", Font.PLAIN, 18));
        characterTextArea.setBounds(50,260,350,50);

        //Character Stats
        JLabel characterStats = new JLabel("Character Stats");
        characterStats.setFont(new Font("Californian FB", Font.PLAIN, 22));
        characterStats.setBounds(450,105,400,30);

        JLabel hitPointLabel = new JLabel("Hit Points");
        hitPointLabel.setFont(new Font("Californian FB", Font.PLAIN, 18));
        hitPointLabel.setBounds(450,145,400,25);

        hitPoints = new JTextField();
        hitPoints.setEditable(false);
        hitPoints.setFont(new Font("Arial", Font.PLAIN, 18));
        hitPoints.setBounds(550,145,45,25);

        JLabel defenseLabel = new JLabel("Defense");
        defenseLabel.setFont(new Font("Californian FB", Font.PLAIN, 18));
        defenseLabel.setBounds(450,175,400,25);

        defence = new JTextField();
        defence.setEditable(false);
        defence.setFont(new Font("Arial", Font.PLAIN, 18));
        defence.setBounds(550,175,45,25);

        JLabel agilityLabel = new JLabel("Agility");
        agilityLabel.setFont(new Font("Californian FB", Font.PLAIN, 18));
        agilityLabel.setBounds(450,205,400,25);

        agility = new JTextField();
        agility.setEditable(false);
        agility.setFont(new Font("Arial", Font.PLAIN, 18));
        agility.setBounds(550,205,45,25);

        JLabel baseAttackLabel = new JLabel("Base Attack");
        baseAttackLabel.setFont(new Font("Californian FB", Font.PLAIN, 18));
        baseAttackLabel.setBounds(450,235,400,25);

        baseAttack = new JTextField();
        baseAttack.setEditable(false);
        baseAttack.setFont(new Font("Arial", Font.PLAIN, 18));
        baseAttack.setBounds(550,235,45,25);

        //Reroll Button
        JButton rerollStatsButton = new JButton("Reroll");
        rerollStatsButton.setFont(new Font("Californian FB", Font.PLAIN, 18));
        rerollStatsButton.setBounds(465,275,100,25);

        //Weapon Selection
        JLabel weaponType = new JLabel("Select Your Weapon");
        weaponType.setFont(new Font("Californian FB", Font.PLAIN, 22));
        weaponType.setBounds(50,330,400,30);

        weaponImage = new JLabel("Select A Weapon");
        weaponImage.setBounds(200,330,225,200);

        daggerButton = new JRadioButton("Dagger");
        swordButton = new JRadioButton("Sword");
        maceButton = new JRadioButton("Mace");

        ButtonGroup weaponSelect = new ButtonGroup();
        weaponSelect.add(daggerButton);
        weaponSelect.add(swordButton);
        weaponSelect.add(maceButton);

        daggerButton.setBounds(50,375,100,30);
        swordButton.setBounds(50,415,100,30);
        maceButton.setBounds(50,455,100,30);

        daggerButton.setFont(new Font("Serif", Font.PLAIN, 18));
        swordButton.setFont(new Font("Serif", Font.PLAIN, 18));
        maceButton.setFont(new Font("Serif", Font.PLAIN, 18));

        daggerButton.setBackground(null);
        swordButton.setBackground(null);
        maceButton.setBackground(null);

        weaponTextArea = new JTextArea();
        weaponTextArea.setLineWrap(true);
        weaponTextArea.setEditable(false);
        weaponTextArea.setForeground(new Color(170, 190, 255));
        weaponTextArea.setFont(new Font("Californian FB", Font.PLAIN, 18));
        weaponTextArea.setBounds(50,485,350,100);

        //Weapon Stats
        JLabel weaponStats = new JLabel("Weapon Stats");
        weaponStats.setFont(new Font("Californian FB", Font.PLAIN, 22));
        weaponStats.setBounds(450,330,400,30);

        JLabel attackModifierLabel = new JLabel("Attack Modifier");
        attackModifierLabel.setFont(new Font("Californian FB", Font.PLAIN, 16));
        attackModifierLabel.setBounds(450,370,400,25);

        attackModifier = new JTextField();
        attackModifier.setEditable(false);
        attackModifier.setFont(new Font("Arial", Font.PLAIN, 18));
        attackModifier.setBounds(550,370,45,25);

        JLabel weightLabel = new JLabel("Weight");
        weightLabel.setFont(new Font("Californian FB", Font.PLAIN, 16));
        weightLabel.setBounds(450,410,400,25);

        weight = new JTextField();
        weight.setEditable(false);
        weight.setFont(new Font("Arial", Font.PLAIN, 18));
        weight.setBounds(550,410,45,25);

        startBattleButton = new JButton("Start Battle");
        startBattleButton.setFont(new Font("Californian FB", Font.PLAIN, 18));
        startBattleButton.setBounds(470,525,125,25);

        //####################################################//
        //                  Action Listeners                  //
        //####################################################//

        //Class Action Listeners
        warriorButton.addActionListener(arg0 -> {
            changeClassImage("warrior");
            changeClassDescription(0);
            setClassStats(displayWarrior.getHitPoints(), displayWarrior.getDefence(), displayWarrior.getAgility(), displayWarrior.getBaseAttack());
        });

        wizardButton.addActionListener(arg0 -> {
            changeClassImage("wizard");
            changeClassDescription(1);
            setClassStats(displayWizard.getHitPoints(), displayWizard.getDefence(), displayWizard.getAgility(), displayWizard.getBaseAttack());
        });

        clericButton.addActionListener(arg0 -> {
            changeClassImage("cleric");
            changeClassDescription(2);
            setClassStats(displayCleric.getHitPoints(), displayCleric.getDefence(), displayCleric.getAgility(), displayCleric.getBaseAttack());
        });

        //Weapon Action Listeners
        daggerButton.addActionListener(arg0 -> {
            changeWeaponImage("dagger");
            changeWeaponDescription(0);
            setWeaponStats(displayDagger.getDamage(), displayDagger.getWeight());
        });

        swordButton.addActionListener(arg0 -> {
            changeWeaponImage("sword");
            changeWeaponDescription(1);
            setWeaponStats(displaySword.getDamage(), displaySword.getWeight());
        });

        maceButton.addActionListener(arg0 -> {
            changeWeaponImage("mace");
            changeWeaponDescription(2);
            setWeaponStats(displayMace.getDamage(), displayMace.getWeight());
        });

        //Button Action Listeners
        rerollStatsButton.addActionListener(arg0 -> {
            randomizeStats();
        });

        startBattleButton.addActionListener(arg0 -> {
            createCharacter();
        });

        this.add(startBattleButton);
        this.add(attackModifierLabel);
        this.add(attackModifier);
        this.add(weightLabel);
        this.add(weight);
        this.add(weaponImage);
        this.add(weaponTextArea);
        this.add(swordButton);
        this.add(maceButton);
        this.add(daggerButton);
        this.add(weaponType);
        this.add(rerollStatsButton);
        this.add(baseAttack);
        this.add(baseAttackLabel);
        this.add(agility);
        this.add(agilityLabel);
        this.add(defence);
        this.add(defenseLabel);
        this.add(hitPoints);
        this.add(hitPointLabel);
        this.add(characterStats);
        this.add(characterTextArea);
        this.add(classImage);
        this.add(warriorButton);
        this.add(wizardButton);
        this.add(clericButton);
        this.add(characterType);
        this.add(nameLabel);
        this.add(characterName);
        this.add(title);
        this.setBounds(100,100,700,700);
        this.setBackground(new Color(131,151,215));
        this.setVisible(true);
    }

    //Changes the class Image
    private void changeClassImage(String image) {
        this.classImage.setText("");
        this.classImage.setIcon(new ImageIcon(getClass().getResource("/" + image + ".png")));
    }

    //Changes the weapon image
    private void changeWeaponImage(String image) {
        this.weaponImage.setText("");
        this.weaponImage.setIcon(new ImageIcon(getClass().getResource("/" + image + ".png")));
    }

    //Changes class Description
    private void changeClassDescription(int index) {
        this.characterTextArea.setText(classDetails[index]);
    }

    //Changes weapon Description
    private void changeWeaponDescription(int index) {
        this.weaponTextArea.setText(weaponDetails[index]);
    }

    //Sets default class stats
    private void setClassStats(int standardHitPoints, int standardDefence, int standardAgility, int standardBaseAttack) {
        this.hitPoints.setText(String.valueOf(standardHitPoints));
        this.defence.setText(String.valueOf(standardDefence));
        this.agility.setText(String.valueOf(standardAgility));
        this.baseAttack.setText(String.valueOf(standardBaseAttack));
    }

    //Sets weapon stats
    private void setWeaponStats(int weaponAttackModifier, int weaponWeight) {
        this.attackModifier.setText("+" + weaponAttackModifier);
        this.weight.setText(String.valueOf(weaponWeight));
    }

    //Randomizes the stats
    private void randomizeStats() {
        Random ran = new Random();
        hitPoints.setText(String.valueOf(ran.nextInt(100) + 1));
        defence.setText(String.valueOf(ran.nextInt(100) + 1));
        agility.setText(String.valueOf(ran.nextInt(100) + 1));
        baseAttack.setText(String.valueOf(ran.nextInt(100) + 1));
    }

    //Creates the Character
    public void createCharacter() {

        int weaponID;
        if (daggerButton.isSelected()) {
            weaponID = 3;
        } else if (swordButton.isSelected()) {
            weaponID = 2;
        } else {
            weaponID = 1;
        }



        if (warriorButton.isSelected()) {
            System.out.println(warriorButton.isSelected());
            System.out.println(hitPoints.getText());
            this.warrior = new Warrior(weaponID, characterName.getText(), Integer.parseInt(hitPoints.getText()),
                    Integer.parseInt(defence.getText()), Integer.parseInt(agility.getText()), Integer.parseInt(baseAttack.getText()));
        } else if (wizardButton.isSelected()) {
            this.wizard = new Wizard(weaponID, characterName.getText(), Integer.parseInt(hitPoints.getText()),
                    Integer.parseInt(defence.getText()), Integer.parseInt(agility.getText()), Integer.parseInt(baseAttack.getText()));
        } else if (clericButton.isSelected()) {
            this.cleric = new Cleric(weaponID, characterName.getText(), Integer.parseInt(hitPoints.getText()),
                    Integer.parseInt(defence.getText()), Integer.parseInt(agility.getText()), Integer.parseInt(baseAttack.getText()));

        }
    }

    public JButton getDisplayBtn() {
        return startBattleButton;
    }

    public Cleric getCleric() {
        return cleric;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public Warrior getWarrior() {
        return warrior;
    }
}
