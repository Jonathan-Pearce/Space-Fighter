/*
 * Jonathan Pearce
 * ICS3U-1
 * Final Project - Space Fighter
 * May 23, 2014
 */
package pearce_jonathan_finalproject;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Pearce_Jonathan_FinalProject {


    public static void main(String[] args) throws IOException, InterruptedException {
        // To create and setup our JFrame
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setSize(600 , 768);
        myFrame.setVisible(true);
        myFrame.setResizable(false);
        myFrame.setTitle("Space Fighter");
        
        //create and insert our GamePanel into the JFrame
        GamePanel myPanel = new GamePanel();
        myPanel.setSize(600, 768);
        myPanel.setVisible(true);
        myFrame.setContentPane(myPanel);
        
        //Set Focus to gamePanel so that KeyListerners will work
        myPanel.requestFocus();
        myPanel.setFocusable(true);
        
        //run our game
        //calls the run method in GamePanel
        myPanel.run();
    }
}
