package main;

import java.awt.*;

public class EventHandler {
    GamePanel gamepanel;
    EventRectangle[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public EventHandler(GamePanel gamePanel)
    {
        this.gamepanel = gamePanel;

        eventRect = new EventRectangle[gamepanel.maxWorldCol][gamepanel.maxWorldRow];

        for(int col = 0; col < gamepanel.maxWorldRow; col++)
        {
            for(int row = 0; row < gamepanel.maxWorldCol; row++)
            {
                eventRect[col][row] = new EventRectangle();
                eventRect[col][row].x = 20;
                eventRect[col][row].y = 20;
                eventRect[col][row].width = 8;
                eventRect[col][row].height = 8;
                eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
                eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            }
        }

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
            if(hit(26, 20, "any"))
            {
                damagePit(gamepanel.dialogueState);
            }
            if(hit(23, 12, "any"))
            {
                healingPool(gamepanel.dialogueState);
            }
        }
    }

    public boolean hit(int col, int row, String requestDirection)
    {
        boolean hit = false;
        gamepanel.player.solidArea.x = gamepanel.player.solidAreaDefaultX + gamepanel.player.worldX;
        gamepanel.player.solidArea.y = gamepanel.player.solidAreaDefaultY + gamepanel.player.worldY;
        eventRect[col][row].x = col * gamepanel.tileSize + eventRect[col][row].y;
        eventRect[col][row].y = row * gamepanel.tileSize + eventRect[col][row].y;

        if(gamepanel.player.solidArea.intersects(eventRect[col][row]))
        {
            if(gamepanel.player.direction.contentEquals(requestDirection) || requestDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gamepanel.player.worldX;
                previousEventY = gamepanel.player.worldY;
            }
        }

        gamepanel.player.solidArea.x = gamepanel.player.solidAreaDefaultX;
        gamepanel.player.solidArea.y = gamepanel.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public void damagePit(int gameState)
    {
        gamepanel.gameState = gameState;
        gamepanel.ui.currentDialogue = "Ouch! I fell into a pit!";
        gamepanel.player.life -= 2.1;
        canTouchEvent = false;
    }
    public void healingPool(int gameState)
    {
        if(gamepanel.Key.F_Pressed)
        {
            gamepanel.gameState = gameState;
            gamepanel.ui.currentDialogue = "The healing pool's gentle embrace replenishes your vitality, restoring \nyour health with each passing moment.";
            gamepanel.player.life = gamepanel.player.maxLife;
            gamepanel.assetSetter.setMonster();
        }
        gamepanel.Key.F_Pressed = false;
    }
}
