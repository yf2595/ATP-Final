package algorithms.maze3D;


import java.util.Random;
import java.util.Stack;
/**
 * The MyMaze3DGenerator class extends AMaze3DGenerator.
 * its purpose to generate a interesting and sophisticate 3D maze.
 */
public class MyMaze3DGenerator extends AMaze3DGenerator{
    Cell3D[][][] map;
    Random rand;
    Stack<Cell3D> s;

    /**
     * Class constructor MyMaze3DGenerator.
     */
    public MyMaze3DGenerator() {
        rand= new Random();
        s=new Stack<>();
    }
    /**
     * This is a maze generate function.
     * @param row This is a parameter that describe the numbers of rows in the maze.
     * @param column This is a parameter that describe the numbers of columns in the maze.
     * @param depth This is a parameter that describe mazes depth.
     * In this function we generate a maze by choosing random cells.
     * if a path wasen`t found, reset the maze and start again.
     */
    public Maze3D generate(int depth, int row, int column) {
        boolean loop = false;
        Maze3D m= new Maze3D(depth, row, column);
        while (!loop) {
            m.reset();
            map = createMaze(m, depth, row, column);
            if (map[m.getGoalPosition().getDepthIndex()][m.getGoalPosition().getRowIndex()][m.getGoalPosition().getColumnIndex()].getKashir() == 0) {
                loop = true;
            }
        }
        return m;
    }
    /**
     * This method is the general maze creation method.
     * @param m This is the maze that we updates.
     * @param r This is a parameter that describe the numbers of rows in the maze.
     * @param c This is a parameter that describe the numbers of columns in the maze.
     * @param d This is a parameter that describe mazes depth.
     * @return 2D array cell.
     */
    public Cell3D[][][] createMaze(Maze3D m , int d, int r, int c){
        map = new Cell3D[d][r][c];
        for (int t = 0; t < d; t++) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    map[t][i][j] = new Cell3D(t, i, j);
                }
            }
        }
        setAllNeighbors(d,r,c); //set all neighbors of this maze.
        setPosition(d,r,c,m); //choose a start and goal position.
        initBeginCells(m); //pushing the first cells into the stack
        MakeMazeAlgorithm(); //make a maze according to Randomized Prim's algorithm.
        m.getCell3DMap(map); //updating the maze3D object with the map values
        return map;
    }
    /**
     * This private function set all the cells neighbors.
     * @param r This is a parameter that describe the numbers of rows in the maze.
     * @param c This is a parameter that describe the numbers of columns in the maze.
     * @param d This is a parameter that describe mazes depth.
     */
    public void setAllNeighbors(int d, int r,int c){
        for (int t = 0; t < d; t++) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (t == 0) { //check borders
                        map[t][i][j].addNeighbors(map[t + 1][i][j]);
                    }
                    else if (t == d - 1) {//check borders
                        map[t][i][j].addNeighbors(map[t - 1][i][j]);
                    }
                    else {
                        map[t][i][j].addNeighbors(map[t - 1][i][j]);
                        map[t][i][j].addNeighbors(map[t + 1][i][j]);
                    }
                    if (i == 0 && j == 0) {//check borders corners
                        map[t][i][j].addNeighbors(map[t][i + 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j + 1]);
                    }
                    else if (i == r - 1 && j == 0) {//check borders corners
                        map[t][i][j].addNeighbors(map[t][i - 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j + 1]);
                    }
                    else if (i == r - 1 && j == c - 1) {//check borders corners
                        map[t][i][j].addNeighbors(map[t][i - 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j - 1]);
                    }
                    else if (i == 0 && j == c - 1) {//check borders corners
                        map[t][i][j].addNeighbors(map[t][i + 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j - 1]);
                    } else if (i == 0) { //check borders row
                        map[t][i][j].addNeighbors(map[t][i + 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j + 1]);
                        map[t][i][j].addNeighbors(map[t][i][j - 1]);
                    } else if (i == r - 1) { //check borders row
                        map[t][i][j].addNeighbors(map[t][i - 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j + 1]);
                        map[t][i][j].addNeighbors(map[t][i][j - 1]);
                    } else if (j == 0) { //check borders column
                        map[t][i][j].addNeighbors(map[t][i + 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j + 1]);
                        map[t][i][j].addNeighbors(map[t][i - 1][j]);
                    } else if (j == c - 1) { //check borders column
                        map[t][i][j].addNeighbors(map[t][i + 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j - 1]);
                        map[t][i][j].addNeighbors(map[t][i - 1][j]);
                    } else { // any other cell
                        map[t][i][j].addNeighbors(map[t][i + 1][j]);
                        map[t][i][j].addNeighbors(map[t][i - 1][j]);
                        map[t][i][j].addNeighbors(map[t][i][j - 1]);
                        map[t][i][j].addNeighbors(map[t][i][j + 1]);
                    }
                }
            }
        }
    }
    /**
     * This is a private function.
     * choose a start and goal position and update accordingly this attributes.
     * @param m This is a maze that we updates.
     * @param r This is a parameter that describe the number of rows in the maze.
     * @param c This is a parameter that describe the number of column in the maze maze.
     * @param d This is a parameter that describe mazes depth.
     */
    public void setPosition(int d,int r,int c,Maze3D m){
        int startRow = rand.nextInt(r - 1);
        int endRow = rand.nextInt(r - 1);
        int endDepth = rand.nextInt(d - 1);
        m.setStartPosition(new Position3D(0, startRow, 0));
        m.setGoalPosition(new Position3D(endDepth, endRow, c - 1));
    }
    /**
     * This methods inserts the first cells into the stack according to the start Position
     * in this part we pushing to the stuck attribute: start cell,
     * and 2 neighbors of start cell (choose at randomly process).
     * @param m used to get the start position
     */
    public void initBeginCells(Maze3D m){
        int count = 0;
        Cell3D c1 = map[0][m.getStartPosition().getRowIndex()][0];
        c1.setKashir(count);
        c1.visit();
        int size = c1.getNumOfNeighbors();
        int choose = rand.nextInt(size);
        Cell3D c2 = c1.getNeighborsByIndex(choose);
        c2.setKashir(count);
        c2.visit();
        c1.removeNeighbors(c2);
        size = c1.getNumOfNeighbors();
        choose = rand.nextInt(size);
        Cell3D c3 = c1.getNeighborsByIndex(choose);
        c3.setKashir(count);
        c3.visit();
        c1.removeNeighbors(c3);
        s.push(c2);
        s.push(c3);
    }
    /**
     * This method is creating the maze according to Randomized Prim's algorithm.
     * we come to this function from createMaze function.
     */
    public void MakeMazeAlgorithm(){
        Cell3D curr, neig;
        while (!(s.isEmpty())) {
            curr = s.pop();
            if (!curr.is_circle(curr.getKashir())) {
                curr.kashirut();
                curr.checkNeighbors();
                int size = curr.getNumOfNeighbors();
                if (size > 0) {
                    int choose = rand.nextInt(size);
                    curr.kashirut();
                    if (!(curr.getNeighborsByIndex(choose).is_circle(curr.getKashir())) && !curr.getNeighborsByIndex(choose).isVisited()) {
                        neig = curr.getNeighborsByIndex(choose);
                        neig.setKashir(curr.getKashir());
                        neig.kashirut();
                        neig.visit();
                        s.push(neig);
                        s.push(curr);
                    } else {
                        curr.removeNeighbors(curr.getNeighborsByIndex(choose));
                        if (curr.getNumOfNeighbors() != 0) {
                            s.push(curr);
                        }
                        curr.kashirut();
                        curr.checkNeighbors();
                    }
                }
            } else {
                curr.noVisit();
                curr.setKashir(-1);
            }
        }
    }
}
