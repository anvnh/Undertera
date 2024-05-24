package main;

import javax.swing.*;
import java.util.Objects;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Undertera");

        // Set the icon
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/icon/icon.png")));
        window.setIconImage(icon.getImage());

        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);

        gamepanel.config.loadConfig();

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamepanel.setupGame();
        gamepanel.startGameThread();
    }
}
