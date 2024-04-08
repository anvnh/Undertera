package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class BlueSlime extends Entity {
    GamePanel gamepanel;
    public BlueSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamepanel = gamePanel;

        type = 2;
        name = "blue_slime";
        speed = 1;
        maxLife = 20;
        life = maxLife;

        /*
        solidArea.x = 3;
        solidArea.y = 4;
        solidArea.width = 10;
        solidArea.height = 9;
        */
        solidArea.x = 10;
        solidArea.y = 10;
        solidArea.width = 20;
        solidArea.height = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage() {
        go_up[0] = setup_entity("/monster/blue_slime/blue_slime_up_0");
        go_up[1] = setup_entity("/monster/blue_slime/blue_slime_up_1");
        go_up[2] = setup_entity("/monster/blue_slime/blue_slime_up_2");
        go_up[3] = setup_entity("/monster/blue_slime/blue_slime_up_3");
        go_up[4] = setup_entity("/monster/blue_slime/blue_slime_up_4");
        go_up[5] = setup_entity("/monster/blue_slime/blue_slime_up_5");
        go_up[6] = setup_entity("/monster/blue_slime/blue_slime_up_6");
        go_up[7] = setup_entity("/monster/blue_slime/blue_slime_up_7");


        go_down[0] = setup_entity("/monster/blue_slime/blue_slime_down_0");
        go_down[1] = setup_entity("/monster/blue_slime/blue_slime_down_1");
        go_down[2] = setup_entity("/monster/blue_slime/blue_slime_down_2");
        go_down[3] = setup_entity("/monster/blue_slime/blue_slime_down_3");
        go_down[4] = setup_entity("/monster/blue_slime/blue_slime_down_4");
        go_down[5] = setup_entity("/monster/blue_slime/blue_slime_down_5");
        go_down[6] = setup_entity("/monster/blue_slime/blue_slime_down_6");
        go_down[7] = setup_entity("/monster/blue_slime/blue_slime_down_7");

        go_left[0] = setup_entity("/monster/blue_slime/blue_slime_left_0");
        go_left[1] = setup_entity("/monster/blue_slime/blue_slime_left_1");
        go_left[2] = setup_entity("/monster/blue_slime/blue_slime_left_2");
        go_left[3] = setup_entity("/monster/blue_slime/blue_slime_left_3");
        go_left[4] = setup_entity("/monster/blue_slime/blue_slime_left_4");
        go_left[5] = setup_entity("/monster/blue_slime/blue_slime_left_5");
        go_left[6] = setup_entity("/monster/blue_slime/blue_slime_left_6");
        go_left[7] = setup_entity("/monster/blue_slime/blue_slime_left_7");

        go_right[0] = setup_entity("/monster/blue_slime/blue_slime_right_0");
        go_right[1] = setup_entity("/monster/blue_slime/blue_slime_right_1");
        go_right[2] = setup_entity("/monster/blue_slime/blue_slime_right_2");
        go_right[3] = setup_entity("/monster/blue_slime/blue_slime_right_3");
        go_right[4] = setup_entity("/monster/blue_slime/blue_slime_right_4");
        go_right[5] = setup_entity("/monster/blue_slime/blue_slime_right_5");
        go_right[6] = setup_entity("/monster/blue_slime/blue_slime_right_6");
        go_right[7] = setup_entity("/monster/blue_slime/blue_slime_right_7");
    }
    public void setAction()
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
    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gamepanel.player.direction;
    }


}
