package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gamepanel) {
        super(gamepanel);
        direction = "down";
        speed = 1;
        name = "old_man";
        solidArea.width = 40;
        solidArea.height = 40;

        dialogueIndex = 0;

        getNPC_IMG();
        setDialogue();
    }
    public void getNPC_IMG() {
        go_up[0] = setup_entity("/npc/oldman/up_1",48,48);
        go_up[1] = setup_entity("/npc/oldman/up_2",48,48);
        go_up[2] = setup_entity("/npc/oldman/up_1",48,48);
        go_up[3] = setup_entity("/npc/oldman/up_2",48,48);
        go_up[4] = setup_entity("/npc/oldman/up_1",48,48);
        go_up[5] = setup_entity("/npc/oldman/up_2",48,48);

        go_down[0] = setup_entity("/npc/oldman/down_1",48,48);
        go_down[1] = setup_entity("/npc/oldman/down_2",48,48);
        go_down[2] = setup_entity("/npc/oldman/down_1",48,48);
        go_down[3] = setup_entity("/npc/oldman/down_2",48,48);
        go_down[4] = setup_entity("/npc/oldman/down_1",48,48);
        go_down[5] = setup_entity("/npc/oldman/down_2",48,48);

        go_left[0] = setup_entity("/npc/oldman/left_1",48,48);
        go_left[1] = setup_entity("/npc/oldman/left_2",48,48);
        go_left[2] = setup_entity("/npc/oldman/left_1",48,48);
        go_left[3] = setup_entity("/npc/oldman/left_2",48,48);
        go_left[4] = setup_entity("/npc/oldman/left_1",48,48);
        go_left[5] = setup_entity("/npc/oldman/left_2",48,48);

        go_right[0] = setup_entity("/npc/oldman/right_1",48,48);
        go_right[1] = setup_entity("/npc/oldman/right_2",48,48);
        go_right[2] = setup_entity("/npc/oldman/right_1",48,48);
        go_right[3] = setup_entity("/npc/oldman/right_2",48,48);
        go_right[4] = setup_entity("/npc/oldman/right_1",48,48);
        go_right[5] = setup_entity("/npc/oldman/right_2",48,48);

    }
    public void setDialogue() {
        dialogue[0][0] = "I'm just an old man";
        dialogue[0][1] = "Ah, young one. I've seen many winters come and go.";

        dialogue[1][0] = "I've witnessed the rise and fall of empires, and the birth and death of \ncountless souls.";
        dialogue[1][1] = "The past is a book that is always open, but it is up to us to choose \nwhat lessons we learn from it.";

        dialogue[2][0] = "The world has changed much since I was a lad, but one thing remains the \nsame: the importance of kindness.";
        dialogue[2][1] = "I wonder how to open that door";
    }
    public void setAction() {
        if(onPath)
        {

            //int endCol = 10;
            //int endRow = 8;
            int endCol = (gamepanel.player.worldX + gamepanel.player.solidArea.x) / gamepanel.tileSize;
            int endRow = (gamepanel.player.worldY + gamepanel.player.solidArea.y) / gamepanel.tileSize;

            searchPath(endCol, endRow);
        }
        else
        {
            actionLockCounter ++;
            if(actionLockCounter == 120)
            {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if(i <= 25)
                    direction = "left";
                else if(i <= 50)
                    direction = "up";
                else if(i <= 75)
                    direction = "right";
                else
                    direction = "down";
                actionLockCounter = 0;
            }
        }
    }
    public void speak() {
        // Do this character specific stuff
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;

        // Can change to specific dialogues when the player is at a specific health
        if(dialogue[dialogueSet][0] == null) {
            dialogueSet = 0;
        }
    }

}
