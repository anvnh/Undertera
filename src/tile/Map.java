package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{
    GamePanel gamepanel;
    BufferedImage[] worldMap;
    public boolean miniMapOn = true;

    public Map(GamePanel gamepanel)
    {
        super(gamepanel);
        this.gamepanel = gamepanel;
        createWorldMap();
    }
    public void createWorldMap()
    {
        worldMap = new BufferedImage[gamepanel.maxMap];
        int worldMapWidth = gamepanel.tileSize * gamepanel.maxWorldCol;
        int worldMapHeight = gamepanel.tileSize * gamepanel.maxWorldRow;

        for(int i = 0; i < gamepanel.maxMap; i++)
        {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();

            for(int col = 0; col < gamepanel.maxWorldCol; col++)
            {
                for(int row = 0; row < gamepanel.maxWorldRow; row++)
                {
                    int tileNum = gamepanel.tileM.mapTileNum[i][col][row];
                    g2.drawImage(gamepanel.tileM.tile[tileNum].image, col * gamepanel.tileSize, row * gamepanel.tileSize, null);
                }
            }
            g2.dispose();
        }
    }
    public void drawFullMapScreen(Graphics2D g2)
    {
        //Background color
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gamepanel.screenWidth, gamepanel.screenHeight);

        // Draw map
        int width = 700;
        int height = 700;
        int x = gamepanel.screenWidth / 2 - width / 2;
        int y = gamepanel.screenHeight / 2 - height / 2;
        g2.drawImage(worldMap[gamepanel.currentMap], x, y, width, height, null);

        // Draw player
        double scale = (double) (gamepanel.tileSize * gamepanel.maxWorldCol) / width;
        int playerX = (int) (x + gamepanel.player.worldX / scale) - 20;
        int playerY = (int) (y + gamepanel.player.worldY / scale) - 20;
        int playerSize = (int) (gamepanel.tileSize / scale);
        g2.drawImage(gamepanel.player.image, playerX, playerY, 48, 48, null);

        // Hint
        g2.setFont(gamepanel.ui.CCRedAlert.deriveFont(38f));
        g2.setColor(Color.WHITE);
        g2.drawString("Press M to close", gamepanel.screenWidth - 350, gamepanel.screenHeight - 20);
    }
    public void drawMiniMap(Graphics2D g2)
    {
        if(miniMapOn)
        {
            int width = 300;
            int height = 300;
            int x = gamepanel.screenWidth - width - 33;
            int y = 83;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.drawImage(worldMap[gamepanel.currentMap], x, y, width, height, null);

            // Draw player
            double scale = (double) (gamepanel.tileSize * gamepanel.maxWorldCol) / width;
            int playerX = (int) (x + gamepanel.player.worldX / scale) - 20;
            int playerY = (int) (y + gamepanel.player.worldY / scale) - 20;
            int playerSize = (int) (gamepanel.tileSize / scale);
            g2.drawImage(gamepanel.player.image, playerX, playerY, 48, 48, null);

            // Draw border
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(x, y, width, height);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
