package object;

import entity.Entity;
import main.GamePanel;

public class ShieldObject extends Entity {

    public ShieldObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "Armor";
        type = type_armor;
        image = setup_entity("/objects/weapons/tier_0/shield", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 10;
        description = "[" + name + "] \nBasic shield that can be used to \nblock enemy attacks.";
    }
}
