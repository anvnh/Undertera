package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage
            standing, go_left_1, go_left_2, go_right_1, go_right_2,
            go_up_1, go_up_2, go_down_1, go_down_2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
