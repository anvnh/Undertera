package data;

import main.GamePanel;

import java.io.*;

public class SaveLoad {
    GamePanel gamepanel;

    public boolean success = false;
    public SaveLoad(GamePanel gamepanel)
    {
        this.gamepanel = gamepanel;
    }

    public void save()
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            // Player stats
            ds.level = gamepanel.player.level;
            ds.maxLife = (int)gamepanel.player.maxLife;
            ds.life = (int)gamepanel.player.life;
            ds.mana = gamepanel.player.mana;
            ds.strength = gamepanel.player.strength;
            ds.dexterity = gamepanel.player.dexterity;
            ds.exp = gamepanel.player.exp;
            ds.nextLevelExp = gamepanel.player.nextLevelExp;
            ds.coin = gamepanel.player.coin;

            // Player inventory
            for(int i = 0; i < gamepanel.player.inventory.size(); i++)
            {
                ds.itemNames.add(gamepanel.player.inventory.get(i).name);
                ds.itemQuantity.add(gamepanel.player.inventory.get(i).quantity);
            }

            // Player equipment
            ds.currentWeaponSlot = gamepanel.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gamepanel.player.getCurrentShieldSlot();

            // Objects on map
            ds.mapObjectName = new String[gamepanel.maxMap][gamepanel.objects[1].length];
            ds.mapObjectWorldX = new int[gamepanel.maxMap][gamepanel.objects[1].length];
            ds.mapObjectWorldY = new int[gamepanel.maxMap][gamepanel.objects[1].length];
            ds.mapObjectLootNames = new String[gamepanel.maxMap][gamepanel.objects[1].length];
            ds.mapObjectOpened = new boolean[gamepanel.maxMap][gamepanel.objects[1].length];

            for(int mapNum = 0; mapNum < gamepanel.maxMap; mapNum++) {
                for(int i = 0; i < gamepanel.objects[1].length; i++) {
                    if(gamepanel.objects[mapNum][i] == null) {
                        ds.mapObjectName[mapNum][i] = "NA";
                    }
                    else {
                        ds.mapObjectName[mapNum][i] = gamepanel.objects[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gamepanel.objects[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gamepanel.objects[mapNum][i].worldY;
                        if(gamepanel.objects[mapNum][i].loot != null) {
                            ds.mapObjectLootNames[mapNum][i] = gamepanel.objects[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gamepanel.objects[mapNum][i].opened;
                    }
                }
            }

            // Write the DataStorage object to the file
            oos.writeObject(ds);

            success = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void load()
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // Read the DataStorage object from the file
            DataStorage ds = (DataStorage) ois.readObject();

            // Player stats
            gamepanel.player.level = ds.level;
            gamepanel.player.maxLife = ds.maxLife;
            gamepanel.player.life = ds.life;
            gamepanel.player.mana = ds.mana;
            gamepanel.player.strength = ds.strength;
            gamepanel.player.dexterity = ds.dexterity;
            gamepanel.player.exp = ds.exp;
            gamepanel.player.nextLevelExp = ds.nextLevelExp;
            gamepanel.player.coin = ds.coin;

            // player inventory
            gamepanel.player.inventory.clear();
            for(int i = 0; i < ds.itemNames.size(); i++)
            {
                gamepanel.player.inventory.add(gamepanel.entityGenerator.getObject(ds.itemNames.get(i)));
                gamepanel.player.inventory.get(i).quantity = ds.itemQuantity.get(i);
            }

            // Player equipment
            gamepanel.player.currentWeapon = gamepanel.player.inventory.get(ds.currentWeaponSlot);
            gamepanel.player.currentShield = gamepanel.player.inventory.get(ds.currentShieldSlot);
            gamepanel.player.getAttack();
            gamepanel.player.getDefense();
            gamepanel.player.getPlayerAttackImage();

            // Objects on map
            for(int mapNum = 0; mapNum < gamepanel.maxMap; mapNum++)
            {
                for(int i = 0; i < gamepanel.objects[1].length; i++)
                {
                    if(ds.mapObjectName[mapNum][i].equals("NA")) {
                        gamepanel.objects[mapNum][i] = null;
                    }
                    else {
                        gamepanel.objects[mapNum][i] = gamepanel.entityGenerator.getObject(ds.mapObjectName[mapNum][i]);
                        gamepanel.objects[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gamepanel.objects[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if(gamepanel.objects[mapNum][i].loot != null) {
                            gamepanel.objects[mapNum][i].setLoot(gamepanel.entityGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
                        }
                        gamepanel.objects[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if(gamepanel.objects[mapNum][i].opened)
                        {
                            gamepanel.objects[mapNum][i].image = gamepanel.objects[mapNum][i].image3;
                        }
                    }
                }
            }


        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
