/*
 * Jonathan Pearce
 * ICS3U-1
 * Final Project - Space Fighter
 * May 23, 2014
 */
package main;

import java.awt.image.BufferedImage;


public class Bullet {
    //Integer to keep track of bullet's coordinates
    public int bulletX = 0;
    public int bulletY = 0;
    //boolean to keep track of whether bullet needs to be drawn to gamePanel
    public boolean active = false;
    public BufferedImage img;
    
    //animates bullet, moves it upwards
    public void animate(){
        bulletY -= 10;
    }
    //removes bullet from gamePanel
    public void deactivate(){
        active = false;
        bulletX = 0;
        bulletY = 0;
    }
        
}
