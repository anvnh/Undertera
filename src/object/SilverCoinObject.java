package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class SilverCoinObject extends Entity {
    GamePanel gamepanel;

    public SilverCoinObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        type = type_pickuponly;
        name = "Silver Coin";
        value = 2;
        objectType = "object";
        image = setup_entity_1("/objects/coins/silver_coin");
    }
    public void use(Entity entity, GamePanel gamepanel)
    {
        gamepanel.playSoundEffect(7);
        gamepanel.ui.addMessage("Coin +" + value);
        gamepanel.player.coin += value;
    }

}
