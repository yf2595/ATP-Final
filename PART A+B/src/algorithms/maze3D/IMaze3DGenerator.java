package algorithms.maze3D;

/**
 * an interface for all maze3D generators
 */
public interface IMaze3DGenerator {
    Maze3D generate(int depth, int row, int column);
    long measureAlgorithmTimeMillis(int depth, int row, int column);
}
