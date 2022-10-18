package algorithms.mazeGenerators;
import java.util.Random;
import java.util.Stack;

/**
 * The MyMazeGenerator class extends AMazeGenerator.
 * its purpose to generate a interesting and sophisticate maze.
 */
public class MyMazeGenerator extends AMazeGenerator {
    Cell[][] map;
    Random rand;
    Stack<Cell> s;

    /**
     * Class constructor MyMazeGenerator.
     */
    public MyMazeGenerator() {
        rand=new Random();
        s= new Stack<>();
    }

    /**
     * This is a maze generate function.
     * @param r This is a parameter that describe the numbers of rows in the maze.
     * @param c This is a parameter that describe the numbers of columns in the maze.
     * In this function we generate a maze by choosing random cells.
     * if a path wasen`t found, reset the maze and start again.
     */
    public Maze generate(int r, int c){
        boolean loop = false;
        Maze m = new Maze(r, c);
        while(!loop){ // if loop=false -> generate a new maze with new paths.
            m.reset(); //reset all attributes m maze.
            map = createMaze(m,r,c); // create maze
            if (map[m.getGoalPosition().getRowIndex()]
                    [m.getGoalPosition().getColumnIndex()].getConnectNum()==0){
                //if goal position in m maze is a pass (value=0). meaning- have a path from start to goal
                loop=true;
            }
        }
        return m;
    }

    /**
     * This private function set all the cells neighbors.
     * @param r This is a parameter that describe the numbers of rows in the maze.
     * @param c This is a parameter that describe the numbers of columns in the maze.
     */
    private void setAllNeighbors(int r,int c){
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(i==0 && j==0){ //left upper corner
                    map[i][j].addNeighbors(map[i+1][j]);
                    map[i][j].addNeighbors(map[i][j+1]);
                }
                else if(i==r-1 && j==0){ // left lower corner
                    map[i][j].addNeighbors(map[i-1][j]);
                    map[i][j].addNeighbors(map[i][j+1]);
                }
                else if(i==r-1 && j==c-1){ // right lower corner
                    map[i][j].addNeighbors(map[i-1][j]);
                    map[i][j].addNeighbors(map[i][j-1]);
                }
                else if(i==0 && j==c-1){ //right upper corner
                    map[i][j].addNeighbors(map[i+1][j]);
                    map[i][j].addNeighbors(map[i][j-1]);
                }
                else if(i==0){ // upper row
                    map[i][j].addNeighbors(map[i+1][j]);
                    map[i][j].addNeighbors(map[i][j+1]);
                    map[i][j].addNeighbors(map[i][j-1]);
                }
                else if(i==r-1){ // lower row
                    map[i][j].addNeighbors(map[i-1][j]);
                    map[i][j].addNeighbors(map[i][j+1]);
                    map[i][j].addNeighbors(map[i][j-1]);
                }
                else if(j==0){ // the first column
                    map[i][j].addNeighbors(map[i+1][j]);
                    map[i][j].addNeighbors(map[i][j+1]);
                    map[i][j].addNeighbors(map[i-1][j]);
                }
                else if(j==c-1){ // the last column
                    map[i][j].addNeighbors(map[i+1][j]);
                    map[i][j].addNeighbors(map[i][j-1]);
                    map[i][j].addNeighbors(map[i-1][j]);
                }
                else{ // any other cell that was not mentioned before
                    map[i][j].addNeighbors(map[i+1][j]);
                    map[i][j].addNeighbors(map[i-1][j]);
                    map[i][j].addNeighbors(map[i][j-1]);
                    map[i][j].addNeighbors(map[i][j+1]);
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
     */
    private void setPosition(int r,int c, Maze m){
        int start_row=rand.nextInt(r-1);
        int end_row=rand.nextInt(r-1);
        m.setStartPosition(new Position(start_row,0));
        m.setGoalPosition(new Position(end_row,c-1));
    }

    /**
     * This method is the general maze creation method.
     * @param m This is the maze that we updates.
     * @param r This is a parameter that describe the numbers of rows in the maze.
     * @param c This is a parameter that describe the numbers of columns in the maze.
     * @return 2D array cell.
     */
    public Cell[][] createMaze(Maze m, int r,int c){
        map=new Cell[r][c];
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                map[i][j]=new Cell(i,j);
            }
        }
        setAllNeighbors(r,c); //set all neighbors of this maze.
        setPosition(r,c,m); //choose a start and goal position.
        initBeginCells(m); //pushing the first cells into the stack
        MakeMazeAlgorithm(); //make a maze according to Randomized Prim's algorithm.
        m.getCellMap(map); // setting up the mazes value according to the map cells value
        return map;
    }

