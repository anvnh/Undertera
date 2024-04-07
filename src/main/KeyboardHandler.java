package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyboardHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, communicateWithNPC, F_Pressed;
    GamePanel gamepanel;

    public KeyboardHandler(GamePanel gamePanel)
    {
        this.gamepanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //Title State
        if(gamepanel.gameState == gamepanel.titleState)
        {
            if(code == KeyEvent.VK_UP)
            {
                gamepanel.ui.commandNumber--;
                if(gamepanel.ui.commandNumber < 0)
                    gamepanel.ui.commandNumber = 3;
            }
            if(code == KeyEvent.VK_DOWN)
            {
                gamepanel.ui.commandNumber++;
                if(gamepanel.ui.commandNumber > 3)
                    gamepanel.ui.commandNumber = 0;
            }
            if(code == KeyEvent.VK_ENTER)
            {
                if(gamepanel.ui.commandNumber == 0)
                {
                    gamepanel.gameState = gamepanel.playState;
                }
                if(gamepanel.ui.commandNumber == 1)
                {
                }
                if(gamepanel.ui.commandNumber == 2)
                {
                }
                if(gamepanel.ui.commandNumber == 3)
                {
                    System.exit(0);
                }
            }
        }
        //play state
        if(gamepanel.gameState == gamepanel.playState)
        {
            if(code == KeyEvent.VK_W)
            {
                upPressed = true;
            }
            if(code == KeyEvent.VK_S)
            {
                downPressed = true;
            }
            if(code == KeyEvent.VK_A)
            {
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D)
            {
                rightPressed = true;
            }
            if(code == KeyEvent.VK_ESCAPE)
            {
                gamepanel.gameState = gamepanel.pauseState;
            }
            if(code == KeyEvent.VK_E)
            {
                communicateWithNPC = true;
            }
            if(code == KeyEvent.VK_F)
            {
                F_Pressed = true;
            }
        }
        //Pause State
        if(gamepanel.gameState == gamepanel.pauseState)
        {
            if(code == KeyEvent.VK_ESCAPE)
            {
                gamepanel.gameState = gamepanel.playState;
            }
        }
        //Dialogue State
        if(gamepanel.gameState == gamepanel.dialogueState)
        {
            if(code == KeyEvent.VK_ENTER)
            {
                gamepanel.gameState = gamepanel.playState;
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
    }
}
