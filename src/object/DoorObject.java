package object;
import entity.Entity;
import main.GamePanel;

import java.io.IOException;
import javax.imageio.ImageIO;
public class DoorObject extends Entity
{
    GamePanel gamepanel;
    public static final String objName = "Door";
    public DoorObject(GamePanel gamepanel)
    {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = objName;
        direction = "down";
        objectType = "object";
        type = type_obstacle;
        /*
        down_1 = setup_entity("/objects/door.png");
         */
        image = setup_entity("/objects/doors/wooden_door_close", gamepanel.tileSize, gamepanel.tileSize);
        collision = true;
    }
    public void setDialogue()
    {
        dialogue[0][0] = "You need a key to open this";
        dialogue[0][1] = "Can you find a key anywhere ?";
    }
    public void interact()
    {
        setDialogue();
        startDialogue(this, 0);
    }
}
