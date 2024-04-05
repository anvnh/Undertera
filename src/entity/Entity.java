package entity;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {
    GamePanel gamepanel;
    public int worldX, worldY;
    public int speed;
    public String direction;
    public int runCount = 0;
    public int runAnimation = 1;
    public int standCount = 0;
    public int standAnimation = 1;

    //Buffered Image for Player
    BufferedImage[] stand_down = new BufferedImage[6];
    BufferedImage[] stand_up = new BufferedImage[6];
    BufferedImage[] stand_left = new BufferedImage[6];
    BufferedImage[] stand_right = new BufferedImage[6];
    BufferedImage[] go_down = new BufferedImage[6];
    BufferedImage[] go_up = new BufferedImage[6];
    BufferedImage[] go_left = new BufferedImage[6];
    BufferedImage[] go_right = new BufferedImage[6];

    //Buffered Image for NPC
    BufferedImage up_1, up_2, down_1, down_2, left_1, left_2, right_1, right_2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public Entity(GamePanel gamePanel)
    {
        this.gamepanel = gamePanel;
    }

    public void setAction(){}
    public void update(){
        setAction();
        collisionOn = false;
        gamepanel.collisionCheck.checkTile(this);
        gamepanel.collisionCheck.checkObject(this, false);
        gamepanel.collisionCheck.checkPlayer(this);
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
        if(runCount > 15)
        {
            if(runAnimation == 1)
                runAnimation = 2;
            else if(runAnimation == 2)
                runAnimation = 1;
            runCount = 0;
        }
    }

    public BufferedImage setup(int index, BufferedImage[] animationType, String imgName)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imgName + ".png")));
            scaledImage = utilityTools.scaleImage(scaledImage, gamepanel.playerSize, gamepanel.playerSize);
            animationType[index] = scaledImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
    public BufferedImage setup_npc(String imgName)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imgName + ".png")));
            scaledImage = utilityTools.scaleImage(scaledImage, gamepanel.npcSize, gamepanel.npcSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
    public BufferedImage getRunImage(BufferedImage image, BufferedImage[] stand)
    {
        if(standAnimation == 1)
            image = stand[0];
        else if(standAnimation == 2)
            image = stand[1];
        else if(standAnimation == 3)
            image = stand[2];
        else if(standAnimation == 4)
            image = stand[3];
        else if(standAnimation == 5)
            image = stand[4];
        else if(standAnimation == 6)
            image = stand[5];
        return image;
    }
    public BufferedImage getBufferedImage(BufferedImage image, BufferedImage[] run) {
        if(runAnimation == 1)
            image = run[0];
        else if(runAnimation == 2)
            image = run[1];
        else if(runAnimation == 3)
            image = run[2];
        else if(runAnimation == 4)
            image = run[3];
        else if(runAnimation == 5)
            image = run[4];
        else if(runAnimation == 6)
            image = run[5];
        return image;
    }
    public void draw_npc(Graphics2D g2)
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
}
