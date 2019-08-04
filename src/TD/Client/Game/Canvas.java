
package TD.Client.Game;

/**
 *
 * @author Liam Gomez <liamgomez75@gmail.com>
 */

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import javax.swing.*;
        
public class Canvas extends JPanel implements Runnable {
    
    public Thread gameLoop = new Thread(this);
    public static int myWidth, myHeight;
    public static int coinAmount = 10;
    public static int health = 1;
    public static int kills = 0, killsToWin = 0, level = 1, maxLevel = 3, difficulty = 1, coalBound = 30;
    public static int ores = 100;
    public static boolean isFirst = true;
    public static Room room;
    public static Image[] tileset_ground = new Image[100];
    public static Image[] tileset_air = new Image[100];
    public static Image[] tileset_res = new Image[100];
    public static Image[] tileset_mob = new Image[100];
    public static Save save;
    public static Point mse = new Point(0, 0);
    public static Store store;
    public static Mob[] mobs = new Mob[100];
    public static boolean isDebug = false;
    public static boolean hasWon = false;
    public static int winFrame = 0, winTime = 4000;
    
    
    public Canvas(Window window) {
        window.addMouseListener(new KeyHandler()); // Adds listeners to listen for mouse and keyboard input.
        window.addMouseMotionListener(new KeyHandler());
        
        gameLoop.start(); // Starts the loop of the game.
    }
    
   /* This method checks if the player has beaten the level.
    * If the player has won all of his values are reset in preparation for the next level.
    */
    public static void hasWon() {
        if(kills >= killsToWin) {
            hasWon = true;
            kills = 0;
            ores = 100;
            coinAmount = 10;
            health = 1;
            difficulty = 1;
            coalBound = 30;
        } else {
            hasWon = false;
            if (ores <= 75 && ores > 50) {
                difficulty = 2;
                coalBound = 50;
            }
            else if (ores <= 50 && ores > 25) {
                difficulty = 3;
                coalBound = 75;
            }
            else if (ores <= 25) {
                difficulty = 4;
                coalBound = 100;
            }
        }
    }
    
    //This method is responsible for initializing everything from images to the map.
    public void define() {
        save = new Save();
        room = new Room();
        store = new Store();
        coinAmount = 10;
        
        
        
        for(int i = 0; i < tileset_ground.length; i++) { // Initializes the ground images
            tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
            tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 64*i, 64, 64))); // This is used to get an image that is in a tileset.
        }
        for(int i = 0; i < tileset_air.length; i++) { // Initializes the air images.
            tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
            tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 64*i, 64, 64)));
        }
        tileset_res[0] = new ImageIcon("res/cell.png").getImage();
        tileset_res[1] = new ImageIcon("res/heart.png").getImage();
        tileset_res[2] = new ImageIcon("res/coin.png").getImage();
        tileset_mob[0] = new ImageIcon("res/goldore.png").getImage();
        tileset_mob[1] = new ImageIcon("res/coalore.png").getImage();
        tileset_mob[2] = new ImageIcon("res/ironore.png").getImage();
        
        save.loadSave(new File("save/mission" + level + ".sav")); // Loads the level from the save file.
        
        //Initializes the mobs for the map.
        for(int i = 0;i< mobs.length;i++) {
            mobs[i] = new Mob();
        }
        
    }
    
    //This method handles all of the graphics for painting the entire screen.
    @Override
    public void paintComponent(Graphics g) {
        if(isFirst) { // Initializes the width and height values and defines everything.
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            isFirst = false;
        }
        g.setColor(new Color (50, 50, 50));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(0, 0, 0));
        g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y + room.blockSize); //Draws the left line of the game border
        g.drawLine(room.block[0][room.worldWidth-1].x + room.blockSize, 0, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize); //Draws the right line of the game border
        g.drawLine(room.block[0][0].x, room.block[room.worldHeight-1][0].y + room.blockSize, room.block[0][room.worldWidth -1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize); //draws the bottom line of the game border.
        room.draw(g); //Draws the room.
        
        for(int i = 0; i <mobs.length; i++) {
            if(mobs[i].inGame) { // Draws the mob if it is in the game.
                mobs[i].draw(g);
            }
        }
        
        store.draw(g); //Draws the store.
        
        if(health < 1) {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, myWidth, myHeight);
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Courier New", Font.BOLD,36));
            g.drawString("GAME OVER",220 ,300 ); // Draws the game over screen.
        }
        if(hasWon) {
            g.setColor(new Color(255,255,255));
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("Courier New", Font.BOLD,14));
            if(level == maxLevel) {
                g.drawString("YOU WIN!",getWidth()/2 ,getHeight()/2 ); // Draws the win screen if there are no additional levels.
            } else {
                g.drawString("LOADING NEXT LEVEL...",getWidth()/2 - 80 ,getHeight()/2 ); // Draws the loading screen if there is another level.
            }
        }
    }
    
    public int spawnTime = 2500, spawnFrame= 0;
    
    //This method spawns mobs into the map.
    public void mobSpawner() {
        Random random = new Random();
        int num = 1;
        if(spawnFrame >= spawnTime) { // This acts as a timer so that mobs don't all spawn at once.
            for(int i = 0; i < mobs.length; i++) {
                if(!mobs[i].inGame) {
                    num = random.nextInt(100) + 1;
                    if (num <= 10 * difficulty) {
                        mobs[i].spawnMob(Value.mobIron);
                    }
                    else if (num > 10 * difficulty && num <= coalBound) {
                        mobs[i].spawnMob(Value.mobCoal);
                    }
                    else {
                        mobs[i].spawnMob(Value.mobGold);// Spawns the Gold ore.
                    }
                    break;
                }
            }
            
            spawnFrame = 0;
        } else {
            spawnFrame+= 1;
        }
    }

    public static void click (int mouseButton) {
        if (mouseButton == 1) {
            for (int i = 0; i < mobs.length; i++)
            if(mobs[i].contains(Canvas.mse) && mobs[i].inGame) {
                mobs[i].takeDamage(1);
            }
        }
    }

    //This method runs the game loop and determines what happens every second that the game is running.
    @Override
    public void run() {
        while(true){
            if(!isFirst && health > 0 && !hasWon) { // If the player has not lost or won then the loop will run as normal.
                room.physics();
                mobSpawner();
                for(int i = 0; i < mobs.length; i++) {
                    if(mobs[i].inGame) {
                        mobs[i].physics();
                    }
                }
            } else {
                if(hasWon) { // If the player has won then the loop will load the next level or exit the game if the player is on the last level.
                    if(winFrame >= winTime) {
                        if(level == maxLevel) {
                            System.exit(0);
                        } else {
                            level += 1;
                            winFrame = 0;
                            define();
                            hasWon = false;
                        }
                        
                    } else {
                        winFrame += 1;
                    }
                }
            }
            
            
            repaint();
            try { // This is how often the game loop is updated.
                gameLoop.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
