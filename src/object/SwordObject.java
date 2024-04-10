package object;

import entity.Entity;
import main.GamePanel;

public class SwordObject extends Entity {

    public SwordObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "Sword";
        image = setup_entity("/objects/sword_normal");
        attackValue = 1;
        description = "[" + name + "] \nBasic sword that can be used to \nattack enemies.";
    }
}
