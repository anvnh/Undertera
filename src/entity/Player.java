package entity;
import main.GamePanel;
import main.KeyboardHandler;
import main.UtilityTools;
import object.ArmorObject;
import object.FireballObject;
import object.KeyObject;
import object.SwordObject;

import javax.imageio.ImageIO;
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
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 144;

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
        solidArea.height = 25;
        name = "player";

        /*
        //Attack area of player
        attackArea.width = 45;
        attackArea.height = 45;
         */

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues(){
        worldX = gamepanel.tileSize * 22;
        worldY = gamepanel.tileSize * 20;
        speed = 3;
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
        coin = 0;
        currentWeapon = new SwordObject(gamepanel);
        currentArmor = new ArmorObject(gamepanel);
        projectile = new FireballObject(gamepanel);
        attack = getAttack(); // total attack
        defense = getDefense(); // total defense
    }
    public void setItems(){
        inventory.add(currentWeapon);
        inventory.add(currentArmor);
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense() {
        return defense = dexterity * currentArmor.defenseValue;
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
    public void getPlayerAttackImage()
    {
        if(currentWeapon.type == type_sword)
        {
            for(int i = 0; i < 4; i++)
            {
                attack_down[i] = setup_player("/player/attack/sword/attack_down_" + (i));
            }
            for(int i = 0; i < 4; i++)
            {
                attack_up[i] = setup_player("/player/attack/sword/attack_up_" + (i));
            }
            for(int i = 0; i < 4; i++)
            {
                attack_right[i] = setup_player("/player/attack/sword/attack_right_" + (i));
            }
            for(int i = 0; i < 4; i++)
            {
                attack_left[i] = setup_player("/player/attack/sword/attack_left_" + (i));
            }
        }
        if(currentWeapon.type == type_axe)
        {
            for(int i = 0; i < 4; i++)
            {
                attack_down[i] = setup_player("/player/attack/axe/attack_down_axe_" + (i));
            }
            for(int i = 0; i < 4; i++)
            {
                attack_up[i] = setup_player("/player/attack/axe/attack_up_axe_" + (i));
            }
            for(int i = 0; i < 4; i++)
            {
                attack_right[i] = setup_player("/player/attack/axe/attack_right_axe_" + (i));
            }
            for(int i = 0; i < 4; i++)
            {
                attack_left[i] = setup_player("/player/attack/axe/attack_left_axe_" + (i));
            }

        }
    }
    public void update()
    {
        if(attacking)
        {
            attack();
            //System.out.println(attackAvailableCounter);
        }
        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed || Key.communicateWithNPC)
        {
            if (Key.upPressed) {
                direction = "up";
                //worldY -= speed;
            } else if (Key.downPressed) {
                direction = "down";
                //worldY += speed;
            } else if (Key.leftPressed) {
                direction = "left";
                //worldX -= speed;
            } else if (Key.rightPressed){
                direction = "right";
                //worldX += speed;
            }

            collisionOn = false;

            // Check object collision
            int objectIndex = gamepanel.collisionCheck.checkObject(this, true);
            pickUpObject(objectIndex);

            // Check NPC Collision
            int npcIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.npc);
            interactNPC(npcIndex);

            // Check Monster's Collision
            int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
            contactMonster(monsterIndex);

            // Check Interactive Tile Collision
            gamepanel.collisionCheck.checkEntity(this, gamepanel.interactiveTile);

            //Attack
            check_attack();

            // Check event
            gamepanel.eventHandler.checkEvent();

            //Check tile collision
            gamepanel.collisionCheck.checkTile(this);

            // Check attacking
            if(gamepanel.Key.J_Pressed)
            {
                attacking = true;
            }


            //if collision is false, player can move
            if(!collisionOn && !Key.communicateWithNPC)
            {
                switch (direction)
                {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            gamepanel.Key.communicateWithNPC = false;

            runCount++;
            if (runCount > 15) {
                runAnimation = runAnimation == 6 ? 1 : runAnimation + 1;
                runCount = 0;
            }
        }
        else {
            standCount++;
            if (standCount > 15) {
                standAnimation = standAnimation == 6 ? 1 : standAnimation + 1;
                standCount = 0;
            }
        }

        // Shooting projectile
        if(gamepanel.Key.Projectile_Pressed && !projectile.alive && shotAvailableCounter == 60
                && projectile.haveEnoughMana(this)
        ) {
            // Set default coordinate, direction and user
            projectile.set(worldX, worldY, direction, true, this);

            // Use mana
            projectile.useMana(this);

            //Add to the list
            gamepanel.projectileList.add(projectile);

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
        }
        //if(life > maxLife) life = maxLife;
        //if(mana > maxMana) mana = maxMana;
        life = Math.min(life, maxLife);
        mana = Math.min(mana, maxMana);
    }
    public void regenerateMana(){
        mana += 5;
    }
    public void check_attack()
    {
        if(gamepanel.Key.J_Pressed)
        {
            attacking = true;
            gamepanel.Key.J_Pressed = false;
            gamepanel.playSoundEffect(1);
        }
    }
    public double calculateDamageDeal(Entity entity)
    {
        attack = getAttack();
        return attack * ((double) 110 / (110 + entity.defense));
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
    public void attack()
    {
        attackCount++;
        if(attackCount <= 5) {
            attackAnimation = 1;
        }
        if(attackCount > 5 && attackCount <= 25) {
            attackAnimation = 2;

            //Current world x, world y, solid area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            if(direction.equals("up"))
            {
                worldY -= attackArea.height;
            }
            if(direction.equals("down"))
            {
                worldY += attackArea.height;
            }
            if(direction.equals("left"))
            {
                worldX -= attackArea.width;
            }
            if(direction.equals("right"))
            {
                worldX += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check monster collision
            int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
            damageMonster(monsterIndex);

            // Check interactive collision
            int interactiveIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.interactiveTile);
            damageInteractiveTile(interactiveIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(attackCount > 25 && attackCount <= 45) attackAnimation = 3;
        if(attackCount > 45 && attackCount <= 65) attackAnimation = 4;
        if(attackCount > 65)
        {
            attackCount = 0;
            attackAnimation = 1;
            attacking = false;
        }
    }

    public void pickUpObject(int objectIndex) {
        if(objectIndex != 999)
        {
            // Pick up only items
            if(gamepanel.objects[objectIndex].type == type_pickuponly)
            {
                gamepanel.objects[objectIndex].use(this, gamepanel);
                gamepanel.objects[objectIndex] = null;
            }
            else {
                String text;
                if(inventory.size() != maxInventorySize)
                {
                    inventory.add(gamepanel.objects[objectIndex]);
                    gamepanel.playSoundEffect(7);
                    text = "Picked up " + gamepanel.objects[objectIndex].name;
                    gamepanel.ui.addMessage(text);
                    gamepanel.objects[objectIndex] = null;
                }
            }
        }
    }

    public void interactNPC(int npcIndex) {
        if(npcIndex != 999)
        {
            if(gamepanel.Key.communicateWithNPC)
            {
                gamepanel.gameState = gamepanel.dialogueState;
                gamepanel.npc[npcIndex].speak();
                //gamepanel.Key.communicateWithNPC = false;
            }
        }
    }

    public void contactMonster(int monsterIndex) {
        if(monsterIndex != 999)
        {
            if(!invincible && !gamepanel.monster[monsterIndex].dying)
            {
                life -= calculateDamageReceive(gamepanel.monster[monsterIndex]);
                invincible = true;
                gamepanel.playSoundEffect(4);
            }
        }
    }
    public void damageInteractiveTile(int interactiveIndex){
        if(interactiveIndex != 999
                && gamepanel.interactiveTile[interactiveIndex].destructible
                && gamepanel.interactiveTile[interactiveIndex].isCorrectItem(this)
                && !gamepanel.interactiveTile[interactiveIndex].invincible
        )
        {
            gamepanel.interactiveTile[interactiveIndex].playSE();
            gamepanel.interactiveTile[interactiveIndex].life--;
            gamepanel.interactiveTile[interactiveIndex].invincible = true;

            if(gamepanel.interactiveTile[interactiveIndex].life == 0){
                gamepanel.interactiveTile[interactiveIndex] = gamepanel.interactiveTile[interactiveIndex].getDestroyedForm();
            }
        }
    }
    public void damageMonster(int monsterIndex)
    {
        if(monsterIndex != 999)
        {
            if(!gamepanel.monster[monsterIndex].invincible)
            {
                gamepanel.playSoundEffect(3);


                gamepanel.monster[monsterIndex].life -= calculateDamageDeal(gamepanel.monster[monsterIndex]);
                gamepanel.ui.addMessage(calculateDamageDeal(gamepanel.monster[monsterIndex]) + " damage");
                //System.out.println(gamepanel.monster[monsterIndex].life);

                gamepanel.monster[monsterIndex].invincible = true;
                gamepanel.monster[monsterIndex].damageReaction();


                if(gamepanel.monster[monsterIndex].life <= 0)
                {
                    gamepanel.monster[monsterIndex].dying = true;
                    gamepanel.ui.addMessage("Killed " + gamepanel.monster[monsterIndex].name);
                    gamepanel.ui.addMessage("Received " + gamepanel.monster[monsterIndex].exp + " exp");
                    exp += gamepanel.monster[monsterIndex].exp;
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
            if(!gamepanel.monster[monsterIndex].invincible)
            {
                gamepanel.playSoundEffect(3);


                gamepanel.monster[monsterIndex].life -= calculateDamageDealwithProjectile(gamepanel.monster[monsterIndex], Attack);
                gamepanel.ui.addMessage(calculateDamageDealwithProjectile(gamepanel.monster[monsterIndex], Attack) + " damage");
                //System.out.println(gamepanel.monster[monsterIndex].life);

                gamepanel.monster[monsterIndex].invincible = true;
                gamepanel.monster[monsterIndex].damageReaction();


                if(gamepanel.monster[monsterIndex].life <= 0)
                {
                    gamepanel.monster[monsterIndex].dying = true;
                    gamepanel.ui.addMessage("Killed " + gamepanel.monster[monsterIndex].name);
                    gamepanel.ui.addMessage("Received " + gamepanel.monster[monsterIndex].exp + " exp");
                    exp += gamepanel.monster[monsterIndex].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void checkLevelUp()
    {
        if(exp >= nextLevelExp)
        {
            gamepanel.playSoundEffect(5);
            level++;
            exp = 0;
            nextLevelExp = (int) (nextLevelExp * 1.5);
            //maxLife += 10;
            life = maxLife;
            mana = maxMana;
            strength += 2;
            dexterity += 2;
            gamepanel.ui.addMessage("Level Up!");
        }
    }

    public void selectItem(){
        int itemIndex = gamepanel.ui.getItemIndexOnSlot();

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
                currentArmor = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable)
            {
                selectedItem.use(this, gamepanel);
                inventory.remove(itemIndex);
            }
        }
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
                    image = getStandAnimate(image, stand_down);
                }
                else {
                    image = getAttackAnimate(image, attack_down);
                }
            }
            else if(direction.equals("up"))
            {
                if(!attacking)
                {
                    image = getStandAnimate(image, stand_up);
                }
                else {
                    image = getAttackAnimate(image, attack_up);
                }
            }
            else if(direction.equals("left"))
            {
                if(!attacking)
                {
                    image = getStandAnimate(image, stand_left);
                }
                else {
                    image = getAttackAnimate(image, attack_left);
                }
            }
            else if(direction.equals("right"))
            {
                if(!attacking)
                {
                    image = getStandAnimate(image, stand_right);
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
            g2.drawImage(image, screenX, screenY, null);

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
                    image = getRunAnimate(image, go_up);
                }
                else {
                    image = getAttackAnimate(image, attack_up);
                }
            }
            else if(direction.equals("down"))
            {
                if(!attacking)
                {
                    image = getRunAnimate(image, go_down);
                }
                else {
                    image = getAttackAnimate(image, attack_down);
                }
            }
            else if(direction.equals("left"))
            {
                if(!attacking)
                {
                    image = getRunAnimate(image, go_left);
                }
                else {
                    image = getAttackAnimate(image, attack_left);
                }
            }
            else if(direction.equals("right"))
            {
                if(!attacking)
                {
                    image = getRunAnimate(image, go_right);
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
            g2.drawImage(image, screenX, screenY, null);

            //Reset alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            //g2.drawImage(image, screenX, screenY, gamepanel.playerSize, gamepanel.playerSize, null);
        }
    }
}
