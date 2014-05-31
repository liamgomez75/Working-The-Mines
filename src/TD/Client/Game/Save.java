
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
    // This method reads and loads the map into the game from a save file.
    public void loadSave(File loadPath) {
        try {
            Scanner loadScanner = new Scanner(loadPath);
            
            while(loadScanner.hasNext()) {
                Canvas.killsToWin = loadScanner.nextInt(); // Obtains the needed amount of kills to beat the level from the save file.
                for(int y = 0; y < Canvas.room.block.length; y++) {
                    for(int x = 0; x < Canvas.room.block[0].length; x++) {
                        Canvas.room.block[y][x].groundID = loadScanner.nextInt(); // Creates the map from block IDs stored in the save file.
                    }
                }
                for(int y = 0; y < Canvas.room.block.length; y++) {
                    for(int x = 0; x < Canvas.room.block[0].length; x++) { // Creates the air section of the map from block IDs stored in the save file.
                        Canvas.room.block[y][x].airID = loadScanner.nextInt();
                    }
                }
            }
            loadScanner.close();
        } catch (FileNotFoundException ex) { // Handles the error if the save file is missing.
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
