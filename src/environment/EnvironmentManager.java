package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {
    GamePanel gamepanel;
    Lightning lightning;

    public EnvironmentManager(GamePanel gamepanel)
    {
        this.gamepanel = gamepanel;
    }
    public void setup()
    {
        lightning = new Lightning(gamepanel, 350);
    }
    public void draw(Graphics2D g2)
    {
        lightning.draw(g2);
    }
}
