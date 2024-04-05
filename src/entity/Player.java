package entity;
import main.GamePanel;
import main.KeyboardHandler;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamepanel;
    KeyboardHandler Key;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    BufferedImage[] stand_down = new BufferedImage[6];
    BufferedImage[] stand_up = new BufferedImage[6];
    BufferedImage[] stand_left = new BufferedImage[6];
    BufferedImage[] stand_right = new BufferedImage[6];

    BufferedImage[] go_down = new BufferedImage[6];
    BufferedImage[] go_up = new BufferedImage[6];
    BufferedImage[] go_left = new BufferedImage[6];
    BufferedImage[] go_right = new BufferedImage[6];
    public Player(GamePanel gp, KeyboardHandler kh){
        this.gamepanel = gp;
        this.Key = kh;
        screenX = gamepanel.screenWidth / 2 - (gamepanel.playerSize / 2);
        screenY = gamepanel.screenHeight / 2 - (gamepanel.playerSize / 2);

        solidArea = new Rectangle();

        solidArea.x = 40;
        solidArea.y = 60;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        solidArea.width = 20;
        solidArea.height = 20;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gamepanel.tileSize * 16;
        worldY = gamepanel.tileSize * 11;
        speed = 3;
        direction = "down";
    }
    public void getPlayerImage()
    {
        //Standing position
        setup(0, stand_right, "stand_right_1");
        setup(1, stand_right, "stand_right_2");
        setup(2, stand_right, "stand_right_3");
        setup(3, stand_right, "stand_right_4");
        setup(4, stand_right, "stand_right_5");
        setup(5, stand_right, "stand_right_6");

        setup(0, stand_down, "stand_down_1");
        setup(1, stand_down, "stand_down_2");
        setup(2, stand_down, "stand_down_3");
        setup(3, stand_down, "stand_down_4");
        setup(4, stand_down, "stand_down_5");
        setup(5, stand_down, "stand_down_6");

        setup(0, stand_left, "stand_left_1");
        setup(1, stand_left, "stand_left_2");
        setup(2, stand_left, "stand_left_3");
        setup(3, stand_left, "stand_left_4");
        setup(4, stand_left, "stand_left_5");
        setup(5, stand_left, "stand_left_6");

        setup(0, stand_up, "stand_up_1");
        setup(1, stand_up, "stand_up_2");
        setup(2, stand_up, "stand_up_3");
        setup(3, stand_up, "stand_up_4");
        setup(4, stand_up, "stand_up_5");
        setup(5, stand_up, "stand_up_6");

        //Movement
        setup(0, go_right, "go_right_1");
        setup(1, go_right, "go_right_2");
        setup(2, go_right, "go_right_3");
        setup(3, go_right, "go_right_4");
        setup(4, go_right, "go_right_5");
        setup(5, go_right, "go_right_6");

        setup(0, go_down, "go_down_1");
        setup(1, go_down, "go_down_2");
        setup(2, go_down, "go_down_3");
        setup(3, go_down, "go_down_4");
        setup(4, go_down, "go_down_5");
        setup(5, go_down, "go_down_6");

        setup(0, go_left, "go_left_1");
        setup(1, go_left, "go_left_2");
        setup(2, go_left, "go_left_3");
        setup(3, go_left, "go_left_4");
        setup(4, go_left, "go_left_5");
        setup(5, go_left, "go_left_6");

        setup(0, go_up, "go_up_1");
        setup(1, go_up, "go_up_2");
        setup(2, go_up, "go_up_3");
        setup(3, go_up, "go_up_4");
        setup(4, go_up, "go_up_5");
        setup(5, go_up, "go_up_6");

    }
    public BufferedImage setup(int index, BufferedImage[] animationType, String imgName)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imgName + ".png")));
            scaledImage = utilityTools.scaleImage(scaledImage, gamepanel.playerSize, gamepanel.playerSize);
            animationType[index] = scaledImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
    public void update()
    {
        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed) {
            if (Key.upPressed) {
                direction = "up";
                //worldY -= speed;
            } else if (Key.downPressed) {
                direction = "down";
                //worldY += speed;
            } else if (Key.leftPressed) {
                direction = "left";
                //worldX -= speed;
            } else {
                direction = "right";
                //worldX += speed;
            }

            //Check tile collision
            collisionOn = false;
            gamepanel.collisionCheck.checkTile(this);
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

            // Check object collision
            int objectIndex = gamepanel.collisionCheck.checkObject(this, true);
            pickUpObject(objectIndex);


            runCount++;
            if (runCount > 15) {
                if (runAnimation == 1)
                    runAnimation = 2;
                else if (runAnimation == 2)
                    runAnimation = 3;
                else if (runAnimation == 3)
                    runAnimation = 4;
                else if (runAnimation == 4)
                    runAnimation = 5;
                else if (runAnimation == 5)
                    runAnimation = 6;
                else if (runAnimation == 6)
                    runAnimation = 1;
                runCount = 0;
            }
        }
        else {
            standCount++;
            if (standCount > 15) {
                if (standAnimation == 1)
                    standAnimation = 2;
                else if (standAnimation == 2)
                    standAnimation = 3;
                else if (standAnimation == 3)
                    standAnimation = 4;
                else if (standAnimation == 4)
                    standAnimation = 5;
                else if (standAnimation == 5)
                    standAnimation = 6;
                else if (standAnimation == 6)
                    standAnimation = 1;
                standCount = 0;
            }
        }
    }
    public void pickUpObject(int objectIndex) {
        if(objectIndex != 999)
        {
            //gamepanel.basedObject[objectIndex] = null;
            String objectName = gamepanel.basedObject[objectIndex].name;
            switch(objectName)
            {
                case "Key":
                    hasKey++;
                    gamepanel.basedObject[objectIndex] = null;
                    break;
                case "Chest":
                    break;
                case "Door":
                    if(hasKey > 0)
                    {
                        hasKey--;
                        gamepanel.basedObject[objectIndex] = null;
                    }
                    break;
            }
        }
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        if(!Key.upPressed && !Key.downPressed && !Key.leftPressed && !Key.rightPressed)
        {
            if(direction.equals("down"))
            {
                image = getRunImage(image, stand_down);
            }
            else if(direction.equals("up"))
            {
                image = getRunImage(image, stand_up);
            }
            else if(direction.equals("left"))
            {
                image = getRunImage(image, stand_left);
            }
            else if(direction.equals("right"))
            {
                image = getRunImage(image, stand_right);
            }
            g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }
        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed)
        {
            if(direction.equals("up"))
            {
                image = getBufferedImage(image, go_up);
            }
            else if(direction.equals("down"))
            {
                image = getBufferedImage(image, go_down);
            }
            else if(direction.equals("left"))
            {
                image = getBufferedImage(image, go_left);
            }
            else if(direction.equals("right"))
            {
                image = getBufferedImage(image, go_right);
            }
            g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }
    }
    private BufferedImage getRunImage(BufferedImage image, BufferedImage[] stand)
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
    private BufferedImage getBufferedImage(BufferedImage image, BufferedImage[] run) {
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
}
