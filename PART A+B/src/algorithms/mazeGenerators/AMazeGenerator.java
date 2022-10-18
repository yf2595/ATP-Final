package algorithms.mazeGenerators;

import java.util.Random;

/**
 * The AMazeGenerator abstract class implements IMazeGenerator.
 * This abstract class is a base of all maze generator.
 */
public abstract class AMazeGenerator implements IMazeGenerator{

    /**
     * This method return how long did it take to generate the maze with r rows and c columns.
     * @param r This is a parameter that describe the count of rows maze.
     * @param c This is a parameter that describe the count of column maze.
     * @return long This return is how long did it take to generate.
     */
    public long measureAlgorithmTimeMillis(int r,int c){
        long s=System.currentTimeMillis();
        generate(r,c);
        long e=System.currentTimeMillis();
        return e-s;
    }

    /**
     * This method generates a basic maze used for the empty and simple mazes
     * @param r This is a parameter that describe the count of rows maze.
     * @param c This is a parameter that describe the count of column maze.
     * @return maze This maze that this function is generate.
     */
    public Maze basicGenerate(int r,int c){
        Random rand= new Random();
        int start_row=rand.nextInt(r-1);
        int start_col=0;
        int end_row=rand.nextInt(r-1);
        int end_col=c-1;
        Maze m = new Maze(r,c);
        m.setStartPosition(new Position(start_row,start_col));
        m.setGoalPosition(new Position(end_row,end_col));
        return m;
    }

}
