/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TD.Client.Game;

import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author Liam Gomez<liamgomez75@gmail.com>
 */
public class KeyHandler implements MouseMotionListener, MouseListener {

    @Override
    public void mouseDragged(MouseEvent e) { // This method is used to get the position of the mouse at all times.
        Canvas.mse = new Point((e.getX()) + ((Window.size.width - Canvas.myWidth)/2), (e.getY()) + ((Window.size.height - (Canvas.myHeight)) - (Window.size.width - Canvas.myWidth)/2));
    }

    @Override
    public void mouseMoved(MouseEvent e) { // This method is used to get the position of the mouse at all times.
        Canvas.mse = new Point((e.getX()) - ((Window.size.width - Canvas.myWidth)/2), (e.getY()) - ((Window.size.height - (Canvas.myHeight)) - (Window.size.width - Canvas.myWidth)/2));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) { // Checks if the player has clicked on a button and returns the clicked button.
        Canvas.store.click(e.getButton());
        Canvas.click(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
