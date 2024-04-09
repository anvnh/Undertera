package object;

import entity.Entity;
import main.GamePanel;

public class ArmorObject extends Entity {

    public ArmorObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "armor";
        down_1 = setup_entity("/objects/shield_wood");
        defenseValue = 10;
    }
}
