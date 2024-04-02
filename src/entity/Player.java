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
    public Player(GamePanel gp, KeyboardHandler kh){
        this.gamepanel = gp;
        this.Key = kh;
        screenX = gamepanel.screenWidth / 2 - (gamepanel.tileSize / 2);
        screenY = gamepanel.screenHeight / 2 - (gamepanel.tileSize / 2);
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gamepanel.tileSize * 16;
        worldY = gamepanel.tileSize * 11;
        speed = 3;
        direction = "right";
    }
    public void getPlayerImage()
    {
        try{
            //standing = ImageIO.read(getClass().getResourceAsStream("/player/standing_front.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player_based/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player_based/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player_based/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player_based/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player_based/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player_based/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player_based/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player_based/right2.png"));
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
            } else if (Key.rightPressed) {
                direction = "right";
                worldX += speed;
            }
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2)
    {
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gamepanel.tileSize, gamepanel.tileSize);
        BufferedImage image = null;
        switch(direction)
        {
            case "up":
                if(spriteNum == 1)
                    image = up1;
                else if(spriteNum == 2)
                    image = up2;
                break;
            case "down":
                if(spriteNum == 1)
                    image = down1;
                else if(spriteNum == 2)
                    image = down2;
                break;
            case "left":
                if(spriteNum == 1)
                    image = left1;
                else if(spriteNum == 2)
                    image = left2;
                break;
            case "right":
                if(spriteNum == 1)
                    image = right1;
                else if(spriteNum == 2)
                    image = right2;
                break;

        }
        g2.drawImage(image, screenX, screenY, gamepanel.tileSize, gamepanel.tileSize , null);
    }
}
