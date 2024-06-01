package entity;

import main.GamePanel;

public class PlayerDummy extends Entity{
    public static final String npcName = "Dummy";
    public PlayerDummy(GamePanel gamepanel) {
        super(gamepanel);

        name = npcName;
        getImage();
    }
    public void getImage()
    {
        for(int i = 0; i < 6; i++)
        {
            go_up[i] = setup_entity("/player/stand_up_" + (i + 1), gamepanel.playerSize, gamepanel.playerSize);
            go_down[i] = setup_entity("/player/stand_down_" + (i + 1), gamepanel.playerSize, gamepanel.playerSize);
            go_left[i] = setup_entity("/player/stand_left_" + (i + 1), gamepanel.playerSize, gamepanel.playerSize);
            go_right[i] = setup_entity("/player/stand_right_" + (i + 1), gamepanel.playerSize, gamepanel.playerSize);
        }
    }
}
