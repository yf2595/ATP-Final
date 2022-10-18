package algorithms.mazeGenerators;

/**
 * An Interface for all MazeGenerators
 */
public interface IMazeGenerator {
    Maze generate(int r,int c);
    long measureAlgorithmTimeMillis(int r,int c);
}
