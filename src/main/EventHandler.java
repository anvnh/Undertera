package main;

import data.Progress;
import entity.Entity;

import java.awt.*;

public class EventHandler {
    GamePanel gamepanel;
    EventRectangle[][][] eventRect;
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamepanel) {
        this.gamepanel = gamepanel;

        eventMaster = new Entity(gamepanel);

        eventRect = new EventRectangle[gamepanel.maxMap][gamepanel.maxWorldCol][gamepanel.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gamepanel.maxMap && col < gamepanel.maxWorldCol && row < gamepanel.maxWorldRow){
            eventRect[map][col][row] = new EventRectangle();
            eventRect[map][col][row].x = 20;
            eventRect[map][col][row].y = 20;
            eventRect[map][col][row].width = 8;
            eventRect[map][col][row].height = 8;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gamepanel.maxWorldCol)
            {
                col = 0;
                row++;
                if(row == gamepanel.maxWorldRow)
                {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue()
    {
        assert eventMaster != null;
        eventMaster.dialogue[0][0] = "You fall into a pit";
        eventMaster.dialogue[1][0] = "The healing pool's gentle embrace replenishes your vitality, restoring \nyour health with each passing moment." +
                "\nThe process has been saved.";
        eventMaster.dialogue[2][0] = "The monsters have been reset.";
    }


    public void checkEvent() {
        //Check if player is more than 1 tiles away from the latest event
        int xDistance = Math.abs(gamepanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamepanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance >= gamepanel.tileSize)
        {
            canTouchEvent = true;
        }

        if(canTouchEvent)
        {
            if(hit(0, 20, 12, "up"))
            {
                resetMons(gamepanel.dialogueState);
                healingPool(gamepanel.dialogueState);
            }
            if(hit(0, 31, 12, "any")) {
                teleport(1, 12, 12, gamepanel.inDoor); // from world to merchant
            }
            if(hit(1, 12, 12, "any")) {
                teleport(0, 31, 12, gamepanel.outSide); // from merchant to world
            }
            if(hit(0, 12, 9, "any")) {
                teleport(2, 9, 41, gamepanel.dungeon); // from world map to dungeon 1
            }
            if(hit(2, 8, 7, "any")) {
                teleport(3, 26, 41, gamepanel.dungeon); // from dungeon 1 to dungeon 2
            }
            if(hit(3, 26, 41, "any")) {
                teleport(2, 8, 8, gamepanel.dungeon); // from dungeon 2 to dungeon 1
            }
            if(hit(2, 9, 41, "any")) {
                teleport(0, 12, 9, gamepanel.outSide); // from dungeon 1 to world map
            }
            if(hit(3, 25, 27, "any")) {
                Angel_Of_Death(); // Meet the Angel of Death
            }
        }
    }

    public boolean hit(int map, int col, int row, String requestDirection)
    {
        boolean hit = false;
        if(map == gamepanel.currentMap) {
            gamepanel.player.solidArea.x = gamepanel.player.solidAreaDefaultX + gamepanel.player.worldX;
            gamepanel.player.solidArea.y = gamepanel.player.solidAreaDefaultY + gamepanel.player.worldY;
            eventRect[map][col][row].x = col * gamepanel.tileSize + eventRect[map][col][row].y;
            eventRect[map][col][row].y = row * gamepanel.tileSize + eventRect[map][col][row].y;

            if(gamepanel.player.solidArea.intersects(eventRect[map][col][row]))
            {
                if(gamepanel.player.direction.contentEquals(requestDirection) || requestDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gamepanel.player.worldX;
                    previousEventY = gamepanel.player.worldY;
                }
            }

            gamepanel.player.solidArea.x = gamepanel.player.solidAreaDefaultX;
            gamepanel.player.solidArea.y = gamepanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }
    public void damagePit(int gameState)
    {
        gamepanel.gameState = gameState;
        gamepanel.player.life -= 9.5;
        eventMaster.startDialogue(eventMaster, 0);
        canTouchEvent = false;
    }
    public void healingPool(int gameState)
    {
        gamepanel.gameState = gameState;
        gamepanel.player.life = gamepanel.player.maxLife;
        gamepanel.player.mana = gamepanel.player.maxMana;
        eventMaster.startDialogue(eventMaster, 1);
        //gamepanel.saveLoad.save();
    }
    public void resetMons(int gameState)
    {
        gamepanel.gameState = gameState;
        eventMaster.startDialogue(eventMaster, 2);
        gamepanel.assetSetter.setMonster();
    }
    public void teleport(int map, int col, int row, int area) {
        if(area != gamepanel.currentArea) {
            gamepanel.stopMusic();
        }
        gamepanel.playSoundEffect(11);

        gamepanel.currentMap = map;
        gamepanel.currentArea = area;

        gamepanel.player.worldX = gamepanel.tileSize * col;
        gamepanel.player.worldY = gamepanel.tileSize * row;

        previousEventX = gamepanel.player.worldX;
        previousEventY = gamepanel.player.worldY;

        canTouchEvent = false;
    }
    public void Angel_Of_Death() {
        if (!gamepanel.bossBattleOn && !Progress.angleofdeathDefeated)
        {
            gamepanel.gameState = gamepanel.cutSceneState;
            gamepanel.cutsceneManager.sceneNumber = gamepanel.cutsceneManager._Angel_Of_Death;
        }
    }
}
