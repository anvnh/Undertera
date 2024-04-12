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
        gamepanel.objects[0] = new KeyObject(gamepanel);
        gamepanel.objects[0].worldX = gamepanel.tileSize * 24;
        gamepanel.objects[0].worldY = gamepanel.tileSize * 19;

        gamepanel.objects[1] = new AxeObject(gamepanel);
        gamepanel.objects[1].worldX = gamepanel.tileSize * 24;
        gamepanel.objects[1].worldY = gamepanel.tileSize * 21;
    }

    public void setNPC() {
        gamepanel.npc[0] = new NPC_OldMan(gamepanel);
        gamepanel.npc[0].worldX = gamepanel.tileSize * 22;
        gamepanel.npc[0].worldY = gamepanel.tileSize * 26;

        gamepanel.npc[1] = new NPC_OldMan(gamepanel);
        gamepanel.npc[1].worldX = gamepanel.tileSize * 24;
        gamepanel.npc[1].worldY = gamepanel.tileSize * 26;

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

        /*
        i++;
        gamepanel.monster[i] = new BlueSlime(gamepanel);
        gamepanel.monster[i].worldX = gamepanel.tileSize * 31;
        gamepanel.monster[i].worldY = gamepanel.tileSize * 22;
         */
    }

}
