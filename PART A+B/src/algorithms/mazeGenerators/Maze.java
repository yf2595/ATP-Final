package algorithms.mazeGenerators;
import java.io.Serializable;
import java.util.Random;

/**
 * This class represents a 2d Maze.
 */
public class Maze implements Serializable {
    private int[][] data;
    private  int rows;
    private  int columns;
    private Position start,goal;

    /**
     * Class maze constructor .
     * @param rows This is a parameter that describe the number of rows in the maze.
     * @param columns This is a parameter that describe the number of columns in the maze.
     */
    public Maze(int rows, int columns) {
        if(rows<2 || columns <2){
            throw new IllegalArgumentException("Maze size has to be at least 2X2");
        }
        this.rows = rows;
        this.columns = columns;
        data= new int[rows][columns];
    }

    /**
     * Function that creates a unique string that represents the maze
     * @return String that represents an unique id of the maze
     */
    public String mazeAsString(){
        int numof0=0;
        int firstRow0=0;
        int secondRow0=0;
        int firstCol0=0;
        int secondCol0=0;
        for(int row=0;row<rows;row++){
            for(int col=0;col<columns;col++){
                if(data[row][col]==0){
                    numof0++;
                    if(row==0) firstRow0++;
                    if(row==1) secondRow0++;
                    if(col==0) firstCol0++;
                    if(col==1) secondCol0++;
                }
            }
        }
        return String.valueOf(rows)+" "+String.valueOf(columns) +" "+start.toString()+" "+goal.toString()+" "+String.valueOf(numof0)+" "+String.valueOf(firstRow0)+ " "+String.valueOf(secondRow0)+" "+String.valueOf(firstCol0)+ " "+String.valueOf(secondCol0)+" ";
    }
    public Maze(byte[] byteArray){
        int i=0;
        int seperate = 0;
        while(seperate<4){
            if(byteArray[i]<0){
                this.rows += 256 + byteArray[i];
            }
            else {
                this.rows += byteArray[i];
            }
            i++;
            seperate++;
        }
        seperate=0;
        while(seperate<4){
            if(byteArray[i]<0) {
                this.columns+=256+byteArray[i];
            }
            else {
                this.columns += byteArray[i];
            }
            i++;
            seperate++;
        }
        seperate=0;
        data=new int[rows][columns];
        int startRow=0;
        while(seperate<4){
            if(byteArray[i]<0) {
                startRow+=256+byteArray[i];
            }
            else {
                startRow += byteArray[i];
            }
            i++;
            seperate++;
        }
        Position sp=new Position(startRow,0);
        setStartPosition(sp);
        int goalRow=0;
        seperate=0;
        while(seperate<4){
            if(byteArray[i]<0) {
                goalRow+=256+byteArray[i];
            }
            else {
                goalRow += byteArray[i];
            }
            i++;
            seperate++;
        }
        Position gp=new Position(goalRow,columns-1);
        setGoalPosition(gp);
        for(int row=0;row<rows;row++){
            for(int col=0;col<columns;col++){
                data[row][col]=byteArray[i++];
            }
        }
    }

    /**
     * This method returns the number of rows.
     * @return int This returns a row attribute of this maze.
     */
    public int getRows() {
        return rows;
    }

    /**
     * This method is return the number of columns .
     * @return int This returns a columns attribute of this maze.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * This method used to reset data attribute.
     */
    public void reset(){

        data= new int[rows][columns];
    }

    /**
     * This method finds a path from start to goal by choosing random cells.
     * @param sr This is a parameter that describe the start position row in the maze.
     * @param sc This is a parameter that describe the start position column  in the maze.
     * @param gr This is a parameter that describe the goal position row in the maze.
     * @param gc This is a parameter that describe the goal position column in the maze.
     */
    public void findPath(int sr,int sc,int gr, int gc){

        Random rand= new Random();
        while (!(sr == gr && sc == gc)){
            int choose=rand.nextInt(4);
            if(choose==1 && sr!=0 ){
                sr=sr-1;
            }
            else if(choose==2 && sc!= columns-1 ){
                sc=sc+1;
            }
            else if(choose==3 && sr!= rows-1 ){
                sr=sr+1;
            }
            set(sr,sc,3);

        }
    }

