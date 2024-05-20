package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class GoldCoinObject extends Entity {
    GamePanel gamepanel;

    public GoldCoinObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        type = type_pickuponly;
        name = "Gold Coin";
        value = 10;
        objectType = "object";
        image = setup_entity_1("/objects/coins/gold_coin");
    }
    public void use(Entity entity, GamePanel gamepanel)
    {
        gamepanel.playSoundEffect(7);
        gamepanel.ui.addMessage("Coin +" + value);
        gamepanel.player.coin += value;
    }

}
