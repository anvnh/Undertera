package main;

import entity.*;
import object.*;
import monster.*;

public class AssetSetter {
    GamePanel gamepanel;
    public AssetSetter(GamePanel gp) {
        this.gamepanel = gp;
    }
    public void setObject() {
    }

    public void setNPC() {
        gamepanel.npc[0] = new NPC_OldMan(gamepanel);
        gamepanel.npc[0].worldX = gamepanel.tileSize * 22;
        gamepanel.npc[0].worldY = gamepanel.tileSize * 23;

        gamepanel.npc[2] = new NPC_OldMan(gamepanel);
        gamepanel.npc[2].worldX = gamepanel.tileSize * 27;
        gamepanel.npc[2].worldY = gamepanel.tileSize * 22;
    }

    public void setMonster() {
        int i = 0;
        gamepanel.monster[i] = new BlueSlime(gamepanel);
        gamepanel.monster[i].worldX = gamepanel.tileSize * 22;
        gamepanel.monster[i].worldY = gamepanel.tileSize * 22;

        i++;
        gamepanel.monster[i] = new BlueSlime(gamepanel);
        gamepanel.monster[i].worldX = gamepanel.tileSize * 25;
        gamepanel.monster[i].worldY = gamepanel.tileSize * 22;

        i++;
        gamepanel.monster[i] = new BlueSlime(gamepanel);
        gamepanel.monster[i].worldX = gamepanel.tileSize * 28;
        gamepanel.monster[i].worldY = gamepanel.tileSize * 22;
    }

}
