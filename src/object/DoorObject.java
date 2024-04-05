package object;
import main.GamePanel;

import java.io.IOException;
import javax.imageio.ImageIO;
public class DoorObject extends BasedObject
{
    GamePanel gamePanel;
    public DoorObject(GamePanel gamePanel)
    {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/wooden_door.png"));
            utilityTools.scaleImage(image, gamePanel.objectSize, gamePanel.objectSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
