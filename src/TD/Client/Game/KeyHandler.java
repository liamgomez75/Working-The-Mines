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
    public void mouseDragged(MouseEvent e) {
        Canvas.mse = new Point((e.getX()) + ((Window.size.width - Canvas.myWidth)/2), (e.getY()) + ((Window.size.height - (Canvas.myHeight)) - (Window.size.width - Canvas.myWidth)/2));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Canvas.mse = new Point((e.getX()) - ((Window.size.width - Canvas.myWidth)/2), (e.getY()) - ((Window.size.height - (Canvas.myHeight)) - (Window.size.width - Canvas.myWidth)/2));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Canvas.store.click(e.getButton());
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
