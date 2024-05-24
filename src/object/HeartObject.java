package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class HeartObject extends Entity {
    GamePanel gamepanel;
    public HeartObject(GamePanel gp)
    {
        super(gp);
        this.gamepanel = gp;
        name = "Heart";
        value = 2;
        objectType = "object";
        type = type_pickuponly;
        image = setup_entity_1("/objects/heart_full");
        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart0.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart1.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart2.png")));
            image4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart3.png")));
            image5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart4.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void use(Entity entity, GamePanel gamepanel)
    {
        gamepanel.playSoundEffect(7);
        gamepanel.ui.addMessage("Healed " + value + " HP.");
        entity.life += value;
    }
}
