package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class TileManager {
    GamePanel gamepanel;
    Tile[] tile;
    int[][] mapTileNum;
    public TileManager(GamePanel gp){
        this.gamepanel = gp;
        tile = new Tile[10];

        mapTileNum = new int[gamepanel.maxWorldCol][gamepanel.maxWorldRow];

        getTileImage();
        loadmap("/maps/map02.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass01.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water01.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road00.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road01.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadmap(String filePath) {
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
                    mapTileNum[col][row] = num;
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
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamepanel.tileSize;
            int worldY = worldRow * gamepanel.tileSize;
            int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
            int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;

            g2.drawImage(tile[tileNum].image, screenX, screenY, gamepanel.tileSize, gamepanel.tileSize, null);
            worldCol++;

            if(worldCol == gamepanel.maxWorldCol)
            {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
