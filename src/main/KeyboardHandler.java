package main;

import javax.security.auth.kerberos.KeyTab;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Key;

public class KeyboardHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, communicateWithNPC, F_Pressed, C_Pressed,
            J_Pressed, K_Pressed, L_Pressed, I_Pressed, O_Pressed, U_Pressed, Y_Pressed, pausePress, characterScreenPressed;
    boolean checkDrawTime = true;
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
        if(code == KeyEvent.VK_EQUALS)
        {
            if(!checkDrawTime)
            {
                checkDrawTime = true;
            }
            else
            {
                checkDrawTime = false;
            }
        }
        //Title State
        if(code == KeyEvent.VK_ESCAPE)
        {

        }
        if(gamepanel.gameState == gamepanel.titleState)
        {
            titleState(code);
        }
        //play state
        if(gamepanel.gameState == gamepanel.playState)
        {
            playState(code);
        }
        //Pause State
        if(gamepanel.gameState == gamepanel.pauseState)
        {
            pauseState(code);
        }
        //Dialogue State
        if(gamepanel.gameState == gamepanel.dialogueState)
        {
            dialogueState(code);
        }
        //Character State
        if(gamepanel.gameState == gamepanel.characterState)
        {
            characterState(code);
        }
    }
    public void titleState(int code) {
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
    public void playState(int code) {
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
        if(code == KeyEvent.VK_C)
        {
            gamepanel.gameState = gamepanel.characterState;
            characterScreenPressed = true;
        }
        if(code == KeyEvent.VK_E)
        {
            communicateWithNPC = true;
        }
        if(code == KeyEvent.VK_F)
        {
            F_Pressed = true;
        }
        if(code == KeyEvent.VK_J)
        {
            J_Pressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE)
        {
            gamepanel.gameState = gamepanel.pauseState;
            pausePress = true;
        }

        if(code == KeyEvent.VK_BACK_SLASH)
        {
            gamepanel.tileM.loadmap("/maps/worldV4.txt");
        }

    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_ESCAPE && !pausePress)
        {
            gamepanel.gameState = gamepanel.playState;
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER){
            gamepanel.gameState = gamepanel.playState;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C && !characterScreenPressed)
        {
            gamepanel.gameState = gamepanel.playState;
        }
        if(code == KeyEvent.VK_J || code == KeyEvent.VK_DOWN)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.slotRow < 7)
                gamepanel.ui.slotRow++;
        }
        if(code == KeyEvent.VK_K || code == KeyEvent.VK_UP)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.slotRow > 0)
                gamepanel.ui.slotRow--;
        }
        if(code == KeyEvent.VK_L || code == KeyEvent.VK_RIGHT)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.slotCol <= 17){
                gamepanel.ui.slotCol++;
                if(gamepanel.ui.slotCol == 18) {
                    gamepanel.ui.slotCol = 0;
                    if(gamepanel.ui.slotRow < 7)
                        gamepanel.ui.slotRow++;
                    else gamepanel.ui.slotRow = 0;
                }
            }
        }
        if(code == KeyEvent.VK_H || code == KeyEvent.VK_LEFT)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.slotCol >= 0){
                gamepanel.ui.slotCol--;
                if(gamepanel.ui.slotCol == -1) {
                    gamepanel.ui.slotCol = 17;
                    if(gamepanel.ui.slotRow > 0)
                        gamepanel.ui.slotRow--;
                    else gamepanel.ui.slotRow = 7;
                }
            }
        }
        if(code == KeyEvent.VK_ENTER)
        {
            gamepanel.player.selectItem();
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
        if(code == KeyEvent.VK_ESCAPE)
        {
            pausePress = false;
        }
        if(code == KeyEvent.VK_C)
        {
            characterScreenPressed = false;
        }
    }
}
