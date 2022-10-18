package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SolutionDisplayer extends Canvas {
    Maze maze;
    GraphicsContext graphicsContext;
    StringProperty imageFileNameSol = new SimpleStringProperty();
    Solution sol;
    boolean showSol=false;

    /**
     * Set the class maze attribute.
     * @param m the maze.
     */
    public void setMaze(Maze m) {
        this.maze = m;
    }

    /**
     * Function the clears the graphic context and clear the image.
     */
    public void clear(){
        if(graphicsContext!=null)
        this.graphicsContext.clearRect(0,0,getWidth(),getHeight());
    }
    /**
     * returns the imageFileNameSol attribute string.
     * @return string.
     */
    public String getImageFileNameSol() {
        return imageFileNameSol.get();
    }
    /**
     * sets the imageFileNameSol attribute string.
     * @param imageFileNameSol
     */
    public void setImageFileNameSol(String imageFileNameSol) {
        this.imageFileNameSol.set(imageFileNameSol);
    }

    /**
     * Sets the sou attribute.
     * @param sol the solution given.
     */
    public void setSolution(Solution sol){
        this.sol=sol;
    }

    /**
     * change the value of the boolean attribute.
     */
    public void changeSol(){
        if(showSol){
            showSol=false;
        }
        else{
            showSol=true;
        }
    }
    /**
     * draw the solution image according to the size of the maze and the canvas.
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

            if(showSol){
                ArrayList<AState> path;
                if(this.sol!=null) {
                    path = this.sol.getSolutionPath();
                    for (int i = 0; i < path.size(); i++) {
                        MazeState m = (MazeState) path.get(i);
                        h = m.getRow() * cellHeight;
                        w = m.getColumn()* cellWidth;
                        Image solImage=null;
                        try {
                            solImage = new Image(new FileInputStream(getClass().getResource(getImageFileNameSol()).getFile()));
                        } catch (FileNotFoundException e) {
                            ErrorAlert er=new ErrorAlert("Unable to load Sol Image");

                        }
                        if(solImage==null){
                            graphicsContext.fillRect(w,h,cellWidth,cellHeight);
                        }
                        else{
                            graphicsContext.drawImage(solImage,w,h,cellWidth,cellHeight);
                        }
                    }
                }
            }


        }
    }
}
