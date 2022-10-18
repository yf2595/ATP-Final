package algorithms.maze3D;

import algorithms.search.AState;

import java.util.Objects;

/**
 * The Maze3DState class extend AState.
 * represents a State in the maze.
 */
public class Maze3DState extends AState {
    private final int depth;
    private final int row;
    private final int column;

    /**
     * MazeState constructor.
     * @param depth Describes state depth in the maze.
     * @param row Describes state row in the maze.
     * @param column Describes state column in the maze.
     */
    public Maze3DState(int depth, int row, int column) {
        this.depth = depth;
        this.row = row;
        this.column = column;
    }

    /**
     * Printing function for the mazeState class.
     * @return string
     */
    @Override
    public String toString() {
        return "{" + depth + "," + row + ","  + column + '}';
    }


    /**
     * This method returns states row number.
     * @return int  the state row.
     */
    public int getRow() {
        return row;
    }


    /**
     * This method returns states column number.
     * @return int the state column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * This method checks if this and Object o is equals.
     * @param o This is a parameter to equals this.
     * @return boolean This returns true\false if O and this are equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Maze3DState that = (Maze3DState) o;
        return depth == that.depth && row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), depth, row, column);
    }
}
