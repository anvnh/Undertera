package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class FireballObject extends Projectile {
    GamePanel gamepanel;
    public static final String objName = "Fireball";
    public FireballObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = objName;
        speed = 5;
        maxLife = 100;
        life = maxLife;
        attack = 10;
        knockBackPower = 5;
        useCost = 10;
        alive = false;
        getImage();
    }

    public void getImage() {
        go_up[0] = setup_entity("/projectile/fireball/fireball_up_00",70,70);
        go_up[1] = setup_entity("/projectile/fireball/fireball_up_01",70,70);
        go_up[2] = setup_entity("/projectile/fireball/fireball_up_02",70,70);
        go_up[3] = setup_entity("/projectile/fireball/fireball_up_03",70,70);
        go_up[4] = setup_entity("/projectile/fireball/fireball_up_04",70,70);
        go_up[5] = setup_entity("/projectile/fireball/fireball_up_05",70,70);
        go_up[6] = setup_entity("/projectile/fireball/fireball_up_06",70,70);
        go_up[7] = setup_entity("/projectile/fireball/fireball_up_07",70,70);
        go_up[8] = setup_entity("/projectile/fireball/fireball_up_08",70,70);
        go_up[9] = setup_entity("/projectile/fireball/fireball_up_09",70,70);
        go_right[0] = setup_entity("/projectile/fireball/fireball_right_00",70,70);
        go_right[1] = setup_entity("/projectile/fireball/fireball_right_01",70,70);
        go_right[2] = setup_entity("/projectile/fireball/fireball_right_02",70,70);
        go_right[3] = setup_entity("/projectile/fireball/fireball_right_03",70,70);
        go_right[4] = setup_entity("/projectile/fireball/fireball_right_04",70,70);
        go_right[5] = setup_entity("/projectile/fireball/fireball_right_05",70,70);
        go_right[6] = setup_entity("/projectile/fireball/fireball_right_06",70,70);
        go_right[7] = setup_entity("/projectile/fireball/fireball_right_07",70,70);
        go_right[8] = setup_entity("/projectile/fireball/fireball_right_08",70,70);
        go_right[9] = setup_entity("/projectile/fireball/fireball_right_09",70,70);
        go_left[0] = setup_entity("/projectile/fireball/fireball_left_00",70,70);
        go_left[1] = setup_entity("/projectile/fireball/fireball_left_01",70,70);
        go_left[2] = setup_entity("/projectile/fireball/fireball_left_02",70,70);
        go_left[3] = setup_entity("/projectile/fireball/fireball_left_03",70,70);
        go_left[4] = setup_entity("/projectile/fireball/fireball_left_04",70,70);
        go_left[5] = setup_entity("/projectile/fireball/fireball_left_05",70,70);
        go_left[6] = setup_entity("/projectile/fireball/fireball_left_06",70,70);
        go_left[7] = setup_entity("/projectile/fireball/fireball_left_07",70,70);
        go_left[8] = setup_entity("/projectile/fireball/fireball_left_08",70,70);
        go_left[9] = setup_entity("/projectile/fireball/fireball_left_09",70,70);
        go_down[0] = setup_entity("/projectile/fireball/fireball_down_00",70,70);
        go_down[1] = setup_entity("/projectile/fireball/fireball_down_01",70,70);
        go_down[2] = setup_entity("/projectile/fireball/fireball_down_02",70,70);
        go_down[3] = setup_entity("/projectile/fireball/fireball_down_03",70,70);
        go_down[4] = setup_entity("/projectile/fireball/fireball_down_04",70,70);
        go_down[5] = setup_entity("/projectile/fireball/fireball_down_05",70,70);
        go_down[6] = setup_entity("/projectile/fireball/fireball_down_06",70,70);
        go_down[7] = setup_entity("/projectile/fireball/fireball_down_07",70,70);
        go_down[8] = setup_entity("/projectile/fireball/fireball_down_08",70,70);
        go_down[9] = setup_entity("/projectile/fireball/fireball_down_09",70,70);
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
