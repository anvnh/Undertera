package main;

import object.KeyObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gamepanel;
    Graphics2D g2;
    public String currentDialogue;
    Font CCRedAlert;
    public UI(GamePanel gp)
    {
        this.gamepanel = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/C_C_Red_Alert__INET_.ttf");
            CCRedAlert = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
    public void showMessage()
    {

    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;
        g2.setColor(Color.WHITE);
        g2.setFont(CCRedAlert);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Play state
        if(gamepanel.gameState == gamepanel.playState)
        {
        }

        //Pause state
        if(gamepanel.gameState == gamepanel.pauseState)
        {
            drawPauseScreen();
        }

        //Dialogue State
        if(gamepanel.gameState == gamepanel.dialogueState)
        {
            drawDialogueScreen();
        }

    }
    public void drawPauseScreen()
    {
        String text = "PAUSED";
        int y = gamepanel.screenHeight / 2;
        int x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen()
    {
        int x = gamepanel.tileSize * 2;
        int y = gamepanel.tileSize / 2;
        int width = gamepanel.screenWidth - gamepanel.tileSize * 4;
        int height = gamepanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(CCRedAlert.deriveFont(40f));
        x += gamepanel.tileSize;
        y += gamepanel.tileSize;
        for (String line : currentDialogue.split("\n"))
        {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0, 0, 0, 220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 30, 30);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
}
