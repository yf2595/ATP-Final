package algorithms.maze3D;

/**
 * This class represents a 3d Maze.
 */
public class Maze3D {
    private int [][][] map;
    private Position3D start, goal;
    private final int depth;
    private final int row;
    private final int column;
    /**
     * Class maze constructor .
     * @param row This is a parameter that describe the number of rows in the maze.
     * @param column This is a parameter that describe the number of columns in the maze.
     * @param depth This is a parameter that describe the mazes depth.
     */
    public Maze3D(int depth, int row, int column) {
        this.depth = depth;
        this.row = row;
        this.column = column;
        if(depth<2 || row<2 || column <2){
            throw new IllegalArgumentException("Maze size has to be at least 2X2");
        }
        map = new int[depth][row][column];

    }
    /**
     * This method returns the maze start position.
     * @return a start position attribute.
     */
    public Position3D getStartPosition() {

        return start;
    }

    /**
     * This method used to reset data attribute.
     */
    public void reset(){

        map= new int[depth][row][column];
    }
    /**
     * This method returns the maze goal position.
     * @return a goal position attribute.
     */
    public Position3D getGoalPosition() {

        return goal;
    }
    /**
     * This method returns the mazes depth.
     * @return int This returns the depth attribute of this maze.
     */
    public int getDepth() {

        return depth;
    }
    /**
     * This method returns the number of rows.
     * @return int This returns a row attribute of this maze.
     */
    public int getRow() {

        return row;
    }
    /**
     * This method returns the number of columns.
     * @return int This returns a column attribute of this maze.
     */
    public int getColumn() {

        return column;
    }
    /**
     * This method  sets the maze start position.
     * @param start This is a new start position.
     */
    public void setStartPosition(Position3D start) {

        this.start = start;
    }
    /**
     * This method returns the cell value.
     * @param row Cells row.
     * @param col Cells column.
     * @param dep Cells depth.
     * @return int Cells value.
     */
    public int getCell3DValue(int dep,int row,int col){
        return map[dep][row][col];
    }
    /**
     * This method is updating the maze values according to the 3D cell map
     * @param map This is a cells array that holds the right values for the data attribute.
     */
    public void getCell3DMap(Cell3D[][][] map){
        for(int t=0;t<depth;t++){
            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    this.map[t][i][j]=map[t][i][j].getValue();
                }
            }
        }

    }

    /**
     * This method sets the maze goal position .
     * @param goal This is a new goal position.
     */
    public void setGoalPosition(Position3D goal) {

        this.goal = goal;
    }
    /**
     * This method is printing the maze.
     */
    public void print(){
        int count=-1;
        System.out.println("{");
        for (int[][] maze: map) {
            count++;
            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    if(j==0){
                        System.out.print("{ ");
                        if(i==start.getRowIndex() && count==start.getDepthIndex()){
                            System.out.print("S ");
                            continue;
                        }
                    }

                    if(j==column-1){
                        if(i==goal.getColumnIndex() && count== goal.getDepthIndex()){
                            System.out.println("E }");
                            if( i==row-1 && count != depth-1) {
                                for(int t=0;t<column*2 +2 ;t++) {
                                    System.out.print("-");
                                }
                                System.out.println("-");
                            }

                        }
                        else if(i==row-1){
                            System.out.println(maze[i][j]+" }");
                            if(count != depth-1) {
                                for(int t=0;t<column*2+2;t++) {
                                    System.out.print("-");
                                }
                                System.out.println("-");
                            }
                        }
                        else{
                            System.out.println(maze[i][j]+" }");

                        }
                    }
                    else{
                        System.out.print(maze[i][j]+" ");
                    }
                }
            }
        }
        System.out.println("}");
    }
}
