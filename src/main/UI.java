package main;

import object.KeyObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gamepanel;
    Graphics2D g2;
    Font arial_60;
    public UI(GamePanel gp)
    {
        this.gamepanel = gp;
        arial_60 = new Font("Arial", Font.PLAIN, 60);
    }
    public void showMessage()
    {

    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;
        g2.setFont(arial_60);
        g2.setColor(Color.WHITE);
        if(gamepanel.gameState == gamepanel.playState)
        {
        }
        if(gamepanel.gameState == gamepanel.pauseState)
        {
            drawPauseScreen();
        }
    }
    public void drawPauseScreen()
    {
        String text = "PAUSED";
        int y = gamepanel.screenHeight / 2;
        int x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        g2.drawString(text, x, y);
    }
}
