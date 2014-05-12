package TD.Client.Game;

import java.awt.*;

/**
 *
 * @author Liam Gomez <liamgomez75@gmail.com>
 */
public class Mob extends Rectangle {

    public int xCoord, yCoord;
    public int mobSize = 64;
    public int mobWalk = 0;
    public int upward = 0, downward = 1, right = 2, left = 3;
    public int direction = right;
    public int mobID = Value.mobAir;
    public boolean inGame = false;
    public boolean hasUpward = false;
    public boolean hasDownward = false;
    public boolean hasLeft = false;
    public boolean hasRight = false;

    public Mob() {
    }

    public void spawnMob(int mobID) {
        for (int y = 0; y < Canvas.room.block.length; y++) {
            if (Canvas.room.block[y][0].groundID == Value.groundRoad) {
                setBounds(Canvas.room.block[y][0].x, Canvas.room.block[y][0].y, mobSize, mobSize);
                xCoord = 0;
                yCoord = y;
            }
        }
        this.mobID = mobID;
        inGame = true;
    }
    public int walkFrame = 0, walkSpeed = 40;

    public void physics() {
        if (walkFrame >= walkSpeed) {
            move();
            fullTileCheck();
            changeDirection();
            walkFrame = 0; 
        } else {
                walkFrame += 1;
        }

    }

    
        

    private void fullTileCheck() {
        if (mobWalk == Canvas.room.blockSize) {
            if (direction == right) {
                xCoord += 1;
                hasRight = true;


            } else if (direction == upward) {
                yCoord -= 1;
                hasUpward = true;
            } else if (direction == downward) {
                yCoord += 1;
                hasDownward = true;
            } else if (direction == left) {
                xCoord -= 1;
                hasLeft = true;
            }
            mobWalk = 0;
            
        }
        
    }

    private void move() {
        if (direction == right) {
            x += 1;
        } else if (direction == upward) {
            y -= 1;
        } else if (direction == downward) {
            y += 1;
        } else if (direction == left) {
            x -= 1;
        }
        mobWalk += 1;
    }

    private void changeDirection() {
        System.out.println("Hasdownward : " + hasDownward);
        System.out.println("HasUpward : " + hasUpward);
        if (!hasDownward) { 
            try {
                if (Canvas.room.block[yCoord + 1][xCoord].groundID == Value.groundRoad) {
                    direction = downward;
                }
            } catch (Exception e) {}
        }


        if (hasUpward) {
            try {
                if (Canvas.room.block[yCoord - 1][xCoord].groundID == Value.groundRoad) {
                    direction = upward;
                }
            } catch (Exception e) {}
        }
        if (hasRight) {

            try {
                if (Canvas.room.block[yCoord][xCoord + 1].groundID == Value.groundRoad) {
                    direction = right;
                    
                    
                }
            } catch (Exception e) {}
        }

        if (hasLeft) {

            try {
                if (Canvas.room.block[yCoord][xCoord - 1].groundID == Value.groundRoad) {
                    direction = left;
                    
                }
            } catch (Exception e) {}
        }
        hasUpward = false;
        hasDownward = false;
        hasLeft = false;
        hasRight = false;
    }


public void draw(Graphics g) {
        g.drawImage(Canvas.tileset_mob[mobID],x ,y ,width ,height , null);
    }
}
