package main;

import entity.Entity;

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

        switch (entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamepanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamepanel.tileSize;
                tileNum1 = gamepanel.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
        }

    }
    public int checkObject(Entity entity, boolean player)
    {
        int index = 999;
        for(int i = 0; i < gamepanel.objects.length; i++)
        {
            if(gamepanel.objects[i] != null)
            {
                // Get entity's solid area
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Get the object's solid area
                gamepanel.objects[i].solidArea.x = gamepanel.objects[i].worldX + gamepanel.objects[i].solidArea.x;
                gamepanel.objects[i].solidArea.y = gamepanel.objects[i].worldY + gamepanel.objects[i].solidArea.y;

                switch(entity.direction)
                {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }
                if(entity.solidArea.intersects(gamepanel.objects[i].solidArea))
                {
                    entity.collisionOn = true;
                    index = i;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamepanel.objects[i].solidArea.x = gamepanel.objects[i].solidAreaDefaultX;
                gamepanel.objects[i].solidArea.y = gamepanel.objects[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    //NPC or Monster
    public int checkEntity(Entity entity, Entity[] target)
    {
        int index = 999;
        for(int i = 0; i < target.length; i++)
        {
            if(target[i] != null)
            {
                // Get entity's solid area
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Get the target's solid area
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch(entity.direction)
                {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }
                if(entity.solidArea.intersects(target[i].solidArea))
                {
                    if(target[i] != entity)
                    {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
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
