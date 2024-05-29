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

    public Lightning(GamePanel gamepanel, int circleSize)
    {
        // Create a buffered image
        darknessFilter = new BufferedImage(gamepanel.screenWidth, gamepanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // Created a darkened screen-sized rectangle
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gamepanel.screenWidth, gamepanel.screenHeight));

        // Get center of the x and y of the circle
        int centerX = gamepanel.player.screenX + gamepanel.tileSize - 14;
        int centerY = gamepanel.player.screenY + gamepanel.tileSize / 2;

        // Get the top left x and y of the light circle
        double x = centerX - (double) circleSize / 2;
        double y = centerY - (double) circleSize / 2;

        // Create a light circle shape
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // Create a light circle area
        Area lightArea = new Area(circleShape);

        // Subtract the light circle area from the screen area
        screenArea.subtract(lightArea);


        // Create a gradation effect within the light circle
        Color[] color = new Color[20];
        float[] fraction = new float[20];

        // Set the color and fraction data
        for (int i = 0; i < 20; i++) {
            color[i] = new Color(0, 0, 0, (float) (i / (20 + 0.5)));
            fraction[i] = (float) i / 20;
        }

        // Create a gradation paint settings for the light circle
        RadialGradientPaint gradientPaint = new RadialGradientPaint(centerX, centerY, (float) circleSize / 2, fraction, color);

        // Set the gradient data on g2
        g2.setPaint(gradientPaint);

        // Draw the light circle
        g2.fill(lightArea);

        /* Set color to draw the rectangle
        g2.setColor(new Color(0, 0, 0, 0.91f));
         */

        // Draw the screen rectangle without the light circle area
        g2.fill(screenArea);

        g2.dispose();
    }
    public void draw(Graphics2D g2)
    {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
