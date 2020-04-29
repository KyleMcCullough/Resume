package ca.nscc.Interface;

import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JPanel {
    private JPanel panel;
    private JButton button;

    public TitleScreen() {
        panel = new JPanel();

        setLayout(null);
        JLabel label = new JLabel("Dragons and Dungeons");

        label.setFont(new Font("Californian FB", Font.PLAIN, 32));
        label.setForeground(new Color(46, 48, 58));
        label.setBounds(185, 30, 500, 80);

        JLabel bgImage = new JLabel();

        bgImage.setIcon(new ImageIcon(getClass().getResource("/logo.jpg")));
        bgImage.setBounds(110, 100, 450, 206); // Set Width/Height same as image.

        button = new JButton("Build A Character");
        button.setFont(new Font("Californian FB", Font.PLAIN, 16));
        button.setForeground(new Color(46, 48, 58));
        button.setBounds(220,325,200,25);

        this.add(button);
        this.add(bgImage);
        this.add(label);
        this.setBounds(100,100,700,700);
        this.setBackground(new Color(131,151,215));
        this.setVisible(true);
    }



    public JButton getDisplayBtn() {
        return button;
    }
}
