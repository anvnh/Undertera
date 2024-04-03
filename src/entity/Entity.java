package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage
            go_left_1, go_left_2, go_right_1, go_right_2,
            go_up_1, go_up_2, go_down_1, go_down_2,
            standing_down_1, standing_down_2, standing_down_3, standing_down_4, standing_down_5, standing_down_6,
            standing_up_1, standing_up_2, standing_up_3, standing_up_4, standing_up_5, standing_up_6,
            standing_left_1, standing_left_2, standing_left_3, standing_left_4, standing_left_5, standing_left_6,
            standing_right_1, standing_right_2, standing_right_3, standing_right_4, standing_right_5, standing_right_6;
    public String direction;
    public int runCount = 0;
    public int runAnimation = 1;
    public int standCount = 0;
    public int standAnimation = 1;

}
