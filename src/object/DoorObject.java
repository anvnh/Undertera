package object;
import entity.Entity;
import main.GamePanel;

import java.io.IOException;
import javax.imageio.ImageIO;
public class DoorObject extends Entity
{
    GamePanel gamepanel;
    public DoorObject(GamePanel gamepanel)
    {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = "Door";
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
    }
    public void interact()
    {
        startDialogue(this, 0);
    }
}
