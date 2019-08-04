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
    public static int iconSpace = 3;
    public static int iconYSpace = 13;
    public static int heldID = -1;
    public static int realID = -1;
    public static int[] buttonID = {Value.airTowerLaser,Value.airDrill,Value.airAir,Value.airAir,Value.airAir,Value.airAir,Value.airAir,Value.airTrash};
    public static int itemBorder = 4;
    public static int[] buttonPrice = {10,100,0,0,0,0,0,0};
    
    public static boolean holdingItem = false;
    
    public Store() {
        define();
    }
    
    //This method handles what happens when a player clicks on a button in the store.
    public void click(int mouseButton) {
        if(mouseButton == 1) {
            for(int i = 0;i < button.length;i++) {
                if(button[i].contains(Canvas.mse)) {
                    if(buttonID[i] != Value.airAir) {
                        if(buttonID[i] == Value.airTrash) { //Deletes the current item held.
                            holdingItem = false;
                        } else {
                            heldID= buttonID[i]; // Sets the held item by the player.
                            realID=i;
                            holdingItem = true;
                        }
                    }
                    
                    
                }
            }
            if(holdingItem) { // Handles tower placement on the map and shop transactions.
                if(Canvas.coinAmount >= buttonPrice[realID]) {
                    for(int y = 0; y<Canvas.room.block.length;y++) {
                        for(int x = 0; x<Canvas.room.block[0].length;x++) {
                            if(Canvas.room.block[y][x].contains(Canvas.mse)) {
                                if(Canvas.room.block[y][x].groundID != Value.groundRoad && Canvas.room.block[y][x].airID == Value.airAir) {
                                    Canvas.room.block[y][x].airID = heldID;
                                    Canvas.coinAmount -= buttonPrice[realID];
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    // This method creates the shop and all of its buttons.
    public void define() {
        for(int i = 0; i<button.length; i++) {
            button[i] = new Rectangle((Canvas.myWidth/2) - ((shopWidth*(buttonSize + cellSpace))/2) + ((buttonSize + cellSpace)*i), (Canvas.room.block[Canvas.room.worldHeight -1][0].y) + Canvas.room.blockSize + awayFromRoom , buttonSize, buttonSize);
        }
        buttonHealth = new Rectangle(Canvas.room.block[0][0].x+25,button[0].y+70, iconSize, iconSize); // This creates the players health.
        buttonMoney = new Rectangle(Canvas.room.block[0][0].x+25,button[0].y+10 + button[0].height+iconSize, iconSize, iconSize); // This creates the players money.
    }
    
    //This method handles all of the graphics involving the shop and it draws the shop.
    public void draw(Graphics g) {
        for(int i = 0; i<button.length; i++) {
            if(button[i].contains(Canvas.mse)) { // This creates the effect of a highlighted button on mouseover.
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }
            
            g.drawImage(Canvas.tileset_res[0] ,button[i].x, button[i].y, button[i].width, button[i].height, null);
            if(buttonID[i] != Value.airAir) 
                g.drawImage(Canvas.tileset_air[buttonID[i]],button[i].x + itemBorder, button[i].y + itemBorder, button[i].width - (itemBorder*2), button[i].height-(itemBorder*2), null); // Draws the item in the button.
            if(buttonPrice[i] > 0) {
                g.setColor(new Color(255,255,255));
                g.setFont(new Font("Courier New", Font.BOLD,14));
                g.drawString("$" + buttonPrice[i],button[i].x + itemBorder,button[i].y + itemBorder + 10); // Draws the price of the tower within the button.
                
            }
                
        }
        //Draws the players health and money.
        g.drawImage(Canvas.tileset_res[1] ,buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(Canvas.tileset_res[2],buttonMoney.x, buttonMoney.y, buttonMoney.width, buttonMoney.height, null);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(255, 255, 255));
        g.drawString("" + Canvas.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + iconYSpace);
        g.drawString("" + Canvas.coinAmount, buttonMoney.x + buttonMoney.width + iconSpace, buttonMoney.y + iconYSpace);
        g.drawString("Ores Left: " + Canvas.ores, (buttonMoney.x + buttonMoney.width + iconSpace)*6, buttonMoney.y + iconYSpace);
        
        //Draws the item that the player is holding.
        if(holdingItem) {
            g.drawImage(Canvas.tileset_air[heldID], Canvas.mse.x - ((button[0].width - (itemBorder*2))/2) + itemBorder, Canvas.mse.y - ((button[0].height - (itemBorder*2))/2) + itemBorder, button[0].width - (itemBorder*2), button[0].height-(itemBorder*2), null);
        }
    }
}
