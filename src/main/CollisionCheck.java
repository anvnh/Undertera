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
        for(int i = 0; i < gamepanel.basedObject.length; i++)
        {
            if(gamepanel.basedObject[i] != null)
            {
                // Get entity's solid area
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Get the object's solid area
                gamepanel.basedObject[i].solidArea.x = gamepanel.basedObject[i].worldX + gamepanel.basedObject[i].solidArea.x;
                gamepanel.basedObject[i].solidArea.y = gamepanel.basedObject[i].worldY + gamepanel.basedObject[i].solidArea.y;

                switch(entity.direction)
                {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(gamepanel.basedObject[i].solidArea))
                        {
                            if(gamepanel.basedObject[i].collision)
                            {
                                entity.collisionOn = true;
                                index = i;
                            }
                            if(player == true)
                            {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gamepanel.basedObject[i].solidArea))
                        {
                            if(gamepanel.basedObject[i].collision)
                            {
                                entity.collisionOn = true;
                                index = i;
                            }
                            if(player == true)
                            {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gamepanel.basedObject[i].solidArea))
                        {
                            if(gamepanel.basedObject[i].collision)
                            {
                                entity.collisionOn = true;
                                index = i;
                            }
                            if(player)
                            {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gamepanel.basedObject[i].solidArea))
                        {
                            if(gamepanel.basedObject[i].collision)
                            {
                                entity.collisionOn = true;
                                index = i;
                            }
                            if(player == true)
                            {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamepanel.basedObject[i].solidArea.x = gamepanel.basedObject[i].solidAreaDefaultX;
                gamepanel.basedObject[i].solidArea.y = gamepanel.basedObject[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
