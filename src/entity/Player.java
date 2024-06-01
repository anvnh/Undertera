package entity;
import main.GamePanel;
import main.KeyboardHandler;
import main.UtilityTools;
import object.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamepanel;
    KeyboardHandler Key;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    public final int maxInventorySize = 190;
    public boolean dashSound = false;
    public boolean lightUpdate = false;

    public Player(GamePanel gp, KeyboardHandler kh){
        super(gp);
        this.gamepanel = gp;
        this.Key = kh;
        screenX = gamepanel.screenWidth / 2 - (gamepanel.playerSize / 2);
        screenY = gamepanel.screenHeight / 2 - (gamepanel.playerSize / 2);

        solidArea = new Rectangle();

        solidArea.x = 30;
        solidArea.y = 35;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        solidArea.width = 20;
        solidArea.height = 30;
        name = "player";

        /*
        //Attack area of player
        attackArea.width = 45;
        attackArea.height = 45;
         */

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        getShieldImage();
        setItems();
    }
    public void setDefaultValues()
    {
        image = setup_player("/player/go_down_1");
        worldX = gamepanel.tileSize * 22;
        worldY = gamepanel.tileSize * 20;
        /*
        worldX = gamepanel.tileSize * 58;
        worldY = gamepanel.tileSize * 47;
         */
        speed = 3;
        originalSpeed = 3;
        dashSpeed = speed * 4;
        direction = "down";
        //Status
        maxLife = 100; // max life (cannot add more than this)
        life = 100;
        maxMana = 100;  // mana for projectile
        mana = maxMana;
        strength = 10;  // more strength, more attack
        dexterity = 10; // more dexterity, more defense
        exp = 0;
        nextLevelExp = 20;
        ///
        coin = 1000;
        ///
        currentWeapon = new SwordObject(gamepanel);
        currentShield = new ShieldObject(gamepanel);
        currentLight = null;
        projectile = new FireballObject(gamepanel);
        attack = getAttack(); // total attack
        defense = getDefense(); // total defense
    }
    public void setDefaultPosition() {
        worldX = gamepanel.tileSize * 22;
        worldY = gamepanel.tileSize * 20;
        direction = "down";
    }
    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
        attacking = false;
        shielding = false;
        knockBack = false;
        lightUpdate = true;
    }
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new AxeObject(gamepanel));
        inventory.add(new KeyObject(gamepanel));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion_duration_1 = currentWeapon.motion_duration_1;
        motion_duration_2 = currentWeapon.motion_duration_2;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense()
    {
        return defense = dexterity * currentShield.defenseValue;
    }

    public int getCurrentWeaponSlot()
    {
        int currentSlot = 0;
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i) == currentWeapon)
            {
                currentSlot = i;
                break;
            }
        }
        return currentSlot;
    }
    public int getCurrentShieldSlot()
    {
        int currentSlot = 0;
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i) == currentShield)
            {
                currentSlot = i;
                break;
            }
        }
        return currentSlot;
    }

    public void getPlayerImage()
    {
        //Standing position
        for(int i = 0; i < 6; i++)
        {
            stand_down[i] = setup_player("/player/stand_down_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            stand_up[i] = setup_player("/player/stand_up_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            stand_right[i] = setup_player("/player/stand_right_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            stand_left[i] = setup_player("/player/stand_left_" + (i + 1));
        }

        //Running position
        for(int i = 0; i < 6; i++)
        {
            go_down[i] = setup_player("/player/go_down_" + (i + 1));
        }
         for(int i = 0; i < 6; i++)
        {
            go_up[i] = setup_player("/player/go_up_" + (i + 1));
        }
        for(int i = 0; i < 6; i++)
        {
            go_left[i] = setup_player("/player/go_left_" + (i + 1));
        }
           for(int i = 0; i < 6; i++)
        {
            go_right[i] = setup_player("/player/go_right_" + (i + 1));
        }
    }
    public void getSleepingImage(BufferedImage image1)
    {
        for(int i = 0; i < 6; i++)
        {
            stand_down[i] = image1;
            stand_up[i] = image1;
            stand_left[i] = image1;
            stand_right[i] = image1;
        }
    }
    public void getPlayerAttackImage()
    {
        if(currentWeapon.type == type_sword)
        {
            for(int i = 0; i < 4; i++)
            {
                attack_down[i] = setup_player("/player/attack/sword/attack_down_" + (i));
                attack_up[i] = setup_player("/player/attack/sword/attack_up_" + (i));
                attack_right[i] = setup_player("/player/attack/sword/attack_right_" + (i));
                attack_left[i] = setup_player("/player/attack/sword/attack_left_" + (i));
            }
        }
        if(currentWeapon.type == type_axe)
        {
            for(int i = 0; i < 4; i++)
            {
                attack_down[i] = setup_player("/player/attack/axe/attack_down_axe_" + (i));
                attack_up[i] = setup_player("/player/attack/axe/attack_up_axe_" + (i));
                attack_right[i] = setup_player("/player/attack/axe/attack_right_axe_" + (i));
                attack_left[i] = setup_player("/player/attack/axe/attack_left_axe_" + (i));
            }
        }
    }
    public void getShieldImage()
    {
        for(int i = 0; i < 4; i++)
        {
            shield_down[i] = setup_player("/player/shield/shield_down_" + (i));
            shield_up[i] = setup_player("/player/shield/shield_up_" + (i));
            shield_right[i] = setup_player("/player/shield/shield_right_" + (i));
            shield_left[i] = setup_player("/player/shield/shield_left_" + (i));
        }
    }

    public void update()
    {
        if(knockBack)
        {
            collisionOn = false;
            //Check tile collision
            gamepanel.collisionCheck.checkTile(this); // Check player collision with tile

            // Check object collision
            int objectIndex = gamepanel.collisionCheck.checkObject(this, true); // check player collision with object
            pickUpObject(objectIndex);

            // Check NPC Collision
            int npcIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.npc); // check NPC collision with player
            interactNPC(npcIndex);

            // Check Monster's Collision
            int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster); // check monster collision with player
            contactMonster(monsterIndex);

            // Check Interactive Tile Collision
            gamepanel.collisionCheck.checkEntity(this, gamepanel.interactiveTile);  // check player collision with interactive tiles

            if(collisionOn)
            {
                knockBackCounter = 0;
                knockBack = false;
                speed = originalSpeed;
            }
            else
            {
                switch(knockBackDirection)
                {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            knockBackCounter++;
            // the more knockBackCounter is, the more distance object will be knock back
            if(knockBackCounter == 20)
            {
                knockBackCounter = 0;
                knockBack = false;
                speed = originalSpeed;
            }
        }
        if(attacking)
        {
            attack();
        }
        if(Key.shieldPressed) {
            shielding = true;
            parryCounter++;
        }
        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed || Key.communicateWithNPC)
        {
            if (Key.upPressed) {
                direction = "up";
            } else if (Key.downPressed) {
                direction = "down";
            } else if (Key.leftPressed) {
                direction = "left";
            } else if (Key.rightPressed){
                direction = "right";
            }

            collisionOn = false;

            //Check tile collision
            gamepanel.collisionCheck.checkTile(this); // Check player collision with tile

            // Check object collision
            int objectIndex = gamepanel.collisionCheck.checkObject(this, true); // check player collision with object
            pickUpObject(objectIndex);

            // Check NPC Collision
            int npcIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.npc); // check NPC collision with player
            interactNPC(npcIndex);

            // Check Monster's Collision
            int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster); // check monster collision with player
            contactMonster(monsterIndex);

            // Check Interactive Tile Collision
            gamepanel.collisionCheck.checkEntity(this, gamepanel.interactiveTile);  // check player collision with interactive tiles

            // Check projectile collision with terrain (Not yet implemented)

            //Attack
            check_attack();

            // Check event
            gamepanel.eventHandler.checkEvent(); // Check if player is in the event area

            // Check attacking
            if(gamepanel.Key.attack_Pressed)
            {
                attacking = true;
            }

            //if collision is false, player can move
            if(!collisionOn && !Key.communicateWithNPC)
            {
                switch (direction) {
                    case "up":
                        if (gamepanel.Key.dashPressed)
                        {
                            worldY -= dashSpeed;
                            if (!dashSound)
                            {
                                gamepanel.playSoundEffect(13);
                                dashSound = true;
                            }
                        } else
                        {
                            worldY -= speed;
                            dashSound = false;
                        }
                        break;
                    case "down":
                        if(gamepanel.Key.dashPressed)
                        {
                            worldY += dashSpeed;
                            if(!dashSound)
                            {
                                gamepanel.playSoundEffect(13);
                                dashSound = true;
                            }
                        }
                        else
                        {
                            worldY += speed;
                            dashSound = false;
                        }
                        break;
                    case "left":
                        if(gamepanel.Key.dashPressed)
                        {
                            if(!dashSound)
                            {
                                gamepanel.playSoundEffect(13);
                                dashSound = true;
                            }
                            worldX -= dashSpeed;
                        }
                        else
                        {
                            worldX -= speed;
                            dashSound = false;
                        }
                        break;
                    case "right":
                        if(gamepanel.Key.dashPressed)
                        {
                            worldX += dashSpeed;
                            if(!dashSound)
                            {
                                gamepanel.playSoundEffect(13);
                                dashSound = true;
                            }
                        }
                        else
                        {
                            worldX += speed;
                            dashSound = false;
                        }
                        break;
                }
            }

            gamepanel.Key.communicateWithNPC = false;

            shielding = false;

            //Counter for running sounds
            runningSoundCounter++;
            if(runningSoundCounter == 45) {
                gamepanel.playSoundEffect(16);
                runningSoundCounter = 0;
            }

            runCount++; // Counter for switching running animation
            if (runCount > 15) {
                runAnimation = (runAnimation == 6) ? (1) : (runAnimation + 1);
                runCount = 0;
            }

            shieldCount++;
            if(shieldCount > 15)
            {
                shieldAnimation = shieldAnimation == 4 ? 4 : shieldAnimation + 1;
                shieldCount = 0;
            }
        }
        else {
            shieldCount++;
            if(shieldCount > 15)
            {
                shieldAnimation = shieldAnimation == 4 ? 4 : shieldAnimation + 1;
                shieldCount = 0;
            }

            standCount++; // Counter for switching standing animation
            if (standCount > 15) {
                standAnimation = standAnimation == 6 ? 1 : standAnimation + 1;
                standCount = 0;
            }

        }

        // Shooting projectile
        if(gamepanel.Key.Projectile_Pressed && !projectile.alive && shotAvailableCounter == 60
                && projectile.haveEnoughMana(this) )
        {
            // Set default coordinate, direction and user
            projectile.set(worldX, worldY, direction, true, this);

            // Use mana
            projectile.useMana(this);

            //Add to the list
            // gamepanel.projectileList.add(projectile);
            for(int i = 0; i < gamepanel.projectile[1].length; i++)
            {
                if(gamepanel.projectile[gamepanel.currentMap][i] == null)
                {
                    gamepanel.projectile[gamepanel.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;
        }

        if(invincible)
        {
            invincibleCounter++;
            if(invincibleCounter == 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 60)
        {
            shotAvailableCounter++;
        }

        if(Key.Projectile_Pressed) manaRegenCounter = 0;
        if(manaRegenCounter < 240 && mana < maxMana)
        {
            manaRegenCounter++;
        }
        if(manaRegenCounter == 240)
        {
            regenerateMana();
            manaRegenCounter = 0;
            if(mana == maxMana) gamepanel.playSoundEffect(12); // sound effect after mana is full
        }

        life = Math.min(life, maxLife);
        mana = Math.min(mana, maxMana);

        if(life <= 0) {
            gamepanel.gameState = gamepanel.gameOverState;
        }
    }
    public void regenerateMana(){
        mana += 5;
    }
    public void check_attack()
    {
        if(gamepanel.Key.attack_Pressed)
        {
            attacking = true;
            gamepanel.playSoundEffect(1);
            gamepanel.Key.attack_Pressed = false;
        }
    }
    public boolean check_dash()
    {
        return gamepanel.Key.dashPressed;
    }
    public double calculateDamageDeal(Entity entity)
    {
        attack = getAttack();
        double damage = attack * ((double) 110 / (110 + entity.defense));
        return Math.round(damage * 100.0) / 100.0; // round up the damage
        // 110 is defense constant, defined as C, but I personally like to write it all down than use variables
    }
    public double calculateDamageDealwithProjectile(Entity entity, int Attack)
    {
        return Attack;
    }
    public double calculateDamageReceive(Entity entity)
    {
        return entity.attack * ((double) 110 / (110 + defense));
    }

    public void pickUpObject(int objectIndex) {
        if(objectIndex != 999)
        {
            // Pick up only items
            if(gamepanel.objects[gamepanel.currentMap][objectIndex].type == type_pickuponly) //fixed
            {
                gamepanel.objects[gamepanel.currentMap][objectIndex].use(this, gamepanel); //fixed
                gamepanel.objects[gamepanel.currentMap][objectIndex] = null; //fixed
            }
            else if(gamepanel.objects[gamepanel.currentMap][objectIndex].type == type_obstacle)
            {
                if(Key.enterPressed)
                {
                    gamepanel.objects[gamepanel.currentMap][objectIndex].interact();
                }
            }
            else
            {
                String text;
                if(canObtainItem(gamepanel.objects[gamepanel.currentMap][objectIndex]))
                {
                    //inventory.add(gamepanel.objects[gamepanel.currentMap][objectIndex]); //fixed
                    gamepanel.playSoundEffect(7);
                    text = "Picked up " + gamepanel.objects[gamepanel.currentMap][objectIndex].name; //fixed
                    /*
                    gamepanel.ui.addMessage(text);
                     */
                    gamepanel.objects[gamepanel.currentMap][objectIndex] = null; //fixed || REMEMBER THIS LINE!!!
                }
                else {
                    text = "Cannot carry any more items.";
                }
                gamepanel.ui.addMessage(text);
            }
        }
    }

    public void interactNPC(int npcIndex) {
        if(npcIndex != 999)
        {
            if(gamepanel.Key.communicateWithNPC)
            {
                //gamepanel.gameState = gamepanel.dialogueState;
                gamepanel.npc[gamepanel.currentMap][npcIndex].speak(); //fixed
            }
        }
    }

    public void contactMonster(int monsterIndex) {
        if(monsterIndex != 999)
        {
            if(!invincible && !gamepanel.monster[gamepanel.currentMap][monsterIndex].dying)     //fixed
            {
                life -= calculateDamageReceive(gamepanel.monster[gamepanel.currentMap][monsterIndex]); //fixed
                invincible = true;
                gamepanel.playSoundEffect(4);
            }
        }
    }

    public void damageInteractiveTile(int interactiveIndex){
        if(interactiveIndex != 999
                && gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].destructible //fixed
                && gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].isCorrectItem(this) //fixed
                && !gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].invincible //fixed
        )
        {
            gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].playSE(); //fixed
            gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].life--; //fixed
            gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].invincible = true; //fixed

            // Generate particle
            generateParticle(gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex], gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex]); //fixed

            if(gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].life == 0){ //fixed
                gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex] = gamepanel.interactiveTile[gamepanel.currentMap][interactiveIndex].getDestroyedForm(); //fixed
            }
        }
    }
    public void damageMonster(int monsterIndex, Entity attacker,int knockBackPower)
    {
        if(monsterIndex != 999)
        {
            if(!gamepanel.monster[gamepanel.currentMap][monsterIndex].invincible) // fixed
            {
                gamepanel.playSoundEffect(3);

                if(knockBackPower > 0)
                {
                    setKnockBack(gamepanel.monster[gamepanel.currentMap][monsterIndex], attacker,knockBackPower);
                }

                gamepanel.monster[gamepanel.currentMap][monsterIndex].life -= calculateDamageDeal(gamepanel.monster[gamepanel.currentMap][monsterIndex]); //fixed
                gamepanel.ui.addMessage(calculateDamageDeal(gamepanel.monster[gamepanel.currentMap][monsterIndex]) + " damage"); //fixed
                //System.out.println(gamepanel.monster[monsterIndex].life);

                gamepanel.monster[gamepanel.currentMap][monsterIndex].invincible = true; //fixed
                gamepanel.monster[gamepanel.currentMap][monsterIndex].damageReaction(); //fixed


                if(gamepanel.monster[gamepanel.currentMap][monsterIndex].life <= 0) //fixed
                {
                    gamepanel.monster[gamepanel.currentMap][monsterIndex].dying = true;
                    gamepanel.ui.addMessage("Killed " + gamepanel.monster[gamepanel.currentMap][monsterIndex].name); //fixed
                    gamepanel.ui.addMessage("Received " + gamepanel.monster[gamepanel.currentMap][monsterIndex].exp + " exp"); //fixed
                    exp += gamepanel.monster[gamepanel.currentMap][monsterIndex].exp; //fixed
                    checkLevelUp();
                }
            }
        }
    }
    // Projectile attacked ignore defense
    public void damageProjectileMonster(int monsterIndex, int Attack)
    {
        if(monsterIndex != 999)
        {
            if(!gamepanel.monster[gamepanel.currentMap][monsterIndex].invincible) // fixed
            {
                gamepanel.playSoundEffect(3);

                gamepanel.monster[gamepanel.currentMap][monsterIndex].life -= calculateDamageDealwithProjectile(gamepanel.monster[gamepanel.currentMap][monsterIndex], Attack); //fixed
                gamepanel.ui.addMessage(calculateDamageDealwithProjectile(gamepanel.monster[gamepanel.currentMap][monsterIndex], Attack) + " damage");
                //System.out.println(gamepanel.monster[monsterIndex].life);

                gamepanel.monster[gamepanel.currentMap][monsterIndex].invincible = true;
                gamepanel.monster[gamepanel.currentMap][monsterIndex].damageReaction();


                if(gamepanel.monster[gamepanel.currentMap][monsterIndex].life <= 0)
                {
                    gamepanel.monster[gamepanel.currentMap][monsterIndex].dying = true;
                    gamepanel.ui.addMessage("Killed " + gamepanel.monster[gamepanel.currentMap][monsterIndex].name);
                    gamepanel.ui.addMessage("Received " + gamepanel.monster[gamepanel.currentMap][monsterIndex].exp + " exp");
                    exp += gamepanel.monster[gamepanel.currentMap][monsterIndex].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageProjectile(int projectileIndex)
    {
        if(projectileIndex != 999)
        {
            Entity projectile = gamepanel.projectile[gamepanel.currentMap][projectileIndex];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }
    public void checkLevelUp()
    {
        if(exp >= nextLevelExp)
        {
            gamepanel.playSoundEffect(5);
            calculateExp();
            life = maxLife;
            mana = maxMana;
            gamepanel.ui.addMessage("Level Up!");
        }
    }
    public void calculateExp()
    {
        int remainder = exp;
        exp = 0;
        while(remainder >= nextLevelExp)
        {
            strength += (int)(strength * 0.75);
            dexterity += (int)(dexterity * 0.5);
            level++;
            remainder -= nextLevelExp;
            nextLevelExp = (int) (nextLevelExp * 2.5);
        }
        exp = remainder;
    }

    // Select or using items from inventory
    public void selectItem(){
        int itemIndex = gamepanel.ui.getItemIndexOnSlot(gamepanel.ui.playerSlotCol, gamepanel.ui.playerSlotRow);
        if(itemIndex < inventory.size())
        {
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe)
            {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_armor)
            {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light)
            {
                if(currentLight == selectedItem)
                {
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdate = true;
            }
            if(selectedItem.type == type_consumable)
            {
                if(selectedItem.use(this, gamepanel)) {
                    if (selectedItem.quantity > 1) {
                        selectedItem.quantity--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName)
    {
        int itemIndex = 999;
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i).name.equals(itemName))
            {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item)
    {

        boolean canObtain = false;

        Entity newItem = gamepanel.entityGenerator.getObject(item.name);

        if(newItem.stackable)
        {
            int index = searchItemInInventory(newItem.name);
            if(index != 999)
            {
                inventory.get(index).quantity++;
                canObtain = true;
            }
            else // New item so need to check vacant space
            {
                if(inventory.size() != maxInventorySize)
                {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else  // Not stackable
        {
            if(inventory.size() < maxInventorySize)
            {
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void draw_player(Graphics2D g2)
    {
        BufferedImage image = null;
        if(!Key.upPressed && !Key.downPressed && !Key.leftPressed && !Key.rightPressed)
        {
            check_attack();
            if(direction.equals("down"))
            {
                if(!attacking)
                {
                    if(!shielding){
                        image = getStandAnimate(image, stand_down);
                    }
                    else {
                        image = getShieldAnimate(image, shield_down);
                        shielding = false;
                    }
                }
                else
                {
                    if(!shielding)
                    {
                        image = getAttackAnimate(image, attack_down);
                    }
                    else
                    {
                        image = getShieldAnimate(image, shield_down);
                    }
                }
            }
            else if(direction.equals("up"))
            {
                if(!attacking)
                {
                    if(!shielding)
                    {
                        image = getStandAnimate(image, stand_up);
                    }
                    else
                    {
                        image = getShieldAnimate(image, shield_up);
                        shielding = false;
                    }
                }
                else {
                    if(!shielding)
                    {
                        image = getAttackAnimate(image, attack_up);
                    }
                    else
                    {
                        image = getShieldAnimate(image, shield_up);
                    }
                }
            }
            else if(direction.equals("left"))
            {
                if(!attacking)
                {
                    if(!shielding)
                    {
                        image = getStandAnimate(image, stand_left);
                    }
                    else
                    {
                        image = getShieldAnimate(image, shield_left);
                        shielding = false;
                    }
                }
                else {
                    if(!shielding)
                    {
                        image = getAttackAnimate(image, attack_left);
                    }
                    else
                    {
                        image = getShieldAnimate(image, shield_left);
                    }
                }
            }
            else if(direction.equals("right"))
            {
                if(!attacking)
                {
                    if(!shielding)
                    {
                        image = getStandAnimate(image, stand_right);
                    }
                    else
                    {
                        image = getShieldAnimate(image, shield_right);
                        shielding = false;
                    }
                }
                else {
                    if(!shielding)
                    {
                        image = getAttackAnimate(image, attack_right);
                    }
                    else
                    {
                        image = getShieldAnimate(image, shield_right);
                    }
                }
            }
            if(invincible)
            {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            //
            if(drawing)
            {
                g2.drawImage(image, screenX, screenY, null);
            }

            //Reset alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            //g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }

        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed)
        {
            if(direction.equals("up"))
            {
                if(!attacking)
                {
                    if(check_dash())
                    {
                        image = null;
                        if(!dashing) {
                            dashing = true;
                        }
                    }
                    else
                    {
                        image = getRunAnimate(image, go_up);
                        dashing = false;
                    }
                }
                else {
                    image = getAttackAnimate(image, attack_up);
                }
            }
            else if(direction.equals("down"))
            {
                if(!attacking)
                {
                    if(check_dash())
                    {
                        image = null;
                        if(!dashing) {
                            dashing = true;
                        }
                    }
                    else
                    {
                        image = getRunAnimate(image, go_down);
                        dashing = false;
                    }
                }
                else {
                    image = getAttackAnimate(image, attack_down);
                }
            }
            else if(direction.equals("left"))
            {
                if(!attacking)
                {
                    if(check_dash())
                    {
                        image = null;
                        if(!dashing) {
                            dashing = true;
                        }
                    }
                    else
                    {
                        image = getRunAnimate(image, go_left);
                        dashing = false;
                    }
                }
                else {
                    image = getAttackAnimate(image, attack_left);
                }
            }
            else if(direction.equals("right"))
            {
                if(!attacking)
                {
                    if(check_dash())
                    {
                        image = null;
                        if(!dashing) {
                            dashing = true;
                        }
                    }
                    else
                    {
                        image = getRunAnimate(image, go_right);
                        dashing = false;
                    }
                }
                else {
                    image = getAttackAnimate(image, attack_right);
                }
            }
            if(invincible)
            {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            //
            if(drawing)
            {
                g2.drawImage(image, screenX, screenY, null);
            }

            //Reset alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            //g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }
    }
}