    /**
     * This method is creating the maze according to Randomized Prim's algorithm.
     * we come to this function from createMaze function.
     */
    private void MakeMazeAlgorithm(){
        Cell curr,neig;
        while(!(s.isEmpty())){
            curr=s.pop();
            if(!curr.is_circle(curr.connectNum)){ // Are not closed a circle?
                curr.CC(); //updating the connectNum of all the cells that are a part of the father connected compound.
                curr.checkNeighbors(); //updating this cells neighbors map by removing the cells that already has been visited
                int size=curr.getNumOfNeighbors();
                if(size>0){ // because count neighbors can will be 0.
                    int choose=rand.nextInt(size);
                    curr.CC(); //updating the connectNum of all the cells that are a part of the father connected compound.
                    neig = curr.getNeighborsByIndex(choose); // A selected randomly neighbor.
                    if(!(neig.is_circle(curr.getConnectNum())) &&
                            !neig.isVisited()){ // if selected neighbor is not close a circle and it's not visited
                        neig.setConnectNum(curr.getConnectNum()); // set connect number attribute for neighbor.
                        neig.CC();
                        neig.visit(); //update isVisit's neighbor neig to be true.
                        s.push(neig); //push this neighbor to s stack.
                        s.push(curr); //push again current cell to s stack.
                    }
                    else{ //this neighbor is close a circle or visited before.
                        curr.removeNeighbors(curr.getNeighborsByIndex(choose)); //remove this neighbor from arrayList neighbor's curr.
                        if(curr.getNumOfNeighbors()!=0) { //if to this cell have another neighbors.
                            s.push(curr);
                        }
                        curr.CC(); //updating the connectNum of all the cells that are a part of the father connected compound.
                        curr.checkNeighbors(); //updating this cells neighbors map by removing the cells that already has been visited
                    }
                }
            }
            else{ //is close a circle.
                curr.noVisit(); //change isVisit attribute to false. will not mark this cell as visit.
                curr.setConnectNum(-1); //connect number of curr cell will be -1 (As in the initialization).
            }
        }
    }

    /**
     * This methods inserts the first cells into the stack according to the start Position
     * in this part we pushing to the stuck attribute: start cell,
     * and 2 neighbors of start cell (choose at randomly process).
     * @param m used to get the start position
     */
    public void initBeginCells(Maze m){
        int cc = 0;
        Cell c1=map[m.getStartPosition().getRowIndex()][0]; //start position cell
        c1.setConnectNum(cc);
        c1.visit();
        int size = c1.getNumOfNeighbors();
        int choose=rand.nextInt(size);
        Cell c2=c1.getNeighborsByIndex(choose); //Neighbor's this cell is chosen randomLy.
        c2.setConnectNum(cc);
        c2.visit();
        c1.removeNeighbors(c2);
        size = c1.getNumOfNeighbors();
        choose=rand.nextInt(size);
        Cell c3=c1.getNeighborsByIndex(choose); //Another neighbor's this cell is chosen randomLy.
        c3.setConnectNum(cc);
        c3.visit();
        c1.removeNeighbors(c3);
        s.push(c2); //Push the 2 selected neighbors into the stack.
        s.push(c3);
    }
}