package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Lightning {
    GamePanel gamepanel;
    BufferedImage darknessFilter;

    public Lightning(GamePanel gamepanel)
    {
        this.gamepanel = gamepanel;
        setLightSource();
    }
    public void setLightSource()
    {
        // Create a buffered image
        darknessFilter = new BufferedImage(gamepanel.screenWidth, gamepanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if(gamepanel.player.currentLight == null)
        {
            g2.setColor(new Color(0, 0, 0, 0.95f));
        }
        else {
            // Get center of the x and y of the circle
            int centerX = gamepanel.player.screenX + gamepanel.tileSize - 14;
            int centerY = gamepanel.player.screenY + gamepanel.tileSize / 2;

            // Create a gradation effect within the light circle
            Color[] color = new Color[20];
            float[] fraction = new float[20];

            // Set the color and fraction data
            for (int i = 0; i < 20; i++) {
                color[i] = new Color(0, 0, 0, (float) (i / (20 + 0.5)));
                fraction[i] = (float) i / 20;
            }

            // Create a gradation paint settings for the light circle
            RadialGradientPaint gradientPaint = new RadialGradientPaint(centerX, centerY, (float) gamepanel.player.currentLight.lightRadius, fraction, color);

            // Set the gradient data on g2
            g2.setPaint(gradientPaint);
        }
        g2.fillRect(0, 0, gamepanel.screenWidth, gamepanel.screenHeight);
        g2.dispose();
    }
    public void update()
    {
        if(gamepanel.player.lightUpdate){
            setLightSource();
            gamepanel.player.lightUpdate = false;
        }
    }
    public void draw(Graphics2D g2)
    {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
