package TD.Client.Game;

import java.awt.*;

/**
 *
 * @author Liam Gomez <liamgomez75@gmail.com>
 */
public class Mob extends Rectangle {

    public int xCoord, yCoord;
    public int health;
    public int healthSpace = 3, healthHeight = 6;
    public int mobSize = 64;
    public int mobWalk = 0;
    public int upward = 0, downward = 1, right = 2, left = 3;
    public int walkFrame = 0, walkSpeed = 40;
    public int direction = right;
    public int mobID = Value.mobAir;
    public boolean inGame = false;

    public Mob() {
    }

    
    /* This method iterates through every block on the first row of the map
    * and spawns the mob on the first block with the groundID of groundRoad. 
    */
    public void spawnMob(int mobID) {
        for (int y = 0; y < Canvas.room.block.length; y++) {
            if (Canvas.room.block[y][0].groundID == Value.groundRoad) {
                setBounds(Canvas.room.block[y][0].x, Canvas.room.block[y][0].y, mobSize, mobSize);
                xCoord = 0;// xCoord and yCoord are the coordinates of the tiles while x and y are the pixel coordinates
                yCoord = y;
            }
        }
        this.mobID = mobID;
        this.health = mobSize;// The health is based on how many pixels there are in the width of the mob's image.
        inGame = true;
        
    }
    
    // This method is used to remove the mob from the game.
    public void deleteSelf() {
        inGame = false;
        direction = right;
        mobWalk = 0;
        if(health<=0) { //This is to make sure that the mob was killed by the player and not removed by touching the end goal.
            Canvas.room.block[0][0].getLoot(mobID);//Assigns the player a reward for killing the mob.
        }
    }
    
    //This method deals damage and takes away one of the players lives.
    public void dealDamage() {
        Canvas.health-=1;
    }

    //This method handles all of the movement and actions of the mob.
    public void physics() {
        //walkFrame and walkSpeed are used to set the speed of how often the mob will move.
        if (walkFrame >= walkSpeed) {
            move();
            fullTileCheck();

            //This portion of the method handles the direction changes of the mob if it has reached a turn.
            final int oppositeDirection = getOppositeDirection(direction);
            for (int turnsMade = 0; turnsMade < 4; turnsMade++) {
                if (isDirectionClear() && ((turnsMade == 0) || (direction != oppositeDirection))) {
                    break;
                } else {
                    turnClockwise();
                }   
            }
            
            if(Canvas.room.block[yCoord][xCoord].airID == Value.airCage ) {
                deleteSelf();//If the mob has reached the end goal it will remove 
                dealDamage();//itself from the game and deal 1 damage to the players lives.
            }
            
            

            walkFrame = 0; //walkFrame is reset to 0 everytime the mob moves.
        } else {
            walkFrame += 1;
        }
    }

    //this method moves the mob by either increasing or decreasing its pixel position on the map.
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
        mobWalk += 1;//mobWalk is used to show how many pixels the mob has traveled.
    }

    private void fullTileCheck() {
        if (mobWalk == Canvas.room.blockSize) { //if the amount of pixels is the same as the block size
            if (direction == right) {// then the mob will have moved one full block.
                xCoord += 1; //If the mob has moved a full tile it will increment or decrement either the xCoord or yCoord based on the direction it is moving.
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

    //This method is used to get the opposite direction that the mob is traveling in.
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
            return 4; // 4 is an unused direction and will never be returned.
        }
    }

    //This method is used to check if the blocks around the mob are a part of the road path.
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
                        return false;// Returns false is the block next to him is not of groundRoad.
                    }
                }
            } catch (Exception e) {}
        
        return true;
    }

    //This method is used to turn the mob counter clockwise based on the direction he is moving..
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
    
    //This method is used to handle damage taken by the mob.
    public void takeDamage(int amount) {
        health -= amount;
        checkDeath();
    }
    
    //This method is used to check if the mob is dead or not.
    public void checkDeath() {
        if(health <=0) {
            deleteSelf();
        }
            
    }
    
    //This method is used to check whether or not if the mob is alive and in the game or not.
    public boolean isDead() {
        if(inGame) {
            return false;
        }else{
            return true;
        }
    }

    //This method is used to draw all of the graphics for the mob.
    public void draw(Graphics g) {
        g.drawImage(Canvas.tileset_mob[mobID], x, y, width, height, null);
        
        //This is the red background of the healthbar.
        g.setColor(new Color(200,50,50));
        g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

        //This is the top green layer of the health bar.
        g.setColor(new Color(50,180,50));
        g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);
        
        //This is the black outline surrounding the health bar.
        g.setColor(new Color(0,0,0));
        g.drawRect(x, y - (healthSpace + healthHeight), health-1, healthHeight-1);
    }
}
