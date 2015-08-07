/*
 * Jonathan Pearce
 * ICS3U-1
 * Final Project - Space Fighter
 * May 23, 2014
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener {

    //Creates Images, declares image names
    BufferedImage imgBackground;
    BufferedImage imgBullet;
    BufferedImage imgMainCharacter;
    BufferedImage imgEnemyBullet;
    BufferedImage[] imgEnemy = new BufferedImage[3];
    BufferedImage imgMenu;
    BufferedImage imgGameOver;
    BufferedImage imgGameWon;
    //integer to keep track of Y value of background image
    int imgBackgroundY = 0;
    //Booleans to track direction of keypresses
    
    //boolean to keep track of whether user is attempting to shoot
    boolean isShooting = false;
    //Timer to delay enemy spawn
    int counter = 1;
    //creates 30 bullets for user to use
    Bullet[] myBullets = new Bullet[30];
    //integer to keep track of bullets and used to ensure 
    int bulletNum = 0;
    //Array list of enemies and allows enemy class to be accessed by GamePanel
    ArrayList<Enemy> Enemy = new ArrayList();
    //declares ship and user and allows ship and user class to be accessed by GamePanel
    Ship ship = new Ship();
    User user = new User();
    //variables used to keep track of time elapsed in each new game
    double timer = 0;
    int timerCounter = 0;
    //integer to limit fire rate of user's ship
    int shotCounter = 0;
    //creates offScreenG that is used to doublebuffer
    //all images are drawn to offScreenG but not displayed, then offScreenG is displayed itself
    BufferedImage imgOffScreen = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
    Graphics2D offScreenG = (Graphics2D) imgOffScreen.getGraphics();
    String urlBase = "/Users/jaypearce9/Desktop/Math and Computer Science/Summer Projects/VerticalScrollingGame/src/pictures/";
    
    GamePanel() throws IOException, URISyntaxException { //will stop/crash program in case background example cannot be found

        //Creates Bullet for every bullet in the array and allows user's bullets to access Bullet class
        for (int i = 0; i < 30; i++) {
            myBullets[i] = new Bullet();
        }
        
        File sourceImage = new File(urlBase + "background2.png");
        imgBackground = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "Ship_2_1.png");
		imgMainCharacter = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "Ball_3.png");
		imgBullet = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "Enemy_Ball.png");
		imgEnemyBullet = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "Enemy_5.png");
		imgEnemy[0] = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "Enemy_2_1.png");
        imgEnemy[1] = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "Enemy_7.png");
		imgEnemy[2] = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "Title_Screen.png");
		imgMenu = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "gameOver.png");
		imgGameOver = ImageIO.read(sourceImage);
		sourceImage = new File(urlBase + "game_Won.png");
        imgGameWon = ImageIO.read(sourceImage);

        //Enable all listeners
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        //runs code if user is still on the opening menu
        if (user.mainMenu) {
            //draws menuScreen
            offScreenG.drawImage(imgMenu, 0, 0, this);


        } //runs code if user is playing the game
        else if (user.playing) {
            //Draw 2 backgrounds side by side in order to allow for proper scrolling
            offScreenG.drawImage(imgBackground, 0, imgBackgroundY, this);
            offScreenG.drawImage(imgBackground, 0, imgBackgroundY - imgBackground.getHeight(), this);
            //
            offScreenG.drawImage(imgMainCharacter, ship.shipX - imgMainCharacter.getWidth() / 2, ship.shipY - imgMainCharacter.getHeight() / 2, this);

            //Draws active enemies
            //if enemy has been killed but enemy still has active bullets, bullets will be drawn
            for (int i = 0; i < Enemy.size(); i++) {
                if (Enemy.get(i).shipActive) {
                    offScreenG.drawImage(Enemy.get(i).img, Enemy.get(i).enemyX - Enemy.get(i).img.getWidth() / 2, Enemy.get(i).enemyY - Enemy.get(i).img.getHeight() / 2, this);
                    for (int j = 0; j < 20; j++) {
                        if (Enemy.get(i).bulletActive(j)) {
                            offScreenG.drawImage(imgEnemyBullet, Enemy.get(i).getX(j), Enemy.get(i).getY(j), this);
                        }
                    }
                } else if (!(Enemy.get(i).shipActive) && Enemy.get(i).active) {
                    for (int j = 0; j < 20; j++) {
                        if (Enemy.get(i).bulletActive(j)) {
                            offScreenG.drawImage(imgEnemyBullet, Enemy.get(i).getX(j), Enemy.get(i).getY(j), this);
                        }
                    }
                }
            }
            //draws health bar and specifies colour depending on health level
            offScreenG.setColor(Color.green);
            if (user.health < 40 && user.health > 20) {
                offScreenG.setColor(Color.orange);
            } else if (user.health <= 20) {
                offScreenG.setColor(Color.red);
            }
            offScreenG.fillRect(0, 0, 6 * user.health, 20);

            //Draws user's score
            offScreenG.setColor(Color.white);
            Font scoreFont = new Font("Century Gothic", Font.BOLD, 24);
            offScreenG.setFont(scoreFont);
            offScreenG.drawString("Score: " + user.score, 20, 40);

            //Calculates and draws time of game duration
            timer = Math.round(timerCounter / 60.0 * 10) / 10.0;
            offScreenG.drawString("Time: " + timer, 460, 40);

            //Checks whether user's bullets are active
            //If bullet is active it is drawn to the screen
            for (int i = 0; i < 30; i++) {
                if (myBullets[i].active) {
                    offScreenG.drawImage(myBullets[i].img, myBullets[i].bulletX, myBullets[i].bulletY, this);
                }
            }
            //draws user's level
            offScreenG.drawString("Level: " + user.level, 250, 40);

        } //runs code if game is over 
        else if (user.gameOver) {
            //Draws game over screen and displays score
            offScreenG.drawImage(imgGameOver, 0, 0, this);
            offScreenG.setColor(Color.red);
            Font myFont = new Font("Century Gothic", Font.PLAIN, 48);
            offScreenG.setFont(myFont);
            offScreenG.drawString("Score: " + user.score, 175, 325);
        }//runs code if game is won 
        else if (user.gameWon){
            offScreenG.drawImage(imgGameWon, 0,0, this);
            offScreenG.setColor(Color.red);
            Font myFont = new Font("Century Gothic", Font.PLAIN, 48);
            offScreenG.setFont(myFont);
            offScreenG.drawString("Time: " + timer, 175, 200);
            offScreenG.drawString("Score: " + user.score, 155, 250);
        }
        //Draws all of offScreenG contents to gamePanel
        g.drawImage(imgOffScreen, 0, 0, this);
    }

    public void run() throws InterruptedException, IOException {

        //Runs an infinite loop 60 times a second
        while (true) {
            //runs code if user is playing game
            if (user.playing) {
                //increments counters
                timerCounter++;
                shotCounter++;

                //Scrolls background downwards
                imgBackgroundY += 3;
                //Checks if background evers leaves screen and resets Y value 
                if (imgBackgroundY >= 768) {
                    imgBackgroundY = 0;
                }
                //Checking what direction ship is moving and them moves ship in that direction 
                if (ship.down) {
                    ship.moveDown();
                }
                if (ship.up) {
                    ship.moveUp();
                }
                if (ship.left) {
                    ship.moveLeft();
                }
                if (ship.right) {
                    ship.moveRight();
                }

                //Increases level every 100 points user scores
                if (user.score >= (user.level * 100)) {
                    user.level++;
                }
                if(user.level == 10){
                    user.playing = false;
                    user.gameWon = true;
                }

                //activates bullet if user is trying to shoot
                if (isShooting && shotCounter > 10) {
                    shotCounter = 0;
                    myBullets[bulletNum].active = true;
                    myBullets[bulletNum].bulletX = ship.shipX - 2;
                    myBullets[bulletNum].bulletY = ship.shipY - imgMainCharacter.getHeight() / 2;
                    myBullets[bulletNum].img = imgBullet;
                    bulletNum++;
                    if (bulletNum >= 29) {
                        bulletNum = 0;
                    }
                }

                //Activates enemy if counter is equal to timer value
                if (counter % ((int) 50 - (user.level * 2)) == 0) {
                    counter = 0;
                    Enemy.add(new Enemy());
                    Enemy.get(Enemy.size() - 1).active = true;
                    Enemy.get(Enemy.size() - 1).shipActive = true;
                    //Sets X value to a random number between 60 and 540
                    Enemy.get(Enemy.size() - 1).enemyX = (int) (Math.random() * 480) + 60;
                    //Sets Y value to 0 to start enemy at top of the gamePanel 
                    Enemy.get(Enemy.size() - 1).enemyY = 0;
                    //randomly assigns one of the differnt types of enemies to new enemy
                    int randPic = (int) (Math.random() * 10) + 1;
                    if (randPic < 3) {
                        randPic = 1;
                    } else if (randPic > 8) {
                        randPic = 2;
                    } else {
                        randPic = 0;
                    }
                    Enemy.get(Enemy.size() - 1).img = imgEnemy[randPic];
                }

                //cycles through active enemies and determines whether it is time to fire enemyBullet
                for (int i = 0; i < Enemy.size(); i++) {
                    if (Enemy.get(i).active) {
                        if (Enemy.get(i).fireTimer % (45 - (user.level * 2)) == 0) {
                            Enemy.get(i).fireTimer = 1;
                            Enemy.get(i).activateBullet();
                        }
                    }
                }
                //Collision detection between enemies and user's bullets
                //Score is incremented in different amounts depending on what enemy is hit
                //If enemy is hit it is deactivated
                for (int i = 0; i < Enemy.size(); i++) {
                    if (Enemy.get(i).active) {
                        for (int j = 0; j < 30; j++) {
                            if (myBullets[j].active) {
                                if (Math.sqrt(
                                        (Math.pow(myBullets[j].bulletX - (Enemy.get(i).enemyX), 2)
                                        + (Math.pow(myBullets[j].bulletY - (Enemy.get(i).enemyY + (Enemy.get(i).img.getHeight() / 3)), 2)))) < 40) {
                                    myBullets[j].deactivate();
                                    Enemy.get(i).shipDeactivate();
                                    if (Enemy.get(i).img == imgEnemy[0]) {
                                        user.score += 10;
                                    } else if (Enemy.get(i).img == imgEnemy[1]) {
                                        user.score += 20;
                                    } else {
                                        user.score += 30;
                                    }
                                }
                            }
                        }
                        //Collision detection between enemies and user's ship
                        //If collision, enemy is removed and user's health goes done
                        if (Math.sqrt(
                                (Math.pow((ship.shipX) - (Enemy.get(i).enemyX), 2)
                                + (Math.pow((ship.shipY) - (Enemy.get(i).enemyY), 2)))) < 44) {
                            user.health -= 10;
                            Enemy.get(i).shipDeactivate();
                        }
                    }
                }
                //Collision detection between enemy bullets and user's ship
                //If bullet and ship collide bullet is removed and ship's health decreases
                for (int j = 0; j < Enemy.size(); j++) {
                    if (Enemy.get(j).active) {
                        for (int k = 0; k < 20; k++) {
                            if (Enemy.get(j).bulletActive(k)) {
                                if ((Math.sqrt(
                                        (Math.pow((ship.shipX) - Enemy.get(j).getX(k), 2))
                                        + Math.pow(ship.shipY - Enemy.get(j).getY(k), 2))) < 40) {
                                    user.health -= 5;
                                    Enemy.get(j).deactivateBullet(k);

                                }
                            }
                        }
                    }
                }
                counter++;

                //Randomly determines whether enemy firetimer should be incremented
                //This prevents every enemy from shooting at the same time
                for (int i = 0; i < Enemy.size(); i++) {
                    if ((int) (Math.random() * 2) >= 1) {
                        Enemy.get(i).fireTimer++;

                    }
                }
                //checks whether user's health is greater than zero to determine whether game is over
                if (user.health <= 0) {
                    user.playing = false;
                    user.gameOver = true;
                    System.out.println("Game Over");
                }

                //Animates enemy
                //If enemy leaves screen enemy is deactivated
                for (int i = 0; i < Enemy.size(); i++) {
                    if (Enemy.get(i).active) {
                        Enemy.get(i).animate();
                        if (Enemy.get(i).enemyY > imgBackground.getHeight() + 100) {
                            Enemy.get(i).deactivate();
                        }
                    }
                }
                //checks whether enemy is active
                //depending on which type of enemy is which bullets are animated in respective manner
                //If bullet leaves screen bullet is deactivated
                for (int i = 0; i < Enemy.size(); i++) {
                    if (Enemy.get(i).active) {
                        for (int j = 0; j < 20; j++) {
                            if (Enemy.get(i).bulletActive(j)) {
                                if (Enemy.get(i).img == imgEnemy[0]) {
                                    Enemy.get(i).animateBullet(j);
                                } else if (Enemy.get(i).img == imgEnemy[1]) {
                                    Enemy.get(i).sinAnimateBullet(j, Enemy.get(i).enemyX);
                                } else {
                                    Enemy.get(i).trackAnimateBullet(j, ship.shipX, ship.shipY);
                                }
                                if (Enemy.get(i).getY(j) > imgBackground.getHeight() + 100) {
                                    Enemy.get(i).deactivateBullet(j);
                                }
                            }
                        }
                    }
                }
                //Animates user's bullets
                //If bullet leaves screen it is deactivated
                for (int i = 0; i < 30; i++) {
                    if (myBullets[i].active) {
                        myBullets[i].animate();
                        if (myBullets[i].bulletY < 20) {
                            myBullets[i].deactivate();
                        }
                    }
                }
                //Cycles through enemies and removes any inactive enemies from the array
                for (int i = 0; i < Enemy.size(); i++) {
                    if (!(Enemy.get(i).active)) {
                        Enemy.remove(i);
                    }
                }
                //checks whether enemies who have been hit still have any active on screen bullets
                for (int i = 0; i < Enemy.size(); i++) {
                    if (!(Enemy.get(i).shipActive)) {
                        if (Enemy.get(i).bulletsInactive()) {
                            Enemy.get(i).deactivate();

                        }
                    }
                }
            }
            //redraws everything to GamePanel
            repaint();
            //Runs game at 60 frames per second 
            Thread.sleep(17);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Checks whether user has pressed start game button and sets game playing to true
        if (user.mainMenu) {
            if (e.getX() > 162 && e.getX() < 430 && e.getY() > 200 && e.getY() < 240) {
                user.mainMenu = false;
                user.playing = true;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //If directional key is pressed, respective direction boolean is set to true
            case 87:
                ship.up = true;
                break;
            case 65:
                ship.left = true;
                break;
            case 83:
                ship.down = true;
                break;
            case 68:
                ship.right = true;
                break;
            //If space is pressed shooting boolean is set to true
            case KeyEvent.VK_SPACE:
                isShooting = true;
                break;
            //CHEAT CODE: allows user to jump to higher level
            case KeyEvent.VK_Q:
                user.level = 6;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            //If directional key is released, respective direction boolean is set to false
            case 87:
                ship.up = false;
                break;
            case 65:
                ship.left = false;
                break;
            case 83:
                ship.down = false;
                break;
            case 68:
                ship.right = false;
                break;
            //If space bar is released shooting boolean is set to false
            case KeyEvent.VK_SPACE:
                isShooting = false;
                break;
        }
    }
}