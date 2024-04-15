package object;

import main.GamePanel;

import javax.management.monitor.GaugeMonitor;
import entity.*;

public class Potion_RedObject extends Entity{
    GamePanel gamepanel;
    int value = 10;
    public Potion_RedObject(GamePanel gamepanel){
        super(gamepanel);

        name = "Red Potion";
        //type = type_potion;
        image = setup_entity("/objects/potion_red");
        objectType = "object";
        type = type_consumable;
        description = "[" + name + "]" + " A potion that can be \n used to restore health. \n Restores " + value + " HP.";
    }
    public void use(Entity entity, GamePanel gamepanel)
    {
        entity.life += value;
        if(entity.life > entity.maxLife)
        {
            entity.life = entity.maxLife;
        }
        gamepanel.ui.addMessage("You used a Red Potion to restore " + value + " HP.");
    }
}
