package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * The Position class represents a position in maze.
 */
public class Position implements Serializable {
    private final int row;
    private final int column;

    /**
     * Class constructor.
     * @param row The Positions row.
     * @param column The Positions column.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * This method Prints the Positions.
     * @return string
     */
    @Override
    public String toString() {
        return "{" +
                 + row +
                "," + column +
                '}';
    }

    /**
     * This method returns the Position row index.
     * @return int This returns a row index.
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * This method returns the Position column index.
     * @return int This returns a column index.
     */
    public int getColumnIndex() {
        return column;
    }
}
