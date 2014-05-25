
package TD.Client.Game;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */

public class Save {
    public void loadSave(File loadPath) {
        try {
            Scanner loadScanner = new Scanner(loadPath);
            
            while(loadScanner.hasNext()) {
                Canvas.killsToWin = loadScanner.nextInt();
                for(int y = 0; y < Canvas.room.block.length; y++) {
                    for(int x = 0; x < Canvas.room.block[0].length; x++) {
                        Canvas.room.block[y][x].groundID = loadScanner.nextInt();
                    }
                }
                for(int y = 0; y < Canvas.room.block.length; y++) {
                    for(int x = 0; x < Canvas.room.block[0].length; x++) {
                        Canvas.room.block[y][x].airID = loadScanner.nextInt();
                    }
                }
            }
            loadScanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
