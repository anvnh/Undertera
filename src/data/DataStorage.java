package data;

import entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // Player Stats
    int level;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    // Player Inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemQuantity = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    // Object on map
    String[][] mapObjectName;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String mapObjectLootNames[][];
    boolean[][] mapObjectOpened;
}
