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
    }

}
