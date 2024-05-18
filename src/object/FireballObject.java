package object;

import entity.Projectile;
import main.GamePanel;

public class FireballObject extends Projectile {
    GamePanel gamepanel;
    public FireballObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 15;
        useCost = 1;
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
}
