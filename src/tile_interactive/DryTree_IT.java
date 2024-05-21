package tile_interactive;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class DryTree_IT extends InteractiveTile {
    GamePanel gamepanel;

    public DryTree_IT(GamePanel gamepanel, int col, int row) {
        super(gamepanel, col, row);
        this.gamepanel = gamepanel;

        this.worldX = gamepanel.tileSize * col;
        this.worldY = gamepanel.tileSize * row;

        image = setup_entity("/tiles_interactive/drytree");
        destructible = true;
        life = 5;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;

        if(entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }

        return isCorrectItem;
    }
    public void playSE(){
        gamepanel.playSoundEffect(8);
    }
    public InteractiveTile getDestroyedForm() {
        InteractiveTile Itile = new Trunk_IT(gamepanel, worldX / gamepanel.tileSize, worldY / gamepanel.tileSize);
        return Itile;
    }
}
