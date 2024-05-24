package entity.NPC;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Merchant extends Entity {
    public Merchant(GamePanel gamepanel) {
        super(gamepanel);
        direction = "down";
        speed = 0;
        name = "merchant";
        solidArea.width = 40;
        solidArea.height = 80;
        getNPC_IMG();
        setDialogue();
    }
    public void getNPC_IMG() {
        go_down[0] = setup_entity("/npc/merchant/stand_down_00", 80, 80);
        go_down[1] = setup_entity("/npc/merchant/stand_down_01", 80, 80);
        go_down[2] = setup_entity("/npc/merchant/stand_down_02", 80, 80);
        go_down[3] = setup_entity("/npc/merchant/stand_down_03", 80, 80);
        go_down[4] = setup_entity("/npc/merchant/stand_down_04", 80, 80);
        go_down[5] = setup_entity("/npc/merchant/stand_down_05", 80, 80);
        go_down[6] = setup_entity("/npc/merchant/stand_down_06", 80, 80);
        go_down[7] = setup_entity("/npc/merchant/stand_down_07", 80, 80);
        go_down[8] = setup_entity("/npc/merchant/stand_down_08", 80, 80);
        go_down[9] = setup_entity("/npc/merchant/stand_down_09", 80, 80);
    }
    public void setDialogue() {
        dialogue[0] = "I have many wares for sale.";
        dialogue[1] = "Would you like to buy something?";
    }
    public void setAction() {
    }
    public void speak() {
        super.speak();
    }
}
