package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ChestObject extends Entity {
    GamePanel gamepanel;
    public ChestObject(GamePanel gamepanel){
        super(gamepanel);
        name = "Chest";
        image = setup_entity("/objects/chest.png", gamepanel.tileSize, gamepanel.tileSize);
    }
}
