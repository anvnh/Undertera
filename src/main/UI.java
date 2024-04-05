package main;

import object.KeyObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gamepanel;
    Font arial_40;
    BufferedImage keyImage;
    public UI(GamePanel gp)
    {
        this.gamepanel = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 30);
        KeyObject key = new KeyObject(gamepanel);
        keyImage = key.image;
    }

    public void draw(Graphics2D g2)
    {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, 5, 1, 90, 90, null);
        g2.drawString("" + gamepanel.player.hasKey, 80, 55);
    }
}
