
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
import javax.swing.*;
        
public class Canvas extends JPanel implements Runnable {
    
    public Thread gameLoop = new Thread(this);
    public static int myWidth, myHeight;
    public static int coinAmount = 10;
    public static int health = 1;
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
    public static boolean cutscene = false;
    
    
    public Canvas(Window window) {
        window.addMouseListener(new KeyHandler());
        window.addMouseMotionListener(new KeyHandler());
        
        gameLoop.start();
    }
    
    public void define() {
        save = new Save();
        room = new Room();
        store = new Store();
        
        
        
        for(int i = 0; i < tileset_ground.length; i++) {
            tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
            tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 64*i, 64, 64)));
        }
        for(int i = 0; i < tileset_air.length; i++) {
            tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
            tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 64*i, 64, 64)));
        }
        tileset_res[0] = new ImageIcon("res/cell.png").getImage();
        tileset_res[1] = new ImageIcon("res/heart.png").getImage();
        tileset_res[2] = new ImageIcon("res/coin.png").getImage();
        tileset_mob[0] = new ImageIcon("res/mob.png").getImage();
        
        save.loadSave(new File("save/mission1.sav"));
        
        for(int i = 0;i< mobs.length;i++) {
            mobs[i] = new Mob();
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if(isFirst) {
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
            if(mobs[i].inGame) {
                mobs[i].draw(g);
            }
        }
        
        store.draw(g); //Draws the store.
        
        if(health < 1) {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, myWidth, myHeight);
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Courier New", Font.BOLD,36));
            g.drawString("GAME OVER",220 ,300 );
            try {
                if(!cutscene) {
                    cutscene = true;
                } 
            } catch(Exception e) {}
        }
    }
    
    public int spawnTime = 2500, spawnFrame= 0;
    
    public void mobSpawner() {
        if(spawnFrame >= spawnTime) {
            for(int i = 0; i < mobs.length; i++) {
                if(!mobs[i].inGame) {
                    mobs[i].spawnMob(Value.mobBee);
                    break;
                }
            }
            
            spawnFrame = 0;
        } else {
            spawnFrame+= 1;
        }
    }

    @Override
    public void run() {
        while(true){
            if(!isFirst && health > 0) {
                room.physics();
                mobSpawner();
                for(int i = 0; i < mobs.length; i++) {
                    if(mobs[i].inGame) {
                        mobs[i].physics();
                    }
                }
            }
            repaint();
            try {
                gameLoop.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
