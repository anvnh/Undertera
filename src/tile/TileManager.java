package tile;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    GamePanel gamepanel;
    public Tile[] tile;
    public static int[][][] mapTileNum;
    boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<String>();
    ArrayList<String> collisionStatus = new ArrayList<String>();

    public TileManager(GamePanel gp){
        this.gamepanel = gp;

        // READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        assert is != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Getting tile names and coolision info from the file
        String line;
        try {
            while((line = br.readLine()) != null)
            {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize tile array based on the fileNames size
        tile = new Tile[fileNames.size()];
        getTileImage();

        //Get the map col and row
        is = getClass().getResourceAsStream("/maps/worldmap.txt");
        assert is != null;
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String[] maxTile = line2.split(" ");

            gamepanel.maxWorldCol = maxTile.length;
            gamepanel.maxWorldRow = maxTile.length;
            mapTileNum = new int[gamepanel.maxMap][gamepanel.maxWorldCol][gamepanel.maxWorldRow];

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        loadmap("/maps/worldmap.txt", 0);
        loadmap("/maps/indoor01.txt", 1);
        loadmap("/maps/dungeon01.txt", 2);
        loadmap("/maps/dungeon02.txt", 3);
        //System.out.println(gamepanel.maxWorldCol + " " + gamepanel.maxWorldRow);
    }

    public void getTileImage() {
        for(int i = 0; i < fileNames.size(); i++)
        {
            String fileName;
            boolean collision;

            // Get a file name
            fileName = fileNames.get(i);

            // Get a collision status
            //collision = collisionStatus.get(i).equals("true");
            if(collisionStatus.get(i).equals("true"))
            {
                collision = true;
            }
            else {
                collision = false;
            }

            setup(i, fileName, collision);
        }
    }
    public void setup(int index, String imgPath, boolean collision) {
        UtilityTools utilityTools = new UtilityTools();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imgPath)));
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
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

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
        // Draw the path that the entity is following
        /*
        if(drawPath)
        {
            g2.setColor(new Color(255, 0, 0, 70));
            for(int i = 0; i < gamepanel.pathFinder.pathList.size(); i++)
            {
                int worldX = gamepanel.pathFinder.pathList.get(i).col * gamepanel.tileSize;
                int worldY = gamepanel.pathFinder.pathList.get(i).row * gamepanel.tileSize;
                int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
                int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;



                g2.fillRect(screenX, screenY, gamepanel.tileSize, gamepanel.tileSize);

            }
        }
         */
    }
}
