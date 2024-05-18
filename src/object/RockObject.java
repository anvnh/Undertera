package object;

import entity.Projectile;
import main.GamePanel;

public class RockObject extends Projectile {
    GamePanel gamepanel;
    public RockObject(GamePanel gamepanel) {
        super(gamepanel);
        this.gamepanel = gamepanel;

        name = "Rock";
        speed = 7;
        maxLife = 80;
        life = maxLife;
        attack = 15;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        go_up[0] = setup_entity("/projectile/rock/rock");
        go_up[1] = setup_entity("/projectile/rock/rock");
        go_right[0] = setup_entity("/projectile/rock/rock");
        go_right[1] = setup_entity("/projectile/rock/rock");
        go_down[0] = setup_entity("/projectile/rock/rock");
        go_down[1] = setup_entity("/projectile/rock/rock");
        go_left[0] = setup_entity("/projectile/rock/rock");
        go_left[1] = setup_entity("/projectile/rock/rock");
    }
}
