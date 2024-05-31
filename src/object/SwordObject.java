package object;

import entity.Entity;
import main.GamePanel;

public class SwordObject extends Entity {

    public static final String objName = "Sword";

    public SwordObject(GamePanel gamePanel) {
        super(gamePanel);
        name = objName;
        type = type_sword;
        image = setup_entity("/objects/weapons/tier_0/sword", gamePanel.tileSize, gamePanel.tileSize);

        attackValue = 50;
        attackArea.width = 48;
        attackArea.height = 48;
        price = 10;

        knockBackPower = 5;
        motion_duration_1 = 5;
        motion_duration_2 = 15;

        description = "[" + name + "] \nBasic sword that can be used\nto attack enemies.";

    }
}
