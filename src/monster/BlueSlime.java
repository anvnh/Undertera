package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.util.Random;

public class BlueSlime extends Entity {
    GamePanel gamepanel;
    public BlueSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamepanel = gamePanel;

        type = type_monster;
        name = "Blue Slime";
        speed = 1;
        originalSpeed = 1;
        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 50;
        exp = 10;
        projectile = new RockObject(gamepanel);

        solidArea.x = 10;
        solidArea.y = 10;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage() {
        go_up[0] = setup_entity("/monster/blue_slime/blue_slime_up_0", gamepanel.tileSize, gamepanel.tileSize);
        go_up[1] = setup_entity("/monster/blue_slime/blue_slime_up_1", gamepanel.tileSize, gamepanel.tileSize);
        go_up[2] = setup_entity("/monster/blue_slime/blue_slime_up_2", gamepanel.tileSize, gamepanel.tileSize);
        go_up[3] = setup_entity("/monster/blue_slime/blue_slime_up_3", gamepanel.tileSize, gamepanel.tileSize);
        go_up[4] = setup_entity("/monster/blue_slime/blue_slime_up_4", gamepanel.tileSize, gamepanel.tileSize);
        go_up[5] = setup_entity("/monster/blue_slime/blue_slime_up_5", gamepanel.tileSize, gamepanel.tileSize);
        go_up[6] = setup_entity("/monster/blue_slime/blue_slime_up_6", gamepanel.tileSize, gamepanel.tileSize);
        go_up[7] = setup_entity("/monster/blue_slime/blue_slime_up_7", gamepanel.tileSize, gamepanel.tileSize);


        go_down[0] = setup_entity("/monster/blue_slime/blue_slime_down_0", gamepanel.tileSize, gamepanel.tileSize);
        go_down[1] = setup_entity("/monster/blue_slime/blue_slime_down_1", gamepanel.tileSize, gamepanel.tileSize);
        go_down[2] = setup_entity("/monster/blue_slime/blue_slime_down_2", gamepanel.tileSize, gamepanel.tileSize);
        go_down[3] = setup_entity("/monster/blue_slime/blue_slime_down_3", gamepanel.tileSize, gamepanel.tileSize);
        go_down[4] = setup_entity("/monster/blue_slime/blue_slime_down_4", gamepanel.tileSize, gamepanel.tileSize);
        go_down[5] = setup_entity("/monster/blue_slime/blue_slime_down_5", gamepanel.tileSize, gamepanel.tileSize);
        go_down[6] = setup_entity("/monster/blue_slime/blue_slime_down_6", gamepanel.tileSize, gamepanel.tileSize);
        go_down[7] = setup_entity("/monster/blue_slime/blue_slime_down_7", gamepanel.tileSize, gamepanel.tileSize);

        go_left[0] = setup_entity("/monster/blue_slime/blue_slime_left_0", gamepanel.tileSize, gamepanel.tileSize);
        go_left[1] = setup_entity("/monster/blue_slime/blue_slime_left_1", gamepanel.tileSize, gamepanel.tileSize);
        go_left[2] = setup_entity("/monster/blue_slime/blue_slime_left_2", gamepanel.tileSize, gamepanel.tileSize);
        go_left[3] = setup_entity("/monster/blue_slime/blue_slime_left_3", gamepanel.tileSize, gamepanel.tileSize);
        go_left[4] = setup_entity("/monster/blue_slime/blue_slime_left_4", gamepanel.tileSize, gamepanel.tileSize);
        go_left[5] = setup_entity("/monster/blue_slime/blue_slime_left_5", gamepanel.tileSize, gamepanel.tileSize);
        go_left[6] = setup_entity("/monster/blue_slime/blue_slime_left_6", gamepanel.tileSize, gamepanel.tileSize);
        go_left[7] = setup_entity("/monster/blue_slime/blue_slime_left_7", gamepanel.tileSize, gamepanel.tileSize);

        go_right[0] = setup_entity("/monster/blue_slime/blue_slime_right_0", gamepanel.tileSize, gamepanel.tileSize);
        go_right[1] = setup_entity("/monster/blue_slime/blue_slime_right_1", gamepanel.tileSize, gamepanel.tileSize);
        go_right[2] = setup_entity("/monster/blue_slime/blue_slime_right_2", gamepanel.tileSize, gamepanel.tileSize);
        go_right[3] = setup_entity("/monster/blue_slime/blue_slime_right_3", gamepanel.tileSize, gamepanel.tileSize);
        go_right[4] = setup_entity("/monster/blue_slime/blue_slime_right_4", gamepanel.tileSize, gamepanel.tileSize);
        go_right[5] = setup_entity("/monster/blue_slime/blue_slime_right_5", gamepanel.tileSize, gamepanel.tileSize);
        go_right[6] = setup_entity("/monster/blue_slime/blue_slime_right_6", gamepanel.tileSize, gamepanel.tileSize);
        go_right[7] = setup_entity("/monster/blue_slime/blue_slime_right_7", gamepanel.tileSize, gamepanel.tileSize);
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

            // Randomly shoot projectile
            //checkShootingAggro(1000, 60);
        }
        else
        {
            // Minimum distance to have aggro
            checkStartAggro(gamepanel.player, 5, 100);

            //Get random direction
            getRandomDirection(100);
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
