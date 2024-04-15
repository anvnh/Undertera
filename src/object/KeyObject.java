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
        direction = "down";
        objectType = "object";
        //collision = false;
        image = setup_entity("/objects/key_01a");
        description = "[" + name + "]" + " A key to unlock the door.";
    }
}
