/*
 * Jonathan Pearce
 * ICS3U-1
 * Final Project - Space Fighter
 * May 23, 2014
 */
package pearce_jonathan_finalproject;


public class Ship {

    //Integers to keep track of ship's coordinates
    int shipX = 100;
    int shipY = 400;
    //boolean to keep track of whether ship is active
    boolean active = true;
    //integer to determine speed of ship
    int movementSpeed = 5;
    //booleans to check whether user is moving ship
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
    //approximiate dimensions of ship
    int shipWidth = 57;
    int shipHeight = 74;

    //moves ship left
    public void moveLeft() {
        if (shipX > 0 + shipWidth / 2) {
            //System.out.println(shipX);
            shipX -= movementSpeed;
        }
    }
    //moves ship right

    public void moveRight() {
        if (shipX < (600 - shipWidth / 2)) {
            //System.out.println(shipX);
            shipX += movementSpeed;
        }
    }
    //moves ship up

    public void moveUp() {
        if (shipY > 80) {
            //System.out.println(shipY);
            shipY -= movementSpeed;
        }
    }
    //moves ship down

    public void moveDown() {
        if (shipY < (768 - shipHeight)) {
            //System.out.println(shipY);
            shipY += movementSpeed;
        }
    }

    //returns X coordinate of ship
    public int getShipX() {
        return shipX;
    }

    //returns Y coordinate of ship
    public int getShipY() {
        return shipY;
    }
}
