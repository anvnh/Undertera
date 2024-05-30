package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class KeyObject extends Entity {
    GamePanel gamepanel;
    public static final String objName = "Key";
    public KeyObject(GamePanel gamepanel){
        super(gamepanel);

        name = objName;
        direction = "down";
        objectType = "object";
        price = 2;
        type = type_consumable;
        //collision = false;
        image = setup_entity("/objects/key_01a", 32, 32);
        description = "[" + name + "]" + " A key to unlock the door.";
        stackable = true;
    }
    public void setDialogue()
    {
        dialogue[0][0] = "You have unlocked the door.";
        dialogue[1][0] = "There is no door to unlock.";
    }

    public boolean use(Entity entity, GamePanel gamepanel)
    {
        setDialogue();
        int objectIndex = getDetected(entity, gamepanel.objects, "Door");
        if(objectIndex != 999)
        {
            startDialogue(this, 0);
            gamepanel.playSoundEffect(14);
            // Sound effect
            gamepanel.objects[gamepanel.currentMap][objectIndex] = null;
            return true;
        }
        else
        {
            startDialogue(this, 1);
            return false;
        }
    }
}
