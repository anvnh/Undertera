package object;

import entity.Entity;
import main.GamePanel;

public class ShieldObject extends Entity {

    public static final String objName = "Shield";

    public ShieldObject(GamePanel gamePanel) {
        super(gamePanel);
        name = objName;
        type = type_armor;
        image = setup_entity("/objects/weapons/tier_0/shield", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 15;
        description = "[" + name + "] \nBasic shield that can be used to \nblock enemy attacks.";
    }
}
