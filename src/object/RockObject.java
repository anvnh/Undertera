package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class RockObject extends Projectile {
    GamePanel gamepanel;
    public static final String objName = "Rock";
    public RockObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = objName;
        speed = 4;
        maxLife = 120;
        life = maxLife;
        attack = 15;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        go_up[0] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
        go_up[1] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
        go_right[0] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
        go_right[1] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
        go_down[0] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
        go_down[1] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
        go_left[0] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
        go_left[1] = setup_entity("/projectile/rock/rock", gamepanel.tileSize, gamepanel.tileSize);
    }

    public Color getParticleColor() {
        Color color = new Color(58, 48, 36);
        return color;
    }
    public int getParticleSize() {
        int size = 6;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
