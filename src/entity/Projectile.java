package entity;

import main.GamePanel;

public class Projectile extends Entity{
    Entity user;
    public Projectile(GamePanel gamepanel) {
        super(gamepanel);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
        this.solidArea.x = 0;
        this.solidArea.y = 0;
    }
    public void update() {
        if(user == gamepanel.player)
        {
            int monsterIndex = gamepanel.collisionCheck.checkEntity(this, gamepanel.monster);
            if(monsterIndex != 999)
            {
                gamepanel.player.damageProjectileMonster(monsterIndex, attack);
                generateParticle(user.projectile, gamepanel.monster[gamepanel.currentMap][monsterIndex]);
                alive = false;
            }
        }
        if(user != gamepanel.player)
        {
            boolean contactPlayer = gamepanel.collisionCheck.checkPlayer(this);
            if(!gamepanel.player.invincible && contactPlayer)
            {
                damagePlayer();
                generateParticle(user.projectile, gamepanel.player);
                alive = false;
            }
        }
        switch (direction) {
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

        life--;
        if(life <= 0) {
            alive = false;
        }
        runCount++;
        if (runCount > 15) {
            runAnimation = runAnimation == 10 ? 1 : runAnimation + 1;
            runCount = 0;
        }
    }
    public boolean haveEnoughMana(Entity user) {
        return user.mana >= useCost;
    }
    public void useMana(Entity user) {
        user.mana -= useCost;
    }
}
