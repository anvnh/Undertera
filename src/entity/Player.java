package entity;
import main.GamePanel;
import main.KeyboardHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamepanel;
    KeyboardHandler Key;
    public final int screenX;
    public final int screenY;
    BufferedImage[] stand_down = new BufferedImage[6];
    BufferedImage[] stand_up = new BufferedImage[6];
    BufferedImage[] stand_left = new BufferedImage[6];
    BufferedImage[] stand_right = new BufferedImage[6];

    BufferedImage[] go_down = new BufferedImage[6];
    BufferedImage[] go_up = new BufferedImage[6];
    BufferedImage[] go_left = new BufferedImage[6];
    BufferedImage[] go_right = new BufferedImage[6];
    public Player(GamePanel gp, KeyboardHandler kh){
        this.gamepanel = gp;
        this.Key = kh;
        screenX = gamepanel.screenWidth / 2 - (gamepanel.imgSize / 2);
        screenY = gamepanel.screenHeight / 2 - (gamepanel.imgSize / 2);
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gamepanel.tileSize * 16;
        worldY = gamepanel.tileSize * 11;
        speed = 3;
        direction = "down";
    }
    public void getPlayerImage()
    {
        try{
            //Standing right
            stand_right[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right_1.png")));
            stand_right[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right_2.png")));
            stand_right[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right_3.png")));
            stand_right[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right_4.png")));
            stand_right[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right_5.png")));
            stand_right[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right_6.png")));

            //Standing down
            stand_down[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_down_1.png")));
            stand_down[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_down_2.png")));
            stand_down[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_down_3.png")));
            stand_down[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_down_4.png")));
            stand_down[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_down_5.png")));
            stand_down[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_down_6.png")));

            //Standing up
            stand_up[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_up_1.png")));
            stand_up[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_up_2.png")));
            stand_up[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_up_3.png")));
            stand_up[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_up_4.png")));
            stand_up[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_up_5.png")));
            stand_up[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_up_6.png")));

            //Standing left
            stand_left[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left_1.png")));
            stand_left[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left_2.png")));
            stand_left[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left_3.png")));
            stand_left[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left_4.png")));
            stand_left[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left_5.png")));
            stand_left[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left_6.png")));

            //Going down
            go_down[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_down_1.png")));
            go_down[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_down_2.png")));
            go_down[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_down_3.png")));
            go_down[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_down_4.png")));
            go_down[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_down_5.png")));
            go_down[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_down_6.png")));

            //Going right
            go_right[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_right_1.png")));
            go_right[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_right_2.png")));
            go_right[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_right_3.png")));
            go_right[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_right_4.png")));
            go_right[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_right_5.png")));
            go_right[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_right_6.png")));

            //Going up
            go_up[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_up_1.png")));
            go_up[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_up_2.png")));
            go_up[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_up_3.png")));
            go_up[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_up_4.png")));
            go_up[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_up_5.png")));
            go_up[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_up_6.png")));

            //Going left
            go_left[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_left_1.png")));
            go_left[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_left_2.png")));
            go_left[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_left_3.png")));
            go_left[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_left_4.png")));
            go_left[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_left_5.png")));
            go_left[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/go_left_6.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update()
    {
        if(Key.upPressed || Key.downPressed || Key.leftPressed || Key.rightPressed) {
            if (Key.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (Key.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (Key.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else {
                direction = "right";
                worldX += speed;
            }
            runCount++;
            if (runCount > 15) {
                if (runAnimation == 1)
                    runAnimation = 2;
                else if (runAnimation == 2)
                    runAnimation = 3;
                else if (runAnimation == 3)
                    runAnimation = 4;
                else if (runAnimation == 4)
                    runAnimation = 5;
                else if (runAnimation == 5)
                    runAnimation = 6;
                else if (runAnimation == 6)
                    runAnimation = 1;
                runCount = 0;
            }
        }
        else {
            standCount++;
            if (standCount > 15) {
                if (standAnimation == 1)
                    standAnimation = 2;
                else if (standAnimation == 2)
                    standAnimation = 3;
                else if (standAnimation == 3)
                    standAnimation = 4;
                else if (standAnimation == 4)
                    standAnimation = 5;
                else if (standAnimation == 5)
                    standAnimation = 6;
                else if (standAnimation == 6)
                    standAnimation = 1;
                standCount = 0;
            }
        }
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        if(!Key.upPressed && !Key.downPressed && !Key.leftPressed && !Key.rightPressed)
        {
            if(direction.equals("down"))
            {
                image = getRunImage(image, stand_down);
            }
            else if(direction.equals("up"))
            {
                image = getRunImage(image, stand_up);
            }
            else if(direction.equals("left"))
            {
                image = getRunImage(image, stand_left);
            }
            else if(direction.equals("right"))
            {
                image = getRunImage(image, stand_right);
            }
            g2.drawImage(image, screenX, screenY, gamepanel.imgSize, gamepanel.imgSize, null);
        }
        else {
            if(direction.equals("up"))
            {
                image = getBufferedImage(image, go_up);
            }
            else if(direction.equals("down"))
            {
                image = getBufferedImage(image, go_down);
            }
            else if(direction.equals("left"))
            {
                image = getBufferedImage(image, go_left);
            }
            else if(direction.equals("right"))
            {
                image = getBufferedImage(image, go_right);
            }
            g2.drawImage(image, screenX, screenY, gamepanel.imgSize, gamepanel.imgSize, null);
        }
    }
    private BufferedImage getRunImage(BufferedImage image, BufferedImage[] stand)
    {
        if(standAnimation == 1)
            image = stand[0];
        else if(standAnimation == 2)
            image = stand[1];
        else if(standAnimation == 3)
            image = stand[2];
        else if(standAnimation == 4)
            image = stand[3];
        else if(standAnimation == 5)
            image = stand[4];
        else if(standAnimation == 6)
            image = stand[5];
        return image;
    }
    private BufferedImage getBufferedImage(BufferedImage image, BufferedImage[] run) {
        if(runAnimation == 1)
            image = run[0];
        else if(runAnimation == 2)
            image = run[1];
        else if(runAnimation == 3)
            image = run[2];
        else if(runAnimation == 4)
            image = run[3];
        else if(runAnimation == 5)
            image = run[4];
        else if(runAnimation == 6)
            image = run[5];
        return image;
    }
}
