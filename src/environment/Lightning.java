package environment;

import com.sun.source.tree.WhileLoopTree;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class Lightning {
    GamePanel gamepanel;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    //Font
    Font CCRedAlert;

    // Day state
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;


    public Lightning(GamePanel gamepanel)
    {
        try {
            InputStream is = getClass().getResourceAsStream("/font/C_C_Red_Alert__INET_.ttf");
            assert is != null;
            CCRedAlert = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
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
            g2.setColor(new Color(0, 0, 0.1f, 0.95f));
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
    public void resetDay()
    {
        dayState = day;
        filterAlpha = 0f;
    }
    public void update()
    {
        if(gamepanel.player.lightUpdate){
            setLightSource();
            gamepanel.player.lightUpdate = false;
        }

        // Check the state of the day
        if(dayState == day)
        {
            dayCounter++;
            // Note by dev: (anvnh)
            // Based on the current frame per second of the game(the game is 120fps), 7200 frames is equivalent to 1 minutes
            // Because:
            //         1 second = 120 frames
            //      => 1 min = 120 * 60 = 7200 frames
            // So, 7200 frames is equivalent to 1 minutes
            // Yet, if we change the dayCounter to 7200, it will be 1 minute to change the day state
            if(dayCounter > 7200 / 60) {
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk)
        {
            filterAlpha += 0.0005f;
            if(filterAlpha >= 0.95f) {
                filterAlpha = 0.95f;
                dayState = night;
            }
        }
        if(dayState == night)
        {
            dayCounter++;
            if(dayCounter > 7200 / 60) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn)
        {
            filterAlpha -= 0.0005f;
            if(filterAlpha <= 0f) {
                filterAlpha = 0f;
                dayState = day;
            }
        }

    }
    public void draw(Graphics2D g2)
    {
        if(gamepanel.currentArea == gamepanel.outSide) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        else if(gamepanel.currentArea == gamepanel.dungeon){
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setFont(CCRedAlert);

        // DEBUG
        String currentDayState = "";

        switch (dayState)
        {
            case day: currentDayState = "Day"; break;
            case dusk: currentDayState = "Dusk"; break;
            case night: currentDayState = "Night"; break;
            case dawn: currentDayState = "Dawn"; break;
        }
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.drawString(currentDayState, 10, 40);
    }
}
