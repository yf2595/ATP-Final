package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;
import java.util.ArrayList;

/**
 * The SearchableMaze3D class implements ISearchable. Its purpose is to transform a 3d maze into a searchable maze using search algorithms.
 * map- a 3D array of MazeState. this map containing states of maze.
 */
public class SearchableMaze3D implements ISearchable {
    Maze3D m;
    Maze3DState start,end;
    Maze3DState[][][] map;

    /**
     * Class constructor.
     * @param m The maze we wish to transform
     */
    public SearchableMaze3D(Maze3D m) {
        this.m = m;
        int row = m.getRow();
        int col = m.getColumn();
        int dep = m.getDepth();
        map = new Maze3DState[dep][row][col];
        for (int t = 0; t < dep; t++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                //initialize a start position to and set the attribute start.
                    if (t == m.getStartPosition().getDepthIndex() && i == m.getStartPosition().getRowIndex() && j == m.getStartPosition().getColumnIndex()) {
                        map[t][i][j] = new Maze3DState(t, i, j);
                        this.start = map[t][i][j];
                    //initialize a goal position and set the attribute goal.
                    }
                    else if (t == m.getGoalPosition().getDepthIndex() && i == m.getGoalPosition().getRowIndex() && j == m.getGoalPosition().getColumnIndex()) {
                        map[t][i][j] = new Maze3DState(t, i, j);
                        this.end = map[t][i][j];

                    }
                    else {
                    //initialize every other position which is not a wall
                        if (m.getCell3DValue(t, i, j) == 0){
                        map[t][i][j] = new Maze3DState(t, i, j);
                        }
                    }
                }
            }
        }
        getAllPossibleStates3D(dep,row,col); // set all the neighbors after all states are initialize
    }

    /**
     * This method is used to update all exist states with their sucssesors.
     * @param dep This is a parameter that describe mazes depth.
     * @param row This is a parameter that describe mazes number of rows.
     * @param col This is a parameter that describe mazes number of column .
     */
    public void getAllPossibleStates3D(int dep,int row,int col){
        for (int t = 0; t < dep; t++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if(m.getCell3DValue(t,i,j)==0){ // only if ths state is now a wall
                        if (t == 0) { // if we are on the maze floor
                            if(m.getCell3DValue(t+1,i,j)==0)
                                map[t][i][j].addNeighbors(1,map[t + 1][i][j]);
                        }
                        else if (t == dep - 1) { //if we are on the maze top
                            if(m.getCell3DValue(t-1,i,j)==0)
                                map[t][i][j].addNeighbors(1,map[t - 1][i][j]);
                        }
                        else { // we are somewhere in the middle
                            if(m.getCell3DValue(t-1,i,j)==0){
                                map[t][i][j].addNeighbors(1,map[t - 1][i][j]);
                            }
                            if(m.getCell3DValue(t+1,i,j)==0){
                                map[t][i][j].addNeighbors(1,map[t + 1][i][j]);
                            }
                        }
                        if (i == 0 && j == 0) { //left upper corner
                            if(m.getCell3DValue(t,i+1,j)==0)
                            map[t][i][j].addNeighbors(1,map[t][i + 1][j]);
                            if(m.getCell3DValue(t,i,j+1)==0)
                            map[t][i][j].addNeighbors(1,map[t][i][j + 1]);
                        }
                        else if (i == row - 1 && j == 0) { // left lower corner
                            if(m.getCell3DValue(t,i-1,j)==0)
                            map[t][i][j].addNeighbors(1,map[t][i - 1][j]);
                            if(m.getCell3DValue(t,i,j+1)==0)
                            map[t][i][j].addNeighbors(1,map[t][i][j + 1]);
                        }
                        else if (i == row - 1 && j == col - 1) { //right lower corner
                            if(m.getCell3DValue(t,i-1,j)==0)
                            map[t][i][j].addNeighbors(1,map[t][i - 1][j]);
                            if(m.getCell3DValue(t,i,j-1)==0)
                            map[t][i][j].addNeighbors(1,map[t][i][j - 1]);
                        }
                        else if (i == 0 && j == col - 1) { //right upper corner
                            if(m.getCell3DValue(t,i+1,j)==0)
                            map[t][i][j].addNeighbors(1,map[t][i + 1][j]);
                            if(m.getCell3DValue(t,i,j-1)==0)
                            map[t][i][j].addNeighbors(1,map[t][i][j - 1]);
                        } else if (i == 0) { // upper row
                            if(m.getCell3DValue(t,i+1,j)==0)
                            map[t][i][j].addNeighbors(1,map[t][i + 1][j]);
                            if(m.getCell3DValue(t,i,j+1)==0)
                            map[t][i][j].addNeighbors(1,map[t][i][j + 1]);
                            if(m.getCell3DValue(t,i,j-1)==0)
                            map[t][i][j].addNeighbors(1,map[t][i][j - 1]);
                        } else if (i == row - 1) { //lower row
                            if(m.getCell3DValue(t,i-1,j)==0)
                                map[t][i][j].addNeighbors(1,map[t][i - 1][j]);
                            if(m.getCell3DValue(t,i,j+1)==0)
                                map[t][i][j].addNeighbors(1,map[t][i][j + 1]);
                            if(m.getCell3DValue(t,i,j-1)==0)
                                map[t][i][j].addNeighbors(1,map[t][i][j - 1]);
                        } else if (j == 0) { // most left column
                            if(m.getCell3DValue(t,i+1,j)==0)
                                map[t][i][j].addNeighbors(1,map[t][i + 1][j]);
                            if(m.getCell3DValue(t,i,j+1)==0)
                                map[t][i][j].addNeighbors(1,map[t][i][j + 1]);
                            if(m.getCell3DValue(t,i-1,j)==0)
                                map[t][i][j].addNeighbors(1,map[t][i - 1][j]);
                        } else if (j == col - 1) { // most right column
                            if(m.getCell3DValue(t,i+1,j)==0)
                                map[t][i][j].addNeighbors(1,map[t][i + 1][j]);
                            if(m.getCell3DValue(t,i,j-1)==0)
                                map[t][i][j].addNeighbors(1,map[t][i][j - 1]);
                            if(m.getCell3DValue(t,i-1,j)==0)
                                map[t][i][j].addNeighbors(1,map[t][i - 1][j]);
                        } else { // any other position which not have benn mentioned
                            if(m.getCell3DValue(t,i+1,j)==0)
                                map[t][i][j].addNeighbors(1,map[t][i + 1][j]);
                            if(m.getCell3DValue(t,i-1,j)==0)
                                map[t][i][j].addNeighbors(1,map[t][i - 1][j]);
                            if(m.getCell3DValue(t,i,j-1)==0)
                                map[t][i][j].addNeighbors(1,map[t][i][j - 1]);
                            if(m.getCell3DValue(t,i,j+1)==0)
                                map[t][i][j].addNeighbors(1,map[t][i][j + 1]);
                        }
                    }
                }
            }
        }
    }
    /**
     * This method is used to return all the current AState successor's as an array list.
     * @param s This is a state that we want to return all his successor.
     * @return arrayList of AState This returns all current state successor's .
     */
    public ArrayList<AState> getAllSuccessors(AState s) {
        Object[] stateSet = s.getNeighbors().keySet().toArray();
        ArrayList<AState> stateList = new ArrayList<>();
        for (int i = 0; i < stateSet.length; i++) {
            stateList.add((AState) stateSet[i]);
        }
        return stateList;
    }

    /**
     * This method checks if s is equals to goal.
     * @param s This is a parameter that represents a State we are on now.
     * @return boolean This returns true\false if s and goal are equals.
     */
    public boolean goalTest(AState s){
        return s.equals(getGoalState());
    }

    /**
     * This method returns maze's start state.
     * @return AState This returns mazes start state.
     */
    @Override
    public AState getStartState(){
        return start;
    }

    /**
     * This method returns a maze's goal state.
     * @return AState This returns mazes goal state .
     */
    @Override
    public AState getGoalState(){
        return end;
    }


}
