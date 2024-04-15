package object;

import entity.Entity;
import main.GamePanel;

public class SwordObject extends Entity {

    public SwordObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "Sword";
        type = type_sword;
        image = setup_entity("/objects/sword_tier_0");

        attackValue = 3;
        attackArea.width = 48;
        attackArea.height = 48;

        description = "[" + name + "] \nBasic sword that can be used\n to attack enemies.";
    }
}