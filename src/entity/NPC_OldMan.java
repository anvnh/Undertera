package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gamepanel) {
        super(gamepanel);
        direction = "down";
        speed = 1;
        getNPC_IMG();
    }
    public void getNPC_IMG() {
        up_1 = setup_npc("/npc/oldman/up_1");
        up_2 = setup_npc("/npc/oldman/up_2");
        down_1 = setup_npc("/npc/oldman/down_1");
        down_2 = setup_npc("/npc/oldman/down_2");
        left_1 = setup_npc("/npc/oldman/left_1");
        left_2 = setup_npc("/npc/oldman/left_2");
        right_1 = setup_npc("/npc/oldman/right_1");
        right_2 = setup_npc("/npc/oldman/right_2");
    }
    public void setAction() {

        actionLockCounter ++;
        if(actionLockCounter == 120)
        {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if(i <= 25)
            {
                direction = "left";
            }
            else if(i <= 50)
            {
                direction = "up";
            }
            else if(i <= 75)
            {
                direction = "right";
            }
            else
            {
                direction = "down";
            }
            actionLockCounter = 0;
        }
    }
}
