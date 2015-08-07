/*
 * Jonathan Pearce
 * ICS3U-1
 * Final Project - Space Fighter
 * May 23, 2014
 */
package pearce_jonathan_finalproject;

import java.awt.image.BufferedImage;


public class Enemy_Bullet {

    //Integers to keep track of bullets coordinates
    public int enemyBulletX = 0;
    public int enemyBulletY = 0;
    //boolean to keep track of whether bullet is active or not
    public boolean active = false;
    public BufferedImage img;
    //declares user and ship and allows access to user and ship class
    User user = new User();
    Ship ship = new Ship();

    //regular bullet animation
    public void animate() {
        enemyBulletY += speed();
    }

    //Sin function bullet animation
    public void sinAnimate(int baseX) {
        enemyBulletY += speed();
        enemyBulletX = (int) (Math.sin(enemyBulletY) * (10 + (user.level * 5))) + baseX;
    }

    //track ship bullet animation
    public void trackerAnimate(int x, int y) {
        enemyBulletY += speed();
        if (enemyBulletX < x) {
            enemyBulletX += 2 + (user.level * 2);
        } else if (enemyBulletX > x) {
            enemyBulletX -= 2 + (user.level * 2);
        }
    }

    //deactivates bullet
    public void deactivate() {
        active = false;
        enemyBulletX = 0;
        enemyBulletY = 0;
    }

    //activates bullet
    public void activate() {
        active = true;
    }

    //determines speed of bullet
    public int speed() {
        return (8 + user.level);
    }
}
