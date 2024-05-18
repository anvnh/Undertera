package entity;

import main.GamePanel;
import main.KeyboardHandler;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {
    GamePanel gamepanel;
    public int worldX, worldY;
    public int speed;
    public String direction = "down";

    // COUNTER
    public int runCount = 0;
    public int runAnimation = 1;
    public int standCount = 0;
    public int standAnimation = 1;
    public int attackCount = 0;
    public int attackAnimation = 1;
    public int shotAvailableCounter = 0;
    public int manaRegenCounter = 0;


    // ATTACK
    boolean attacking = false;
    public boolean alive = true;
    //Dying
    public boolean dying = false;

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
    public BufferedImage[] attack_right = new BufferedImage[4];
    public BufferedImage[] attack_left = new BufferedImage[4];
    public BufferedImage[] attack_up = new BufferedImage[4];
    public BufferedImage[] attack_down = new BufferedImage[4];

    // Buffered dying
    public BufferedImage[] dying_animate = new BufferedImage[10];


    //Solid area
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean invincible = false;

    // Type
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_armor = 5;
    public final int type_consumable = 6;


    public String objectType = "";

    // Counter
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int dyingAnimation = 1;
    //HP Bar
    boolean HPBarOn = false;
    int HPBarCounter = 0;
    // Dialogue
    String[] dialogue = new String[20];
    int dialogueIndex = 0;


    //

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
    public Entity currentWeapon;
    public Entity currentArmor;
    public Projectile projectile;

    //Item attributes
    public int attackValue;
    public int defenseValue;
    public String description;

    public int useCost;

    // I have no idea what this does
    // Oh okay this is for entity like heart, mana
    public BufferedImage image;
    public BufferedImage image1, image2, image3, image4, image5;
    public String name;

    //Collision
    public boolean collision = false;

    // MAX HP
    public double maxLife;
    public double life;
    //
    KeyboardHandler Key;
    public Entity(GamePanel gamePanel)
    {
        this.gamepanel = gamePanel;
    }

    public void setAction(){}
    public void damageReaction(){}
    public void speak() {
        if(dialogueIndex == 11)
            dialogueIndex = 0;
        gamepanel.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
        if(Objects.equals(gamepanel.player.direction, "up"))
            direction = "down";
        else if(Objects.equals(gamepanel.player.direction, "down"))
            direction = "up";
        else if(Objects.equals(gamepanel.player.direction, "left"))
            direction = "right";
        else if(Objects.equals(gamepanel.player.direction, "right"))
            direction = "left";
    }
    public void use(Entity entity, GamePanel gamepanel){}
    public void update(){

        setAction();

        collisionOn = false;
        gamepanel.collisionCheck.checkTile(this);
        gamepanel.collisionCheck.checkObject(this, false);
        gamepanel.collisionCheck.checkEntity(this, gamepanel.npc);
        gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
        boolean contactPlayer = gamepanel.collisionCheck.checkPlayer(this);

        if(this.type == type_monster && contactPlayer)
        {
            damagePlayer();
        }

        //if collision is false, player can move
        if(!collisionOn)
        {
            switch (direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
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
    public BufferedImage setup_entity(String imgPath)
    {
        UtilityTools utilityTools = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream(imgPath + ".png"))));
            image = utilityTools.scaleImage(image, gamepanel.tileSize, gamepanel.tileSize);
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
    public BufferedImage getAttackAnimate(BufferedImage image, BufferedImage[] attack) {
        image = attack[attackAnimation - 1];
        return image;
    }
    public void setDyingAnimate() {
        for(int i = 0; i < 8; i++)
        {
            dying_animate[i] = setup_entity("/monster/blue_slime/dying/blue_slime_dead" + i);
        }
    }
    public void damagePlayer()
    {
        if(!gamepanel.player.invincible)
        {
            gamepanel.playSoundEffect(4);
            gamepanel.player.life -= ((double) (this.attack * 110) / (110 + gamepanel.player.defense));
            gamepanel.player.invincible = true;
        }
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
    public void changeAlpha(Graphics2D g2, float alpha)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }
    public void draw_object(Graphics2D g2)
    {
        g2.drawImage(image, worldX - gamepanel.player.worldX + gamepanel.player.screenX, worldY - gamepanel.player.worldY + gamepanel.player.screenY, null);
    }
    public void draw_entity(Graphics2D g2)
    {
        BufferedImage image = null;
        int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
        int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;
        if(direction.equals("up"))
        {
            image = getRunAnimate(image, go_up);
        }
        else if(direction.equals("down"))
        {
            image = getRunAnimate(image, go_down);
        }
        else if(direction.equals("left"))
        {
            image = getRunAnimate(image, go_left);
        }
        else if(direction.equals("right"))
        {
            image = getRunAnimate(image, go_right);
        }

        //HP Bar of the monster
        if(type == 2 && HPBarOn)
        {
            double scale = (double) gamepanel.tileSize/ maxLife;
            double HPBar = life * scale;

            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(screenX - 1, screenY - 10, gamepanel.tileSize + 2, 7);

            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX, screenY - 10, Math.max((int)HPBar, 0), 5);

            HPBarCounter++;
            if(HPBarCounter == 600)
            {
                HPBarOn = false;
                HPBarCounter = 0;
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

}
