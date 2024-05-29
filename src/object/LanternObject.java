package object;

import entity.Entity;
import main.GamePanel;

public class LanternObject extends Entity {
    public LanternObject(GamePanel gamepanel)
    {
        super(gamepanel);
        type = type_light;
        objectType = "object";
        name = "Lantern";
        image = setup_entity("/objects/lantern/lantern", gamepanel.tileSize, gamepanel.tileSize);
        description = "A lantern that can light up the \ndark.";
        price = 150;
        lightRadius = 250;
    }
}
