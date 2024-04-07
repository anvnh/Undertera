package entity;

import main.GamePanel;
import main.KeyboardHandler;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {
    GamePanel gamepanel;
    public int worldX, worldY;
    public int speed;
    public String direction = "down";
    public int runCount = 0;
    public int runAnimation = 1;
    public int standCount = 0;
    public int standAnimation = 1;

    //Buffered Image for Player
    public BufferedImage[] stand_down = new BufferedImage[6];
    public BufferedImage[] stand_up = new BufferedImage[6];
    public BufferedImage[] stand_left = new BufferedImage[6];
    public BufferedImage[] stand_right = new BufferedImage[6];
    public BufferedImage[] go_down = new BufferedImage[6];
    public BufferedImage[] go_up = new BufferedImage[6];
    public BufferedImage[] go_left = new BufferedImage[6];
    public BufferedImage[] go_right = new BufferedImage[6];

    public BufferedImage[] stand_down_entity = new BufferedImage[20];
    public BufferedImage[] stand_up_entity = new BufferedImage[20];
    public BufferedImage[] stand_left_entity = new BufferedImage[20];
    public BufferedImage[] stand_right_entity = new BufferedImage[20];
    public BufferedImage[] go_down_entity = new BufferedImage[20];
    public BufferedImage[] go_up_entity = new BufferedImage[20];
    public BufferedImage[] go_left_entity = new BufferedImage[20];
    public BufferedImage[] go_right_entity = new BufferedImage[20];
    //Buffered Image for NPC
    public BufferedImage up_1, up_2, down_1, down_2, left_1, left_2, right_1, right_2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int type;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    String[] dialogue = new String[20];
    int dialogueIndex = 0;

    public BufferedImage image, image1, image2, image3, image4, image5;
    public String name;
    public boolean collision = false;

    //Character status
    public double maxLife;
    public double life;
    KeyboardHandler Key;
    public Entity(GamePanel gamePanel)
    {
        this.gamepanel = gamePanel;
    }

    public void setAction(){}
    public void speak() {
        if(dialogueIndex == 11)
            dialogueIndex = 0;
        gamepanel.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
        if(Objects.equals(gamepanel.player.direction, "up"))
            direction = "down";
        else if(Objects.equals(gamepanel.player.direction, "down"))
            direction = "up";
        else if(Objects.equals(gamepanel.player.direction, "left"))
            direction = "right";
        else if(Objects.equals(gamepanel.player.direction, "right"))
            direction = "left";
    }
    public void update(){

        setAction();

        collisionOn = false;
        gamepanel.collisionCheck.checkTile(this);
        gamepanel.collisionCheck.checkObject(this, false);
        gamepanel.collisionCheck.checkEntity(this, gamepanel.npc);
        gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
        boolean contactPlayer = gamepanel.collisionCheck.checkPlayer(this);

        if(this.type == 2 && contactPlayer)
        {
            if(!gamepanel.player.invincible)
            {
                gamepanel.player.life -= 5;
                gamepanel.player.invincible = true;
            }

        }

        //if collision is false, player can move
        if(!collisionOn)
        {
            switch (direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        runCount++;
        if (runCount > 15) {
            runAnimation = runAnimation == 6 ? 1 : runAnimation + 1;
            runCount = 0;
        }
    }

    public BufferedImage setup_player(String imgName)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imgName + ".png")));
            scaledImage = utilityTools.scaleImage(scaledImage, gamepanel.playerSize, gamepanel.playerSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
    public BufferedImage setup_entity(String imgPath)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream(imgPath + ".png"))));
            image = utilityTools.scaleImage(image, gamepanel.npcSize, gamepanel.npcSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    /*
    public BufferedImage setup_entity_1(String imgPath)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream(imgPath + ".png"))));
            image = utilityTools.scaleImage(image, gamepanel.testSize, gamepanel.testSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    */
    public BufferedImage getStandAnimate(BufferedImage image, BufferedImage[] stand)
    {
        image = stand[standAnimation - 1];
        return image;
    }
    public BufferedImage getRunAnimate(BufferedImage image, BufferedImage[] run) {
        image = run[runAnimation - 1];
        return image;
    }
    /*
    public void draw_entity(Graphics2D g2)
    {
        BufferedImage image = null;
        int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
        int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;
        switch (direction)
        {
            case "up":
                if(runAnimation == 1)
                    image = up_1;
                else if(runAnimation == 2)
                    image = up_2;
                break;
            case "down":
                if(runAnimation == 1)
                    image = down_1;
                else if(runAnimation == 2)
                    image = down_2;
                break;
            case "left":
                if(runAnimation == 1)
                    image = left_1;
                else if(runAnimation == 2)
                    image = left_2;
                break;
            case "right":
                if(runAnimation == 1)
                    image = right_1;
                else if(runAnimation == 2)
                    image = right_2;
                break;
        }

        g2.drawImage(image, screenX, screenY, gamepanel.npcSize, gamepanel.npcSize, null);
    }
     */
    public void draw_entity(Graphics2D g2)
    {
        BufferedImage image = null;
        int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
        int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;
        if(direction.equals("up"))
        {
            image = getRunAnimate(image, go_up);
            //image = runAnimation == 1 ? up_1 : up_2;
        }
        else if(direction.equals("down"))
        {
            image = getRunAnimate(image, go_down);
            //image = runAnimation == 0 ? down_1 : down_2;
        }
        else if(direction.equals("left"))
        {
            image = getRunAnimate(image, go_left);
            //image = runAnimation == 1 ? left_1 : left_2;
        }
        else if(direction.equals("right"))
        {
            image = getRunAnimate(image, go_right);
            //image = runAnimation == 1 ? right_1 : right_2;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
