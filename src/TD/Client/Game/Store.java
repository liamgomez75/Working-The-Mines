/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TD.Client.Game;

import java.awt.*;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public class Store {
    
    public static int shopWidth = 8;
    public Rectangle[] button = new Rectangle[shopWidth];
    public static int buttonSize = 64;
    public static int cellSpace = 2;
    public static int awayFromRoom = 20;
    
    public Store() {
        define();
    }
    
    public void define() {
        for(int i = 0; i<button.length; i++) {
            button[i] = new Rectangle((Canvas.myWidth/2) - ((shopWidth*(buttonSize + cellSpace))/2) + ((buttonSize + cellSpace)*i), (Canvas.room.block[Canvas.room.worldHeight -1][0].y) + Canvas.room.blockSize + awayFromRoom , buttonSize, buttonSize);
        }
    }
    
    public void draw(Graphics g) {
        for(int i = 0; i<button.length; i++) {
            g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
        }
    }
}
