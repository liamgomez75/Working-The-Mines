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
    
    
    public Block (int x, int y, int width, int height, int groundID, int airID) {
        setBounds(x,y,width,height);
        towerRange = new Rectangle(x - (towerRangeSize/2),y-(towerRangeSize/2),width + towerRangeSize,height + towerRangeSize);
        this.groundID = groundID;
        this.airID = airID;
    }
    
    public void physics() {
        if(shotMob != -1 && towerRange.intersects(Canvas.mobs[shotMob])) {
            firing = true;
        } else {
            firing = false;
        }
        
        if(!firing) {
            if(airID == Value.airTowerLaser) {
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
            
            if(loseFrame >= loseTime) {
                Canvas.mobs[shotMob].takeDamage(1);
                loseFrame = 0;
            } else {
                loseFrame += 1;
            }
            
            if(Canvas.mobs[shotMob].isDead()) {
                firing = false;
                shotMob = -1;
                Canvas.kills += 1;
                Canvas.hasWon();
                
            }
        }
         
    }
    
    public void getLoot(int mobID) {
        Canvas.coinAmount += Value.loot[mobID];
    }
    
    public void draw(Graphics g) {
        g.drawImage(Canvas.tileset_ground[groundID], x, y, width, height, null);
        
        if(airID != Value.airAir) {
            g.drawImage(Canvas.tileset_air[airID], x, y, width, height, null);
        }
        
    }
    
    public void combatGraphics(Graphics g) {
        if(Canvas.isDebug) {
            if(airID == Value.airTowerLaser) {
                g.setColor(Color.RED);
                g.drawRect(towerRange.x,towerRange.y,towerRange.width,towerRange.height);
            }
        }
        
        if(firing) {
            g.setColor(Color.CYAN);
            g.drawLine(x + (width/2), y + (height/2), Canvas.mobs[shotMob].x + (Canvas.mobs[shotMob].width/2),Canvas.mobs[shotMob].y + (Canvas.mobs[shotMob].height/2));
        }
    }
}
