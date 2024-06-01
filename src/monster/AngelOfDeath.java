package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.util.Random;

public class AngelOfDeath extends Entity {
    GamePanel gamepanel;
    public static final String monsterName = "Angel Of Death";
    public AngelOfDeath(GamePanel gamePanel) {
        super(gamePanel);
        this.gamepanel = gamePanel;

        type = type_monster;
        name = monsterName;
        originalSpeed = 2;
        speed = originalSpeed;
        maxLife = 3000;
        life = maxLife;
        attack = 80;
        defense = 70;
        exp = 400;

        knockBackPower = 6;

        solidArea.x = 10;
        solidArea.y = 41;
        solidArea.width = 140;
        solidArea.height = 90;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 98;
        attackArea.height = 98;

        motion_duration_1 = 10;
        motion_duration_2 = 15;

        boss = true;
        sleep = true;

        getImage();
        getAttackImage();
        getStandingImage();
        setDialogue();
    }
    public void getImage() {
        if(inRage) {
            go_up[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);

            go_down[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);

            go_left[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);

            go_right[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
        }
        else {
            go_up[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_up[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_up_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);

            go_down[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_down[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_down_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);

            go_left[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_left[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_left_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);

            go_right[0] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_0", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[1] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_1", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[2] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_2", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[3] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_3", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[4] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_4", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            go_right[5] = setup_entity("/monster/BOSS/Angel_of_Death/moving/go_right_5", gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
        }
    }
    public void getAttackImage()
    {
        for(int i = 0; i < 6; i++)
        {
            attack_up[i] = setup_entity("/monster/BOSS/Angel_of_Death/attack/attack_up_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            attack_down[i] = setup_entity("/monster/BOSS/Angel_of_Death/attack/attack_down_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            attack_left[i] = setup_entity("/monster/BOSS/Angel_of_Death/attack/attack_left_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            attack_right[i] = setup_entity("/monster/BOSS/Angel_of_Death/attack/attack_right_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
        }
    }
    public void getStandingImage()
    {
        for(int i = 0; i < 6; i++)
        {
            stand_up[i] = setup_entity("/monster/BOSS/Angel_of_Death/standing/standing_up_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            stand_down[i] = setup_entity("/monster/BOSS/Angel_of_Death/standing/standing_down_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            stand_left[i] = setup_entity("/monster/BOSS/Angel_of_Death/standing/standing_left_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);
            stand_right[i] = setup_entity("/monster/BOSS/Angel_of_Death/standing/standing_right_" + i, gamepanel.monsterSize *  2, gamepanel.monsterSize *  2);

        }
    }
    public void update()
    {
        super.update();
    }
    public void setDialogue() {
        dialogue[0][0] = "I am the Angel of Death.";
        dialogue[0][1] = "...";
        dialogue[0][2] = "Prepare yourself, for I am the one who will take your everything.";
        //dialogue[0][4] = "I am the one who will take your everything.";

    }
    public void setAction()
    {
        if(!inRage && life <= maxLife / 10) {
            startDialogue(this, 0);
            invincible = true;
            maxLife *= 2;
            life = maxLife;
            inRage = true;
            getImage();
            getAttackImage();
            originalSpeed *= 2;
            speed = originalSpeed;
            attack *= 3;
            defense *= 2;
        }

        if(getTileDistance(gamepanel.player) < 40)
        {
            moveTowardPlayer(60);
        }
        else
        {
            //Get random direction
            getRandomDirection(100);
        }

        // check if it's time to attack
        if(!attacking)
        {
            checkAttackAggro(1, 100, 100);
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
