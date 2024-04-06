package main;

import entity.Entity;
import entity.Player;
import object.BasedObject;
import object.KeyObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN
    final int originalTileSize = 16;
    final int scale = 3;
    public final int playerSize = originalTileSize * (scale + 2);
    public final int npcSize = originalTileSize * (scale);
    public final int objectSize = originalTileSize * scale;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 30;
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol; //30 * 48 = 1440
    public final int screenHeight = tileSize * maxScreenRow; //15 * 48 = 720

    //World Settings
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 50;
    //public final int worldWidth = tileSize * maxWorldCol;
    //public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 120;

    Thread gameThread;


    //System
    TileManager tileM = new TileManager(this);
    public KeyboardHandler Key = new KeyboardHandler(this);
    Sound sound = new Sound();
    public UI ui = new UI(this);
    public CollisionCheck collisionCheck = new CollisionCheck(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public BasedObject[] basedObject = new BasedObject[10];
    //Player + NPC
    public Player player = new Player(this, Key);
    public Entity[] npc = new Entity[10];

    //Game state
    public int gameState;
    public final int pauseState = 0;
    public final int playState = 1;
    public final int dialogueState = 2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(Key);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        assetSetter.setObject();
        assetSetter.setNPC();

        playMusic(0);
        gameState = playState;

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawTime = (double) 1000 / FPS;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null)
        {
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / drawTime;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer == 1000)
            {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if(gameState == playState)
        {
            //player
            player.update();

            //npc
            for(int i = 0; i < npc.length; i++)
            {
                if(npc[i] != null)
                {
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState)
        {

        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //tile
        tileM.draw(g2);

        //UI
        ui.draw(g2);

        //object
        for (int i = 0; i < basedObject.length; i++) {
            if (basedObject[i] != null) {
                basedObject[i].draw(g2, this);
            }
        }

        //player
        player.draw(g2);

        //NPC
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw_npc(g2);
                //System.out.println(npc[i].direction);
            }
        }


        for(int i = 0; i < npc.length; i++)
        {
            if(npc[i] != null)
            {
                npc[i].draw_npc(g2);
            }
        }

        g2.dispose();
    }
    public void playMusic(int i)
    {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic()
    {
        sound.stop();
    }
    public void playSoundEffect(int i)
    {
        sound.setFile(i);
        sound.play();
    }
}
