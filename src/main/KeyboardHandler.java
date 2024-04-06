package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
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
                gamepanel.gameState = gamepanel.playState;
            }
            if(code == KeyEvent.VK_ENTER)
            {
                enterPressed = true;
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
