package View;

import algorithms.mazeGenerators.Maze;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;


public class MazeDisplayer extends Canvas implements Serializable {
    private Maze maze;
    GraphicsContext graphicsContext;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    private int playerRow = 0;
    private int playerCol = 0;

    /**
     * returns the height of the cell as a result of the maze and the canvas size.
     * @return double, the height of cell in the maze.
     */
    public double getCellHeight() {
        return getHeight()/maze.getRows();
    }
    /**
     * returns the Width of the cell as a result of the maze and the canvas size.
     * @return double, the Width of cell in the maze.
     */
    public double getCellWidth() {
        return getWidth()/maze.getColumns();
    }

    /**
     * sets the player position and draw the image in the new position.
     * @param row row index of the char position.
     * @param col column index of the char position.
     */
    public void setPlayerPosition(int row, int col){
        this.playerRow = col;
        this.playerCol = row;
        draw();
    }
    /**
     * returns the imageFileNameWall attribute string.
     * @return string.
     */
    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }
    /**
     * sets the imageFileNameWall attribute string.
     * @param imageFileNameWall
     */
    public void setImageFileNameWall(String imageFileNameWall) {

        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void drawMaze(Maze maze) {
        this.maze = maze;
        playerRow = maze.getStartPosition().getColumnIndex();
        playerCol = maze.getStartPosition().getRowIndex();
        draw();
    }
    /**
     * Function the clears the graphic context and clear the image.
     */
    public void clear(){
        if (graphicsContext==null){
            return;
        }
        this.graphicsContext.clearRect(0,0,getWidth(),getHeight());
    }
    /**
     * draw the walls of the maze according to the size of the maze and the canvas.
     * draws red rectangle if image was not given.
     * also draws rectangle from 4 lines to frame the maze.
     */
    public void draw(){
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
            //Draw Maze
            Image wallImage = null;
            try {
                wallImage = new Image(new FileInputStream(getClass().getResource(getImageFileNameWall()).getFile()));
            } catch (FileNotFoundException e) {
                ErrorAlert er=new ErrorAlert("Unable to load Wall Image");
            }
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    if(maze.getCellvalue(i,j) == 1) // Wall
                    {
                        h = i * cellHeight;
                        w = j * cellWidth;
                        if (wallImage == null){
                            graphicsContext.fillRect(w,h,cellWidth,cellHeight);
                        }else{
                            graphicsContext.drawImage(wallImage,w,h,cellWidth,cellHeight);
                        }
                    }
                }
            }
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.setLineWidth(10);
            graphicsContext.strokeLine(0, 0, 0, canvasHeight);
            graphicsContext.strokeLine(0, 0, canvasWidth, 0);
            graphicsContext.strokeLine(canvasWidth, 0, canvasWidth, canvasHeight);
            graphicsContext.strokeLine(0,canvasHeight , canvasWidth, canvasHeight);
            //drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }






/*
    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = playerRow * cellWidth;
        double y = playerCol * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getClass().getResource(getImageFileNamePlayer()).getFile()));
        } catch (FileNotFoundException e) {
            ErrorAlert er=new ErrorAlert("Unable to load player Image");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

*/
}
