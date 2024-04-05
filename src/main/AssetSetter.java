package main;

import object.*;

public class AssetSetter {
    GamePanel gamepanel;
    public AssetSetter(GamePanel gp) {
        this.gamepanel = gp;
    }
    public void setObject() {
        gamepanel.basedObject[0] = new KeyObject(gamepanel);
        gamepanel.basedObject[0].worldX = 23 * gamepanel.tileSize;
        gamepanel.basedObject[0].worldY = 7 * gamepanel.tileSize;

        gamepanel.basedObject[1] = new KeyObject(gamepanel);
        gamepanel.basedObject[1].worldX = 20 * gamepanel.tileSize;
        gamepanel.basedObject[1].worldY = 7 * gamepanel.tileSize;

        gamepanel.basedObject[2] = new ChestObject(gamepanel);
        gamepanel.basedObject[2].worldX = 10 * gamepanel.tileSize;
        gamepanel.basedObject[2].worldY = 8 * gamepanel.tileSize;

        gamepanel.basedObject[3] = new DoorObject(gamepanel);
        gamepanel.basedObject[3].worldX = 10 * gamepanel.tileSize;
        gamepanel.basedObject[3].worldY = 12 * gamepanel.tileSize;
    }
}
