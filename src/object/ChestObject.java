package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ChestObject extends BasedObject{
    GamePanel gamepanel;
    public ChestObject(GamePanel gamepanel){
        name = "Chest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
            utilityTools.scaleImage(image, gamepanel.objectSize, gamepanel.objectSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
