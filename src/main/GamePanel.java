package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public final int maxWorldCol = 50;
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
    public EventHandler eventHandler = new EventHandler(this);
    public CollisionCheck collisionCheck = new CollisionCheck(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, Key);

    public Entity[] objects = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];

    ArrayList<Entity> entityArrayList = new ArrayList<>();

    //Game state
    public int gameState;
    public final int titleState = -1;
    public final int pauseState = 0;
    public final int playState = 1;
    public final int dialogueState = 2;
    public final int characterState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(Key);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        gameState = titleState;
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        playMusic(0);

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
        if(gameState == titleState)
        {
        }
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

            //monster
            for(int i = 0; i < monster.length; i++)
            {
                if(monster[i] != null)
                {
                    if(monster[i].alive && !monster[i].dying)
                    {
                        monster[i].update();
                    }
                    if(!monster[i].alive)
                    {
                        monster[i] = null;
                    }
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

        //TITLE SCREEN
        if(gameState == titleState)
        {
            ui.draw(g2);
        }
        else {

            //Tile
            tileM.draw(g2);

            //Add entity to the array

            entityArrayList.add(player);

            //player.draw_player(g2);

            //adding npc's
            for(int i = 0; i < npc.length; i++)
            {
                if(npc[i] != null)
                {
                    entityArrayList.add(npc[i]);
                }
            }

            //adding objects
            for(int i = 0; i < objects.length; i++)
            {
                if(objects[i] != null)
                {
                    entityArrayList.add(objects[i]);
                }
            }

            //adding monster
            for(int i = 0; i < monster.length; i++)
            {
                if(monster[i] != null)
                {
                    entityArrayList.add(monster[i]);
                }
            }

            // Sort
            entityArrayList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // Draw entities
            for(int i = 0; i < entityArrayList.size(); i++)
            {
                //entityArrayList.get(i).draw_entity(g2);
                //System.out.println(entityArrayList.get(i).name);
                if(entityArrayList.get(i).name.equals("player"))
                {
                    player.draw_player(g2);
                }
                else if(entityArrayList.get(i).objectType.equals("object"))
                {
                    entityArrayList.get(i).draw_object(g2);
                }
                else
                {
                    entityArrayList.get(i).draw_entity(g2);
                }
            }
            //Remove entities
            entityArrayList.clear();

            //UI
            ui.draw(g2);
        }
        //DEBUG
        if(Key.checkDrawTime == true)
        {
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            int x, y;
            x = 10;
            y = 400;
            int lineHeight = 20;

            g2.setColor(Color.BLACK);
            g2.drawString("World X: " + player.worldX, x + 1, y + 1);
            g2.drawString("World Y: " + player.worldY, x + 1, y + lineHeight + 1);
            g2.drawString("X: " + (player.worldX + player.solidArea.x) / tileSize, x + 1, y + lineHeight * 2 + 1);
            g2.drawString("Y: " + (player.worldY + player.solidArea.y) / tileSize, x + 1, y + lineHeight * 3 + 1);

            g2.setColor(Color.WHITE);
            g2.drawString("World X: " + player.worldX, x, y);
            g2.drawString("World Y: " + player.worldY, x, y + lineHeight);
            g2.drawString("X: " + (player.worldX + player.solidArea.x) / tileSize, x, y + lineHeight * 2);
            g2.drawString("Y: " + (player.worldY + player.solidArea.y) / tileSize, x, y + lineHeight * 3);

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
