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
    public int walkFrame = 0, walkSpeed = 40;
    public int direction = right;
    public int mobID = Value.mobAir;
    public boolean inGame = false;

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
    
    public void deleteSelf() {
        inGame = false;
    }
    
    public void dealDamage() {
        Canvas.health-=1;
    }

    public void physics() {
        if (walkFrame >= walkSpeed) {
            move();
            fullTileCheck();

            final int oppositeDirection = getOppositeDirection(direction);
            for (int turnsMade = 0; turnsMade < 4; turnsMade++) {
                if (isDirectionClear() && ((turnsMade == 0) || (direction != oppositeDirection))) {
                    break;
                } else {
                    turnClockwise();
                }   
            }
            
            if(Canvas.room.block[yCoord][xCoord].airID == Value.airCage ) {
                deleteSelf();
                dealDamage();
            }
            
            

            walkFrame = 0;
        } else {
            walkFrame += 1;
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

    private void fullTileCheck() {
        if (mobWalk == Canvas.room.blockSize) {
            if (direction == right) {
                xCoord += 1;
            } else if (direction == upward) {
                yCoord -= 1;
            } else if (direction == downward) {
                yCoord += 1;
            } else if (direction == left) {
                xCoord -= 1;
            }
            mobWalk = 0;
        }
    }

    private int getOppositeDirection(int direction) {
        if (direction == upward) {
            return downward;
        } else if (direction == downward) {
            return upward;
        } else if (direction == left) {
            return right;
        } else if (direction == right) {
            return left;
        } else {
            return 4;
        }
    }

    private boolean isDirectionClear() {
        try {
                if (direction == downward) {
                    if (Canvas.room.block[yCoord + 1][xCoord].groundID != Value.groundRoad) {
                        return false;
                    }
                } else if (direction == upward) {
                    if (Canvas.room.block[yCoord - 1][xCoord].groundID != Value.groundRoad) {
                        return false;
                    }
                } else if (direction == right) {
                    if (Canvas.room.block[yCoord][xCoord + 1].groundID != Value.groundRoad) {
                        return false;
                    }
                } else if (direction == left) {
                    if (Canvas.room.block[yCoord][xCoord - 1].groundID != Value.groundRoad) {
                        return false;
                    }
                }
            } catch (Exception e) {}
        
        return true;
    }

    private void turnClockwise() {
        if (direction == upward) {
            direction = right;
        } else if (direction == right) {
            direction = downward;
        } else if (direction == downward) {
            direction = left;
        } else if (direction == left) {
            direction = upward;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(Canvas.tileset_mob[mobID], x, y, width, height, null);
    }
}
