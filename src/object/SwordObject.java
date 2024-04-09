package object;

import entity.Entity;
import main.GamePanel;

public class SwordObject extends Entity {

    public SwordObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "sword";
        down_1 = setup_entity("/objects/sword_normal");
        attackValue = 1;
    }
}
