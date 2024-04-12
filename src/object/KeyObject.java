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
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        description = "[" + name + "]" + " A key to unlock the door.";
    }
}
