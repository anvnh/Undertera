package object;

import main.GamePanel;

import javax.management.monitor.GaugeMonitor;
import entity.*;

public class Potion_RedObject extends Entity{
    GamePanel gamepanel;
    public static final String objName = "Red Potion";
    public Potion_RedObject(GamePanel gamepanel){
        super(gamepanel);

        value = 10;
        name = objName;
        //type = type_potion;
        image = setup_entity("/objects/potion_red", 32, 32);
        objectType = "object";
        type = type_consumable;
        description = "[" + name + "]" + " A potion that can be \n used to restore health. \n Restores " + value + " HP.";
        price = 5;
        stackable = true;
    }
    public boolean use(Entity entity, GamePanel gamepanel)
    {
        entity.life += value;
        if(entity.life > entity.maxLife)
        {
            entity.life = entity.maxLife;
        }
        gamepanel.ui.addMessage("You used a Red Potion to restore " + value + " HP.");
        return true;
    }
}
