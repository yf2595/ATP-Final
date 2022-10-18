package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

/**
 * The SearchableMaze class implements ISearchable. Its purpose is to transform a maze into searchable problem .
 * map- a 2D array of MazeState. this map containing states of maze.
 */
public class SearchableMaze implements ISearchable{
    Maze m;
    MazeState start,end;
    MazeState[][] map;

    /**
     * Class SearchableMaze constructor .
     * @param m This is a maze that we want to transform.
     */
    public SearchableMaze(Maze m) {
        this.m = m;
        int row=m.getRows();
        int col=m.getColumns();
        map=new MazeState[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                //insert a start position to map and to attribute start.
                if(i==m.getStartPosition().getRowIndex() && j==m.getStartPosition().getColumnIndex()){
                    map[i][j]=new MazeState(i,j);
                    this.start=map[i][j];
                }
                //insert a goal position to map and to attribute goal.
                else if(i==m.getGoalPosition().getRowIndex() && j==m.getGoalPosition().getColumnIndex()){
                    map[i][j]=new MazeState(i,j);
                    this.end=map[i][j];

                }
                else {
                //insert every other valid move into the map.
                    if(m.getCellvalue(i,j)==0){
                        map[i][j] = new MazeState(i, j);
                    }
                }
            }
        }
        getAllPossibleStates(row,col); //after initializing all the states we will update their neighbors
    }

    /**
     * This method returns all AState successor's in array list.
     * @param s This is a state that we want to return all his successor.
     * @return arrayList of AState This returns all param s successor's .
     */
    public ArrayList<AState> getAllSuccessors(AState s){
        Object[] stateSet= s.getNeighbors().keySet().toArray();
        ArrayList<AState> stateList= new ArrayList<>();
        for(int i=0;i<stateSet.length;i++){
            stateList.add((AState) stateSet[i]);
        }
        return  stateList;
    }

    /**
     * This method checks if param s is equals to goal.
     * @param s This is a parameter to equals goal.
     * @return boolean This returns true\false if s and goal are equals.
     */
    public boolean goalTest(AState s){
        return
                s.equals(getGoalState());
    }

    /**
     * This method is used to update maps state neighbors with all possible valid states around them.
     * @param row Mazes total number of rows
     * @param col Mazes total number of columns
     */
    public void getAllPossibleStates(int row,int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (m.getCellvalue(i, j) == 0) {
                    if (i == 0 && j == 0) {
                        //left upper corner
                        if (m.getCellvalue(i + 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i + 1][j]);
                        }
                        if (m.getCellvalue(i, j + 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j + 1]);
                        }
                        if (m.getCellvalue(i, j) == 0 && m.getCellvalue(i + 1, j + 1) == 0) {
                            if (m.getCellvalue(i + 1, j) == 0 || m.getCellvalue(i, j + 1) == 0) {
                                map[i][j].addNeighbors(3, map[i + 1][j + 1]);
                            }
                        }
                    }
                    else if (i == row - 1 && j == 0) {
                        //left lower corner
                        if (m.getCellvalue(i, j + 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j + 1]);
                        }
                        if (m.getCellvalue(i - 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i - 1][j]);
                        }
                        if (m.getCellvalue(i, j) == 0 && m.getCellvalue(i - 1, j + 1) == 0) {
                            if (m.getCellvalue(i - 1, j) == 0 || m.getCellvalue(i, j + 1) == 0) {
                                map[i][j].addNeighbors(3, map[i - 1][j + 1]);
                            }
                        }
                    } else if (i == row - 1 && j == col - 1) {
                        //right lower corner
                        if (m.getCellvalue(i - 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i - 1][j]);
                        }
                        if (m.getCellvalue(i, j - 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j - 1]);
                        }
                        if (m.getCellvalue(i, j) == 0 && m.getCellvalue(i - 1, j - 1) == 0) {
                            if (m.getCellvalue(i - 1, j) == 0 || m.getCellvalue(i, j - 1) == 0) {
                                map[i][j].addNeighbors(3, map[i - 1][j - 1]);
                            }
                        }
                    } else if (i == 0 && j == col - 1) {
                        //right upper corner
                        if (m.getCellvalue(i + 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i + 1][j]);
                        }
                        if (m.getCellvalue(i, j - 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j - 1]);
                        }
                        if (m.getCellvalue(i, j) == 0 && m.getCellvalue(i + 1, j - 1) == 0) {
                            if (m.getCellvalue(i + 1, j) == 0 || m.getCellvalue(i, j - 1) == 0) {
                                map[i][j].addNeighbors(3, map[i + 1][j - 1]);
                            }
                        }
                    } else if (i == 0) {
                        //the upper row
                        if (m.getCellvalue(i + 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i + 1][j]);
                        }
                        if (m.getCellvalue(i, j + 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j + 1]);
                        }
                        if (m.getCellvalue(i, j - 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j - 1]);
                        }
                        if (m.getCellvalue(i, j) == 0) {
                            if (m.getCellvalue(i + 1, j + 1) == 0) {
                                if (m.getCellvalue(i, j + 1) == 0 || m.getCellvalue(i + 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i + 1][j + 1]);
                                }
                            }
                            if (m.getCellvalue(i + 1, j - 1) == 0) {
                                if (m.getCellvalue(i, j - 1) == 0 || m.getCellvalue(i + 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i + 1][j - 1]);
                                }
                            }
                        }
                    } else if (i == row - 1) {
                        //the bottom row
                        if (m.getCellvalue(i - 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i - 1][j]);
                        }
                        if (m.getCellvalue(i, j + 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j + 1]);
                        }
                        if (m.getCellvalue(i, j - 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j - 1]);
                        }
                        if (m.getCellvalue(i, j) == 0) {
                            if (m.getCellvalue(i - 1, j + 1) == 0) {
                                if (m.getCellvalue(i, j + 1) == 0 || m.getCellvalue(i - 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i - 1][j + 1]);
                                }
                            }
                            if (m.getCellvalue(i - 1, j - 1) == 0) {
                                if (m.getCellvalue(i, j - 1) == 0 || m.getCellvalue(i - 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i - 1][j - 1]);
                                }
                            }
                        }
                    } else if (j == 0) {
                        //the most left column
                        if (m.getCellvalue(i + 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i + 1][j]);
                        }
                        if (m.getCellvalue(i, j + 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j + 1]);
                        }
                        if (m.getCellvalue(i - 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i - 1][j]);
                        }
                        if (m.getCellvalue(i, j) == 0) {
                            if (m.getCellvalue(i + 1, j + 1) == 0) {
                                if (m.getCellvalue(i, j + 1) == 0 || m.getCellvalue(i + 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i + 1][j + 1]);
                                }
                            }
                            if (m.getCellvalue(i - 1, j + 1) == 0) {
                                if (m.getCellvalue(i, j + 1) == 0 || m.getCellvalue(i - 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i - 1][j + 1]);
                                }
                            }
                        }
                    } else if (j == col - 1) {
                        //the the most right column
                        if (m.getCellvalue(i + 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i + 1][j]);
                        }
                        if (m.getCellvalue(i, j - 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j - 1]);
                        }
                        if (m.getCellvalue(i - 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i - 1][j]);
                        }
                        if (m.getCellvalue(i, j) == 0) {
                            if (m.getCellvalue(i - 1, j - 1) == 0) {
                                if (m.getCellvalue(i, j - 1) == 0 || m.getCellvalue(i - 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i - 1][j - 1]);
                                }
                            }
                            if (m.getCellvalue(i + 1, j - 1) == 0) {
                                if (m.getCellvalue(i, j - 1) == 0 || m.getCellvalue(i + 1, j) == 0) {
                                    map[i][j].addNeighbors(3, map[i + 1][j - 1]);
                                }
                            }
                        }
                    } else {
                        //every other maze cell
                        if (m.getCellvalue(i + 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i + 1][j]);
                        }
                        if (m.getCellvalue(i - 1, j) == 0) {
                            map[i][j].addNeighbors(2, map[i - 1][j]);
                        }
                        if (m.getCellvalue(i, j - 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j - 1]);
                        }
                        if (m.getCellvalue(i, j + 1) == 0) {
                            map[i][j].addNeighbors(2, map[i][j + 1]);
                        }
                        if (m.getCellvalue(i, j) == 0) {
                            if (m.getCellvalue(i - 1, j) == 0 || m.getCellvalue(i, j + 1) == 0) {
                                if (m.getCellvalue(i - 1, j + 1) == 0) {
                                    map[i][j].addNeighbors(3, map[i - 1][j + 1]);
                                }
                            }
                            if (m.getCellvalue(i - 1, j) == 0 || m.getCellvalue(i, j - 1) == 0) {
                                if (m.getCellvalue(i - 1, j - 1) == 0) {
                                    map[i][j].addNeighbors(3, map[i - 1][j - 1]);
                                }
                            }
                            if (m.getCellvalue(i + 1, j) == 0 || m.getCellvalue(i, j - 1) == 0) {
                                if (m.getCellvalue(i + 1, j - 1) == 0) {
                                    map[i][j].addNeighbors(3, map[i + 1][j - 1]);
                                }
                            }
                            if (m.getCellvalue(i, j + 1) == 0 || m.getCellvalue(i + 1, j) == 0) {
                                if (m.getCellvalue(i + 1, j + 1) == 0) {
                                    map[i][j].addNeighbors(3, map[i + 1][j + 1]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * This method returns maze's goal state.
     * @return AState This returns a goal state .
     */
    @Override
    public AState getGoalState() {
        return end;
    }

    /**
     * This method returns maze's start state.
     * @return AState This returns a start state.
     */
    public AState getStartState() {
        return start;
    }




}
