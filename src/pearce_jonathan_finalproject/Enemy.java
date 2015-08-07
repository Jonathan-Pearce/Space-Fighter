/*
 * Jonathan Pearce
 * ICS3U-1
 * Final Project - Space Fighter
 * May 23, 2014
 */
package pearce_jonathan_finalproject;

import java.awt.image.BufferedImage;


public class Enemy {

    //Integer to keep track of enemies' coordinates
    public int enemyX = 0;
    public int enemyY = 0;
    //boolean to keep track of whether enemy is active and whether it has active bullets
    public boolean active = false;
    //boolean to keep track of whether enemy is active
    public boolean shipActive = false;
    public BufferedImage img;
    //Timer for each of enemy to determine when it will shoot a bullet
    public int fireTimer = 0;
    //Sets up an array of bullets for each enemy
    public Enemy_Bullet[] bullet = new Enemy_Bullet[20];
    public int bulletCounter = 0;
    
    //Declares each bullet and allows each array of bullets to access Enemy_Bullet class
    public Enemy() {
        for (int i = 0; i < 20; i++) {
            bullet[i] = new Enemy_Bullet();
        }
    }

    //Animates enemy
    public void animate() {
        //counter += 0.1;
        enemyY += 4;
        //x = (int)(300 * Math.sin(counter) + basey);
    }

    //removes enemy from gamePanel
    public void deactivate() {
        active = false;
        enemyX = 0;
        enemyY = 0;
    }

    //activates bullet in enemy bullet array
    public void activateBullet() {
        bullet[bulletCounter].activate();
        bullet[bulletCounter].enemyBulletX = enemyX - 3;
        bullet[bulletCounter].enemyBulletY = enemyY + img.getHeight()/2;
        bulletCounter++;
        if (bulletCounter >= 20) {
            bulletCounter = 0;
        }
    }

    //deactivates enemy bullets
    public void deactivateBullet(int num) {
        bullet[num].deactivate();
    }

    //checks whether enemyBullet is active
    public boolean bulletActive(int num) {
        if (bullet[num].active) {
            return true;
        }
        return false;
    }

    //returns enemyBulletX
    public int getX(int num) {
        return bullet[num].enemyBulletX;
    }

    //returns enemyBulletY
    public int getY(int num) {
        return bullet[num].enemyBulletY;
    }

    //regular bullet animation
    public void animateBullet(int num) {
        bullet[num].animate();
    }
    //Sin function bullet animation
    public void sinAnimateBullet(int num, int baseX){
        bullet[num].sinAnimate(baseX);
    }
    //track ship bullet animation
    public void trackAnimateBullet(int num, int x, int y){
        bullet[num].trackerAnimate(x,y);
    }
    //checks whether all bullets are inactive
    public boolean bulletsInactive(){
        for (int i = 0; i < 20; i++) {
            if(bullet[i].active){
                return false;
            }
        }
        return true;
    }
    //removes enemy from gamePanel
    //-300 values are to ensure unwanted bullets don't effect game
    public void shipDeactivate(){
        shipActive = false;
        enemyX = -300;
        enemyY = -300;
    }
}
