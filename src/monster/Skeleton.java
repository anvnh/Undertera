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
        name = "Skeleton";
        speed = 1;
        originalSpeed = 1;
        maxLife = 200;
        life = maxLife;
        attack = 50;
        defense = 80;
        exp = 25;
        projectile = new RockObject(gamepanel);

        knockBackPower = 5;

        solidArea.x = 30;
        solidArea.y = 35;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;

        motion_duration_1 = 10;
        motion_duration_2 = 20;

        getImage();
        getAttackImage();
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
    }
    public void getAttackImage()
    {
       for(int i = 0; i < 5; i++)
       {
           attack_up[i] = setup_entity("/monster/skeleton/attack/attack_up_" + i, gamepanel.monsterSize, gamepanel.monsterSize);
           attack_down[i] = setup_entity("/monster/skeleton/attack/attack_down_" + i, gamepanel.monsterSize, gamepanel.monsterSize);
           attack_left[i] = setup_entity("/monster/skeleton/attack/attack_left_" + i, gamepanel.monsterSize, gamepanel.monsterSize);
           attack_right[i] = setup_entity("/monster/skeleton/attack/attack_right_" + i, gamepanel.monsterSize, gamepanel.monsterSize);
       }
    }
    public void update()
    {
        super.update();
    }
    public void setAction()
    {
        if(onPath)
        {
            // The maximum distance to drop aggro
            checkDropAggro(gamepanel.player, 20, 100);

            // Find the direction
            searchPath(getGoalCol(gamepanel.player), getGoalRow(gamepanel.player));
        }
        else
        {
            // Minimum distance to have aggro
            checkStartAggro(gamepanel.player, 20, 100);

            //Get random direction
            getRandomDirection(100);
        }

        // check if it's time to attack
        if(!attacking)
        {
            checkAttackAggro(100, 55, 55);
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
