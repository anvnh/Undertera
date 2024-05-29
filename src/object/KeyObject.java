package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class KeyObject extends Entity {
    GamePanel gamepanel;
    public KeyObject(GamePanel gamepanel){
        super(gamepanel);

        name = "Key";
        direction = "down";
        objectType = "object";
        price = 2;
        type = type_consumable;
        //collision = false;
        image = setup_entity("/objects/key_01a", 32, 32);
        description = "[" + name + "]" + " A key to unlock the door.";
        stackable = true;
    }
    public boolean use(Entity entity, GamePanel gamepanel)
    {
        gamepanel.gameState = gamepanel.dialogueState;
        int objectIndex = getDetected(entity, gamepanel.objects, "Door");
        if(objectIndex != 999)
        {
            gamepanel.ui.currentDialogue = "You have unlocked the door.";
            gamepanel.playSoundEffect(14);
            // Sound effect
            gamepanel.objects[gamepanel.currentMap][objectIndex] = null;
            return true;
        }
        else
        {
            gamepanel.ui.currentDialogue = "There is no door to unlock.";
            return false;
        }
    }
}
