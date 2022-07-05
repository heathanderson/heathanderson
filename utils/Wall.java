package utils;

import java.awt.*;

public class Wall {
    
    private Point p1, p2;
    private boolean isVisible = true;
    private int roomsize;

    
    public Wall(Point p1, Point p2, int roomsize) {
        this.p1=p1;
        this.p2=p2;
        this.roomsize=roomsize;
    }    
    
    public boolean isVisible() {
        return isVisible;
    }
    
    public void removeWall() {
        this.isVisible=false;
    }
    
    public void setRoomSize(int roomsize) {
        this.roomsize=roomsize;
    }
    
    public void draw(Graphics g, Color c, int xOffset, int yOffset) {
        if (isVisible) {
            g.setColor(c);
            g.drawLine(xOffset+p1.x*roomsize, yOffset+p1.y*roomsize, xOffset+p2.x*roomsize, yOffset+p2.y*roomsize);
        }
    }
}



