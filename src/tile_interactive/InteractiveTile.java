package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gamepanel;
    public boolean destructible = false;
    public InteractiveTile(GamePanel gamepanel, int col, int row) {
        super(gamepanel);
        this.gamepanel = gamepanel;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        return isCorrectItem;
    }
    public void playSE(){}
    public InteractiveTile getDestroyedForm() {
        InteractiveTile Itile = null;
        return Itile;
    }
    public void update() {
        if(invincible)
        {
            invincibleCounter++;
            if(invincibleCounter == 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
}
