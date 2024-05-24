package tile_interactive;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.charset.CoderResult;
import java.util.Objects;

public class DryTree_IT extends InteractiveTile {
    GamePanel gamepanel;

    public DryTree_IT(GamePanel gamepanel, int col, int row) {
        super(gamepanel, col, row);
        this.gamepanel = gamepanel;

        this.worldX = gamepanel.tileSize * col;
        this.worldY = gamepanel.tileSize * row;

        image = setup_entity("/tiles_interactive/drytree", gamepanel.tileSize, gamepanel.tileSize);
        destructible = true;
        life = 2;
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
    public Color getParticleColor() {
        Color color = new Color(65,50,30);
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
