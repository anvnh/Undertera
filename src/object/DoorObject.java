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
        /*
        down_1 = setup_entity("/objects/door.png");
         */
        try {
            down_1 = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
