/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TD.Client.Game;

import java.awt.*;

/**
 *
 * @author Liam Gomez <liamgomez75@gmail.com>
 */
public class Block  extends Rectangle {
    public int groundID;
    public int airID;
    public int towerRangeSize = 150;
    public int loseFrame = 0, loseTime = 100;
    public Rectangle towerRange;
    public int shotMob = -1;
    public boolean firing = false;
    
    //The constructor initializes the Block object.
    public Block (int x, int y, int width, int height, int groundID, int airID) {
        setBounds(x,y,width,height); // Sets the dimensions of the block.
        towerRange = new Rectangle(x - (towerRangeSize/2),y-(towerRangeSize/2),width + towerRangeSize,height + towerRangeSize);//creates a rectangle in which the tower can fire within.
        this.groundID = groundID;// Sets the ID of the block.
        this.airID = airID;
        if(airID == Value.airDrill) {
            towerRange = new Rectangle(x - (towerRangeSize/2),y-(towerRangeSize/2),width + towerRangeSize*2,height + towerRangeSize*2);
        }
    }
    
    //This method handles all of the combat physics for blocks with towers.
    public void physics() {
        if(shotMob != -1 && towerRange.intersects(Canvas.mobs[shotMob])) { // Checks if the mob has entered the range of the tower.
            firing = true;
        } else {
            firing = false;
        }
        //Tells the tower to fire if a mob has entered its range.
        if(!firing) {
            if(airID == Value.airTowerLaser || airID == Value.airDrill) {
                for(int i = 0;i<Canvas.mobs.length;i++) {
                    if(Canvas.mobs[i].inGame) {
                        if(towerRange.intersects(Canvas.mobs[i])) {
                            firing = true;
                            shotMob = i;
                        }
                    }
                }
            }
        }
        
        if(firing) {
            //Tells the mob to lose health if he is shot by the tower.
            if(loseFrame >= loseTime) {
                if(airID == 2){
                    Canvas.mobs[shotMob].takeDamage(2);
                    loseFrame = 0;
                }
                else {
                    Canvas.mobs[shotMob].takeDamage(1);
                    loseFrame = 0;
                }
            } else {
                loseFrame += 1;
            }
            
            if(Canvas.mobs[shotMob].isDead()) { // Handles the death of a mob.
                firing = false;
                shotMob = -1;
            }
        }
         
    }
    
    //This method assigns a reward to the player for killing the mob.
    public void getLoot(int mobID) {
        Canvas.coinAmount += Value.loot[mobID];
    }
    
    //This method handles the graphics for the block.
    public void draw(Graphics g) {
        g.drawImage(Canvas.tileset_ground[groundID], x, y, width, height, null); // Draws the ground image of the block.
        
        if(airID != Value.airAir) {
            g.drawImage(Canvas.tileset_air[airID], x, y, width, height, null); // Draws the air image of the block.
        }
        
    }
    
    //This method handles all of the graphics dealing with combat for towers.
    public void combatGraphics(Graphics g) {
        if(Canvas.isDebug) { // Draws the range of the tower onto the screen if debug mode is enabled.
            if(airID == Value.airTowerLaser) {
                g.setColor(Color.RED);
                g.drawRect(towerRange.x,towerRange.y,towerRange.width,towerRange.height);
            }
        }
        
        if(firing) { // Draws the laser that is aimed towards a mob.
            g.setColor(Color.CYAN);
            g.drawLine(x + (width/2), y + (height/2), Canvas.mobs[shotMob].x + (Canvas.mobs[shotMob].width/2),Canvas.mobs[shotMob].y + (Canvas.mobs[shotMob].height/2));
        }
    }
}