    /**
     * This method returns the cell value.
     * @param r Cells row.
     * @param c Cells column.
     * @return int Cells value.
     */
    public int getCellvalue(int r,int c){

        return data[r][c];
    }

    /**
     * This method is update the value of cell that exist in row r and column c.
     * @param r Cells row.
     * @param c Cells column.
     * @param option used to set the right value to the cell.
     */
    public void set(int r,int c,int option){
        if(option==0){ //value will be 0 (a pass)
            if(data[r][c]==1){
                data[r][c]=0;
            }
        }
        else if(option==1){ //value will be 1 (a wall)
            if(data[r][c]==0){
                data[r][c]=1;
            }
        }
        else if(option==2){ //value will be 0 (a pass)
            if(data[r][c]==2){
                data[r][c]=0;
            }
        }
        else if(option==3){ //value will be 2 (a pass in path from start to goal)
            if(data[r][c]==0){
                data[r][c]=2;
            }
        }
        else if(option==4){
            if(data[r][c]==0){
                data[r][c]=3;
            }
        }
    }

    /**
     * This method is updating the maze values according to the cell map
     * @param map This is a cells array that holds the right values for the data attribute.
     */
    public void getCellMap(Cell[][] map){
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                data[i][j]=map[i][j].getValue();
            }
        }
    }

    /**
     * This method  sets the maze start position.
     * @param start This is a new start position.
     */
    public void setStartPosition(Position start) {
        this.start = start;
        data[start.getRowIndex()][start.getColumnIndex()]=0;
    }

    /**
     * This method sets the maze goal position .
     * @param goal This is a new goal position.
     */
    public void setGoalPosition(Position goal) {
        this.goal = goal;
        data[goal.getRowIndex()][goal.getColumnIndex()]=0;
    }

    /**
     * This method returns the maze start position.
     * @return a start position attribute.
     */
    public Position getStartPosition() {
        return start;
    }

    /**
     * This method returns the maze goal position.
     * @return a goal position attribute.
     */
    public Position getGoalPosition() {
        return goal;
    }

    /**
     * This method is printing the maze.
     */
    public void print(){
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                if(j==0){
                    System.out.print("{ ");
                }
                if(i==start.getRowIndex() && j==start.getColumnIndex()){
                    if(j==columns-1){
                        System.out.println("S");
                    }
                    else{
                        System.out.print("S ");
                    }
                }
                else if(i==goal.getRowIndex() && j==goal.getColumnIndex()){
                    if(j==columns-1){
                        System.out.println("E }");
                    }
                    else{
                        System.out.print("E ");
                    }
                }
                else {
                    if (j == columns - 1) {
                        System.out.println(data[i][j]+" }");
                    } else {
                        System.out.print(data[i][j] + " ");
                    }
                }
            }
        }
    }
    public byte[] toByteArray(){
        byte[] b=new byte[rows*columns+16];
        int[] arr={rows,columns,start.getRowIndex(), goal.getRowIndex()};
        int bCount=0;
        for(int in=0;in<arr.length;in++){
            byte seperate=0;
            int curr=arr[in];
            while(curr>=0){
                if(curr<=255){
                    b[bCount++]=(byte)curr;
                    seperate++;
                    break;
                }
                curr=curr-255;
                b[bCount++]=(byte) 255;
                seperate++;
            }
            while(seperate!=4){
                b[bCount++]=0;
                seperate++;
            }
        }
        for(int row=0;row<rows;row++){
            for(int col=0;col<columns;col++){
                b[bCount++]=(byte)data[row][col];
            }
        }
        return b;
    }
}
