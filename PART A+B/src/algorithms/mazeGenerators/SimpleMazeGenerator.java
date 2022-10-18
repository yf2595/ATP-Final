package algorithms.mazeGenerators;
import java.util.Random;

/**
 * The SimpleMazeGenerator class extends AMazeGenerator.
 * its purpose is to generate a simple maze by finding a path and setting up random walls.
 */
public class SimpleMazeGenerator extends AMazeGenerator{
    Random rand= new Random();

    /**
     * This is a maze generate function.
     * @param r This is a parameter that describe the number of rows in the maze.
     * @param c This is a parameter that describe the number of columns in the maze.
     */
    public Maze generate(int r,int c) {
        Maze m = basicGenerate(r, c); // initializing Empty maze
        m.findPath(m.getStartPosition().getRowIndex(), m.getStartPosition().getColumnIndex(), m.getGoalPosition().getRowIndex(), m.getGoalPosition().getColumnIndex());
        int times = (r * c) / 2;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                m.set(i, j, 1); //turning all the untouched cells into walls
                m.set(i, j, 2); // turning all the 2 value cells into 0 value
            }
        }
        for (int t = 0; t < times; t++) {
            m.set(rand.nextInt(r - 1), rand.nextInt(c - 1), 0); // adding walls randomly
        }
        return m;
    }
}
