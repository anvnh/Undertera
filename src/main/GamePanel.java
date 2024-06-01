package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;
import environment.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN
    final int originalTileSize = 16;
    final int scale = 3;
    public final int playerSize = originalTileSize * (scale + 2);
    public final int weapSize = originalTileSize * (scale + 1);
    public final int tileSize = originalTileSize * scale;
    public final int monsterSize = originalTileSize * (scale + 2);
    public final int maxScreenCol = 31;
    public final int maxScreenRow = 17;
    public final int screenWidth = tileSize * maxScreenCol; //30 * 48 = 1440
    public final int screenHeight = tileSize * maxScreenRow; //15 * 48 = 720

    //World Settings
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;

    //public final int worldWidth = tileSize * maxWorldCol;
    //public final int worldHeight = tileSize * maxWorldRow;

    // Toggle full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    //FPS
    int FPS = 120;

    Thread gameThread;


    //System
    public TileManager tileM = new TileManager(this);
    public KeyboardHandler Key = new KeyboardHandler(this);
    Sound sound = new Sound();
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public CollisionCheck collisionCheck = new CollisionCheck(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Config config = new Config(this);
    public PathFinder pathFinder = new PathFinder(this);
    public EnvironmentManager environmentManager = new EnvironmentManager(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator entityGenerator = new EntityGenerator(this);
    public CutsceneManager cutsceneManager = new CutsceneManager(this);
    Map map = new Map(this);

    // Entity and Object
    public Player player = new Player(this, Key);
    public Entity[][] objects = new Entity[maxMap][500];
    public Entity[][] npc = new Entity[maxMap][100];
    public Entity[][] monster = new Entity[maxMap][100];
    public InteractiveTile[][] interactiveTile = new InteractiveTile[maxMap][100];
    ArrayList<Entity> entityArrayList = new ArrayList<>();
    //public ArrayList<Entity> projectileList = new ArrayList<>();
    public Entity projectile[][] = new Entity[maxMap][100];
    public ArrayList<Entity> particleList = new ArrayList<>();

    //==================================== Game states ==================================-======//
    public int gameState;
    public final int titleState = -1;
    public final int pauseState = 0;
    public final int playState = 1;
    public final int dialogueState = 2;
    public final int characterState = 3;
    public final int optionsState = 4;
    public final int tradeState = 5;
    public final int sleepState = 6;
    public final int mapState = 7;
    public final int cutSceneState = 8;
    public final int gameOverState = 999;
    //==========================================================================================//

    //==========================================================================================//
    public boolean bossBattleOn=  false;
    //==========================================================================================//

    //======================================= Area =============================================//
    public int currentArea;
    public int nextArea;
    public final int outSide = 50;
    public final int inDoor = 51;
    public final int dungeon = 52;
    //==========================================================================================//

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(Key);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        //Initial for game
        gameState = titleState;
        currentArea = outSide;

        playMusic(0);

        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
        environmentManager.setup();

        //tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        //g2 = (Graphics2D) tempScreen.getGraphics();
    }
    public void resetGame(boolean restart)
    {
        currentArea = outSide;
        currentMap = 0;

        removeTempEntity();
        bossBattleOn = false;

        player.setDefaultPosition();
        player.restoreStatus();
        assetSetter.setNPC();
        assetSetter.setMonster();

        if(restart)
        {
            player.setDefaultValues();
            player.setItems();
            assetSetter.setObject();
            assetSetter.setInteractiveTile();
            environmentManager.lightning.resetDay();
            currentMap = 0;
        }
    }
    /*
    public void retry() {
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        assetSetter.setNPC();
        assetSetter.setMonster();
    }
    public void restart() {
        player.setDefaultValues();
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        player.setItems();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setObject();
        assetSetter.setInteractiveTile();
    }
     */
    public void setFullScreen() {
        // get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // get full screen width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawTime = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawTime;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if(delta >= 1)
            {
                update();
                repaint();
                /*
                drawToTempScreen();  // draw everything to the buffered image
                drawToScreen();     // draw the buffered image to the screen
                 */
                delta--;
                drawCount++;
            }
            if(timer == 1000000000)
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
            for(int i = 0; i < npc[1].length; i++)
            {
                if(npc[currentMap][i] != null)
                {
                    npc[currentMap][i].update();
                }
            }

            //monster
            for(int i = 0; i < monster[1].length; i++)
            {
                if(monster[currentMap][i] != null)
                {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying)
                    {
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive)
                    {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][i] != null) {
                    if(projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if(!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }
            for(int i = 0; i < interactiveTile[1].length; i++)
            {
                if(interactiveTile[currentMap][i] != null)
                {
                    interactiveTile[currentMap][i].update();
                }
            }
            environmentManager.update();
        }
        if(gameState == characterState)
        {
            environmentManager.update();
        }
        if(gameState == pauseState)
        {
            // do nothing
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
        else if(gameState == mapState)
        {
            map.drawFullMapScreen(g2);
        }
        else {

            //Tile
            tileM.draw(g2);

            for(int i = 0; i < interactiveTile[1].length; i++)
            {
                if(interactiveTile[currentMap][i] != null)
                {
                    interactiveTile[currentMap][i].draw_object(g2);
                }
            }

            //Add entity to the array

            entityArrayList.add(player);

            //player.draw_player(g2);

            //adding npc
            for(int i = 0; i < npc[1].length; i++)
            {
                if(npc[currentMap][i] != null)
                {
                    entityArrayList.add(npc[currentMap][i]);
                }
            }

            //adding objects
            for(int i = 0; i < objects[1].length; i++)
            {
                if(objects[currentMap][i] != null)
                {
                    entityArrayList.add(objects[currentMap][i]);
                }
            }

            //adding monster
            for(int i = 0; i < monster[1].length; i++)
            {
                if(monster[currentMap][i] != null)
                {
                    entityArrayList.add(monster[currentMap][i]);
                }
            }

            // Projectile
            for(int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][i] != null) {
                    entityArrayList.add(projectile[currentMap][i]);
                }
            }

            // Particle
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    entityArrayList.add(particleList.get(i));
                }
            }

            // Sorting
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
                else if(entityArrayList.get(i).objectType.equals("particle"))
                {
                    entityArrayList.get(i).draw(g2);
                }
                else
                {
                    entityArrayList.get(i).draw_entity(g2);
                }
            }
            //Remove entities
            entityArrayList.clear();

            // Environment
            environmentManager.draw(g2);

            // Mini map
            map.drawMiniMap(g2);

            //cut scene
            cutsceneManager.draw(g2);

            //UI
            ui.draw(g2);
        }
        //DEBUG
        if(Key.checkDrawTime)
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
            g2.drawString("Curr Map: " + currentMap, x + 1, y + lineHeight * 4 + 1);
            g2.drawString("Current Area: " + currentArea, x + 1, y + lineHeight * 5 + 1);

            g2.setColor(Color.WHITE);
            g2.drawString("World X: " + player.worldX, x, y);
            g2.drawString("World Y: " + player.worldY, x, y + lineHeight);
            g2.drawString("X: " + (player.worldX + player.solidArea.x) / tileSize, x, y + lineHeight * 2);
            g2.drawString("Y: " + (player.worldY + player.solidArea.y) / tileSize, x, y + lineHeight * 3);
            g2.drawString("Curr Map: " + currentMap, x, y + lineHeight * 4);
            g2.drawString("Current Area: " + currentArea, x, y + lineHeight * 5);
        }
        g2.dispose();
    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
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
    public void removeTempEntity()
    {
        for(int mapNum = 0; mapNum < maxMap; mapNum++)
        {
            for(int i = 0; i < objects[1].length; i++)
            {
                if(objects[mapNum][i] != null && objects[mapNum][i].temp)
                {
                    objects[mapNum][i] = null;
                }
            }
        }
    }
}

