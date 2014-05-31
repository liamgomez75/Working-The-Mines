
package TD.Client.Game;

import java.awt.*;

/**
 *
 * @author Liam Gomez <liamgomez75@gmail.com>
 */
public class Room {
    
    public int worldWidth = 9;
    public int worldHeight = 7;
    public int blockSize = 64;
    public Block[][] block;
    
    public Room() {
        define();
    }
    
    //This method is used to create the map of the game.
    public void define() {
        block = new Block[worldHeight][worldWidth];// This creates a grid of Block objects
        
        for(int y = 0; y < block.length; y++) {
            for(int x =0; x < block[0].length; x++) { //Values are assigned to each block on the map so that they can be easily identified.
                block[y][x] = new Block((Canvas.myWidth/2) - (worldWidth*blockSize/2) + (x*blockSize), y*blockSize, blockSize, blockSize, Value.groundGrass, Value.airAir);
            }
        }
    }
    
    //This method is used to call the physics method of each block on the map.
    public void physics() {
        for(int y = 0;y < block.length;y++) {
            for(int x = 0; x < block[0].length;x++) {
                block[y][x].physics();
            }
        }
    }
    
    //This method handles all of the graphics and draws out each block onto the map.
    public void draw(Graphics g) {
        for(int y = 0; y < block.length; y++) {
            for(int x =0; x < block[0].length; x++) {
                block[y][x].draw(g);
            }
        }
        
        /* This for loop is used in order to provide a seperate instance for the graphics dealing with combat
        * This is done to avoid the overlapping of graphics and to eliminate
        *  issues with collision detection.
        */
        for(int y = 0; y < block.length; y++) {
            for(int x =0; x < block[0].length; x++) {
                block[y][x].combatGraphics(g);
            }
        }
    }
}
