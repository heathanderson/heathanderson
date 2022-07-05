package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ControlPanel extends JPanel implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 100;
    
    private MazePanel maze;
    private MazeTimer mazeTimer;
    private JButton btnGenerate,
                    btnSolve,
                    btnWidthInc,
                    btnWidthDec,
                    btnHeightInc,
                    btnHeightDec,
                    btnTimerDec,
                    btnTimerInc,
                    btnDecRoomSize,
                    btnIncRoomSize;
    private JPanel buttons;
    private JCheckBox ckAnimateCreation,
    				  ckAnimateSolve,
                      ckShowWrongPath;
    
    private int cols,
                rows;
    private JTextField txtWidth,
                       txtHeight,
                       txtTimer,
                       txtRoomSize;
    
        
    public ControlPanel(MazePanel maze, MazeTimer mazeTimer, int cols, int rows) {
        this.maze=maze;
        this.mazeTimer=mazeTimer;
        this.cols=cols;
        this.rows=rows;
        setupLayout();
        txtWidth.setText("" + cols);
        txtHeight.setText("" + rows);
        txtRoomSize.setText("" + maze.getRoomSize());
        txtTimer.setText("" + mazeTimer.getSleepTime());
    }    
    
    private void setupLayout() {
        JPanel p = new JPanel(new GridLayout(1,5,5,5));
        buttons = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new GridLayout(1,2));

        p.add(getWidthPanel());
        p.add(getHeightPanel());
        p.add(getTimerController());
        p.add(getSizePanel());
        p.add(getGeneratePanel());
        btnGenerate = new JButton("Generate");
        btnGenerate.addActionListener(this);
        
        btnSolve = new JButton("Solve");
        btnSolve.addActionListener(this);
        
        southPanel.add(btnGenerate);
        southPanel.add(btnSolve);
        buttons.add(BorderLayout.CENTER,p);
        buttons.add(BorderLayout.SOUTH,southPanel);
        add(buttons);
    }
    
    private JPanel getWidthPanel() {
        JPanel widthPanel = new JPanel(new BorderLayout()),
        	   labelPanel = new JPanel(),
               buttonPanel = new JPanel(new GridLayout(1,3));
        
        btnWidthDec  = new JButton("-");
        btnWidthDec.addActionListener(this);

        btnWidthInc  = new JButton("+");
        btnWidthInc.addActionListener(this);

        txtWidth = new JTextField(3);
        txtWidth.addKeyListener(this);
        //txtWidth.setEnabled(false);

        labelPanel.add(new JLabel("Width",SwingConstants.CENTER));
        buttonPanel.add(btnWidthDec);
        buttonPanel.add(btnWidthInc);
        buttonPanel.add(txtWidth);

        widthPanel.add(BorderLayout.NORTH, buttonPanel);
        return widthPanel;
    }

    private JPanel getHeightPanel() {
        JPanel p = new JPanel(new BorderLayout()),
               bp = new JPanel(new GridLayout(1,3));
        
        btnHeightDec  = new JButton("-");
        btnHeightDec.addActionListener(this);

        btnHeightInc  = new JButton("+");
        btnHeightInc.addActionListener(this);

        txtHeight = new JTextField(3);
        txtHeight.setEnabled(true);

        bp.add(btnHeightDec);
        bp.add(btnHeightInc);
        bp.add(txtHeight);

        p.add(BorderLayout.NORTH, new JLabel("Height",SwingConstants.CENTER));
        p.add(BorderLayout.CENTER, bp);
        return p;
    }

    private JPanel getTimerController() {
        JPanel p = new JPanel(new BorderLayout()),
               bp = new JPanel(new GridLayout(1,3));
        
        btnTimerDec  = new JButton("-");
        btnTimerDec.addActionListener(this);

        btnTimerInc  = new JButton("+");
        btnTimerInc.addActionListener(this);

        txtTimer = new JTextField(3);
        txtTimer.setEnabled(false);

        bp.add(btnTimerDec);
        bp.add(btnTimerInc);
        bp.add(txtTimer);

        p.add(BorderLayout.NORTH, new JLabel("Timer",SwingConstants.CENTER));
        p.add(BorderLayout.CENTER, bp);
        return p;
    }
    
    private JPanel getSizePanel() {
        JPanel p = new JPanel(new BorderLayout()),
               bp = new JPanel(new GridLayout(1,3));

        btnDecRoomSize = new JButton("-");
        btnDecRoomSize.addActionListener(this);
        btnIncRoomSize = new JButton("+");
        btnIncRoomSize.addActionListener(this);
        txtRoomSize = new JTextField(3);
        txtRoomSize.setEnabled(false);

        bp.add(btnDecRoomSize);
        bp.add(btnIncRoomSize);
        bp.add(txtRoomSize);

        p.add(BorderLayout.NORTH, new JLabel("Room Size",SwingConstants.CENTER));
        p.add(BorderLayout.CENTER, bp);

        return p;        
    }
    
    
    private JPanel getGeneratePanel() {
        JPanel p = new JPanel(new BorderLayout());

        ckAnimateCreation = new JCheckBox("Animate Creation", false);
        ckAnimateSolve = new JCheckBox("Animate Solve", true);
        ckShowWrongPath = new JCheckBox("Show Wrong Path", true);
        ckShowWrongPath.addActionListener(this);
                       
        p.add(BorderLayout.NORTH, ckAnimateCreation);
        p.add(BorderLayout.CENTER, ckAnimateSolve);
        p.add(BorderLayout.SOUTH, ckShowWrongPath);
        //p.add(BorderLayout.SOUTH, btnGenerate);                       
               
        return p;               
    }

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {
    	if (txtWidth.hasFocus()) {
    		if (cols != Integer.parseInt(txtWidth.getText())) {
    			cols = Integer.parseInt(txtWidth.getText());
	    		resetMaze();
    		}
    	}
    }
    
    public void keyTyped(KeyEvent e) {}


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGenerate) {
            mazeTimer.stopGenerating();
            maze.setMazeSize(cols,rows);
            if (ckAnimateCreation.isSelected()) {
                mazeTimer.startGenerating();
            }
            else {
                maze.generate();
                maze.requestFocus();
                repaint();
            }
            maze.showWrongPath(ckShowWrongPath.isSelected());
        }
        else if (e.getSource() == btnWidthInc) {
            cols += 1;
            resetMaze();
        }
        else if (e.getSource() == btnWidthDec) {
            cols = Math.max(1, cols-1);
            resetMaze();
        }
        else if (e.getSource() == btnHeightInc) {
            rows += 1;
            resetMaze();
        }
        else if (e.getSource() == btnHeightDec) {
            rows = Math.max(1, rows-1);
            resetMaze();
        }
        else if (e.getSource() == btnIncRoomSize) {
            maze.setRoomSize(maze.getRoomSize()+1);
        }
        else if (e.getSource() == btnDecRoomSize) {
            maze.setRoomSize(Math.max(maze.getRoomSize()-1, 3));
        }
        else if (e.getSource() == btnTimerInc) {
            mazeTimer.setThreadTime(Math.min(5120,mazeTimer.getSleepTime() * 2));
        }
        else if (e.getSource() == btnTimerDec) {
            mazeTimer.setThreadTime(Math.max(5, mazeTimer.getSleepTime() / 2));
        }
        else if(e.getSource() == btnSolve) {
            if (ckAnimateSolve.isSelected()) {
                mazeTimer.startSolving();
            }
            else {
                maze.solve();
            }
        }
        else if(e.getSource() == ckShowWrongPath) {
            maze.showWrongPath(ckShowWrongPath.isSelected());
            maze.requestFocus();
        }
        else if(e.getSource() == ckAnimateCreation) {
            maze.requestFocus();
        }
        txtWidth.setText("" + cols);
        txtHeight.setText("" + rows);
        txtTimer.setText("" + mazeTimer.getSleepTime());
        txtRoomSize.setText("" + maze.getRoomSize());
    }
    
    private void resetMaze() {
        mazeTimer.stopGenerating();
        maze.setMazeSize(cols, rows);
    }
}