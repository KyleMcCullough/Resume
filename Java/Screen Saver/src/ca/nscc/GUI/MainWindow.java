package ca.nscc.GUI;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        this.setBounds(100,100,800,600);
        this.setTitle("Screen Saver");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScreensaverBoard screensaverBoard = new ScreensaverBoard();
        this.add(screensaverBoard);
    }


}
