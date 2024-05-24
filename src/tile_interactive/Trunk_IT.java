package tile_interactive;

import main.GamePanel;

public class Trunk_IT extends InteractiveTile {
    GamePanel gamepanel;

    public Trunk_IT(GamePanel gamepanel, int col, int row) {
        super(gamepanel, col, row);
        this.gamepanel = gamepanel;

        this.worldX = gamepanel.tileSize * col;
        this.worldY = gamepanel.tileSize * row;

        image = setup_entity("/tiles_interactive/trunk", gamepanel.tileSize, gamepanel.tileSize);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
