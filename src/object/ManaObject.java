package object;

import entity.Entity;
import main.GamePanel;

public class ManaObject extends Entity {
    GamePanel gamepanel;
    public ManaObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = "Mana";
        image1 = setup_entity("/objects/mana_full", gamepanel.tileSize, gamepanel.tileSize);
        image2 = setup_entity("/objects/mana_half", gamepanel.tileSize, gamepanel.tileSize);
        image3 = setup_entity("/objects/mana_blank", gamepanel.tileSize, gamepanel.tileSize);
    }
}
