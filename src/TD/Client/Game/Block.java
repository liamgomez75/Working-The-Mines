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
    
    public Block (int x, int y, int width, int height, int groundID, int airID) {
        setBounds(x,y,width,height);
        this.groundID = groundID;
        this.airID = airID;
    }
    
    public void draw(Graphics g) {
        g.drawImage(Canvas.tileset_ground[groundID], x, y, width, height, null);
        
        if(airID != Value.airAir) {
            g.drawImage(Canvas.tileset_air[airID], x, y, width, height, null);
        }
    }
}
