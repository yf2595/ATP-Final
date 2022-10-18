package algorithms.search;


import java.io.Serializable;
import java.util.Objects;

/**
 * The MazeState class extend AState and its purpose is to describe a state in the searchable maze .
 */
public class MazeState extends AState implements Serializable {
    private final int row;
    private final int column;

    /**
     * Class MazeState constructor .
     * @param row This is a parameter that describe the state row position in the maze.
     * @param column This is a parameter that describe the state column position in the maze..
     */
    public MazeState(int row, int column) {
        super();
        this.row = row;
        this.column = column;

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
        MazeState mazeState = (MazeState) o;
        return row == mazeState.row && column == mazeState.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), row, column);
    }

    /**
     * This method returns the states row.
     * @return int This returns a maze's row.
     */
    public int getRow() {
        return row;
    }

    /**
     * This method returns the states column.
     * @return int This returns a maze's column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Printing method for this class
     * @return string .
     */
    @Override
    public String toString() {
        return "{" + row +
                "," + column +
                '}';
    }

}
