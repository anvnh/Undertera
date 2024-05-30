package object;

import entity.Entity;
import main.GamePanel;

public class BedObject extends Entity {
    GamePanel gamepanel;
    public static final String objName="Bed";
    public BedObject(GamePanel gamepanel)
    {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = objName;
        image = setup_entity("/objects/bed/bed", gamepanel.tileSize, gamepanel.tileSize); // default

        collision = true;
        objectType = "object";
        type = type_consumable;
        description = "You know what to do with this.";
    }
    public void setDialogue()
    {
        dialogue[0][0] = "You can only sleep at night.";
    }

    public boolean use(Entity entity, GamePanel gamepanel)
    {
        setDialogue();
        if(gamepanel.environmentManager.lightning.dayState != gamepanel.environmentManager.lightning.night)
        {
            startDialogue(this, 0);
            gamepanel.gameState = gamepanel.dialogueState;
            return false;
        }
        gamepanel.gameState = gamepanel.sleepState;
        gamepanel.player.life = gamepanel.player.maxLife;
        gamepanel.player.mana = gamepanel.player.maxMana;
        gamepanel.player.getSleepingImage(setup_entity("/objects/bed/tent", gamepanel.tileSize, gamepanel.tileSize));
        return true;
    }
}
