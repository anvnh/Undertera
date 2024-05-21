package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class FireballObject extends Projectile {
    GamePanel gamepanel;
    public FireballObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = "Fireball";
        speed = 5;
        maxLife = 100;
        life = maxLife;
        attack = 15;
        useCost = 10;
        alive = false;
        getImage();
    }

    public void getImage() {
        go_up[0] = setup_entity("/projectile/fireball/fireball_up_1");
        go_up[1] = setup_entity("/projectile/fireball/fireball_up_2");
        go_right[0] = setup_entity("/projectile/fireball/fireball_right_1");
        go_right[1] = setup_entity("/projectile/fireball/fireball_right_2");
        go_down[0] = setup_entity("/projectile/fireball/fireball_down_1");
        go_down[1] = setup_entity("/projectile/fireball/fireball_down_2");
        go_left[0] = setup_entity("/projectile/fireball/fireball_left_1");
        go_left[1] = setup_entity("/projectile/fireball/fireball_left_2");
    }

    public boolean haveEnoughMana(Entity user) {
        return user.mana >= useCost;
    }
    public void useMana(Entity user) {
        user.mana -= useCost;
    }
    public Color getParticleColor() {
        Color color = new Color(217, 64, 18);
        return color;
    }
    public int getParticleSize() {
        int size = 10;
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
