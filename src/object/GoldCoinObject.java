package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class GoldCoinObject extends Entity {
    GamePanel gamepanel;
    public static final String objName = "Gold Coin";
    public GoldCoinObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        type = type_pickuponly;
        name = objName;
        value = 10;
        objectType = "object";
        image = setup_entity("/objects/coins/gold_coin", 32, 32);
    }
    public boolean use(Entity entity, GamePanel gamepanel)
    {
        gamepanel.playSoundEffect(7);
        gamepanel.ui.addMessage("Coin +" + value);
        gamepanel.player.coin += value;
        return true;
    }

}
