
package utils;

import javax.swing.*;
import java.awt.*;

public class Maze extends javax.swing.JFrame {

	private static final long serialVersionUID = 100;
    
    private MazePanel maze;
    private ControlPanel cp;
    
    private int ROWS      = 10,
                COLUMNS   = 10,
                SLEEPTIME = 20;
    private MazeTimer mazeTimer;                
                
    
    public Maze() {
        setTitle("Maze Generator");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        mazeTimer = new MazeTimer(SLEEPTIME);
        setupLayout();
        mazeTimer.setMaze(maze);
        mazeTimer.start();
    }
    
    private void setupLayout() {
        maze = new MazePanel();
        maze.setMazeSize(COLUMNS,ROWS);
        cp = new ControlPanel(maze,mazeTimer,COLUMNS,ROWS);
    	
        getContentPane().add(maze, BorderLayout.CENTER);
    	getContentPane().add(cp, BorderLayout.SOUTH);
        setSize(800, 600);
    }
    
    public static void main(String[] args) {
        // set the look and feel to match the system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }        
        new Maze().setVisible(true);
    }    
}
