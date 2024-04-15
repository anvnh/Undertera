package object;

import entity.Entity;
import main.GamePanel;

public class AxeObject extends Entity {
    GamePanel gamePanel;
    public AxeObject(GamePanel gamePanel) {
        super(gamePanel);
        name = "Axe";
        image = setup_weap("/objects/axe_tier_0");
        objectType = "object";
        type = type_axe;

        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 30;

        description = "[" + name + "] \nBasic axe that can be used to \nattack enemies.";
    }
}
