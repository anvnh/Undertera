package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.util.Random;

public class Skeleton extends Entity {
    GamePanel gamepanel;
    public Skeleton(GamePanel gamePanel) {
        super(gamePanel);
        this.gamepanel = gamePanel;

        type = type_monster;
        name = "skeleton";
        speed = 1;
        originalSpeed = 1;
        maxLife = 150;
        life = maxLife;
        attack = 30;
        defense = 80;
        exp = 20;

        solidArea.x = 30;
        solidArea.y = 35;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 20;
        solidArea.height = 25;

        attackArea.width = 48;
        attackArea.height = 48;
        getImage();
    }
    public void getImage() {
        go_up[0] = setup_entity("/monster/skeleton/skeleton_up_0", gamepanel.monsterSize, gamepanel.monsterSize);
        go_up[1] = setup_entity("/monster/skeleton/skeleton_up_1", gamepanel.monsterSize, gamepanel.monsterSize);
        go_up[2] = setup_entity("/monster/skeleton/skeleton_up_2", gamepanel.monsterSize, gamepanel.monsterSize);
        go_up[3] = setup_entity("/monster/skeleton/skeleton_up_3", gamepanel.monsterSize, gamepanel.monsterSize);
        go_up[4] = setup_entity("/monster/skeleton/skeleton_up_4", gamepanel.monsterSize, gamepanel.monsterSize);
        go_up[5] = setup_entity("/monster/skeleton/skeleton_up_5", gamepanel.monsterSize, gamepanel.monsterSize);

        go_down[0] = setup_entity("/monster/skeleton/skeleton_down_0", gamepanel.monsterSize, gamepanel.monsterSize);
        go_down[1] = setup_entity("/monster/skeleton/skeleton_down_1", gamepanel.monsterSize, gamepanel.monsterSize);
        go_down[2] = setup_entity("/monster/skeleton/skeleton_down_2", gamepanel.monsterSize, gamepanel.monsterSize);
        go_down[3] = setup_entity("/monster/skeleton/skeleton_down_3", gamepanel.monsterSize, gamepanel.monsterSize);
        go_down[4] = setup_entity("/monster/skeleton/skeleton_down_4", gamepanel.monsterSize, gamepanel.monsterSize);
        go_down[5] = setup_entity("/monster/skeleton/skeleton_down_5", gamepanel.monsterSize, gamepanel.monsterSize);

        go_left[0] = setup_entity("/monster/skeleton/skeleton_left_0", gamepanel.monsterSize, gamepanel.monsterSize);
        go_left[1] = setup_entity("/monster/skeleton/skeleton_left_1", gamepanel.monsterSize, gamepanel.monsterSize);
        go_left[2] = setup_entity("/monster/skeleton/skeleton_left_2", gamepanel.monsterSize, gamepanel.monsterSize);
        go_left[3] = setup_entity("/monster/skeleton/skeleton_left_3", gamepanel.monsterSize, gamepanel.monsterSize);
        go_left[4] = setup_entity("/monster/skeleton/skeleton_left_4", gamepanel.monsterSize, gamepanel.monsterSize);
        go_left[5] = setup_entity("/monster/skeleton/skeleton_left_5", gamepanel.monsterSize, gamepanel.monsterSize);

        go_right[0] = setup_entity("/monster/skeleton/skeleton_right_0", gamepanel.monsterSize, gamepanel.monsterSize);
        go_right[1] = setup_entity("/monster/skeleton/skeleton_right_1", gamepanel.monsterSize, gamepanel.monsterSize);
        go_right[2] = setup_entity("/monster/skeleton/skeleton_right_2", gamepanel.monsterSize, gamepanel.monsterSize);
        go_right[3] = setup_entity("/monster/skeleton/skeleton_right_3", gamepanel.monsterSize, gamepanel.monsterSize);
        go_right[4] = setup_entity("/monster/skeleton/skeleton_right_4", gamepanel.monsterSize, gamepanel.monsterSize);
        go_right[5] = setup_entity("/monster/skeleton/skeleton_right_5", gamepanel.monsterSize, gamepanel.monsterSize);

        getMonsterAttackImage(this.name);
    }

    public void update()
    {
        super.update();

        int xDistance = Math.abs(worldX - gamepanel.player.worldX);
        int yDistance = Math.abs(worldY - gamepanel.player.worldY);
        int tileDistance = (int)Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2)) / gamepanel.tileSize;

        // Minimum distance to have aggro
        if(!onPath && tileDistance < 3)
        {
            onPath = true;
            attacking = true;
        }
        // The maximum distance to drop aggro
        if(onPath && tileDistance > 8)
        {
            onPath = false;
            attacking = false;
        }
    }
    public void setAction()
    {
        if(onPath)
        {
            int endCol = (gamepanel.player.worldX + gamepanel.player.solidArea.x) / gamepanel.tileSize;
            int endRow = (gamepanel.player.worldY + gamepanel.player.solidArea.y) / gamepanel.tileSize;

            searchPath(endCol, endRow);
            attack();
        }
        else
        {
            actionLockCounter ++;
            if(actionLockCounter == 120)
            {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if(i <= 25)
                    direction = "left";
                else if(i <= 50)
                    direction = "up";
                else if(i <= 75)
                    direction = "right";
                else
                    direction = "down";
                actionLockCounter = 0;
            }
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

            // Check player collision
            if(gamepanel.collisionCheck.checkPlayer(this))
            {
                gamepanel.player.calculateDamageReceive(this);
            }
            // Check interactive collision

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
        }
    }
    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gamepanel.player.direction;
        onPath = true;
    }
    public void checkDrop()
    {
        int i = new Random().nextInt(1000) + 1;
        if(i <= 800){
            dropItem(new BronzeCoinObject(gamepanel));
            dropItem(new HeartObject(gamepanel));
        }
        else if(i <= 990) {
            dropItem(new SilverCoinObject(gamepanel));
            dropItem(new HeartObject(gamepanel));
        }
        else {
            dropItem(new GoldCoinObject(gamepanel));
            dropItem(new HeartObject(gamepanel));
        }

    }
}
