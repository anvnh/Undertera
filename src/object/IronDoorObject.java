package object;
import entity.Entity;
import main.GamePanel;

import java.io.IOException;
import javax.imageio.ImageIO;
public class IronDoorObject extends Entity
{
    GamePanel gamepanel;
    public static final String objName = "Iron Door";
    public IronDoorObject(GamePanel gamepanel)
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
        image = setup_entity("/objects/doors/iron_door", gamepanel.tileSize, gamepanel.tileSize);
        collision = true;
    }
    public void setDialogue()
    {
        dialogue[0][0] = "I don't think we can open this";
        dialogue[0][1] = "Should we try another way ?";
    }
    public void interact()
    {
        setDialogue();
        startDialogue(this, 0);
    }
}
