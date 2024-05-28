package main;

import entity.*;
import object.*;
import monster.*;
import tile_interactive.DryTree_IT;

public class AssetSetter {
    GamePanel gamepanel;
    public AssetSetter(GamePanel gp) {
        this.gamepanel = gp;
    }
    public void setObject() {
        int mapNum = 0;
        int i = 0;
        gamepanel.objects[mapNum][i] = new KeyObject(gamepanel);
        gamepanel.objects[mapNum][i].worldX = gamepanel.tileSize * 24;
        gamepanel.objects[mapNum][i].worldY = gamepanel.tileSize * 19;
        i++;
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        gamepanel.npc[mapNum][i] = new NPC_OldMan(gamepanel);
        gamepanel.npc[mapNum][i].worldX = gamepanel.tileSize * 22;
        gamepanel.npc[mapNum][i].worldY = gamepanel.tileSize * 26;
        i++;

        gamepanel.npc[mapNum][i] = new NPC_OldMan(gamepanel);
        gamepanel.npc[mapNum][i].worldX = gamepanel.tileSize * 24;
        gamepanel.npc[mapNum][i].worldY = gamepanel.tileSize * 26;

        // Map 1
        i = 0; mapNum = 1;
        gamepanel.npc[mapNum][i] = new NPC_Merchant(gamepanel);
        gamepanel.npc[mapNum][i].worldX = gamepanel.tileSize * 12;
        gamepanel.npc[mapNum][i].worldY = gamepanel.tileSize * 7;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        /*
        gamepanel.monster[mapNum][i] = new BlueSlime(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 22;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
        i++;
        gamepanel.monster[mapNum][i] = new BlueSlime(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 25;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
        i++;
         */
        gamepanel.monster[mapNum][i] = new Skeleton(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 28;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 21;
        i++;
        gamepanel.monster[mapNum][i] = new BlueSlime(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 31;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
        i++;
        gamepanel.monster[mapNum][i] = new BlueSlime(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 33;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
    }
    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        /*
        gamepanel.interactiveTile[mapNum][i] = new DryTree_IT(gamepanel, 27, 13);
        i++;
        gamepanel.interactiveTile[mapNum][i] = new DryTree_IT(gamepanel, 28, 13);
        i++;
        gamepanel.interactiveTile[mapNum][i] = new DryTree_IT(gamepanel, 29, 13);
        i++;
        gamepanel.interactiveTile[mapNum][i] = new DryTree_IT(gamepanel, 30, 13);
        i++;
         */
        gamepanel.interactiveTile[mapNum][i] = new DryTree_IT(gamepanel, 27, 20);
        i++;
        gamepanel.interactiveTile[mapNum][i] = new DryTree_IT(gamepanel, 27, 21);
        i++;
        gamepanel.interactiveTile[mapNum][i] = new DryTree_IT(gamepanel, 27, 22);
    }
}
