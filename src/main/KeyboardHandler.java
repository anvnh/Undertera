package main;

import javax.security.auth.kerberos.KeyTab;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Key;

public class KeyboardHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, communicateWithNPC, F_Pressed,
            attack_Pressed, Projectile_Pressed, pausePress, characterScreenPressed, dashPressed,
            enterPressed, Options_Pressed, mapPressed, miniMapPressed, parryPressed, shieldPressed, testPressed;
    boolean checkDrawTime = false;
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
        if(gamepanel.gameState == gamepanel.dialogueState || gamepanel.gameState == gamepanel.cutSceneState)
        {
            dialogueState(code);
        }
        //Character State
        if(gamepanel.gameState == gamepanel.characterState)
        {
            characterState(code);
        }
        //Options State
        if(gamepanel.gameState == gamepanel.optionsState)
        {
            optionState(code);
        }
        //Game over State
        if(gamepanel.gameState == gamepanel.gameOverState)
        {
            gameOverState(code);
        }
        // Trade State
        if(gamepanel.gameState == gamepanel.tradeState)
        {
            tradeState(code);
        }
        // Map State
        if(gamepanel.gameState == gamepanel.mapState)
        {
            mapState(code);
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
            else if(gamepanel.ui.commandNumber == 1)
            {
                gamepanel.saveLoad.load();
                gamepanel.gameState = gamepanel.playState;
            }
            else if(gamepanel.ui.commandNumber == 2)
            {
            }
            else if(gamepanel.ui.commandNumber == 3)
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
            gamepanel.playSoundEffect(9);
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
            attack_Pressed = true;
        }
        // Pause game
        if(code == KeyEvent.VK_P)
        {
            gamepanel.gameState = gamepanel.pauseState;
            pausePress = true;
            //gamepanel.stopMusic();
        }
        // Shoot the projectile
        if(code == KeyEvent.VK_I)
        {
            // shot projectile
            Projectile_Pressed = true;
        }
        if(code == KeyEvent.VK_K) {
            dashPressed = true;
        }
        if(code == KeyEvent.VK_M && !mapPressed){
            mapPressed = true;
            gamepanel.gameState = gamepanel.mapState;
        }
        if(code == KeyEvent.VK_BACK_SLASH && !miniMapPressed){
            gamepanel.map.miniMapOn = !gamepanel.map.miniMapOn;
        }
        if(code == KeyEvent.VK_SPACE){
            shieldPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE)
        {
            gamepanel.gameState = gamepanel.optionsState;
            gamepanel.ui.subState = 0;
            Options_Pressed = true;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_P && !pausePress)
        {
            gamepanel.gameState = gamepanel.playState;
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
            //gamepanel.gameState = gamepanel.playState;
        }
    }
    public void characterState(int code) {
        // For handling the cursor on the character screen
        if(code == KeyEvent.VK_C && !characterScreenPressed)
        {
            gamepanel.gameState = gamepanel.playState;
            gamepanel.playSoundEffect(10);
        }
        if(code == KeyEvent.VK_ENTER)
        {
            gamepanel.player.selectItem();
        }
        playerInventory(code);
    }
    public void optionState(int code) {
        if(code == KeyEvent.VK_ESCAPE && !Options_Pressed)
        {
            gamepanel.gameState = gamepanel.playState;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_J || code == KeyEvent.VK_DOWN)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.subState == 0) {
                gamepanel.ui.commandNum++;
                if(gamepanel.ui.commandNum > 5)
                    gamepanel.ui.commandNum = 0;
            }
            else if(gamepanel.ui.subState == 1) {
                gamepanel.ui.commandNum = 0;
            }
            else if(gamepanel.ui.subState == 2) {
                gamepanel.ui.commandNum ++;
                if(gamepanel.ui.commandNum > 1)
                    gamepanel.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_K || code == KeyEvent.VK_UP)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.subState == 0) {
                gamepanel.ui.commandNum--;
                if(gamepanel.ui.commandNum < 0)
                    gamepanel.ui.commandNum = 5;
            }
            else if(gamepanel.ui.subState == 1) {
                gamepanel.ui.commandNum = 0;
            }
            else if(gamepanel.ui.subState == 2) {
                gamepanel.ui.commandNum --;
                if(gamepanel.ui.commandNum < 0)
                    gamepanel.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_H || code == KeyEvent.VK_LEFT)
        {
            if(gamepanel.ui.subState == 0)
            {
                if(gamepanel.ui.commandNum == 0 && gamepanel.sound.musicVolume > 0){
                    gamepanel.sound.musicVolume--;
                    gamepanel.sound.checkVolume();
                    gamepanel.playSoundEffect(6);
                }
                else if(gamepanel.ui.commandNum == 1 && gamepanel.sound.soundEffect> 0){
                    gamepanel.sound.soundEffect--;
                    gamepanel.sound.checkVolume();
                    gamepanel.playSoundEffect(6);
                }
            }
        }
        if(code == KeyEvent.VK_L || code == KeyEvent.VK_RIGHT)
        {
            if(gamepanel.ui.subState == 0)
            {
                if(gamepanel.ui.commandNum == 0 && gamepanel.sound.musicVolume < 10){
                    gamepanel.sound.musicVolume++;
                    gamepanel.sound.checkVolume();
                    gamepanel.playSoundEffect(6);
                }
                else if(gamepanel.ui.commandNum == 1 && gamepanel.sound.soundEffect < 10){
                    gamepanel.sound.soundEffect++;
                    gamepanel.sound.checkVolume();
                    gamepanel.playSoundEffect(6);
                }
            }
        }

    }
    public void gameOverState(int code) {
        if(code == KeyEvent.VK_J || code == KeyEvent.VK_DOWN) {
            gamepanel.ui.commandNum--;
            if(gamepanel.ui.commandNum < 0) gamepanel.ui.commandNum = 1;
            gamepanel.playSoundEffect(6);
        }
        if(code == KeyEvent.VK_K || code == KeyEvent.VK_UP) {
            gamepanel.ui.commandNum++;
            if(gamepanel.ui.commandNum > 1) gamepanel.ui.commandNum = 0;
            gamepanel.playSoundEffect(6);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gamepanel.ui.commandNum == 0) {
                gamepanel.gameState = gamepanel.playState;
                gamepanel.resetGame(false);
            }
            if(gamepanel.ui.commandNum == 1) {
                gamepanel.gameState = gamepanel.titleState;
                gamepanel.resetGame(true);
            }
        }
    }
    public void tradeState(int code) {
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        if(gamepanel.ui.subState == 0) {
            if(code == KeyEvent.VK_J || code == KeyEvent.VK_DOWN)
            {
                gamepanel.ui.commandNum++;
                if(gamepanel.ui.commandNum > 2)
                    gamepanel.ui.commandNum = 0;
                gamepanel.playSoundEffect(6);
            }
            if(code == KeyEvent.VK_K || code == KeyEvent.VK_UP)
            {
                gamepanel.ui.commandNum--;
                if(gamepanel.ui.commandNum < 0)
                    gamepanel.ui.commandNum = 2;
                gamepanel.playSoundEffect(6);
            }
        }
        if(gamepanel.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE)
            {
                gamepanel.ui.subState = 0;
            }
        }
        if(gamepanel.ui.subState == 2)
        {
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE)
            {
                gamepanel.ui.subState = 0;
            }

        }
    }
    public void mapState(int code) {
        if(code == KeyEvent.VK_M && !mapPressed)
        {
            gamepanel.gameState = gamepanel.playState;
        }
    }
    public void playerInventory(int code)
    {
        if(code == KeyEvent.VK_J || code == KeyEvent.VK_DOWN)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.playerSlotRow < 9)
                gamepanel.ui.playerSlotRow++;
        }
        if(code == KeyEvent.VK_K || code == KeyEvent.VK_UP)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.playerSlotRow > 0)
                gamepanel.ui.playerSlotRow--;
        }
        if(code == KeyEvent.VK_L || code == KeyEvent.VK_RIGHT)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.playerSlotCol <= 18){
                gamepanel.ui.playerSlotCol++;
                if(gamepanel.ui.playerSlotCol == 19) {
                    gamepanel.ui.playerSlotCol = 0;
                    if(gamepanel.ui.playerSlotRow < 9)
                        gamepanel.ui.playerSlotRow++;
                    else gamepanel.ui.playerSlotRow = 0;
                }
            }
        }
        if(code == KeyEvent.VK_H || code == KeyEvent.VK_LEFT)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.playerSlotCol >= 0){
                gamepanel.ui.playerSlotCol--;
                if(gamepanel.ui.playerSlotCol == -1) {
                    gamepanel.ui.playerSlotCol = 18;
                    if(gamepanel.ui.playerSlotRow > 0)
                        gamepanel.ui.playerSlotRow--;
                    else gamepanel.ui.playerSlotRow = 9;
                }
            }
        }
    }
    public void npcInventory(int code)
    {
        if(code == KeyEvent.VK_J || code == KeyEvent.VK_DOWN)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.npcSlotRow < 7)
                gamepanel.ui.npcSlotRow++;
        }
        if(code == KeyEvent.VK_K || code == KeyEvent.VK_UP)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.npcSlotRow > 0)
                gamepanel.ui.npcSlotRow--;
        }
        if(code == KeyEvent.VK_L || code == KeyEvent.VK_RIGHT)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.npcSlotCol <= 7){
                gamepanel.ui.npcSlotCol++;
                if(gamepanel.ui.npcSlotCol == 8) {
                    gamepanel.ui.npcSlotCol = 0;
                    if(gamepanel.ui.npcSlotRow < 7)
                        gamepanel.ui.npcSlotRow++;
                    else gamepanel.ui.npcSlotRow = 0;
                }
            }
        }
        if(code == KeyEvent.VK_H || code == KeyEvent.VK_LEFT)
        {
            gamepanel.playSoundEffect(6);
            if(gamepanel.ui.npcSlotCol >= 0){
                gamepanel.ui.npcSlotCol--;
                if(gamepanel.ui.npcSlotCol == -1) {
                    gamepanel.ui.npcSlotCol = 7;
                    if(gamepanel.ui.npcSlotRow > 0)
                        gamepanel.ui.npcSlotRow--;
                    else gamepanel.ui.npcSlotRow = 7;
                }
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
        if(code == KeyEvent.VK_P)
        {
            pausePress = false;
        }
        if(code == KeyEvent.VK_C)
        {
            characterScreenPressed = false;
        }
        if(code == KeyEvent.VK_I)
        {
            Projectile_Pressed = false;
        }
        if(code == KeyEvent.VK_J)
        {
            attack_Pressed = false;
        }
        if(code == KeyEvent.VK_K){
            dashPressed = false;
        }
        if(code == KeyEvent.VK_ESCAPE){
            Options_Pressed = false;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = false;
        }
        if(code == KeyEvent.VK_M)
        {
            mapPressed = false;
        }
        if(code == KeyEvent.VK_BACK_SLASH)
        {
            miniMapPressed = false;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            shieldPressed = false;
            gamepanel.player.parryCounter = 0; // Reset the parry counter after releasing the shield
        }
    }
}
