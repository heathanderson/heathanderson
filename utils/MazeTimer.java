package utils;

public class MazeTimer extends Thread {
 
    private MazePanel maze;
    private int sleepTime;
    private boolean generating,
                    solving;
 
    public MazeTimer(int sleepTime) {
        this.sleepTime=sleepTime;
        generating=false;
    }  
    
    public void setMaze(MazePanel maze) {
        this.maze=maze;
    }
    
    public void setThreadTime(int sleepTime) {
        this.sleepTime=sleepTime;
    }
    
    public int getSleepTime() {
        return sleepTime;
    }
    
    public void startGenerating() {
        generating=true;
    }
    
    public void stopGenerating() {
        generating=false;
    }
    
    public boolean isGenerating() {
        return generating;
    }
    
    public void startSolving() {
        solving=true;
    }
    
    public void stopSolving() {
        solving=false;
    }
    
    public boolean isSolving() {
        return solving;
    }
    
    public void run() {
        while(true) {
            try {
                sleep(sleepTime);
                if(generating) {
                    maze.step();
                    generating = !maze.isFinished();
                    if(maze.isFinished()) {
                        maze.requestFocus();
                    }
                }
                if (solving) {
                    maze.solveStep();
                    solving = !maze.isSolved();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

