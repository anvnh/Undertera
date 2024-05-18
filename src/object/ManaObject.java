package object;

import entity.Entity;
import main.GamePanel;

public class ManaObject extends Entity {
    GamePanel gamepanel;
    public ManaObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = "Mana";
        image1 = setup_entity("/objects/mana_full");
        image2 = setup_entity("/objects/mana_half");
        image3 = setup_entity("/objects/mana_blank");
    }
}
