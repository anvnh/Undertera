package object;
import entity.Entity;
import main.GamePanel;

import java.io.IOException;
import javax.imageio.ImageIO;
public class DoorObject extends Entity
{
    GamePanel gamePanel;
    public DoorObject(GamePanel gamePanel)
    {
        super(gamePanel);
        name = "Door";
        direction = "down";
        objectType = "object";
        /*
        down_1 = setup_entity("/objects/door.png");
         */
        image = setup_entity("/objects/wooden_door", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
    }
}
