package main;

import entity.Entity;

import java.util.ArrayList;

public class CollisionCheck {

    GamePanel gamepanel;

    public CollisionCheck(GamePanel gp) {
        this.gamepanel = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamepanel.tileSize;
        int entityRightCol = entityRightWorldX / gamepanel.tileSize;
        int entityTopRow = entityTopWorldY / gamepanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamepanel.tileSize;

        int tileNum1, tileNum2;


        // Check if player is using dash or not
        if(entity == gamepanel.player)
        {
            if(gamepanel.Key.dashPressed) entity.speed = entity.dashSpeed;
            else entity.speed = entity.originalSpeed;
        }

        // Use a temporal direction when it's being knocked back
        String direction = entity.direction;
        if(entity.knockBack)
        {
            direction = entity.knockBackDirection;
        }

        switch (direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityRightCol][entityTopRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityRightCol][entityBottomRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityLeftCol][entityBottomRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gamepanel.tileM.mapTileNum[gamepanel.currentMap][entityRightCol][entityBottomRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
        }
        if(entity == gamepanel.player) entity.speed = entity.originalSpeed;

    }
    public int checkObject(Entity entity, boolean player)
    {
        int index = 999;

        // Use a temporal direction when it's being knocked back
        String direction = entity.direction;
        if(entity.knockBack)
        {
            direction = entity.knockBackDirection;
        }

        for(int i = 0; i < gamepanel.objects[1].length; i++)
        {
            if(gamepanel.objects[gamepanel.currentMap][i] != null)
            {
                // Get entity's solid area
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Get the object's solid area
                gamepanel.objects[gamepanel.currentMap][i].solidArea.x = gamepanel.objects[gamepanel.currentMap][i].worldX + gamepanel.objects[gamepanel.currentMap][i].solidArea.x;
                gamepanel.objects[gamepanel.currentMap][i].solidArea.y = gamepanel.objects[gamepanel.currentMap][i].worldY + gamepanel.objects[gamepanel.currentMap][i].solidArea.y;

                // Check if player is dash or not
                if(entity == gamepanel.player)
                {
                    if(gamepanel.Key.dashPressed) entity.speed = entity.dashSpeed;
                    else entity.speed = entity.originalSpeed;
                }

                switch(direction)
                {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if(entity.solidArea.intersects(gamepanel.objects[gamepanel.currentMap][i].solidArea))
                {
                    entity.collisionOn = true;
                    index = i;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamepanel.objects[gamepanel.currentMap][i].solidArea.x = gamepanel.objects[gamepanel.currentMap][i].solidAreaDefaultX;
                gamepanel.objects[gamepanel.currentMap][i].solidArea.y = gamepanel.objects[gamepanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    //NPC or Monster
    public int checkEntity(Entity entity, Entity[][] target)
    {
        int index = 999;
        // Use a temporal direction when it's being knocked back
        String direction = entity.direction;
        if(entity.knockBack) {
            direction = entity.knockBackDirection;

        }
        for(int i = 0; i < target[1].length; i++)
        {
            if(target[gamepanel.currentMap][i] != null)
            {
                // Get entity's solid area
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Get the target's solid area
                target[gamepanel.currentMap][i].solidArea.x = target[gamepanel.currentMap][i].worldX + target[gamepanel.currentMap][i].solidArea.x;
                target[gamepanel.currentMap][i].solidArea.y = target[gamepanel.currentMap][i].worldY + target[gamepanel.currentMap][i].solidArea.y;

                switch(direction)
                {
                    // Check if player is using dash or not
                    case "up":
                        if(gamepanel.Key.dashPressed) entity.solidArea.y -= entity.speed * 4;
                        else entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        if(gamepanel.Key.dashPressed) entity.solidArea.y += entity.speed * 4;
                        else entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        if(gamepanel.Key.dashPressed) entity.solidArea.x -= entity.speed * 4;
                        else entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        if(gamepanel.Key.dashPressed) entity.solidArea.x += entity.speed * 4;
                        else entity.solidArea.x += entity.speed;
                        break;
                }
                if(entity.solidArea.intersects(target[gamepanel.currentMap][i].solidArea))
                {
                    if(target[gamepanel.currentMap][i] != entity)
                    {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gamepanel.currentMap][i].solidArea.x = target[gamepanel.currentMap][i].solidAreaDefaultX;
                target[gamepanel.currentMap][i].solidArea.y = target[gamepanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity){

        boolean contactPlayer = false;

        // Get entity's solid area
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //Get the object's solid area
        gamepanel.player.solidArea.x = gamepanel.player.worldX + gamepanel.player.solidArea.x;
        gamepanel.player.solidArea.y = gamepanel.player.worldY + gamepanel.player.solidArea.y;

        switch(entity.direction)
        {
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }
        if(entity.solidArea.intersects(gamepanel.player.solidArea))
        {
            contactPlayer = true;
            entity.collisionOn = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamepanel.player.solidArea.x = gamepanel.player.solidAreaDefaultX;
        gamepanel.player.solidArea.y = gamepanel.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
