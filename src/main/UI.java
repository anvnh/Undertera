package main;

import entity.Entity;
import object.HeartObject;
import object.ManaObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.InflaterInputStream;

public class UI {
    GamePanel gamepanel;
    Graphics2D g2;
    public String currentDialogue;
    Font CCRedAlert;
    public int commandNumber = 0;
    BufferedImage   heart_full, heart_quarter, heart_half, heart_threequarter, heart_empty,
                    mana_full, mana_half, mana_blank;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public int commandNum = 0;
    public int slotCol = 0, slotRow = 0;
    int subState = 0;
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
        HeartObject heart = new HeartObject(gamepanel);
        heart_full = heart.image1;
        heart_quarter = heart.image4;
        heart_half = heart.image3;
        heart_threequarter = heart.image2;
        heart_empty = heart.image5;

        ManaObject mana = new ManaObject(gamepanel);
        mana_full = mana.image1;
        mana_half = mana.image2;
        mana_blank = mana.image3;

    }
    public void addMessage(String text)
    {
        messages.add(text);
        messageCounter.add(0);
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
            drawPlayerMana();
            drawMessage();
        }

        //Pause state
        if(gamepanel.gameState == gamepanel.pauseState)
        {
            drawPauseScreen(g2);
            drawPlayerLife();
            drawPlayerMana();
        }

        //Dialogue State
        if(gamepanel.gameState == gamepanel.dialogueState)
        {
            drawPlayerLife();
            drawPlayerMana();
            drawDialogueScreen();
        }

        //Character State
        if(gamepanel.gameState == gamepanel.characterState)
        {
            drawCharacterScreen();
            drawInventory();
        }

        //Options State
        if(gamepanel.gameState == gamepanel.optionsState)
        {
            drawOptionsScreen();
        }
    }
    public void drawPlayerMana()
    {
        int x = gamepanel.screenWidth - (gamepanel.tileSize * 7) - 31;
        int y = (gamepanel.tileSize + 2) / 2 + (gamepanel.tileSize / 2 - (gamepanel.tileSize + 2) / 4);
        //Blank mana
        for(int i = 0; i < 10; i++)
        {
            g2.drawImage(mana_blank, x, y, null);
            x += 35;
        }

        // Reset
        x = gamepanel.screenWidth - (gamepanel.tileSize * 7) - 31;
        y = (gamepanel.tileSize + 2) / 2 + (gamepanel.tileSize / 2 - (gamepanel.tileSize + 2) / 4);

        //Draw current mana
        double currentMana = gamepanel.player.mana;
        currentMana /= 10;
        int solidMana = (int) currentMana;
        double remainder = currentMana - solidMana;

        for(int i = 0; i < solidMana; i++)
        {
            g2.drawImage(mana_full, x, y, null);
            x += 35;
        }
        if(remainder == 0.5)
        {
            g2.drawImage(mana_half, x, y, null);
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
        //System.out.println(solidLife + " " + remainder);
        for(int i = 0; i < solidLife; i++)
        {
            g2.drawImage(heart_full, x, y, null);
            x += 35;
        }
        if(remainder < 0.25 && (gamepanel.player.life < 100 ? remainder >= 0 : remainder > 0))
        {
            g2.drawImage(heart_quarter, x, y, null);
        }
        else if(remainder >= 0.25 && remainder <= 0.75)
        {
            g2.drawImage(heart_half, x, y, null);
        }
        else if(remainder > 0.75)
        {
            g2.drawImage(heart_threequarter, x, y, null);
        }
    }
    public void drawMessage(){
        int messageX = gamepanel.tileSize;
        int messageY = gamepanel.screenHeight - gamepanel.tileSize * 2;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        for(int i = 0; i < messages.size(); i++)
        {
            g2.setColor(new Color(0, 0, 0 ));
            g2.drawString(messages.get(i), messageX + 2, messageY);

            g2.setColor(new Color(225, 225, 225 ));
            g2.drawString(messages.get(i), messageX, messageY);
            int counter = messageCounter.get(i) + 1;
            messageCounter.set(i, counter);
            messageY -= 40;

            if(messageCounter.get(i) > 240)
            {
                messages.remove(i);
                messageCounter.remove(i);
            }
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
    public void drawPauseScreen(Graphics2D g2)
    {
        String text = "PAUSED";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));
        int x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        int y = gamepanel.screenHeight / 2 - 190;
        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen()
    {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
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
    public void drawCharacterScreen() {
        final int frameX = gamepanel.tileSize;
        final int frameY = gamepanel.tileSize / 2;
        final int frameWidth = gamepanel.tileSize * 8;
        final int frameHeight = gamepanel.screenHeight - frameY * 2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));

        int textX = frameX + gamepanel.tileSize / 2 + 10 ;
        int textY = frameY + gamepanel.tileSize + gamepanel.tileSize / 2;
        final int lineHeight = 45;

        //Name of attributes
        g2.drawString("Level:", textX, textY);
        textY += lineHeight;
        g2.drawString("HP:", textX, textY);
        textY += lineHeight;
        g2.drawString("STR:", textX, textY);
        textY += lineHeight;
        g2.drawString("DEX:", textX, textY);
        textY += lineHeight;
        g2.drawString("ATK:", textX, textY);
        textY += lineHeight;
        g2.drawString("MANA:", textX, textY);
        textY += lineHeight;
        g2.drawString("DEF:", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP:", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level:", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin:", textX, textY);
        textY += lineHeight;
        g2.drawString("Weapon:", textX, textY);
        textY += lineHeight;
        g2.drawString("Armor:", textX, textY);


        // Values
        int tailX = frameX + gamepanel.tileSize * 5;
        // Reset textY
        textY = frameY + gamepanel.tileSize + gamepanel.tileSize / 2;
        String value;
        BufferedImage imageEquip;

        value = String.valueOf(gamepanel.player.level);
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(Math.round(gamepanel.player.life));
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value + "/100", textX - 54, textY);

        value = String.valueOf(gamepanel.player.strength);
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gamepanel.player.dexterity);
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gamepanel.player.attack);
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gamepanel.player.mana);
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value + "/100", textX - 54, textY);

        value = String.valueOf(gamepanel.player.defense);
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gamepanel.player.exp) + "/" + gamepanel.player.nextLevelExp;
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gamepanel.player.nextLevelExp);
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gamepanel.player.coin);
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        /*
        value = gamepanel.player.currentWeapon.name;
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);

        value = gamepanel.player.currentArmor.name;
        textY += lineHeight;
        textX = getXforAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
         */

        imageEquip = gamepanel.player.currentWeapon.image;
        g2.drawImage(imageEquip, tailX + 70, textY + 10, null);
        textY += lineHeight + 10;

        imageEquip = gamepanel.player.currentArmor.image;
        g2.drawImage(imageEquip, tailX + 70, textY, null);

    }

    public void drawInventory()
    {
        //Inventory
        final int frameX = gamepanel.tileSize * 10;
        final int frameY = gamepanel.tileSize / 2;
        final int frameWidth = gamepanel.screenWidth - frameX - gamepanel.tileSize - 8;
        final int frameHeight = gamepanel.screenHeight - frameY * 12;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        //CURSOR
        final int cursorX = slotXStart + (gamepanel.tileSize * slotCol);
        final int cursorY = slotYStart + (gamepanel.tileSize * slotRow);
        int cursorWidth = gamepanel.tileSize;
        int cursorHeight = gamepanel.tileSize;

        //Draw player's items
        for(int i = 0; i < gamepanel.player.inventory.size(); i++)
        {
            //Equipped cursor color
            if(gamepanel.player.inventory.get(i) == gamepanel.player.currentWeapon
             || gamepanel.player.inventory.get(i) == gamepanel.player.currentArmor )
            {
                g2.setColor(new Color(61, 162, 242));
                g2.fillRoundRect(slotX, slotY, gamepanel.tileSize, gamepanel.tileSize, 10, 10);
            }

            g2.drawImage(gamepanel.player.inventory.get(i).image, slotX, slotY, gamepanel.tileSize, gamepanel.tileSize,null);
            slotX += gamepanel.tileSize;
            if(i % 17 == 0 && i != 0)
            {
                slotX = slotXStart;
                slotY += gamepanel.tileSize;
            }
        }

        // Draw cursor
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);


        //Description of the item
        final int descriptionX = frameX;
        final int descriptionY = frameY + frameHeight + 20;
        final int descriptionWidth = (frameWidth - 20) / 2;
        final int descriptionHeight = gamepanel.screenHeight - frameY * 2 - 20 - frameHeight;

        //Draw description text
        int textDescX = descriptionX + 20;
        int textDescY = descriptionY + gamepanel.tileSize;
        g2.setFont(g2.getFont().deriveFont(30f));
        int itemIndex = getItemIndexOnSlot();
        if(itemIndex < gamepanel.player.inventory.size())
        {
            drawSubWindow(descriptionX, descriptionY, descriptionWidth, descriptionHeight);

            // Stats of the item
            final int statsX = descriptionX + descriptionWidth + 20;
            final int statsY = descriptionY;
            final int statsWidth = descriptionWidth;
            final int statsHeight = descriptionHeight;
            drawSubWindow(statsX, statsY, statsWidth, statsHeight);

            //Draw stats text
            int textStatsX = statsX + 20;
            int textStatsY = statsY + gamepanel.tileSize;
            if(itemIndex < gamepanel.player.inventory.size())
            {
                g2.drawString("Attack: " + gamepanel.player.inventory.get(itemIndex).attackValue, textStatsX, textStatsY);
                textStatsY += 30;
                g2.drawString("Defense: " + gamepanel.player.inventory.get(itemIndex).defenseValue, textStatsX, textStatsY);
            }
            
            for(String line : gamepanel.player.inventory.get(itemIndex).description.split("\n"))
            {
                g2.drawString(line, textDescX, textDescY);
                textDescY += 30;
            }
        }
    }
    public void drawOptionsScreen(){
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));

        // Sub window
        int frameX = gamepanel.tileSize * 12;
        int frameY = gamepanel.tileSize;
        int frameWidth = gamepanel.tileSize * 11;
        int frameHeight = gamepanel.screenHeight - frameY * 2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        switch (subState){
            case 0: options_top(frameX, frameY); break;
            case 1: break;
            case 2: break;
        }
    }
    public void options_top(int frameX, int frameY) {
        int textX, textY;

        // Title
        String text = "Options";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45f));
        textX = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        textY = frameY + gamepanel.tileSize;
        g2.drawString(text, textX, textY);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 31f));

        //Reset
        textX = frameX + gamepanel.tileSize;

        // Full screen on/off ?
        // Music on/off ?
        textY += gamepanel.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX - 25, textY);
        }

        // SE
        textY += gamepanel.tileSize;
        g2.drawString("Sound Effect", textX, textY);
        if(commandNum == 1)
        {
            g2.drawString(">", textX - 25, textY);
        }

        // Control
        textY += gamepanel.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 2)
        {
            g2.drawString(">", textX - 25, textY);
        }

        // End game
        textY += gamepanel.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 3)
        {
            g2.drawString(">", textX - 25, textY);
        }

        // Back
        textY += gamepanel.tileSize;
        g2.drawString("Back", textX, textY);
        if(commandNum == 4)
        {
            g2.drawString(">", textX - 25, textY);
        }
        // ___________________________________________________________________________________________ //
        textX = frameX + gamepanel.tileSize * 6;
        textY = frameY + gamepanel.tileSize + 25;
        g2.setStroke(new BasicStroke(3));
        // Music
        g2.drawRect(textX, textY, 220, 30);
        g2.fillRect(textX, textY, 20 * gamepanel.sound.volumeScale, 30);
        // SE
        textY += gamepanel.tileSize;
        g2.drawRect(textX, textY, 220, 30);
    }
    public int getItemIndexOnSlot() {
        //System.out.println(17 * (slotRow + 1) + slotRow - (17 - slotCol));
        return 17 * (slotRow + 1) + slotRow - (17 - slotCol);
    }
    public void drawInvenHUD(int x, int y, int width, int height)
    {
        Color c = new Color(0, 0, 0, 205);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 30, 30);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x + 2, y + 2, width - 2, height - 2, 25, 25);
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

    public int getXforAlignRight(String text, int tailX)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length + 110;
        return x;
    }
}
