package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The cell class describes a cell in the maze.
 */
public class Cell {
    boolean isVisited;
    int row;
    int column;
    int value;
    ArrayList<Cell> neighbors;
    int connectNum;
    int numNeig;

    /**
     * Class constructor.
     * @param row This is a parameter that describe Cells row index in the maze.
     * @param column This is a parameter that describe Cells column index in the maze.
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.isVisited=false;
        this.value=1;
        neighbors= new ArrayList<>();
        connectNum = -1;
        numNeig=0;
    }

    /**
     * This method sets cells connectNum.
     * @param connectNew This is a new connect number to this cell.
     */
    public void setConnectNum(int connectNew) {
        this.connectNum = connectNew;
    }

    /**
     * This method returns a cells connectNum attribute.
     * @return int This returns the connectNum attribute of this cell.
     */
    public int getConnectNum() {
        return connectNum;
    }

    /**
     * This method marks the cell as visited.
     */
    public void visit(){
        isVisited=true;
        value=0;
    }

    /**
     * This method returns the cell isVisited attribute.
     * @return boolean This returns true/false if this cell is visited.
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * This method is adds c to the cell neighbors map
     */
    public void addNeighbors(Cell c){

        neighbors.add(c);
    }

    /**
     * This method returns cell row index.
     * @return int This returns a row attribute of this cell.
     */
    public int getRow() {
        return row;
    }

    /**
     * This method returns cell column index.
     * @return int This returns a column attribute of this cell.
     */
    public int getColumn() {
        return column;
    }

    /**
     * This method returns this cell value.
     * @return int This returns a value attribute of this cell.
     */
    public int getValue() {
        return value;
    }

    /**
     * This method returns this cells number of neighbors .
     * @return int This returns a number of neighbors.
     */
    public int getNumOfNeighbors(){
        return neighbors.size()+numNeig;
    }

    /**
     * This method returns the cell that locating in index i in this cells neighbors map
     * @return cell Returns the cell at index i
     */
    public Cell getNeighborsByIndex(int i){
        return neighbors.get(i);
    }

    /**
     * This method removing a c cell from this cell neighbors map.
     * @param c is a cell neighbor.
     */
    public void removeNeighbors(Cell c){
        neighbors.remove(c);
    }

    /**
     * This method is updating the connectNum of all the cells that are a part of the father connected compound.
     */
    public void CC(){
        Stack<Cell> s = new Stack<>();
        //iterate throw the neighbors of this cell: insert to s stack if the connectNum is not equal to this cell, and set connectNum.
        for(int i=0;i<getNumOfNeighbors();i++){
            //check if this neighbor didn't have the same "connectNum" like this cell.
            if(this.neighbors.get(i).getConnectNum()!=this.connectNum && this.neighbors.get(i).getConnectNum()!=-1){
                //insert this neighbor to s stack.
                s.push(neighbors.get(i));
                //change the connectNum for this neighbor
                neighbors.get(i).setConnectNum(connectNum);
            }
        }
        Cell c;
        //iterate throw the s stack and insert all neighbor's that dosen't have the same connectNum.
        while(!s.isEmpty()){
            c= s.pop();
            for (int i=0; i<c.getNumOfNeighbors();i++){
                //check if this neighbor didn't have the same "connectNum" like this cell.
                if(c.getNeighborsByIndex(i).getConnectNum()!=-1 && c.getNeighborsByIndex(i).getConnectNum()!=connectNum){
                    //insert this neighbor to s stack.
                    s.push(c.getNeighborsByIndex(i));
                    //change the connectNum for this neighbor
                    c.getNeighborsByIndex(i).setConnectNum(connectNum);
                }
            }
        }
    }

    /**
     * This method updating the isVisited attribute to be false.
     */
    public void noVisit (){
        isVisited = false;
    }

    /**
     * This method is check: Are closed a circle if K will be the connectNum for this cell?
     * @param k is a connect number.
     * @return a boolean- Are closed a circle?
     */
    public boolean is_circle(int k){
        boolean answer = false;
        int count=0;
        for(int i=0;i<getNumOfNeighbors();i++){ //iterate throw the neighbors of this cell.
            if(this.neighbors.get(i).connectNum==k){ // check if neighbor's connectNum is equal k.
                count++;
            }
        }
        if(count>=2){ //if more than 2 neighbors have connectNum=k -> so we have a circle if k will be connectNum for this cell.
            answer = true;
        }
        return answer;
    }

    /**
     * This method updating this cells neighbors map.
     * by removing the cells that already has been visited
     */
    public void checkNeighbors(){
        Cell curr;
        for(int i=0;i<neighbors.size();i++){ ///iterate throw the neighbors of this cell.
            curr=neighbors.get(i);
            if(curr.isVisited){ // check if this neighbor is visited
                neighbors.remove(curr); //if this neighbor is visited -> remove it from arrayList neighbors of this cell.
            }
        }
    }
}

