package algorithms.mazeGenerators;

/**
 * The EmptyMazeGenerator class extends AMazeGenerator.
 * its purpose is to generate an empty maze with no walls.
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * This function generates an empty maze
     * @param r This is a parameter that describe the number of rows  in the maze.
     * @param c This is a parameter that describe the number of column in the maze.
     */
    public Maze generate(int r,int c){
        Maze m=basicGenerate(r,c);
        return m;
    }
}
