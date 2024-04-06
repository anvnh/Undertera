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

    public Player(GamePanel gp, KeyboardHandler kh){
        super(gp);
        this.gamepanel = gp;
        this.Key = kh;
        screenX = gamepanel.screenWidth / 2 - (gamepanel.playerSize / 2);
        screenY = gamepanel.screenHeight / 2 - (gamepanel.playerSize / 2);

        solidArea = new Rectangle();

        solidArea.x = 30;
        solidArea.y = 35;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        solidArea.width = 20;
        solidArea.height = 25;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gamepanel.tileSize * 22;
        worldY = gamepanel.tileSize * 20;
        speed = 3;
        direction = "down";
    }
    public void getPlayerImage()
    {
        //Standing position
        setup(0, stand_right, "/player/stand_right_1");
        setup(1, stand_right, "/player/stand_right_2");
        setup(2, stand_right, "/player/stand_right_3");
        setup(3, stand_right, "/player/stand_right_4");
        setup(4, stand_right, "/player/stand_right_5");
        setup(5, stand_right, "/player/stand_right_6");

        setup(0, stand_down, "/player/stand_down_1");
        setup(1, stand_down, "/player/stand_down_2");
        setup(2, stand_down, "/player/stand_down_3");
        setup(3, stand_down, "/player/stand_down_4");
        setup(4, stand_down, "/player/stand_down_5");
        setup(5, stand_down, "/player/stand_down_6");

        setup(0, stand_left, "/player/stand_left_1");
        setup(1, stand_left, "/player/stand_left_2");
        setup(2, stand_left, "/player/stand_left_3");
        setup(3, stand_left, "/player/stand_left_4");
        setup(4, stand_left, "/player/stand_left_5");
        setup(5, stand_left, "/player/stand_left_6");

        setup(0, stand_up, "/player/stand_up_1");
        setup(1, stand_up, "/player/stand_up_2");
        setup(2, stand_up, "/player/stand_up_3");
        setup(3, stand_up, "/player/stand_up_4");
        setup(4, stand_up, "/player/stand_up_5");
        setup(5, stand_up, "/player/stand_up_6");

        //Movement
        setup(0, go_right, "/player/go_right_1");
        setup(1, go_right, "/player/go_right_2");
        setup(2, go_right, "/player/go_right_3");
        setup(3, go_right, "/player/go_right_4");
        setup(4, go_right, "/player/go_right_5");
        setup(5, go_right, "/player/go_right_6");

        setup(0, go_down, "/player/go_down_1");
        setup(1, go_down, "/player/go_down_2");
        setup(2, go_down, "/player/go_down_3");
        setup(3, go_down, "/player/go_down_4");
        setup(4, go_down, "/player/go_down_5");
        setup(5, go_down, "/player/go_down_6");

        setup(0, go_left, "/player/go_left_1");
        setup(1, go_left, "/player/go_left_2");
        setup(2, go_left, "/player/go_left_3");
        setup(3, go_left, "/player/go_left_4");
        setup(4, go_left, "/player/go_left_5");
        setup(5, go_left, "/player/go_left_6");

        setup(0, go_up, "/player/go_up_1");
        setup(1, go_up, "/player/go_up_2");
        setup(2, go_up, "/player/go_up_3");
        setup(3, go_up, "/player/go_up_4");
        setup(4, go_up, "/player/go_up_5");
        setup(5, go_up, "/player/go_up_6");


        /*
        up_1 = setup_npc("/player/boy_up_1");
        up_2 = setup_npc("/player/boy_up_2");
        down_1 = setup_npc("/player/boy_down_1");
        down_2 = setup_npc("/player/boy_down_2");
        left_1 = setup_npc("/player/boy_left_1");
        left_2 = setup_npc("/player/boy_left_2");
        right_1 = setup_npc("/player/boy_right_1");
        right_2 = setup_npc("/player/boy_right_2");
         */
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

            collisionOn = false;

            // Check object collision
            int objectIndex = gamepanel.collisionCheck.checkObject(this, true);
            pickUpObject(objectIndex);

            // Check NPC Collision
            int npcIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.npc);
            interactNPC(npcIndex);

            //Check tile collision
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

            /*
            runCount++;
            if (runCount > 15)
            {
                if (runAnimation == 1)
                    runAnimation = 2;
                else if (runAnimation == 2)
                    runAnimation = 1;
                runCount = 0;
            }
             */
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
        }
    }

    public void interactNPC(int npcIndex) {
        if(npcIndex != 999)
        {
            if(gamepanel.Key.enterPressed)
            {
                gamepanel.gameState = gamepanel.dialogueState;
                gamepanel.npc[npcIndex].speak();
                gamepanel.Key.enterPressed = false;
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
                //image = runAnimation == 1 ? down_1 : down_2;
            }
            else if(direction.equals("up"))
            {
                image = getRunImage(image, stand_up);
                //image = runAnimation == 1 ? up_1 : up_2;
            }
            else if(direction.equals("left"))
            {
                image = getRunImage(image, stand_left);
                //image = runAnimation == 1 ? left_1 : left_2;
            }
            else if(direction.equals("right"))
            {
                image = getRunImage(image, stand_right);
                //image = runAnimation == 1 ? right_1 : right_2;
            }
            g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }

        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed)
        {
            if(direction.equals("up"))
            {
                image = getBufferedImage(image, go_up);
                //image = runAnimation == 1 ? up_1 : up_2;
            }
            else if(direction.equals("down"))
            {
                image = getBufferedImage(image, go_down);
                //image = runAnimation == 0 ? down_1 : down_2;
            }
            else if(direction.equals("left"))
            {
                image = getBufferedImage(image, go_left);
                //image = runAnimation == 1 ? left_1 : left_2;
            }
            else if(direction.equals("right"))
            {
                image = getBufferedImage(image, go_right);
                //image = runAnimation == 1 ? right_1 : right_2;
            }
            g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }
    }
}
