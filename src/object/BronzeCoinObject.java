package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class BronzeCoinObject extends Entity {
    GamePanel gamepanel;

    public BronzeCoinObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        type = type_pickuponly;
        name = "Bronze Coin";
        value = 1;
        objectType = "object";
        image = setup_entity_1("/objects/coins/bronze_coin");
    }
    public void use(Entity entity, GamePanel gamepanel)
    {
        gamepanel.playSoundEffect(7);
        gamepanel.ui.addMessage("Coin +" + value);
        gamepanel.player.coin += value;
    }

}
