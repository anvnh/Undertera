package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;
import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gamepanel) {
        super(gamepanel);
        direction = "down";
        speed = 1;
        name = "old_man";
        getNPC_IMG();
        setDialogue();
    }
    public void getNPC_IMG() {
        up_1 = setup_entity("/npc/oldman/up_1");
        up_2 = setup_entity("/npc/oldman/up_2");
        down_1 = setup_entity("/npc/oldman/down_1");
        down_2 = setup_entity("/npc/oldman/down_2");
        left_1 = setup_entity("/npc/oldman/left_1");
        left_2 = setup_entity("/npc/oldman/left_2");
        right_1 = setup_entity("/npc/oldman/right_1");
        right_2 = setup_entity("/npc/oldman/right_2");
    }
    public void setDialogue() {
        dialogue[0] = "I'm just an old man";
        dialogue[1] = "Ah, young one. I've seen many winters come and go.";
        dialogue[2] = "Listen closely, for I have tales that will make your ears tingle.";
        dialogue[3] = "The world has changed much since I was a lad, but one thing remains the \nsame: the importance of kindness.";
        dialogue[4] = "In my youth, I embarked on many adventures. I crossed treacherous \nmountains and sailed uncharted seas.";
        dialogue[5] = "I've witnessed the rise and fall of empires, and the birth and death of \ncountless souls.";
        dialogue[6] = "The past is a book that is always open, but it is up to us to choose \nwhat lessons we learn from it.";
        dialogue[7] = "Remember, young one, that even the smallest actions can have \nripple effects that shape the destiny of the world.";
        dialogue[8] = "The true measure of a person is not their wealth or power, but the \ncontent of their heart.";
        dialogue[9] = "May your path be filled with wisdom, courage, and a touch of mischief.";
        dialogue[10] = "Farewell, young one. May the winds guide you and the stars light your way.";
    }
    public void setAction() {

        actionLockCounter ++;
        if(actionLockCounter == 120)
        {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if(i <= 25)
                direction = "left";
            else if(i <= 50)
                direction = "up";
            else if(i <= 75)
                direction = "right";
            else
                direction = "down";
            actionLockCounter = 0;
        }
    }
    public void speak() {
        super.speak();
    }

}
