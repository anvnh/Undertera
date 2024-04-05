package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class KeyObject extends BasedObject{

    GamePanel gamepanel;

    public KeyObject(GamePanel gamepanel){
        name = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
            utilityTools.scaleImage(image, gamepanel.objectSize, gamepanel.objectSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
