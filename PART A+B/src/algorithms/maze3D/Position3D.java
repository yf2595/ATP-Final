package algorithms.maze3D;


/**
 * The Position3D class describe a position in 3D maze.
 */
public class Position3D {
    private final int depth;
    private final int row;
    private final int column;

    /**
     * This is a constructor Position3D.
     * @param depth This is a parameter that describe the positions depth in the maze.
     * @param row This is a parameter that describe the positions row in the maze.
     * @param column This is a parameter that describe the positions column in the maze.
     */
    public Position3D(int depth, int row, int column) {
        this.depth = depth;
        this.row = row;
        this.column = column;
    }

    /**
     * This method return the positions depth.
     * @return int This returns a depth index.
     */
    public int getDepthIndex() {
        return depth;
    }

    /**
     * This method return the positions row.
     * @return int This returns a row index.
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * This method return the positions column.
     * @return int This returns a column index.
     */
    public int getColumnIndex() {
        return column;
    }

    /**
     * Class printing method
     * @return string
     */
    @Override
    public String toString() {
        return
                "{depth=" + depth +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
