package View;

import algorithms.mazeGenerators.Maze;
import com.sun.javafx.scene.EventHandlerProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

import java.io.FileNotFoundException;

/**
 * Class that represents a character displayer.
 * the class extends canvas class.
 * this class will be responsible for showing and moving the character image.
 *
 */
public class CharacterDisplayer extends Canvas {
    private int row_player =0;
    private int col_player =0;
    GraphicsContext graphicsContext;
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    Maze maze;
    String name="Pikachu";

    /**
     * Retruns string which is the name of the pokemon.
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the character.
     * @param name the name of the character.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the class maze attribute and update players row and column index.
     * @param m the maze.
     */
    public void setMaze(Maze m){
        this.maze = m;
        row_player=m.getStartPosition().getRowIndex();
        col_player=m.getStartPosition().getColumnIndex();
    }

    /**
     * returns the imageFileNamePlayer attribute string.
     * @return string.
     */
    public String getImageFileNamePlayer() {

        return imageFileNamePlayer.get();
    }

    /**
     * sets the imageFileNamePlayer attribute string.
     * @param imageFileNamePlayer
     */
    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    /**
     * returns the player row index.
     * @return the player row index.
     */
    public int getRow_player() {
        return row_player;
    }
    /**
     * returns the player column index.
     * @return the player column index.
     */
    public int getCol_player() {
        return col_player;
    }

    /**
     * sets the player position and draw the image in the new position.
     * @param row row index of the char position.
     * @param col column index of the char position.
     */
    public void set_player_position(int row, int col) {
        this.row_player = row;
        this.col_player = col;
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
     * draw the player image according to the size of the maze and the canvas.
     * draws red rectangle if image was not given.
     */
    public void draw() {
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
            //Draw player
            double h_player = getRow_player() * cellHeight;
            double w_player = getCol_player() * cellWidth;
            Image playerImage = null;
            try {
                playerImage = new Image(getClass().getResource(getImageFileNamePlayer()).toURI().toString());
            } catch (Exception e) {
                ErrorAlert er=new ErrorAlert("Unable to load pokemon Image");
            }
            if(playerImage==null){
                graphicsContext.fillRect(w_player,h_player,cellWidth,cellHeight);
            }
            else{
                graphicsContext.drawImage(playerImage,w_player,h_player,cellWidth,cellHeight);
            }

        }
    }


}
