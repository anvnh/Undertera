package main;

import entity.*;
import object.*;
import monster.*;
import tile.TileManager;
import tile_interactive.DryTree_IT;

import java.awt.*;
import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.Random;

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

        gamepanel.objects[mapNum][i] = new LanternObject(gamepanel);
        gamepanel.objects[mapNum][i].worldX = gamepanel.tileSize * 23;
        gamepanel.objects[mapNum][i].worldY = gamepanel.tileSize * 19;
        i++;

        gamepanel.objects[mapNum][i] = new BedObject(gamepanel);
        gamepanel.objects[mapNum][i].worldX = gamepanel.tileSize * 22;
        gamepanel.objects[mapNum][i].worldY = gamepanel.tileSize * 19;
        i++;

        gamepanel.objects[mapNum][i] = new DoorObject(gamepanel);
        gamepanel.objects[mapNum][i].worldX = gamepanel.tileSize * 11;
        gamepanel.objects[mapNum][i].worldY = gamepanel.tileSize * 21;
        i++;

        gamepanel.objects[mapNum][i] = new ChestObject(gamepanel);
        gamepanel.objects[mapNum][i].setLoot(new KeyObject(gamepanel));
        gamepanel.objects[mapNum][i].worldX = gamepanel.tileSize * 16;
        gamepanel.objects[mapNum][i].worldY = gamepanel.tileSize * 20;
        i++;

        /*
        gamepanel.objects[mapNum][i] = new ChestObject(gamepanel);
        gamepanel.objects[mapNum][i].setLoot(new BedObject(gamepanel));
        gamepanel.objects[mapNum][i].worldX = gamepanel.tileSize * 14;
        gamepanel.objects[mapNum][i].worldY = gamepanel.tileSize * 20;
        i++;
         */
        mapNum = 2;
        i = 0;
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
        Random random = new Random();
        ArrayList<Point> availableTiles = new ArrayList<>();
        // ============================================== For map 0 ============================================== //
        int mapNum = 0;
        int i = 0;
        // Get all the tiles where a monster can be placed
        for (int col = 0; col < gamepanel.maxWorldCol; col++) {
            for (int row = 0; row < gamepanel.maxWorldRow; row++) {
                if (!gamepanel.tileM.tile[TileManager.mapTileNum[mapNum][col][row]].collision) {
                    availableTiles.add(new Point(col, row));
                }
            }
        }

        // Generate monsters
        for (int monsterCount = 0; monsterCount < 5; monsterCount++) { // Change 10 to the number of monsters you want to generate
            if (!availableTiles.isEmpty()) {
                // Select a random tile
                int randomIndex = random.nextInt(availableTiles.size());
                Point randomTile = availableTiles.get(randomIndex);
                availableTiles.remove(randomIndex); // Remove the selected tile from the list to avoid placing multiple monsters on the same tile

                // Create a new monster and set its position to the selected tile
                gamepanel.monster[mapNum][i] = new Skeleton(gamepanel);
                gamepanel.monster[mapNum][i].worldX = randomTile.x * gamepanel.tileSize;
                gamepanel.monster[mapNum][i].worldY = randomTile.y * gamepanel.tileSize;
                i++;
            }
        }
        // ======================================================================================================== //

        // ============================================== For map 2 ============================================== //
        mapNum = 2;
        i = 0;
        gamepanel.monster[mapNum][i] = new Bat(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 22;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
        // ======================================================================================================== //

        // ============================================== For map 3 ============================================== //
        mapNum = 3;
        i = 0;
        gamepanel.monster[mapNum][i] = new AngelOfDeath(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 24;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 17;
        // ======================================================================================================== //
    }

    /*
    public void setMonster() {

        int mapNum = 0;
        int i = 0;
        // ================================================================ //
        // Randomly generate monster
        gamepanel.monster[mapNum][i] = new Skeleton(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 13;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 34;
        i++;
        gamepanel.monster[mapNum][i] = new BlueSlime(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 31;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
        i++;
        gamepanel.monster[mapNum][i] = new RedSlime(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 33;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
        i++;
        // ================================================================ //

        mapNum = 2;
        i = 0;
        gamepanel.monster[mapNum][i] = new Bat(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 22;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;

        i++;
        gamepanel.monster[mapNum][i] = new Bat(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 36;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 26;

        i++;
        gamepanel.monster[mapNum][i] = new Bat(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 35;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 26;

        mapNum = 3;
        i = 0;
        gamepanel.monster[mapNum][i] = new AngelOfDeath(gamepanel);
        gamepanel.monster[mapNum][i].worldX = gamepanel.tileSize * 26;
        gamepanel.monster[mapNum][i].worldY = gamepanel.tileSize * 22;
    }
     */
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
