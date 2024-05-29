package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {
    GamePanel gamepanel;
    public Lightning lightning;

    public EnvironmentManager(GamePanel gamepanel)
    {
        this.gamepanel = gamepanel;
    }
    public void setup()
    {
        lightning = new Lightning(gamepanel);
    }
    public void update() {
        lightning.update();
    }
    public void draw(Graphics2D g2)
    {
        lightning.draw(g2);
    }
}
