package TD.Client.Game;


/**
 *
 * @author Liam Gomez <liamgomez75@gmail.com>
 */
import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {

    public static String title = "Working The Mines";
    public static Dimension size = new Dimension(640, 620);
    public static boolean gameStarted =false;
    
    
    
    //The constructor sets the title, size , exit operation, and the location of the window.
    public Window() {
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }
    
    //This method initializes the grid in the window and the canvas.
    public void init() {
        setLayout(new GridLayout(1,1,0,0));
            Canvas canvas = new Canvas(this);
            add(canvas);
        
        setVisible(true);
        
        
    }
    
    //This method creates a window and starts the game.
    public static void main(String[] args) {
        Window window = new Window();
        
    }
}
