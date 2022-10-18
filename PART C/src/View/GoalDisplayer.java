package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 * Class that represents a Goal displayer.
 * the class extends canvas class.
 * this class will be responsible for showing the goal image.
 *
 */
public class GoalDisplayer extends Canvas {
    private Maze maze;
    private int end_pos_row;
    private int end_pos_col;
    GraphicsContext graphicsContext;
    StringProperty imageFileNameEnd = new SimpleStringProperty();
    /**
     * Set the class maze attribute and update the goal row and column index.
     * @param m the maze.
     */
    public void setMaze(Maze m) {
        this.maze = m;
        end_pos_row = m.getGoalPosition().getRowIndex();
        end_pos_col = m.getGoalPosition().getColumnIndex();
    }
    /**
     * returns the imageFileNameEnd attribute string.
     * @return string.
     */
    public String getImageFileNameEnd() {
        return imageFileNameEnd.get();
    }

    /**
     * sets the class end_pos attributes
     * @param row the row index of the goal.
     * @param col the column index of the goal.
     */
    public void setEnd_position(int row,int col){
        end_pos_col=col;
        end_pos_row=row;
    }
    /**
     * sets the imageFileNameEnd attribute string.
     * @param imageFileNameEnd
     */
    public void setImageFileNameEnd(String imageFileNameEnd) {

        this.imageFileNameEnd.set(imageFileNameEnd);
    }
    /**
     * Function the clears the graphic context and clear the image.
     */
    public void clear(){
        if(graphicsContext==null){
            return;
        }
        this.graphicsContext.clearRect(0,0,getWidth(),getHeight());
    }
    /**
     * draw the goal image according to the size of the maze and the canvas.
     * draws red rectangle if image was not given.
     */
    public void draw()
    {
        if( maze!=null)
        {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int row = maze.getRows();
            int col = maze.getColumns();
            double cellHeight = (canvasHeight/row);
            double cellWidth = (canvasWidth/col);
            graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0,0,canvasWidth,canvasHeight);
            graphicsContext.setFill(Color.RED);
            double w,h;


            //Draw End Image
            Image endImage=null;
            try {
                endImage = new Image(new FileInputStream(getClass().getResource(getImageFileNameEnd()).getFile()));
            } catch (FileNotFoundException e) {
                ErrorAlert er=new ErrorAlert("Unable to load End Image");

            }
            double w_end=maze.getGoalPosition().getRowIndex()*cellHeight;
            double h_end=maze.getGoalPosition().getColumnIndex()*cellWidth;
            setEnd_position(maze.getGoalPosition().getRowIndex(),maze.getGoalPosition().getColumnIndex());
            if(endImage==null){
                graphicsContext.fillRect(w_end,h_end,cellWidth,cellHeight);
            }
            else{
                graphicsContext.drawImage(endImage,h_end,w_end,cellWidth,cellHeight);
            }

        }
    }


}