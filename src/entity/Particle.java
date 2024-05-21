package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gamepanel, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gamepanel);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        worldX = generator.worldX;
        worldY = generator.worldY;

        objectType = "particle";

        int offset = (gamepanel.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    public void update() {
        life --;

        if(life < maxLife / 2) {
            yd++;
        }

        worldX += xd * speed;
        worldY += yd * speed;

        if(life == 0) {
            alive = false;
        }
    }
    public void draw(Graphics2D g2) {
        int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
        int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;

        g2.setColor(color);
        g2.fillOval(screenX, screenY, size, size);
    }
}
