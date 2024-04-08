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

        attackArea.width = 35;
        attackArea.height = 35;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
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
        //Standing position
        for(int i = 0; i < 6; i++)
        {
            stand_down[i] = setup_player("/player/stand_down_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            stand_up[i] = setup_player("/player/stand_up_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            stand_right[i] = setup_player("/player/stand_right_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            stand_left[i] = setup_player("/player/stand_left_" + (i + 1));
        }

        //Running position
        for(int i = 0; i < 6; i++)
        {
            go_down[i] = setup_player("/player/go_down_" + (i + 1));
        }
         for(int i = 0; i < 6; i++)
        {
            go_up[i] = setup_player("/player/go_up_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            go_left[i] = setup_player("/player/go_left_" + (i + 1));
        }
           for(int i = 0; i < 6; i++)
        {
            go_right[i] = setup_player("/player/go_right_" + (i + 1));
        }
    }
    public void getPlayerAttackImage()
    {
        for(int i = 0; i < 4; i++)
        {
            attack_down[i] = setup_player("/player/attack/attack_down_" + (i));
        }
        for(int i = 0; i < 4; i++)
        {
            attack_up[i] = setup_player("/player/attack/attack_up_" + (i));
        }
        for(int i = 0; i < 4; i++)
        {
            attack_right[i] = setup_player("/player/attack/attack_right_" + (i));
        }
        for(int i = 0; i < 4; i++)
        {
            attack_left[i] = setup_player("/player/attack/attack_left_" + (i));
        }
    }
    public void update()
    {
        if(attacking)
        {
            attack();
        }
        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed || Key.communicateWithNPC)
        {
            if (Key.upPressed) {
                direction = "up";
                //worldY -= speed;
            } else if (Key.downPressed) {
                direction = "down";
                //worldY += speed;
            } else if (Key.leftPressed) {
                direction = "left";
                //worldX -= speed;
            } else if (Key.rightPressed){
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

            // Check Monster's Collision
            int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
            contactMonster(monsterIndex);

            //Attack
            check_attack();

            // Check event
            gamepanel.eventHandler.checkEvent();

            //Check tile collision
            gamepanel.collisionCheck.checkTile(this);

            // Check attacking
            if(gamepanel.Key.K_Pressed)
            {
                attacking = true;
            }


            //if collision is false, player can move
            if(!collisionOn && !Key.communicateWithNPC)
            {
                switch (direction)
                {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            gamepanel.Key.communicateWithNPC = false;

            runCount++;
            if (runCount > 15) {
                runAnimation = runAnimation == 6 ? 1 : runAnimation + 1;
                runCount = 0;
            }
        }
        else {
            standCount++;
            if (standCount > 15) {
                standAnimation = standAnimation == 6 ? 1 : standAnimation + 1;
                standCount = 0;
            }
        }

        if(invincible)
        {
            invincibleCounter++;
            if(invincibleCounter == 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }
    public void check_attack()
    {
        if(gamepanel.Key.K_Pressed)
        {
            attacking = true;
            gamepanel.Key.K_Pressed = false;
            gamepanel.playSoundEffect(1);
        }
    }

    public void attack()
    {
        attackCount++;
        if(attackCount <= 5) {
            attackAnimation = 1;
        }
        if(attackCount > 5 && attackCount <= 25) {
            attackAnimation = 2;

            //Current world x, world y, solid area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            if(direction.equals("up"))
            {
                worldY -= attackArea.height;
            }
            if(direction.equals("down"))
            {
                worldY += attackArea.height;
            }
            if(direction.equals("left"))
            {
                worldX -= attackArea.width;
            }
            if(direction.equals("right"))
            {
                worldX += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(attackCount > 25 && attackCount <= 45) attackAnimation = 3;
        if(attackCount > 45 && attackCount <= 65) attackAnimation = 4;
        if(attackCount > 65)
        {
            attackCount = 0;
            attackAnimation = 1;
            attacking = false;
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
                //gamepanel.Key.communicateWithNPC = false;
            }
        }
    }

    public void contactMonster(int monsterIndex) {
        if(monsterIndex != 999)
        {
            if(!invincible)
            {
                life -= 5;
                invincible = true;
                gamepanel.playSoundEffect(4);
            }
        }
    }

    public void damageMonster(int monsterIndex)
    {
        if(monsterIndex != 999)
        {
            if(!gamepanel.monster[monsterIndex].invincible)
            {
                gamepanel.monster[monsterIndex].life -= 1;
                gamepanel.monster[monsterIndex].invincible = true;
                gamepanel.playSoundEffect(3);

                if(gamepanel.monster[monsterIndex].life <= 0)
                {
                    gamepanel.monster[monsterIndex].dying = true;
                }
            }
        }
    }

    public void draw_player(Graphics2D g2)
    {
        BufferedImage image = null;
        if(!Key.upPressed && !Key.downPressed && !Key.leftPressed && !Key.rightPressed)
        {
            check_attack();
            if(direction.equals("down"))
            {
                if(!attacking)
                {
                    image = getStandAnimate(image, stand_down);
                }
                else {
                    image = getAttackAnimate(image, attack_down);
                }
            }
            else if(direction.equals("up"))
            {
                if(!attacking)
                {
                    image = getStandAnimate(image, stand_up);
                }
                else {
                    image = getAttackAnimate(image, attack_up);
                }
            }
            else if(direction.equals("left"))
            {
                if(!attacking)
                {
                    image = getStandAnimate(image, stand_left);
                }
                else {
                    image = getAttackAnimate(image, attack_left);
                }
            }
            else if(direction.equals("right"))
            {
                if(!attacking)
                {
                    image = getStandAnimate(image, stand_right);
                }
                else {
                    image = getAttackAnimate(image, attack_right);
                }
            }
            if(invincible)
            {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            //
            g2.drawImage(image, screenX, screenY, null);

            //Reset alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            //g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }

        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed)
        {
            if(direction.equals("up"))
            {
                if(!attacking)
                {
                    image = getRunAnimate(image, go_up);
                }
                else {
                    image = getAttackAnimate(image, attack_up);
                }
            }
            else if(direction.equals("down"))
            {
                if(!attacking)
                {
                    image = getRunAnimate(image, go_down);
                }
                else {
                    image = getAttackAnimate(image, attack_down);
                }
            }
            else if(direction.equals("left"))
            {
                if(!attacking)
                {
                    image = getRunAnimate(image, go_left);
                }
                else {
                    image = getAttackAnimate(image, attack_left);
                }
            }
            else if(direction.equals("right"))
            {
                if(!attacking)
                {
                    image = getRunAnimate(image, go_right);
                }
                else {
                    image = getAttackAnimate(image, attack_right);
                }
            }
            if(invincible)
            {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            //
            g2.drawImage(image, screenX, screenY, null);

            //Reset alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            //g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }
    }
}
