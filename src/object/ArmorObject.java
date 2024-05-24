package object;

import entity.Entity;
import main.GamePanel;

public class ArmorObject extends Entity {

    public ArmorObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "Armor";
        type = type_armor;
        image = setup_entity("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 10;
        description = "[" + name + "] \nBasic shield that can be used to \nblock enemy attacks.";
    }
}
