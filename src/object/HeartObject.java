package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HeartObject extends BasedObject{
    GamePanel gamepanel;
    public HeartObject(GamePanel gp)
    {
        this.gamepanel = gp;
        name = "Heart";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/heart0.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart1.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart2.png"));
            image4 = ImageIO.read(getClass().getResourceAsStream("/objects/heart3.png"));
            image5 = ImageIO.read(getClass().getResourceAsStream("/objects/heart4.png"));
            utilityTools.scaleImage(image1, gamepanel.objectSize, gamepanel.objectSize);
            utilityTools.scaleImage(image2, gamepanel.objectSize, gamepanel.objectSize);
            utilityTools.scaleImage(image3, gamepanel.objectSize, gamepanel.objectSize);
            utilityTools.scaleImage(image4, gamepanel.objectSize, gamepanel.objectSize);
            utilityTools.scaleImage(image5, gamepanel.objectSize, gamepanel.objectSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
