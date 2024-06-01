package entity;

import main.GamePanel;
import main.KeyboardHandler;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Entity {
    GamePanel gamepanel;
    public int worldX, worldY;
    //========================================= Speed =============================================//
    public int speed;
    public int originalSpeed;
    public int dashSpeed;
    //=============================================================================================//

    //=============================================================================================//
    public boolean temp = false;
    public boolean drawing = true;
    //=============================================================================================//

    //========================================= Counter ===========================================//

    public int runCount = 0;
    public int runAnimation = 1;

    public int standCount = 0;
    public int standAnimation = 1;

    public int attackCount = 0;
    public int attackAnimation = 1;

    public int shieldCount = 0;
    public int shieldAnimation = 1;

    public int offBalanceCounter = 0;

    public int parryCounter = 0;

    public int shotAvailableCounter = 0;
    public int runningSoundCounter = 0;
    public int manaRegenCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int dyingAnimation = 1;
    public int HPBarCounter = 0;
    public int knockBackCounter = 0;


    //============================================================================================//

    //========================================= State ============================================//

    public String direction = "down";
    public boolean attacking = false;
    boolean dashing = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean HPBarOn = false;

    public int dialogueIndex = 0;
    public int dialogueSet = 0;

    public boolean collision = false;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean shielding = false;
    public boolean offBalance = false;

    public Entity loot;
    public boolean opened = false;
        //======================================== For boss==========================================//
        public boolean inRage = false;
        public boolean sleep = false;
        //==========================================================================================//

    //==========================================================================================//

    //=========================== Buffered animation for player ===============================//

    //Buffered image standing
    public BufferedImage[] stand_down = new BufferedImage[6];
    public BufferedImage[] stand_up = new BufferedImage[6];
    public BufferedImage[] stand_left = new BufferedImage[6];
    public BufferedImage[] stand_right = new BufferedImage[6];
    public BufferedImage[] go_down = new BufferedImage[10];
    public BufferedImage[] go_up = new BufferedImage[10];
    public BufferedImage[] go_left = new BufferedImage[10];
    public BufferedImage[] go_right = new BufferedImage[10];

    // Buffered attack
    public BufferedImage[] attack_right = new BufferedImage[10];
    public BufferedImage[] attack_left = new BufferedImage[10];
    public BufferedImage[] attack_up = new BufferedImage[10];
    public BufferedImage[] attack_down = new BufferedImage[10];

    // Buffered shield
    public BufferedImage[] shield_down = new BufferedImage[10];
    public BufferedImage[] shield_up = new BufferedImage[10];
    public BufferedImage[] shield_left = new BufferedImage[10];
    public BufferedImage[] shield_right = new BufferedImage[10];

    //==========================================================================================//


    //============================== Buffered animation for npc =================================//
    public BufferedImage[] npc_stand_down = new BufferedImage[10];
    public BufferedImage[] npc_stand_up = new BufferedImage[10];
    public BufferedImage[] npc_stand_left = new BufferedImage[10];
    public BufferedImage[] npc_stand_right = new BufferedImage[10];
    //==========================================================================================//


    //==========================================================================================//
    public BufferedImage[] dying_animate = new BufferedImage[10];
    //==========================================================================================//


    //===================================== Solid Area ==========================================//
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    //==========================================================================================//


    //================================== Item Attribute ========================================//

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 190;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description;
    public int useCost;
    public int price = 0;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int quantity = 1;
    public int lightRadius;

    //==========================================================================================//

    //====================================== Types =============================================//
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_armor = 5;
    public final int type_consumable = 6;
    public final int type_pickuponly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    //==========================================================================================//

    //==========================================================================================//
    public int getScreenX() {
        return worldX - gamepanel.player.worldX + gamepanel.player.screenX;
    }
    public int getScreenY() {
        return worldY - gamepanel.player.worldY + gamepanel.player.screenY;
    }
    public int getLeftX() {
        return worldX + solidArea.x;
    }
    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY() {
        return worldY + solidArea.y;
    }
    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol() {
        return (worldX + solidArea.x) / gamepanel.tileSize;
    }
    public int getRow() {
        return (worldY + solidArea.y) / gamepanel.tileSize;
    }
    //==========================================================================================//
    public int getCenterX() {
        return worldX + go_left[0].getWidth() / 2;
    }
    public int getCenterY() {
        return worldY + go_up[0].getHeight() / 2;
    }
    public int getXDistance(Entity target) {
        return Math.abs(getCenterX() - target.getCenterX());
    }
    public int getYDistance(Entity target) {
        return Math.abs(getCenterY()- target.getCenterY());
    }
    public int getTileDistance(Entity target) {
        return (int)Math.sqrt(Math.pow(getXDistance(target), 2) + Math.pow(getYDistance(target), 2)) / gamepanel.tileSize;
    }
    public int getGoalCol(Entity target) {
        return (target.worldX + target.solidArea.x) / gamepanel.tileSize;
    }
    public int getGoalRow(Entity target) {
        return (target.worldY + target.solidArea.y) / gamepanel.tileSize;
    }
    //==========================================================================================//

    //==========================================================================================//

    //===================================== For advanced alg ====================================//
    public String objectType = "";
    //==========================================================================================//

    //====================================== Dialogues =========================================//
    public String[][] dialogue = new String[20][20];
    //==========================================================================================//

    //==========================================================================================//
    public Entity attacker;
    //==========================================================================================//

    //================================ Player attributes =====================================//
    public int maxMana;
    public int mana;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public double maxLife;
    public double life;

    public int motion_duration_1;
    public int motion_duration_2;

    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;

    public boolean boss;

    //==========================================================================================//


    //================================== For object image =======================================//
    //================================ which don't have much ====================================//
    public BufferedImage image;
    public BufferedImage image1, image2, image3, image4, image5;
    public String name = "";
    //===========================================================================================//

    //Collision

    //===========================================================================================//
    KeyboardHandler Key;
    //===========================================================================================//
    public Entity(GamePanel gamePanel)
    {
        this.gamepanel = gamePanel;
    }

    public void setLoot(Entity loot){}
    public void setAction(){}
    public void damageReaction(){}
    public void speak() {
        // Check if the dialogue is at the end
        /*
        long nonNullCount = Arrays.stream(dialogue).filter(Objects::nonNull).count();
        if(dialogueIndex == nonNullCount) dialogueIndex = 0;
        gamepanel.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
         */
    }
    public void startDialogue(Entity entity, int setNum){
        gamepanel.gameState = gamepanel.dialogueState;
        gamepanel.ui.npc = entity;
        dialogueSet = setNum;
        gamepanel.Key.enterPressed = false;
    }
    public void facePlayer() {
        if(Objects.equals(gamepanel.player.direction, "up"))
            direction = "down";
        else if(Objects.equals(gamepanel.player.direction, "down"))
            direction = "up";
        else if(Objects.equals(gamepanel.player.direction, "left"))
            direction = "right";
        else if(Objects.equals(gamepanel.player.direction, "right"))
            direction = "left";
    }
    public void interact() { }
    public boolean use(Entity entity, GamePanel gamepanel){
        return false;
    }
    public void checkDrop(){}
    public void dropItem(Entity drpItem){ // item dropped from killing monster
        for(int i = 0; i < gamepanel.objects[1].length; i++)
        {
            if(gamepanel.objects[gamepanel.currentMap][i] == null)
            {
                gamepanel.objects[gamepanel.currentMap][i] = drpItem;
                gamepanel.objects[gamepanel.currentMap][i].worldX = worldX;
                gamepanel.objects[gamepanel.currentMap][i].worldY = worldY;
                // Check if the dropped item is on the same tile
                // If is in the same tile, we move it a little bit, in this case, is 15 pixel
                break;
            }
        }
    }
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gamepanel, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gamepanel, target , color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gamepanel, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gamepanel, target, color, size, speed, maxLife, 2, 1);
        gamepanel.particleList.add(p1);
        gamepanel.particleList.add(p2);
        gamepanel.particleList.add(p3);
        gamepanel.particleList.add(p4);
    }
    public void checkCollision()
    {
        collisionOn = false;
        gamepanel.collisionCheck.checkTile(this);
        gamepanel.collisionCheck.checkObject(this, false);
        gamepanel.collisionCheck.checkEntity(this, gamepanel.npc);
        gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
        gamepanel.collisionCheck.checkEntity(this, gamepanel.interactiveTile);
        boolean contactPlayer = gamepanel.collisionCheck.checkPlayer(this);

        if(this.type == type_monster && contactPlayer)
        {
            damagePlayer();
        }
    }

    public void update(){
        if(!sleep)
        {
            if(knockBack)
            {
                checkCollision();
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
            else if(attacking) {
                if(type == type_monster) {
                    attack_at_last_animation();
                }
                else {
                    attack();
                }
            }
            else
            {
                setAction();
                checkCollision();

                //if collision is false, player can move
                if(!collisionOn)
                {
                    switch (direction){
                        case "up": worldY -= speed; break;
                        case "down": worldY += speed; break;
                        case "left": worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }
            }

            runCount++;
            if (runCount > 15) {
                runAnimation = runAnimation == 8 ? 1 : runAnimation + 1;
                runCount = 0;
            }


            if(invincible)
            {
                invincibleCounter++;
                if(invincibleCounter == 30) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }

            if(shotAvailableCounter < 60)
            {
                shotAvailableCounter++;
            }

            if(offBalance)
            {
                offBalanceCounter++;
                if(offBalanceCounter == 120)
                {
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }
    }
    public BufferedImage setup_player(String imgName)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imgName + ".png")));
            scaledImage = utilityTools.scaleImage(scaledImage, gamepanel.playerSize, gamepanel.playerSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
    public BufferedImage setup_entity(String imgPath, int width, int height)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream(imgPath + ".png"))));
            image = utilityTools.scaleImage(image, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public BufferedImage setup_weap(String imgPath)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream(imgPath + ".png"))));
            image = utilityTools.scaleImage(image, gamepanel.weapSize, gamepanel.weapSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public BufferedImage getStandAnimate(BufferedImage image, BufferedImage[] stand)
    {
        image = stand[standAnimation - 1];
        return image;
    }
    public BufferedImage getRunAnimate(BufferedImage image, BufferedImage[] run) {
        if(run[runAnimation - 1] != null)
        {
            image = run[runAnimation - 1];
        }
        else {
            image = run[0];
        }
        return image;
    }
    public BufferedImage getAttackAnimate(BufferedImage image, BufferedImage[] attack)
    {
        image = attack[attackAnimation - 1];
        return image;
    }
    public BufferedImage getShieldAnimate(BufferedImage image, BufferedImage[] shield)
    {
        if(shield[shieldAnimation - 1] != null)
        {
            image = shield[shieldAnimation - 1];
        }
        else
        {
            image = shield[0];
            shielding = false;
        }
        return image;
    }
    public void setDyingAnimate() {
        for(int i = 0; i < 8; i++)
        {
            dying_animate[i] = setup_entity("/monster/blue_slime/dying/blue_slime_dead" + i, gamepanel.tileSize, gamepanel.tileSize);
        }
    }
    //================================== Monster ===============================================//
    public void checkAttackAggro(int rate, int vertical, int horizontal)
    {
        boolean targetInRange = false;
        int xDistance = getXDistance(gamepanel.player);
        int yDistance = getYDistance(gamepanel.player);

        switch (direction)
        {
            case "up":
                if(gamepanel.player.getCenterY() < getCenterY() && yDistance < vertical && xDistance < horizontal)
                {
                    targetInRange = true;
                }
                break;
            case "down":
                if(gamepanel.player.getCenterY() > getCenterY() && yDistance < vertical && xDistance < horizontal)
                {
                    targetInRange = true;
                }
                break;
            case "left":
                if(gamepanel.player.getCenterX() < getCenterX() && xDistance < vertical && yDistance < horizontal)
                {
                    targetInRange = true;
                }
                break;
            case "right":
                if(gamepanel.player.getCenterX() > getCenterX() && xDistance < vertical && yDistance < horizontal)
                {
                    targetInRange = true;
                }
                break;
        }
        if(targetInRange)
        {
            attacking = true;
            attackCount = 0;
            attackAnimation = 1;
        }
    }
    public void checkShootingAggro(int rate, int shotInterval)
    {
        int i = new Random().nextInt(rate) + 1;
        if(i > (int)(rate / 2) && !projectile.alive && shotAvailableCounter == shotInterval){
            projectile.set(worldX, worldY, direction, true, this);
            for(int j = 0; j < gamepanel.projectile[1].length; j++)
            {
                if(gamepanel.projectile[gamepanel.currentMap][j] == null)
                {
                    gamepanel.projectile[gamepanel.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public void checkStartAggro(Entity target, int distance, int rate)
    {
        if(getTileDistance(target) < distance)
        {
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = true;
            }
        }
    }
    public void checkDropAggro(Entity target, int distance, int rate)
    {
        if(getTileDistance(target) > distance)
        {
            int i = new Random().nextInt(rate);
            if(i == 0)
            {
                onPath = false;
            }
        }
    }
    public void getRandomDirection(int interval)
    {
        actionLockCounter ++;
        if(actionLockCounter == interval)
        {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if(i <= 25) direction = "left";
            else if(i <= 50) direction = "up";
            else if(i <= 75) direction = "right";
            else direction = "down";
            actionLockCounter = 0;
        }
    }
    //=========================================================================================//

    //=========================================================================================//
    public void attack_at_last_animation()
    {
        attackCount++;
        if(attackCount <= motion_duration_1) {
            attackAnimation = 1;
        }
        if(attackCount > motion_duration_1 && attackCount <= motion_duration_2) {
            attackAnimation = 2;
        }
        if(attackCount > motion_duration_2 + 15 && attackCount <= motion_duration_2 + 30) {
            attackAnimation = 3;
        }
        if(attackCount > motion_duration_2 + 30 && attackCount <= motion_duration_2 + 50) {
            attackAnimation = 4;
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

            if(type == type_monster)
            {
                if(gamepanel.collisionCheck.checkPlayer(this))
                {
                    damagePlayer();
                }
            }
            else
            {   // Player
                // Check monster collision
                int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
                gamepanel.player.damageMonster(monsterIndex, this, currentWeapon.knockBackPower);

                // Check interactive collision
                int interactiveIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.interactiveTile);
                gamepanel.player.damageInteractiveTile(interactiveIndex);

                // Check projectile collision
                int projectileIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.projectile);
                gamepanel.player.damageProjectile(projectileIndex);
            }

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(attackCount > motion_duration_2 + 50) {
            // Reset attack animation
            attackCount = 0;
            attackAnimation = 1;
            attacking = false;
        }
    }
    public void moveTowardPlayer(int interval)
    {
        actionLockCounter++;
        if(actionLockCounter > interval)
        {
            int xDistance = getXDistance(gamepanel.player);
            int yDistance = getYDistance(gamepanel.player);
            if(xDistance > yDistance)
            {
                if(gamepanel.player.getCenterX() < getCenterX())
                {
                    direction = "left";
                }
                else
                {
                    direction = "right";
                }
            }
            else if(xDistance < yDistance)
            {
                if(gamepanel.player.getCenterY() < getCenterY())
                {
                    direction = "up";
                }
                else
                {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }
    public String getOppositeDirection(String direction)
    {
        return switch (direction) {
            case "up" -> "down";
            case "down" -> "up";
            case "left" -> "right";
            case "right" -> "left";
            default -> "";
        };
    }
    public void attack()
    {
        attackCount++;
        if(attackCount <= motion_duration_1) {
            attackAnimation = 1;
        }
        if(attackCount > motion_duration_1 && attackCount <= motion_duration_2) {
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

            if(type == type_monster)
            {
                if(gamepanel.collisionCheck.checkPlayer(this))
                {
                    damagePlayer();
                }
            }
            else
            {   // Player
                // Check monster collision
                int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
                gamepanel.player.damageMonster(monsterIndex, this, currentWeapon.knockBackPower);

                // Check interactive collision
                int interactiveIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.interactiveTile);
                gamepanel.player.damageInteractiveTile(interactiveIndex);

                // Check projectile collision
                int projectileIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.projectile);
                gamepanel.player.damageProjectile(projectileIndex);
            }

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(attackCount > motion_duration_2 + 15 && attackCount <= motion_duration_2 + 30) attackAnimation = 3;
        if(attackCount > motion_duration_2 + 30 && attackCount <= motion_duration_2 + 50) attackAnimation = 4;
        if(attackCount > motion_duration_2 + 50)
        {
            attackCount = 0;
            attackAnimation = 1;
            attacking = false;
        }
    }
    //=========================================================================================//

    public void damagePlayer()
    {
        if(!gamepanel.player.invincible)
        {
            double damage = ((double) (this.attack * 110) / (110 + gamepanel.player.defense));
            // Get opposite direction of this attacker (specifically for monster)
            String oppositeDirection = getOppositeDirection(direction);
            // If player is facing the opposite direction of the monster, player will receive less damage
            if(gamepanel.player.shielding &&gamepanel.player.direction.equals(oppositeDirection))
            {
                // If player can parry the attack within 10 frames before the attack, player will receive less damage: 75%
                // Or else player just bring the shield on, damage will only be reduced by 25%
                if(gamepanel.player.parryCounter < 10)
                {
                    gamepanel.playSoundEffect(19);
                    damage *= 0.25; // Damage will be reduced by 75%
                }
                else
                {
                    gamepanel.playSoundEffect(18);
                    damage *= 0.75;  // Damage will be reduced by 25%
                }
            }
            else {
                gamepanel.playSoundEffect(4);
                setKnockBack(gamepanel.player, this, knockBackPower);
            }
            gamepanel.player.life -= damage;
            gamepanel.player.invincible = true;
        }
    }
    public void setKnockBack(Entity target, Entity attacker, int knockBackPower)
    {
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    public void drawDying(Graphics2D g2)
    {
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i) changeAlpha(g2, 0f);
        if(dyingCounter > i && dyingCounter <= i * 2) changeAlpha(g2, 1f);
        if(dyingCounter > i * 2 && dyingCounter <= i * 3) changeAlpha(g2, 0f);
        if(dyingCounter > i * 3 && dyingCounter <= i * 4) changeAlpha(g2, 1f);
        if(dyingCounter > i * 4 && dyingCounter <= i * 5) changeAlpha(g2, 0f);
        if(dyingCounter > i * 5 && dyingCounter <= i * 6) changeAlpha(g2, 1f);
        if(dyingCounter > i * 6 && dyingCounter <= i * 7) changeAlpha(g2, 0f);
        if(dyingCounter > i * 7 && dyingCounter <= i * 8) changeAlpha(g2, 1f);
        if(dyingCounter > i * 8)
        {
            //dying = false;
            dyingCounter = 0;
            alive = false;
        }
    }
    public void draw(Graphics2D g2){}
    public void changeAlpha(Graphics2D g2, float alpha)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }
    public void draw_object(Graphics2D g2)
    {
        g2.drawImage(image, worldX - gamepanel.player.worldX + gamepanel.player.screenX, worldY - gamepanel.player.worldY + gamepanel.player.screenY, null);
    }
    public boolean isInCamera()
    {
        boolean ok = false;
        if(
                worldX + solidArea.x + solidArea.width > gamepanel.player.worldX - gamepanel.player.screenX
                && worldX + solidArea.x < gamepanel.player.worldX - gamepanel.player.screenX + gamepanel.screenWidth
                && worldY + solidArea.y + solidArea.height > gamepanel.player.worldY - gamepanel.player.screenY
                && worldY + solidArea.y < gamepanel.player.worldY - gamepanel.player.screenY + gamepanel.screenHeight
        )
        {
            ok = true;
        }
        return ok;
    }
    public void draw_entity(Graphics2D g2)
    {
        BufferedImage image = null;
        int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
        int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;
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




        if(invincible) {
            HPBarOn = true;
            HPBarCounter = 0;
            changeAlpha(g2, 0.4f);
        }

        if(dying) {
            drawDying(g2);
        }

        g2.drawImage(image, screenX, screenY, null);
        changeAlpha(g2, 1f);
    }
    public void searchPath(int endCol, int endRow)
    {
        int startCol = (worldX + solidArea.x) / gamepanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamepanel.tileSize;

        gamepanel.pathFinder.setNodes(startCol, startRow, endCol, endRow, this);

        if(gamepanel.pathFinder.search())
        {
            // Next worldX and worldY
            int nextX = gamepanel.pathFinder.pathList.get(0).col * gamepanel.tileSize;
            int nextY = gamepanel.pathFinder.pathList.get(0).row * gamepanel.tileSize;

            // Entity solid area
            int entityLeftX = worldX + solidArea.x;
            int entityRightX = worldX + solidArea.x + solidArea.width;
            int entityTopY = worldY + solidArea.y;
            int entityBottomY = worldY + solidArea.y + solidArea.height;

            if(entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gamepanel.tileSize)
            {
                direction = "up";
            }
            else if(entityBottomY < nextY && entityLeftX >= nextX && entityRightX < nextX + gamepanel.tileSize)
            {
                direction = "down";
            }
            else if(entityTopY >= nextY && entityBottomY < nextY + gamepanel.tileSize)
            {
                //left or right
                if(entityLeftX > nextX) {
                    direction = "left";
                }
                else if(entityLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(entityTopY > nextY && entityLeftX > nextX)
            {
                //up or left
                direction = "up";
                checkCollision();
                if(collisionOn)
                {
                    direction = "left";
                }
            }
            else if(entityTopY > nextY && entityLeftX < nextX)
            {
                // up or right
                direction = "up";
                checkCollision();
                if(collisionOn)
                {
                    direction = "right";
                }
            }
            else if(entityTopY < nextY && entityLeftX < nextX)
            {
                // down or right
                direction = "down";
                checkCollision();
                if(collisionOn)
                {
                    direction = "right";
                }
            }
            else if(entityTopY < nextY && entityLeftX > nextX)
            {
                // down or left
                direction = "down";
                checkCollision();
                if(collisionOn)
                {
                    direction = "left";
                }
            }


            // If reaches the end, stop the path
            // If make the entity to keep follow the end, disable 5 lines below
            /*
            int nextCol = gamepanel.pathFinder.pathList.get(0).col;
            int nextRow = gamepanel.pathFinder.pathList.get(0).row;
            if(nextCol == endCol && nextRow == endRow)
            {
                onPath = false;
            }
             */
        }
    }
    public int getDetected(Entity entity, Entity[][] target, String targetName)
    {
        int index = 999;

        // check surrounding object
        int nextWorldX = entity.getLeftX();
        int nextWorldY = entity.getTopY();
        switch (entity.direction)
        {
            case "up": nextWorldY = entity.getTopY() - gamepanel.player.speed; break;
            case "down": nextWorldY = entity.getBottomY() + gamepanel.player.speed; break;
            case "left": nextWorldX = entity.getLeftX() - gamepanel.player.speed; break;
            case "right": nextWorldX = entity.getRightX() + gamepanel.player.speed; break;
        }
        int col = nextWorldX / gamepanel.tileSize;
        int row = nextWorldY / gamepanel.tileSize;

        for(int i = 0; i < target[1].length; i++)
        {
            if(target[gamepanel.currentMap][i] != null)
            {
                if(target[gamepanel.currentMap][i].getCol() == col
                && target[gamepanel.currentMap][i].getRow() == row
                && target[gamepanel.currentMap][i].name.equals(targetName) )
                {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}


