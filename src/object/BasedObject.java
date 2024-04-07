package object;

import jdk.jshell.execution.Util;
import main.GamePanel;
import main.UtilityTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BasedObject {
    public BufferedImage image, image1, image2, image3, image4, image5;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTools utilityTools = new UtilityTools();

    public void draw(Graphics2D g2, GamePanel gamepanel)
    {
        int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX;
        int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;
        g2.drawImage(image, screenX, screenY, gamepanel.objectSize, gamepanel.objectSize, null);
    }
}
