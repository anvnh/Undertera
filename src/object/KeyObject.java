package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class KeyObject extends Entity {
    public KeyObject(GamePanel gamepanel){
        super(gamepanel);

        name = "Key";
        down_1 = setup_entity("/objects/key.png");

    }
}
