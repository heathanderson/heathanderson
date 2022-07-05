package utils;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class MazePanel extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = 100;

    private Room room[][];
    private Wall EWwall[][];
    private Wall NSwall[][];
    private Color WALLCOLOR = Color.blue,
                  ROOMCOLOR = Color.yellow;
    private Vector <Room> path;                  
    public static int MOVE_UP    = 0,
                      MOVE_DOWN  = 1,
                      MOVE_LEFT  = 2,
                      MOVE_RIGHT = 3;
    private int roomsize = 30;
    private Room currentRoom,
                 startRoom;
    private Stack <Room> stack;
    private boolean finished;
    
    public MazePanel() {
        addKeyListener(this);
        path = new Vector <Room>();
    }
    
    public void setMazeSize(int cols, int rows) {
        finished = false;
        stack = new Stack <Room>();
        EWwall = new Wall[cols][rows+1];
        for (int col=0; col<EWwall.length; col++) {
            for (int row=0; row<EWwall[col].length; row++) {
                EWwall[col][row] = new Wall(new Point(col,row), new Point(col+1,row), roomsize);
            }
        }

        NSwall = new Wall[cols+1][rows];
        for (int col=0; col<NSwall.length; col++) {
            for (int row=0; row<NSwall[col].length; row++) {
                NSwall[col][row] = new Wall(new Point(col,row), new Point(col,row+1),roomsize);
            }
        }
        
        room = new Room[cols][rows];
        for (int col=0; col<room.length; col++) {
            for (int row=0; row<room[col].length; row++) {
                room[col][row] = new Room(col,
                                          row,
                                          roomsize,
                                          path,
                                          EWwall[col][row], 
                                          EWwall[col][row+1], 
                                          NSwall[col+1][row], 
                                          NSwall[col][row]);
                if (col>0) {
                    room[col][row].setRoom(room[col-1][row], Room.WEST_ROOM);
                    room[col-1][row].setRoom(room[col][row], Room.EAST_ROOM);
                }                                            
                if (row>0) {
                    room[col][row].setRoom(room[col][row-1], Room.NORTH_ROOM);
                    room[col][row-1].setRoom(room[col][row], Room.SOUTH_ROOM);
                }                                            
            }            
        }
        
        currentRoom = room[(int)(Math.random()*room.length)]
                          [(int)(Math.random()*room[0].length)];
        startRoom = currentRoom;
        repaint();
    }    
    
    public void generate() {
        while (!isFinished()) {
            step();
        }
    }
    
    public void setRoomSize(int roomsize) {
        this.roomsize=roomsize;
        for (int col=0; col<EWwall.length; col++) {
            for (int row=0; row<EWwall[col].length; row++) {
                EWwall[col][row].setRoomSize(roomsize);
                
            }
        }
       for (int col=0; col<NSwall.length; col++) {
            for (int row=0; row<NSwall[col].length; row++) {
                NSwall[col][row].setRoomSize(roomsize);
            }
        }
        for (int col=0; col<room.length; col++) {
            for(int row=0; row<room[col].length; row++) {
                room[col][row].setRoomSize(roomsize);
            }            
        }
        repaint();
    }
    
    public int getRoomSize() {
        return roomsize;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    private void startMaze() {
        startRoom   = room[0][0];
        path.add(startRoom);
        currentRoom = startRoom;
        currentRoom.visit();
        room[room.length-1][room[0].length-1].setAsTarget(true);
        repaint();
    }
    
    public boolean isFocusable() {
        return true;
    }
    
    public void showWrongPath(boolean b) {
        for (int col=0; col<room.length; col++) {
            for (int row=0; row<room[col].length; row++) {
                room[col][row].showWrongPath(b);                            
            }            
        }
        requestFocus();
        repaint();
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            move(MOVE_RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            move(MOVE_LEFT);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            move(MOVE_UP);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            move(MOVE_DOWN);
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    
    public void move(int dir) {
        if (dir == MOVE_UP
            && !currentRoom.hasWall(Room.NORTH_WALL)) {
                moveTo(currentRoom.getRoom(Room.NORTH_ROOM));
        }
        if (dir == MOVE_DOWN
            && !currentRoom.hasWall(Room.SOUTH_WALL)) {
            moveTo(currentRoom.getRoom(Room.SOUTH_ROOM));
        }
        if (dir == MOVE_LEFT
            && !currentRoom.hasWall(Room.WEST_WALL)) {
            moveTo(currentRoom.getRoom(Room.WEST_ROOM));
        }
        if (dir == MOVE_RIGHT
            && !currentRoom.hasWall(Room.EAST_WALL)) {
            moveTo(currentRoom.getRoom(Room.EAST_ROOM));
        }
        repaint();
    }
    
    private void moveTo(Room r) {
        if(path.contains(r)) {
            path.removeElement(currentRoom);
        }
        else {
            path.add((Room)r);
        }
        currentRoom = r;
        currentRoom.visit();
    }
    
    public void step() {
        Vector <Room> v = currentRoom.getRoomsWithAllWalls();
        Room nextRoom;
        int next;
        
        finished = false;
        if (v.size() > 0) {
            next = (int)(Math.random()*v.size());
            nextRoom = ((Room)v.elementAt(next));
            if (nextRoom.getCol() == currentRoom.getCol()) {
                if (nextRoom.getRow() > currentRoom.getRow()) {
                    currentRoom.removeWall(Room.SOUTH_WALL);
                }
                else {
                    currentRoom.removeWall(Room.NORTH_WALL);
                }
            }
            else {
                if (nextRoom.getCol() > currentRoom.getCol()) {
                    currentRoom.removeWall(Room.EAST_WALL);
                }
                else {
                    currentRoom.removeWall(Room.WEST_WALL);
                }
            }     
            if (v.size() > 1) {
                stack.push(currentRoom);
            }
            currentRoom = nextRoom;
        }
        else if (!stack.empty()) {
             currentRoom = (Room)stack.pop();
        }
        else {
            finished = true;
        }
        repaint();
        if (finished) {
            startMaze();
        }
    }   
    
    public void solve() {
        while (!currentRoom.isTarget()) {
            solveStep();
        }
    }

    public boolean isSolved() {
        return currentRoom.isTarget();
    }

    public void solveStep() {
        Room nextRoom;
        Vector <Room> option = currentRoom.getNewMoves();
        //System.out.println("Solve Step" + option.size());
        if (option.size() > 0) {
            //System.out.println("option.size() = " + option.size());
            nextRoom = (Room)(option.elementAt((int)(Math.random()*option.size())));
            //path.add(currentRoom);
            moveTo(nextRoom);
        }
        else if(path.size() > 1){
            //System.out.println("path.size() = " + path.size());
            nextRoom = (Room)(path.elementAt(path.size()-2));
            //path.removeElementAt(path.size()-1);
            moveTo(nextRoom);
        }
        else {
            nextRoom = currentRoom;
            //System.out.println("Some Major Error!!!");
        }
        //System.out.println(nextRoom);
        repaint();
    }

    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0,0,800,600);
        for (int col=0; col<EWwall.length; col++) {
            for (int row=0; row<EWwall[col].length; row++) {
                EWwall[col][row].draw(g,WALLCOLOR,
                                      ((getWidth()-roomsize*room.length))/2, 
                                      ((getHeight()-roomsize*room[0].length))/2);
            }
        }
       for (int col=0; col<NSwall.length; col++) {
            for (int row=0; row<NSwall[col].length; row++) {
                NSwall[col][row].draw(g,WALLCOLOR,
                                      ((getWidth()-roomsize*room.length))/2, 
                                      ((getHeight()-roomsize*room[0].length))/2);
            }
        }
        for (int col=0; col<room.length; col++) {
            for(int row=0; row<room[col].length; row++) {
                room[col][row].draw(g,ROOMCOLOR, Color.yellow.darker(),
                       (getWidth()-roomsize*room.length)/2, 
                       (getHeight()-roomsize*room[0].length)/2);
            }            
        }
    }       
}


