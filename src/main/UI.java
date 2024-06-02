package main;

import entity.Entity;
import object.HeartObject;
import object.ManaObject;
import object.SilverCoinObject;

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
    public Font CCRedAlert;
    public int commandNumber = 0;
    BufferedImage   heart_full, heart_quarter, heart_half, heart_threequarter, heart_empty,
                    mana_full, mana_half, mana_blank,
                    coin;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public int commandNum = 0;
    public int playerSlotCol = 0, playerSlotRow = 0;
    public int npcSlotCol = 0, npcSlotRow = 0;
    int subState = 0;
    public Entity npc;
    int counter = 0;
    int charIndex = 0;
    String combinedText = "";
    int Prev_State;

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

        // Heart object
        HeartObject heart = new HeartObject(gamepanel);
        heart_full = heart.image1;
        heart_quarter = heart.image4;
        heart_half = heart.image3;
        heart_threequarter = heart.image2;
        heart_empty = heart.image5;

        // Mana object
        ManaObject mana = new ManaObject(gamepanel);
        mana_full = mana.image1;
        mana_half = mana.image2;
        mana_blank = mana.image3;

        // Coin
        Entity silver_coin = new SilverCoinObject(gamepanel);
        coin = silver_coin.image;
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
            drawMonsterLife();
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
            if(Prev_State != gamepanel.cutSceneState)
            {
                drawPlayerLife();
                drawPlayerMana();
            }
            drawDialogueScreen();
        }

        //Character State
        if(gamepanel.gameState == gamepanel.characterState)
        {
            drawCharacterScreen();
            drawInventory(gamepanel.player, true);
        }

        //Options State
        if(gamepanel.gameState == gamepanel.optionsState)
        {
            drawOptionsScreen();
        }

        // Game over State
        if(gamepanel.gameState == gamepanel.gameOverState)
        {
            drawGameOverScreen();
        }

        // Trade State
        if(gamepanel.gameState == gamepanel.tradeState)
        {
            drawTradeScreen();
        }

        // Sleep State
        if(gamepanel.gameState == gamepanel.sleepState)
        {
            drawSleepScreen();
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
    public void drawMonsterLife()
    {
        //HP Bar of the monster

        for(int i = 0; i < gamepanel.monster[1].length; i++)
        {
            Entity monster = gamepanel.monster[gamepanel.currentMap][i];
            if(monster != null)
            {
                if(!monster.boss)
                {
                    if(monster.HPBarOn)
                    {
                        double scale = (double) gamepanel.tileSize / monster.maxLife;
                        double HPBar = monster.life * scale;

                        g2.setColor(new Color(0, 0, 0));
                        g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 10, gamepanel.tileSize + 2, 7);

                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(monster.getScreenX(), monster.getScreenY() - 10, Math.max((int) HPBar, 0), 5);

                        monster.HPBarCounter++;
                        if (monster.HPBarCounter == 600) {
                            monster.HPBarOn = false;
                            monster.HPBarCounter = 0;
                        }
                    }
                }
                else {
                    if(monster.isInCamera()) {
                        double scale = (double) gamepanel.tileSize * 14 / monster.maxLife;
                        double HPBar = monster.life * scale;

                        int x = gamepanel.screenWidth / 2 - gamepanel.tileSize * 7;
                        int y = gamepanel.tileSize + gamepanel.tileSize / 2;

                        g2.setColor(new Color(0, 0, 0));
                        g2.fillRect(x - 1, y - 1, gamepanel.tileSize * 14 + 2, 25);

                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(x, y, (int) HPBar, 20);

                        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
                        String text = monster.name;
                        g2.setColor(new Color(255, 255, 255));
                        g2.drawString(text, x + gamepanel.tileSize * 7 + gamepanel.tileSize / 2 - g2.getFontMetrics().stringWidth(text) / 2, y - 20);
                    }
                }
            }
        }
    }
    public void drawMessage()
    {
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
    public void drawBossDialogueScreen(Entity entity)
    {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        int x = gamepanel.tileSize * 2;
        int y = gamepanel.tileSize / 2;
        int width = gamepanel.screenWidth - gamepanel.tileSize * 4;
        int height = gamepanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gamepanel.tileSize;
        y += gamepanel.tileSize;

        if(entity.dialogue[0][entity.dialogueIndex] != null) {
            //currentDialogue = npc.dialogue[npc.dialogueSet][npc.dialogueIndex];

            char[] characters = npc.dialogue[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if(charIndex < characters.length) {
                gamepanel.playSoundEffect(20);
                String s = String.valueOf(characters[charIndex]);
                combinedText += s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if(gamepanel.Key.enterPressed)
            {
                charIndex = 0;
                combinedText = "";

                if(gamepanel.gameState == gamepanel.cutSceneState)
                {
                    npc.dialogueIndex++;
                    gamepanel.Key.enterPressed = false;
                }
            }
        }
        else { // If no text
            npc.dialogueIndex = 0;
            if(gamepanel.gameState == gamepanel.cutSceneState)
            {
                gamepanel.cutsceneManager.scenePhase++;
            }
        }

        for (String line : currentDialogue.split("\n"))
        {
            g2.drawString(line, x, y);
            y += 40;
        }
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

        if(npc.dialogue[npc.dialogueSet][npc.dialogueIndex] != null) {
            //currentDialogue = npc.dialogue[npc.dialogueSet][npc.dialogueIndex];

            char characters[] = npc.dialogue[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if(charIndex < characters.length) {
                gamepanel.playSoundEffect(20);
                String s = String.valueOf(characters[charIndex]);
                combinedText += s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if(gamepanel.Key.enterPressed)
            {
                charIndex = 0;
                combinedText = "";

                if(gamepanel.gameState == gamepanel.dialogueState || Prev_State == gamepanel.cutSceneState)
                {
                    npc.dialogueIndex++;
                    gamepanel.Key.enterPressed = false;
                }
            }
        }
        else { // If no text
            npc.dialogueIndex = 0;
            if(gamepanel.gameState == gamepanel.dialogueState)
            {
                gamepanel.gameState = gamepanel.playState;
            }
            if(Prev_State == gamepanel.cutSceneState)
            {
                gamepanel.cutsceneManager.scenePhase++;
            }
        }

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

        value = String.valueOf(gamepanel.player.level + 1);
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

        imageEquip = gamepanel.player.currentShield.image;
        g2.drawImage(imageEquip, tailX + 70, textY, null);

    }

    public void drawInventory(Entity entity, boolean cursor)
    {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        // npc frame
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol;
        int slotRow;

        if(entity == gamepanel.player)
        {
            frameX = gamepanel.tileSize * 10;
            frameY = gamepanel.tileSize / 2;
            frameWidth = gamepanel.screenWidth - frameX - gamepanel.tileSize - 8;
            frameHeight = gamepanel.screenHeight - frameY * 12;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gamepanel.tileSize;
            frameY = gamepanel.tileSize / 2;
            frameWidth = gamepanel.tileSize * 9 - 10;
            frameHeight = gamepanel.screenHeight - frameY * 12;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // player frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        //Draw player's items
        for(int i = 0; i < entity.inventory.size(); i++)
        {
            //Equipped cursor color
            if(
                    entity.inventory.get(i) == entity.currentWeapon
                    || entity.inventory.get(i) == entity.currentShield
                    || entity.inventory.get(i) == entity.currentLight
            ) // Check if the item is equipped
            {
                g2.setColor(new Color(241, 229, 63));
                g2.fillRoundRect(slotX, slotY, gamepanel.tileSize, gamepanel.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).image, slotX, slotY, gamepanel.tileSize, gamepanel.tileSize,null);

            //Display the quantity
            if(entity.inventory.get(i).quantity > 1)
            {
                g2.setFont(g2.getFont().deriveFont(20f));
                int amountX, amountY;

                String s = "" + entity.inventory.get(i).quantity;
                amountX = slotX + gamepanel.tileSize - g2.getFontMetrics().stringWidth(s) - 5;
                amountY = slotY + gamepanel.tileSize - 5;

                // Shadow
                g2.setColor(Color.BLACK);
                g2.drawString(s, amountX, amountY);

                // Number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 2, amountY - 2);
            }

            slotX += gamepanel.tileSize; //calculate the next slot
            if(slotX >= frameX + frameWidth - gamepanel.tileSize)
            {
                slotX = slotXStart;
                slotY += gamepanel.tileSize;
            }
        }

        //CURSOR
        if(cursor) {
            final int cursorX = slotXStart + (gamepanel.tileSize * slotCol);
            final int cursorY = slotYStart + (gamepanel.tileSize * slotRow);
            int cursorWidth = gamepanel.tileSize;
            int cursorHeight = gamepanel.tileSize;

            // Draw cursor
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description of the item
            final int descriptionX = frameX;
            final int descriptionY = frameY + frameHeight + 20;
            final int descriptionWidth = (entity == gamepanel.player ?  (frameWidth - 20) / 2 : frameWidth);
            final int descriptionHeight = gamepanel.screenHeight - frameY * 2 - 20 - frameHeight;

            // Draw description text
            int textDescX = descriptionX + 20;
            int textDescY = descriptionY + gamepanel.tileSize;
            g2.setFont(g2.getFont().deriveFont(30f));
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if(itemIndex < entity.inventory.size())
            {
                drawSubWindow(descriptionX, descriptionY, descriptionWidth, descriptionHeight);

                // Stats of the item
                final int statsX = descriptionX + descriptionWidth + (entity == gamepanel.player ? 20 : 10);
                final int statsY = descriptionY;
                final int statsWidth = descriptionWidth;
                final int statsHeight = descriptionHeight;
                drawSubWindow(statsX, statsY, statsWidth, statsHeight);

                //Draw stats text
                int textStatsX = statsX + 20;
                int textStatsY = statsY + gamepanel.tileSize;
                if(itemIndex < entity.inventory.size())
                {
                    g2.drawString("Attack: " + entity.inventory.get(itemIndex).attackValue, textStatsX, textStatsY);
                    textStatsY += 30;
                    g2.drawString("Defense: " + entity.inventory.get(itemIndex).defenseValue, textStatsX, textStatsY);
                }

                for(String line : entity.inventory.get(itemIndex).description.split("\n"))
                {
                    g2.drawString(line, textDescX, textDescY);
                    textDescY += 30;
                }
            }
        }
    }
    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamepanel.screenWidth, gamepanel.screenHeight);

        int x, y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "Game Over";
        // Shadow
        g2.setColor(Color.BLACK);
        x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        y = gamepanel.screenHeight / 2 - 190;
        g2.drawString(text, x, y);
        // Main
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Retry button
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        text = "Retry";
        x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        y = gamepanel.screenHeight / 2 + 50;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        // Back to the title screen
        text = "Quit";
        x = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        y = gamepanel.screenHeight / 2 + 120;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }
    }
    public void drawOptionsScreen(){
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));

        // Sub window
        int frameX = gamepanel.tileSize * 9 + 20;
        int frameY = gamepanel.tileSize;
        int frameWidth = gamepanel.tileSize * 11;
        int frameHeight = gamepanel.screenHeight - frameY * 2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        switch (subState){
            case 0: options_top(frameX, frameY); break;
            case 1: options_control(frameX, frameY);break;
            case 2: options_endGameConfirmation(frameX, frameY); break;
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
        textY += gamepanel.tileSize + 23;
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
            if(gamepanel.Key.enterPressed) {
                subState = 1;
                commandNum = 0;
                gamepanel.Key.enterPressed = false;
            }
        }

        // Back to menu
        textY += gamepanel.tileSize;
        g2.drawString("Back to menu", textX, textY);
        if(commandNum == 3)
        {
            g2.drawString(">", textX - 25, textY);
            if(gamepanel.Key.enterPressed)
            {
                gamepanel.Key.enterPressed = false;
                subState = 2;
                commandNum = 0;
            }
        }

        // Save game
        textY += gamepanel.tileSize;
        g2.drawString("Save Game", textX, textY);
        if(commandNum == 4)
        {
            g2.drawString(">", textX - 25, textY);
            if(gamepanel.Key.enterPressed)
            {
                gamepanel.saveLoad.save();
                if(gamepanel.saveLoad.success)
                {
                    messages.add("Game saved.");
                    gamepanel.gameState = gamepanel.playState;
                    messageCounter.add(0);
                }
                else
                {
                    messages.add("Failed to save the game.");
                    messageCounter.add(0);
                }
                gamepanel.Key.enterPressed = false;
            }
        }

        // Back
        textY += gamepanel.tileSize;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5)
        {
            g2.drawString(">", textX - 25, textY);
            if(gamepanel.Key.enterPressed)
            {
                gamepanel.gameState = gamepanel.playState;
                gamepanel.Key.enterPressed = false;
                subState = 0;
                commandNum = 0;
            }
        }
        // ___________________________________________________________________________________________ //
        textX = frameX + gamepanel.tileSize * 6;
        textY = frameY + gamepanel.tileSize + 25 + 23;
        g2.setStroke(new BasicStroke(3));


        // Music
        g2.drawRect(textX, textY, 200, 30);
        g2.fillRect(textX, textY, 20 * gamepanel.sound.musicVolume, 30);

        // SE
        textY += gamepanel.tileSize;
        g2.drawRect(textX, textY, 200, 30);
        g2.fillRect(textX, textY, 20 * gamepanel.sound.soundEffect, 30);

        gamepanel.config.saveConfig();
    }

    public void options_control(int frameX, int frameY)
    {
        int textX, textY;

        //Title
        String text = "Control";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45f));
        textX = gamepanel.screenWidth / 2 - g2.getFontMetrics().stringWidth(text) / 2;
        textY = frameY + gamepanel.tileSize;
        g2.drawString(text, textX, textY);

        // Reset
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 31f));
        textX = frameX + gamepanel.tileSize;
        textY += gamepanel.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gamepanel.tileSize;

        g2.drawString("Attack", textX, textY);
        textY += gamepanel.tileSize;

        g2.drawString("Shoot", textX, textY);
        textY += gamepanel.tileSize;

        g2.drawString("Inventory", textX, textY);
        textY += gamepanel.tileSize;

        g2.drawString("Dash", textX, textY);
        textY += gamepanel.tileSize;

        g2.drawString("Pause", textX, textY);
        textY += gamepanel.tileSize;

        // Reset
        textX = frameX + gamepanel.tileSize * 6;
        textY = frameY + gamepanel.tileSize + gamepanel.tileSize;
        g2.drawString("WASD", textX, textY);
        textY += gamepanel.tileSize;
        g2.drawString("J", textX, textY);
        textY += gamepanel.tileSize;
        g2.drawString("I", textX, textY);
        textY += gamepanel.tileSize;
        g2.drawString("C", textX, textY);
        textY += gamepanel.tileSize;
        g2.drawString("K", textX, textY);
        textY += gamepanel.tileSize;
        g2.drawString("P", textX, textY);

        // Back button
        textX = frameX + gamepanel.tileSize;
        textY = frameY + gamepanel.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gamepanel.Key.enterPressed) {
                subState = 0;
                commandNum = 2;
                gamepanel.Key.enterPressed = false;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gamepanel.tileSize;
        int textY = frameY + gamepanel.tileSize * 3;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        currentDialogue = "Are you sure you want \nto end the game?";
        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES option
        String text = "Yes";
        textX = frameX + gamepanel.tileSize * 4;
        textY = frameY + gamepanel.tileSize * 6;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 31f));
        g2.drawString(text, textX, textY);

        if(commandNum == 0) {
            g2.drawString(">", textX - 50, textY);
            if(gamepanel.Key.enterPressed) {
                gamepanel.gameState = gamepanel.titleState;
                gamepanel.Key.enterPressed = false;
                gamepanel.resetGame(true);
            }
        }

        // NO option
        String text2 = "No";
        textX = frameX + gamepanel.tileSize * 4;
        textY = frameY + gamepanel.tileSize * 7;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 31f));
        g2.drawString(text2, textX, textY);

        if(commandNum == 1) {
            g2.drawString(">", textX - 50, textY);
            if(gamepanel.Key.enterPressed) {
                subState = 0;
                commandNum = 3;
                gamepanel.Key.enterPressed = false;
            }
        }


    }
    public void drawTradeScreen()
    {
        switch (subState)
        {
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gamepanel.Key.enterPressed = false;
    }
    public void trade_select()
    {

        npc.dialogueSet = 0;
        drawDialogueScreen();


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));

        //Draw window
        final int frameX = gamepanel.tileSize * 25;
        final int frameY = gamepanel.tileSize * 5;
        final int frameWidth = gamepanel.tileSize * 3;
        final int frameHeight = gamepanel.tileSize * 4;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Draw text
        int x = frameX + gamepanel.tileSize / 2;
        int y = frameY + gamepanel.tileSize + 9;
        g2.drawString("Buy", x, y);
        if(commandNum == 0)
        {
            g2.drawString(">", x - 50, y);
            if(gamepanel.Key.enterPressed)
            {
                subState = 1;
                commandNum = 0;
                gamepanel.Key.enterPressed = false;
            }
        }

        y += gamepanel.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1)
        {
            g2.drawString(">", x - 50, y);
            if(gamepanel.Key.enterPressed)
            {
                subState = 2;
                commandNum = 0;
                gamepanel.Key.enterPressed = false;
            }
        }

        y += gamepanel.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 2)
        {
            g2.drawString(">", x - 50, y);
            if(gamepanel.Key.enterPressed)
            {
                npc.startDialogue(npc, 1);
                commandNum = 0;
                /*
                currentDialogue = "Come back anytime!";
                gamepanel.Key.enterPressed = false;
                 */
            }
        }
    }

    public void trade_buy()
    {
        drawInventory(gamepanel.player, false);
        drawInventory(npc, true);

        // Draw hint window
        int x = gamepanel.screenWidth - gamepanel.tileSize * 11;
        int y = gamepanel.screenHeight - gamepanel.tileSize * 5;
        int width = gamepanel.tileSize * 9 + gamepanel.tileSize / 2 + 13;
        int height = gamepanel.tileSize * 4 + gamepanel.tileSize / 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + width - 150, y + height - 20);
        g2.drawRoundRect(x + width - 175, y + height - 50, 170, 43, 30, 30);

        // Draw player coin
        int coinX = gamepanel.tileSize * 25 + gamepanel.tileSize / 2;
        int coinY = gamepanel.screenHeight - gamepanel.tileSize * 5 - gamepanel.tileSize / 2 - 10;
        int coinWidth = 200;
        int coinHeight = 44;
        drawSubWindow(coinX, coinY, coinWidth, coinHeight);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 31f));
        g2.drawString("Coin: " + gamepanel.player.coin, coinX + 20, coinY + 30);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size())
        {
            int priceX = gamepanel.tileSize * 7;
            int priceY = gamepanel.screenHeight - gamepanel.tileSize * 5 - gamepanel.tileSize / 2 - 10;
            int priceWidth = 108;
            int priceHeight = 43;
            drawSubWindow(priceX, priceY, priceWidth, priceHeight);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 31f));
            g2.drawImage(coin, priceX + 10, priceY + 2, 40, 45, null);
            int price = npc.inventory.get(itemIndex).price;
            g2.drawString(String.valueOf(price), priceX + priceWidth - gamepanel.tileSize - String.valueOf(price).length(), priceY + 30);

            // Buy item
            if(gamepanel.Key.enterPressed)
            {
                if(gamepanel.player.coin < npc.inventory.get(itemIndex).price)
                {
                    subState = 0;
                    npc.startDialogue(npc, 2);
                    //drawDialogueScreen();
                }
                else {
                    if(gamepanel.player.canObtainItem(npc.inventory.get(itemIndex)))
                    {
                        gamepanel.player.coin -= npc.inventory.get(itemIndex).price;
                        //gamepanel.player.inventory.add(npc.inventory.get(itemIndex));
                    }
                    else
                    {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                        //drawDialogueScreen();
                    }
                }
            }
        }

    }

    public void trade_sell()
    {
        drawInventory(gamepanel.player, true);

        // Draw hint window
        int x = gamepanel.tileSize - 10;
        int y = gamepanel.screenHeight - gamepanel.tileSize * 5;
        int width = gamepanel.tileSize * 9 - gamepanel.tileSize / 2 + 15;
        int height = gamepanel.tileSize * 4 + gamepanel.tileSize / 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 20, y + height - 20);
        g2.drawRoundRect(x + 6, y + height - 50, 150, 43, 30, 30);

        // Draw player coin
        int coinX = gamepanel.tileSize * 25 + gamepanel.tileSize / 2;
        int coinY = gamepanel.screenHeight - gamepanel.tileSize * 5 - gamepanel.tileSize / 2 - 10;
        int coinWidth = 200;
        int coinHeight = 44;
        drawSubWindow(coinX, coinY, coinWidth, coinHeight);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 31f));
        g2.drawString("Coin: " + gamepanel.player.coin, coinX + 20, coinY + 30);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gamepanel.player.inventory.size())
        {
            int priceX = gamepanel.tileSize * 10;
            int priceY = gamepanel.screenHeight - gamepanel.tileSize * 5 - gamepanel.tileSize / 2 - 10;
            int priceWidth = 108;
            int priceHeight = 43;
            drawSubWindow(priceX, priceY, priceWidth, priceHeight);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 31f));
            g2.drawImage(coin, priceX + 10, priceY + 2, 40, 45, null);
            int price = gamepanel.player.inventory.get(itemIndex).price * 90 / 100;
            g2.drawString(String.valueOf(price), priceX + priceWidth - gamepanel.tileSize - String.valueOf(price).length(), priceY + 30);
            // Sell item
            if(gamepanel.Key.enterPressed)
            {
                if(gamepanel.player.inventory.get(itemIndex) == gamepanel.player.currentWeapon
                        || gamepanel.player.inventory.get(itemIndex) == gamepanel.player.currentShield)
                {
                    subState = 0;
                    npc.startDialogue(npc, 4);
                    //drawDialogueScreen();
                }
                else
                {
                    if(gamepanel.player.inventory.get(itemIndex).quantity > 1)
                    {
                        gamepanel.player.inventory.get(itemIndex).quantity--;
                    }
                    else {
                        gamepanel.player.inventory.remove(itemIndex);
                    }
                    gamepanel.player.coin += price;
                }
            }
        }
    }

    public void drawSleepScreen()
    {
        counter++;
        if(counter < 360) {
            gamepanel.environmentManager.lightning.filterAlpha += 0.01f;
            if(gamepanel.environmentManager.lightning.filterAlpha > 1f)
            {
                gamepanel.environmentManager.lightning.filterAlpha = 1f;
            }
        }
        if(counter >= 360) { // approximately 3 sec
            gamepanel.environmentManager.lightning.filterAlpha -= 0.01f;
            if(gamepanel.environmentManager.lightning.filterAlpha < 0f)
            {
                gamepanel.environmentManager.lightning.filterAlpha = 0f;
                counter = 0;
                gamepanel.environmentManager.lightning.dayState = gamepanel.environmentManager.lightning.day;
                gamepanel.environmentManager.lightning.dayCounter = 0;
                gamepanel.gameState = gamepanel.playState;
                gamepanel.player.getPlayerImage();
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
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
