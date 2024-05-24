package tile;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TileManager {
    GamePanel gamepanel;
    public Tile[] tile;
    public int[][][] mapTileNum;
    public TileManager(GamePanel gp){
        this.gamepanel = gp;
        tile = new Tile[50];
        mapTileNum = new int[gamepanel.maxMap][50][50];
        getTileImage();
        loadmap("/maps/worldV4.txt", 0);
        loadmap("/maps/interior01.txt", 1);
    }

    public void getTileImage() {
        //placeholder
        setup(0, "blank", true);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        //

        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
        setup(42, "hut", false);
        setup(43, "floor01", false);
        setup(44, "table01", true);

    }
    public void setup(int index, String imgPath, boolean collision) {
        UtilityTools utilityTools = new UtilityTools();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imgPath + ".png")));
            tile[index].image = utilityTools.scaleImage(tile[index].image, gamepanel.tileSize, gamepanel.tileSize);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadmap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new java.io.InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gamepanel.maxWorldCol && row < gamepanel.maxWorldRow)
            {
                String line = br.readLine();
                while(col < gamepanel.maxWorldCol)
                {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gamepanel.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamepanel.maxWorldCol &&  worldRow < gamepanel.maxWorldRow)
        {
            int tileNum = mapTileNum[gamepanel.currentMap][worldCol][worldRow];

            int worldX = worldCol * gamepanel.tileSize;
            int worldY = worldRow * gamepanel.tileSize;
            int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
            int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;

            g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            worldCol++;

            if(worldCol == gamepanel.maxWorldCol)
            {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
