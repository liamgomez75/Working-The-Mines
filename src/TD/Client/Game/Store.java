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
    public Rectangle buttonHealth;
    public Rectangle buttonMoney;
    public static int buttonSize = 64;
    public static int cellSpace = 2;
    public static int awayFromRoom = 20;
    public static int iconSize = 20;
    
    public Store() {
        define();
    }
    
    public void define() {
        for(int i = 0; i<button.length; i++) {
            button[i] = new Rectangle((Canvas.myWidth/2) - ((shopWidth*(buttonSize + cellSpace))/2) + ((buttonSize + cellSpace)*i), (Canvas.room.block[Canvas.room.worldHeight -1][0].y) + Canvas.room.blockSize + awayFromRoom , buttonSize, buttonSize);
        }
        buttonHealth = new Rectangle(Canvas.room.block[0][0].x+25,button[0].y+70, iconSize, iconSize);
        buttonMoney = new Rectangle(Canvas.room.block[0][0].x+25,button[0].y+10 + button[0].height+iconSize, iconSize, iconSize);
    }
    
    public void draw(Graphics g) {
        for(int i = 0; i<button.length; i++) {
            if(button[i].contains(Canvas.mse)) {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }
            
            g.drawImage(Canvas.tileset_res[0] ,button[i].x, button[i].y, button[i].width, button[i].height, null);
        }
        g.drawImage(Canvas.tileset_res[1] ,buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(Canvas.tileset_res[2],buttonMoney.x, buttonMoney.y, buttonMoney.width, buttonMoney.height, null);
    }
}
