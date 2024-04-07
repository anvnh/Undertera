package main;

import entity.*;
import object.*;

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
        gamepanel.npc[0].worldY = gamepanel.tileSize * 22;

        gamepanel.npc[1] = new NPC_OldMan(gamepanel);
        gamepanel.npc[1].worldX = gamepanel.tileSize * 23;
        gamepanel.npc[1].worldY = gamepanel.tileSize * 22;

        gamepanel.npc[2] = new NPC_OldMan(gamepanel);
        gamepanel.npc[2].worldX = gamepanel.tileSize * 25;
        gamepanel.npc[2].worldY = gamepanel.tileSize * 22;

    }

}
