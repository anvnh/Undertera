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
        name = "player";
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gamepanel.tileSize * 22;
        worldY = gamepanel.tileSize * 20;
        speed = 3;
        direction = "down";

        //Status
        maxLife = 100;
        life = 100;
    }
    public void getPlayerImage()
    {
        //Stand and running position
        stand_down[0] = setup_player("/player/stand_down_1");
        stand_down[1] = setup_player("/player/stand_down_2");
        stand_down[2] = setup_player("/player/stand_down_3");
        stand_down[3] = setup_player("/player/stand_down_4");
        stand_down[4] = setup_player("/player/stand_down_5");
        stand_down[5] = setup_player("/player/stand_down_6");

        stand_up[0] = setup_player("/player/stand_up_1");
        stand_up[1] = setup_player("/player/stand_up_2");
        stand_up[2] = setup_player("/player/stand_up_3");
        stand_up[3] = setup_player("/player/stand_up_4");
        stand_up[4] = setup_player("/player/stand_up_5");
        stand_up[5] = setup_player("/player/stand_up_6");

        stand_right[0] = setup_player("/player/stand_right_1");
        stand_right[1] = setup_player("/player/stand_right_2");
        stand_right[2] = setup_player("/player/stand_right_3");
        stand_right[3] = setup_player("/player/stand_right_4");
        stand_right[4] = setup_player("/player/stand_right_4");
        stand_right[5] = setup_player("/player/stand_right_6");

        stand_left[0] = setup_player("/player/stand_left_1");
        stand_left[1] = setup_player("/player/stand_left_2");
        stand_left[2] = setup_player("/player/stand_left_3");
        stand_left[3] = setup_player("/player/stand_left_4");
        stand_left[4] = setup_player("/player/stand_left_5");
        stand_left[5] = setup_player("/player/stand_left_6");

        go_down[0] = setup_player("/player/go_down_1");
        go_down[1] = setup_player("/player/go_down_2");
        go_down[2] = setup_player("/player/go_down_3");
        go_down[3] = setup_player("/player/go_down_4");
        go_down[4] = setup_player("/player/go_down_5");
        go_down[5] = setup_player("/player/go_down_6");

        go_up[0] = setup_player("/player/go_up_1");
        go_up[1] = setup_player("/player/go_up_2");
        go_up[2] = setup_player("/player/go_up_3");
        go_up[3] = setup_player("/player/go_up_4");
        go_up[4] = setup_player("/player/go_up_5");
        go_up[5] = setup_player("/player/go_up_6");

        go_left[0] = setup_player("/player/go_left_1");
        go_left[1] = setup_player("/player/go_left_2");
        go_left[2] = setup_player("/player/go_left_3");
        go_left[3] = setup_player("/player/go_left_4");
        go_left[4] = setup_player("/player/go_left_5");
        go_left[5] = setup_player("/player/go_left_6");

        go_right[0] = setup_player("/player/go_right_1");
        go_right[1] = setup_player("/player/go_right_2");
        go_right[2] = setup_player("/player/go_right_3");
        go_right[3] = setup_player("/player/go_right_4");
        go_right[4] = setup_player("/player/go_right_5");
        go_right[5] = setup_player("/player/go_right_6");

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

            // Check event
            gamepanel.eventHandler.checkEvent();

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
            if(gamepanel.Key.communicateWithNPC)
            {
                gamepanel.gameState = gamepanel.dialogueState;
                gamepanel.npc[npcIndex].speak();
                gamepanel.Key.communicateWithNPC = false;
            }
        }
    }
    public void draw_player(Graphics2D g2)
    {
        BufferedImage image = null;
        if(!Key.upPressed && !Key.downPressed && !Key.leftPressed && !Key.rightPressed)
        {
            if(direction.equals("down"))
            {
                image = getStandAnimate(image, stand_down);
                //image = runAnimation == 1 ? down_1 : down_2;
            }
            else if(direction.equals("up"))
            {
                image = getStandAnimate(image, stand_up);
                //image = runAnimation == 1 ? up_1 : up_2;
            }
            else if(direction.equals("left"))
            {
                image = getStandAnimate(image, stand_left);
                //image = runAnimation == 1 ? left_1 : left_2;
            }
            else if(direction.equals("right"))
            {
                image = getStandAnimate(image, stand_right);
                //image = runAnimation == 1 ? right_1 : right_2;
            }
            g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }

        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed)
        {
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
            g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }
    }
}
