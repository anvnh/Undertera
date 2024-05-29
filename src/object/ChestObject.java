package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ChestObject extends Entity {

    GamePanel gamepanel;
    Entity loot;
    boolean opened = false;
    private int chestAnimationCounter = 0;

    public ChestObject(GamePanel gamepanel, Entity loot) // loot will be more randomly than specific an loot item like this in future
    {
        super(gamepanel);
        this.gamepanel = gamepanel;
        this.loot = loot;

        name = "Chest";
        image = setup_entity("/objects/chests/wooden_chest_0", gamepanel.tileSize, gamepanel.tileSize); // default

        image1 = setup_entity("/objects/chests/wooden_chest_1", gamepanel.tileSize, gamepanel.tileSize); // opened animation 1
        image2 = setup_entity("/objects/chests/wooden_chest_2", gamepanel.tileSize, gamepanel.tileSize); // opened animation 2
        image3 = setup_entity("/objects/chests/wooden_chest_3", gamepanel.tileSize, gamepanel.tileSize); // opened animation 3

        collision = true;
        objectType = "object";
        type = type_obstacle;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
    public void interact()
    {
        gamepanel.gameState = gamepanel.dialogueState;
        if(!opened) {
            gamepanel.playSoundEffect(17);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and found a " + loot.name + ".");

            if(gamepanel.player.inventory.size() == gamepanel.player.maxInventorySize)
            {
                sb.append("\n...But you cannot carry any more.");
            }
            else
            {
                sb.append("\nYou obtain the " + loot.name + ".");
                gamepanel.player.inventory.add(loot);
                image = image3;
            }
            gamepanel.ui.currentDialogue = sb.toString();
        }
        else {
            gamepanel.ui.currentDialogue = "The chest is empty.";
        }
    }
}



