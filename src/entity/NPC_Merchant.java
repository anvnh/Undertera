package entity;

import main.GamePanel;
import object.AxeObject;
import object.KeyObject;
import object.Potion_RedObject;
import object.SwordObject;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(GamePanel gamepanel) {
        super(gamepanel);
        direction = "down";
        speed = 0;
        name = "merchant";
        solidArea.width = 40;
        solidArea.height = 40;
        getNPC_IMG();
        setDialogue();
        setItems();
    }
    public void getNPC_IMG() {
        go_down[0] = setup_entity("/npc/merchant/stand_down_0", 48, 48);
        go_down[1] = setup_entity("/npc/merchant/stand_down_1", 48, 48);
        go_down[2] = setup_entity("/npc/merchant/stand_down_2", 48, 48);
        go_down[3] = setup_entity("/npc/merchant/stand_down_3", 48, 48);
        go_down[4] = setup_entity("/npc/merchant/stand_down_4", 48, 48);
        go_down[5] = setup_entity("/npc/merchant/stand_down_5", 48, 48);
        go_down[6] = setup_entity("/npc/merchant/stand_down_6", 48, 48);
        go_down[7] = setup_entity("/npc/merchant/stand_down_7", 48, 48);
    }
    public void setDialogue() {
        dialogue[0][0] = "I have many wares for sale.";
        dialogue[1][0] = "Come back anytime";
        dialogue[2][0] = "You don't have enough coin.";
        dialogue[3][0] = "Your inventory is full. Free up some space.";
        dialogue[4][0] = "You can't sell equipped items.";
    }
    public void setAction() {
    }
    public void setItems() {
        inventory.add(new Potion_RedObject(gamepanel));
        inventory.add(new KeyObject(gamepanel));
        inventory.add(new SwordObject(gamepanel));
        inventory.add(new AxeObject(gamepanel));

    }
    public void speak() {
        facePlayer();
        gamepanel.gameState = gamepanel.tradeState;
        gamepanel.ui.npc = this;
    }
}
