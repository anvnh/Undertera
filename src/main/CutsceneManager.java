package main;

import entity.Entity;
import entity.PlayerDummy;
import monster.AngelOfDeath;
import object.DoorObject;
import object.IronDoorObject;

import java.awt.*;
import java.util.Objects;

public class CutsceneManager {
    GamePanel gamepanel;
    Graphics2D g2;
    Entity entity;
    public int sceneNumber;
    public int scenePhase;

    // Scene number
    public final int NA = 0;
    public final int _Angel_Of_Death = 1;

    public CutsceneManager(GamePanel gamepanel)
    {
        this.gamepanel = gamepanel;
    }
    public void draw(Graphics2D g2)
    {
        this.g2 = g2;
        switch (sceneNumber)
        {
            case NA:
                break;
            case _Angel_Of_Death:
                Scene_Angel_Of_Death();
                break;
        }
    }
    public void startDialogue(Entity entity){
        gamepanel.gameState = gamepanel.dialogueState;
        gamepanel.ui.npc = entity;
        gamepanel.Key.enterPressed = false;
    }
    public void Scene_Angel_Of_Death()
    {
        gamepanel.map.miniMapOn = false;
        if(scenePhase == 0)
        {
            gamepanel.bossBattleOn = true;
            // Shut the door

            for(int i = 0; i < gamepanel.objects[1].length; i++)
            {
                if(gamepanel.objects[gamepanel.currentMap][i] == null)
                {
                    gamepanel.objects[gamepanel.currentMap][i] = new IronDoorObject(gamepanel);
                    gamepanel.objects[gamepanel.currentMap][i].worldX = gamepanel.tileSize * 25;
                    gamepanel.objects[gamepanel.currentMap][i].worldY = gamepanel.tileSize * 28;
                    gamepanel.objects[gamepanel.currentMap][i].temp = true;
                    break;
                }
            }

            for(int i = 0; i < gamepanel.npc[1].length; i++)
            {
                if(gamepanel.npc[gamepanel.currentMap][i] == null)
                {
                    gamepanel.npc[gamepanel.currentMap][i] = new PlayerDummy(gamepanel);
                    gamepanel.npc[gamepanel.currentMap][i].worldX = gamepanel.player.worldX;
                    gamepanel.npc[gamepanel.currentMap][i].worldY = gamepanel.player.worldY;
                    gamepanel.npc[gamepanel.currentMap][i].direction = gamepanel.player.direction;
                    break;
                }
            }

            gamepanel.player.drawing = false;

            scenePhase++;
        }
        if(scenePhase == 1)
        {
            gamepanel.player.worldY -= 2;
            if(gamepanel.player.worldY == gamepanel.tileSize * 18) {
                scenePhase++;
            }
        }
        if(scenePhase == 2)
        {
            // Search the boss
            for(int i = 0; i < gamepanel.monster[1].length; i++)
            {
                if(gamepanel.monster[gamepanel.currentMap][i] != null &&
                        Objects.equals(gamepanel.monster[gamepanel.currentMap][i].name, AngelOfDeath.monsterName))
                {
                    gamepanel.monster[gamepanel.currentMap][i].sleep = false;
                    startDialogue(gamepanel.monster[gamepanel.currentMap][i]);
                    scenePhase++;
                    break;
                }
            }
        }
        if(scenePhase == 3)
        {
            gamepanel.ui.Prev_State = gamepanel.cutSceneState;
            gamepanel.ui.drawDialogueScreen();
        }
        if(scenePhase == 4)
        {
            // Return to the player

            // Search for the dummy
            for(int i = 0; i < gamepanel.npc[1].length; i++)
            {
                if(gamepanel.npc[gamepanel.currentMap][i] != null &&
                        Objects.equals(gamepanel.npc[gamepanel.currentMap][i].name, PlayerDummy.npcName))
                {
                    gamepanel.player.worldX = gamepanel.npc[gamepanel.currentMap][i].worldX;
                    gamepanel.player.worldY = gamepanel.npc[gamepanel.currentMap][i].worldY;

                    // Delete the dummy
                    gamepanel.npc[gamepanel.currentMap][i] = null;
                    break;
                }
            }

            // Start drawing player
            gamepanel.player.drawing = true;


            // RESET
            sceneNumber = NA;
            scenePhase = 0;
            gamepanel.gameState = gamepanel.playState;

            gamepanel.stopMusic();
        }
    }
}
