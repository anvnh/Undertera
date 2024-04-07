package main;

import object.BasedObject;
import object.HeartObject;
import object.KeyObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {
    GamePanel gamepanel;
    Graphics2D g2;
    public String currentDialogue;
    Font CCRedAlert;
    public int commandNumber = 0;
    BufferedImage heart_full, heart_quarter, heart_half, heart_threequarter, heart_empty;
    public UI(GamePanel gp)
    {
        this.gamepanel = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/C_C_Red_Alert__INET_.ttf");
            assert is != null;
            CCRedAlert = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

        //create HUD object
        BasedObject heart = new HeartObject(gamepanel);
        heart_full = heart.image1;
        heart_quarter = heart.image2;
        heart_half = heart.image3;
        heart_threequarter = heart.image4;
        heart_empty = heart.image5;

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
        //Title screen

        if(gamepanel.gameState == gamepanel.titleState)
        {
            drawTitleScreen();
        }

        //Play state
        if(gamepanel.gameState == gamepanel.playState)
        {
            drawPlayerLife();
        }

        //Pause state
        if(gamepanel.gameState == gamepanel.pauseState)
        {
            drawPlayerLife();
            drawPauseScreen();
        }

        //Dialogue State
        if(gamepanel.gameState == gamepanel.dialogueState)
        {
            drawPlayerLife();
            drawDialogueScreen();
        }

    }
    public void drawPlayerLife()
    {
        int x = gamepanel.screenWidth - (gamepanel.tileSize * 7) - 24;
        int y = (gamepanel.tileSize + 2) / 4;
        //Blank life
        for(int i = 0; i < 10; i++)
        {
            g2.drawImage(heart_empty, x, y, null);
            x += 35;
        }

        // Reset
        x = gamepanel.screenWidth - (gamepanel.tileSize * 7) - 24;
        y = (gamepanel.tileSize + 2) / 4;

        //Draw current life
        double currentLife = gamepanel.player.life;
        currentLife /= 10;
        int solidLife = (int) currentLife;
        double remainder = currentLife - solidLife;
        for(int i = 0; i < solidLife; i++)
        {
            g2.drawImage(heart_full, x, y, null);
            x += 35;
        }
        if(remainder == 0.25)
        {
            g2.drawImage(heart_threequarter, x, y, null);
        }
        else if(remainder == 0.5)
        {
            g2.drawImage(heart_half, x, y, null);
        }
        else if(remainder == 0.75)
        {
            g2.drawImage(heart_quarter, x, y, null);
        }
    }

    public void drawTitleScreen()
    {
        //DRAW BACKGROUND
        drawBackground(g2);

        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));
        String text = "UNDERTERA";
        int x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        int y = gamepanel.screenHeight / 2 - 190;

        // Shadow
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        // Title
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        g2.setColor(Color.BLACK);

        text = "New Game";
        x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        y = gamepanel.screenHeight / 2 + 80;
        g2.drawString(text, x + 3, y + 3);
            // draw the shadow
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNumber == 0)
        {
            g2.drawString(">", x - 50, y);
            g2.drawString("<", x + g2.getFontMetrics().stringWidth(text) + 30, y);
        }


        g2.setColor(Color.BLACK);
        text = "Load Game";
        x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        y = gamepanel.screenHeight / 2 + 140;
        g2.drawString(text, x + 3, y + 3);
            // draw the shadow
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNumber == 1)
        {
            g2.drawString(">", x - 50, y);
            g2.drawString("<", x + g2.getFontMetrics().stringWidth(text) + 30, y);
        }

        g2.setColor(Color.BLACK);
        text = "Option";
        x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        y = gamepanel.screenHeight / 2 + 200;
        g2.drawString(text, x + 3, y + 3);
            // draw the shadow
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNumber == 2)
        {
            g2.drawString(">", x - 50, y);
            g2.drawString("<", x + g2.getFontMetrics().stringWidth(text) + 30, y);
        }

        g2.setColor(Color.BLACK);
        text = "Quit";
        x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        y = gamepanel.screenHeight / 2 + 260;
        g2.drawString(text, x + 3, y + 3);
            // draw the shadow
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNumber == 3)
        {
            g2.drawString(">", x - 50, y);
            g2.drawString("<", x + g2.getFontMetrics().stringWidth(text) + 30, y);
        }
    }
    public void drawBackground(Graphics2D g2){
        try {
            BufferedImage bg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/background.png")));
            g2.drawImage(bg, 0, 0, gamepanel.screenWidth, gamepanel.screenHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
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
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        int x = gamepanel.tileSize * 2;
        int y = gamepanel.tileSize / 2;
        int width = gamepanel.screenWidth - gamepanel.tileSize * 4;
        int height = gamepanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

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
        Color c = new Color(0, 0, 0, 205);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 30, 30);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
}
