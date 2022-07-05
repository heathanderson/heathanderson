package utils;


import java.awt.*;
import java.util.*;

public class Room {
    
    private Wall wall[];
    private Room room[];
    private Vector <Room> path;
    private int row, 
                col,
                roomsize;
    private boolean visited,
                    isTarget,
                    showWrongPath;                
    public static int NORTH_WALL = 0,
                      SOUTH_WALL = 1,
                      EAST_WALL  = 2,
                      WEST_WALL  = 3;
    public static int NORTH_ROOM = 0,
                      SOUTH_ROOM = 1,
                      EAST_ROOM  = 2,
                      WEST_ROOM  = 3;
                      
                      

    public Room(int col, int row, int roomsize, Vector <Room> path, Wall north, Wall south, Wall east, Wall west) {
        wall = new Wall[4];
        room = new Room[4];
        this.path=path;
        this.roomsize=roomsize;
        this.row=row;
        this.col=col;
        wall[NORTH_WALL] = north;
        wall[SOUTH_WALL] = south;
        wall[EAST_WALL]  = east;
        wall[WEST_WALL]  = west;
        visited=false;
    }    
    
    public void removeWall(int pos) {
        wall[pos].removeWall();
    }
    
    public int getRow() {
         return row;   
    }
    
    public boolean hasWall(int w) {
        return wall[w].isVisible();
    }
    
    public int getCol() {
         return col;   
    }
    
    public void setRoom(Room r, int pos) {
        room[pos] = r;
    }
    
    public void setRoomSize(int roomsize) {
        this.roomsize=roomsize;
    }
    
    public Room getRoom(int pos) {
        return room[pos];
    }
    
    public void visit() {
        visited = true;
    }
    
    public boolean wasVisited() {
        return visited;
    }
    
    public void setAsTarget(boolean isTarget) {
        this.isTarget=isTarget;
    }
    
    public boolean isTarget() {
        return isTarget;
    }
    
    public void showWrongPath(boolean showWrongPath) {
        this.showWrongPath=showWrongPath;
    }

    public Vector <Room> getRoomsWithAllWalls() {
        Vector <Room> v = new Vector <Room>();
        for (int i=0; i<room.length; i++) {
            if (room[i] != null && room[i].hasAllWalls()) {
                v.addElement(room[i]);
            }
        }
        return v;
    }
    
    public Vector <Room> getNewMoves() {
        Vector <Room> v = new Vector <Room>();
        for (int i=0; i<room.length; i++) {
            if (!wall[i].isVisible() && !room[i].wasVisited()) {
            	v.add(room[i]);
            }
        }
        return v;
    }
    
    public String toString() {
        return ("Room" + col + "  " + row);
    }
    
    public boolean hasAllWalls() {
        return (wall[NORTH_WALL].isVisible()
             && wall[SOUTH_WALL].isVisible()
             && wall[EAST_WALL ].isVisible()
             && wall[WEST_WALL ].isVisible());
    }

    public void draw(Graphics g, Color c, Color c2, int xOffset, int yOffset) {
        if (visited) {
            if (showWrongPath) {
                g.setColor(c2);
                g.fillRect(xOffset+((col+1)*roomsize-(roomsize/3*2)), 
                           yOffset+((row+1)*roomsize-(roomsize/3*2)), 
                            roomsize/3, 
                            roomsize/3);
                            
                if(!wall[SOUTH_WALL].isVisible() && room[SOUTH_ROOM].wasVisited()) {
                    g.setColor(c2);
                    g.fillRect(xOffset+((col+1)*roomsize-(roomsize/3*2)), 
                               yOffset+((row+1)*roomsize-(roomsize/3*2)), 
                                roomsize/3, 
                                roomsize);
                } 
    
                if(!wall[EAST_WALL].isVisible() && room[EAST_ROOM].wasVisited()) {
                    g.setColor(c2);
                    g.fillRect(xOffset+((col+1)*roomsize-(roomsize/3*2)), 
                               yOffset+((row+1)*roomsize-(roomsize/3*2)), 
                                roomsize, 
                                roomsize/3);
                } 
            }                   
            if (path.contains(this)) {
                if(!wall[SOUTH_WALL].isVisible() && path.contains(room[SOUTH_ROOM])) {
                    g.setColor(c);
                    g.fillRect(xOffset+((col+1)*roomsize-(roomsize/3*2)), 
                               yOffset+((row+1)*roomsize-(roomsize/3*2)), 
                                roomsize/3, 
                                roomsize);
                } 
    
                if(!wall[EAST_WALL].isVisible() && path.contains(room[EAST_ROOM])) {
                    g.setColor(c);
                    g.fillRect(xOffset+((col+1)*roomsize-(roomsize/3*2)), 
                               yOffset+((row+1)*roomsize-(roomsize/3*2)), 
                                roomsize, 
                                roomsize/3);
                } 
                
                g.setColor(c);
                g.fillRect(xOffset+((col+1)*roomsize-(roomsize/3*2)), 
                           yOffset+((row+1)*roomsize-(roomsize/3*2)), 
                            roomsize/3, 
                            roomsize/3);
            }
        }
        if (isTarget) {
            g.setColor(Color.red);
            g.fillRect(xOffset+((col+1)*roomsize-(roomsize/3*2)), 
                       yOffset+((row+1)*roomsize-(roomsize/3*2)), 
                        roomsize/3, 
                        roomsize/3);
        }
    }
}



