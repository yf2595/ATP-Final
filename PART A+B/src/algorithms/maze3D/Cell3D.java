package algorithms.maze3D;

import java.util.ArrayList;
import java.util.Stack;
/**
 * The cell3D class describes a cell in a 3D maze.
 */
public class Cell3D {
    private boolean isVisited;
    private final int row;
    private final int column;
    private final int depth;
    private int value;
    private ArrayList<Cell3D> neighbors;
    private int kashir;
    private int numNeig;

    /**
     * Class constructor.
     * @param row This is a parameter that describe Cells row index in the maze.
     * @param column This is a parameter that describe Cells column index in the maze.
     * @param depth This is a parameter that describe the Cells depth index in the maze.
     */
    public Cell3D(int row, int column, int depth) {
        this.row = row;
        this.column = column;
        this.depth = depth;
        this.isVisited=false;
        this.value=1;
        neighbors= new ArrayList<>();
        kashir = -1;
        numNeig=0;
    }

    /**
     * This method sets cells kashir .
     * @param kashir This is a new kashir to this cell.
     */

    public void setKashir(int kashir) {

        this.kashir = kashir;
    }
    /**
     * This method returns a cells kashir attribute.
     * @return int This returns the kashir attribute of this cell.
     */
    public int getKashir() {

        return kashir;
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
    public void addNeighbors(Cell3D c){

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
    public Cell3D getNeighborsByIndex(int i){

        return neighbors.get(i);
    }
    /**
     * This method removing a c cell from this cell neighbors map.
     * @param c is a cell neighbor.
     */
    public void removeNeighbors(Cell3D c){

        neighbors.remove(c);
    }
    /**
     * This method is updating the kashirut attribute of all the cells int this cells neighbors map.
     */
    public void kashirut(){
        Stack<Cell3D> s = new Stack<>();
        for(int i=0;i<getNumOfNeighbors();i++){
            if(this.neighbors.get(i).getKashir()!=this.kashir && this.neighbors.get(i).getKashir()!=-1){
                s.push(neighbors.get(i));
                neighbors.get(i).setKashir(kashir);
            }
        }
        Cell3D c;
        while(!s.isEmpty()){
            c= s.pop();
            for (int i=0; i<c.getNumOfNeighbors();i++){
                if(c.getNeighborsByIndex(i).getKashir()!=-1 && c.getNeighborsByIndex(i).getKashir()!=kashir){
                    s.push(c.getNeighborsByIndex(i));
                    c.getNeighborsByIndex(i).setKashir(kashir);
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
     * This method is check if k will be this kashir attribute, we close a circle.
     * @param k is a kashir number.
     */
    public boolean is_circle(int k){
        boolean answer = false;
        int count=0;
        for(int i=0;i<getNumOfNeighbors();i++){
            if(this.neighbors.get(i).kashir==k){
                count++;
            }
        }
        if(count>=2){
            answer = true;
        }
        return answer;
    }

    /**
     * This method updating this cells neighbors map.
     * by removing the cells that already has been visited
     */
    public void checkNeighbors(){
        Cell3D curr;
        for(int i=0;i<neighbors.size();i++){
            curr=neighbors.get(i);
            if(curr.isVisited){
                neighbors.remove(curr);
            }
        }
    }
}
