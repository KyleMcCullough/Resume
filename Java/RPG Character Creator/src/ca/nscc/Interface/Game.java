package ca.nscc.Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {

    private JFrame frame;

    public Game () {
        runGame();

    }

    public void runGame() {
        frame = new JFrame();
        this.setLayout(new CardLayout());
        this.setBounds(100,100,700,650);

        TitleScreen TitleScreen = new TitleScreen();
        Creation creationScreen = new Creation();

        this.add(TitleScreen);
        this.add(creationScreen);


        JButton titleScreenButton = TitleScreen.getDisplayBtn();
        JButton creationScreenButton = creationScreen.getDisplayBtn();


        titleScreenButton.addActionListener(arg0 -> {
            TitleScreen.setVisible(false);
            creationScreen.setVisible(true); });

        creationScreenButton.addActionListener(arg0 -> {
            creationScreen.createCharacter();
            creationScreen.setVisible(false);
            BattleScreen battleScreen = new BattleScreen(creationScreen);
            this.add(battleScreen);
            battleScreen.setVisible(true);
        });



    }
}
