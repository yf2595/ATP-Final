package ViewModel;

import Model.IModel;
import Model.Victors;
import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import java.io.*;
import java.util.Observable;
import java.util.Observer;

/**
 * View Model class is a part of the MVVM structure.
 * this class is observed by the View class.
 * this class observe the model class.
 * this class main job is to connect between the user operation in the view to the program logic in the model.
 */
public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private Maze maze;
    private int rowChar;
    private int colChar;
    private Solution sol;

    /**
     * Class constructor.
     * assign the observer.
     * @param model the IModel instance.
     */
    public MyViewModel(IModel model) {
        this.model = model;
        this.maze = null;
        this.sol=null;
        model.assignObserver(this);
    }

    /**
     * return the maze attribute.
     * @return maze.
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * return the rowChar attribute.
     * @return int rowChar - players row index in the maze.
     */
    public int getRowChar() {
        return rowChar;
    }
    /**
     * return the colChar attribute.
     * @return int ColChar - players column index in the maze.
     */
    public int getColChar() {
        return colChar;
    }

    /**
     * Observer update override.
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        update();
        setChanged();
        notifyObservers(arg);
    }

    public void update(){
        if(maze!= null){
            rowChar=model.getRowChar();
            colChar=model.getColChar();
            maze= model.getMaze();
        }
        else{
            maze= model.getMaze();
            rowChar=model.getRowChar();
            colChar=model.getColChar();
        }
    }
    /**
     * calling to model finished function.
     */
    public void finished(){
        this.model.finished();
    }

    /**
     * calling to model generateMaze function.
     * @param row num of rows in the new maze.
     * @param col num of columns in the new maze.
     */
    public void generateMaze(int row,int col) throws IOException
    {
        this.model.generateMaze(row,col);
        maze=model.getMaze();
    }
    /**
     * calling to model save function.
     */
    public void save()throws IOException{
        model.save();
    }
    /**
     * calling to model load function.
     * update class attributes after the loading.
     */
    public void load() throws IOException, ClassNotFoundException{
        model.load();
        update();
    }
    /**
     * calling to model saveScore function.
     */
    public void saveScore(String name, Integer score) throws IOException, ClassNotFoundException {
        model.saveScore(name,score);
    }
    /**
     * calling to model getVictors function.
     */
    public Victors getVictors(){
        return model.getVictors();
    }

    /**
     * using switch case to read the player movement in the gui
     * and sent the result to the model lair to move the character.
     * @param keyEvent the key pressed in the gui.
     */
    public void moveCharacter(KeyEvent keyEvent)
    {
        int direction = -1;

        switch (keyEvent.getCode()){
            case NUMPAD1:
                direction = 1;
                break;
            case NUMPAD2:
                direction = 2;
                break;
            case NUMPAD3:
                direction = 3;
                break;
            case NUMPAD4:
                direction = 4;
                break;
            case NUMPAD6:
                direction = 6;
                break;
            case NUMPAD7:
                direction = 7;
                break;
            case NUMPAD8:
                direction = 8;
                break;
            case NUMPAD9:
                direction = 9;
                break;

        }

        model.updateCharacterLocation(direction);

    }
    /**
     * calling to model getScore function.
     */
    public Integer getscore(){
        return model.getScore();
    }

    /**
     * calling to model ResetScore function.
     */
    public void resetScore(){
        model.ResetScore();
    }
    /**
     * calling to model solveMaze function.
     */
    public void solveMaze() throws IOException
    {
        model.solveMaze();
    }
    /**
     * calling to model closeServers function.
     */
    public void closeServers(){
        model.closeServers();
    }
    /**
     * calling to model getSolution function.
     */
    public Solution getSolution()
    {

        return model.getSolution();
    }
    /**
     * calling to model MusicPlay function.
     */
    public void MusicPlay(){

        model.MusicPlay();
    }
    /**
     * calling to model soundProperties function.
     */
    public void soundProperties(){
        model.soundProperties();
    }

    /**
     * this function will understand the user mouse movement in order to send the model and instruction
     * about how to move the character.
     * @param mouse mouseevent.
     * @param mazeD mazeDisplayer instance.
     */
    public void moveCharacterWithMouse(MouseEvent mouse, MazeDisplayer mazeD) {
        double mouseRow = mouse.getY();
        double mouseCol = mouse.getX();
        double cellHeight = mazeD.getCellHeight();
        double cellWidth = mazeD.getCellWidth();

        //if(row == rowChar && col==colChar){
        if(rowChar+1 > mouseRow/cellHeight && mouseRow/cellHeight > rowChar-1 && colChar+1 > mouseCol/cellWidth && colChar-1 < mouseCol/cellWidth){
            return;
        }
        //if(row == rowChar-1 && mouse.getX()==colChar-1){
        if(rowChar < mouseRow/cellHeight && rowChar+2 > mouseRow/cellHeight && colChar > mouseCol/cellWidth && colChar-2 < mouseCol/cellWidth){
            model.updateCharacterLocation(1);
            return;
        }
        //if(row == rowChar-1 && col==colChar){
        if(rowChar < mouseRow/cellHeight && rowChar+2 > mouseRow/cellHeight && colChar+1 > mouseCol/cellWidth && colChar-1 < mouseCol/cellWidth){
            model.updateCharacterLocation(2);
            return;
        }
        //if(row == rowChar-1 && col==colChar+1){
        if(rowChar < mouseRow/cellHeight && rowChar+2 > mouseRow/cellHeight && colChar < mouseCol/cellWidth && colChar+2 > mouseCol/cellWidth){
            model.updateCharacterLocation(3);
            return;
        }
        if(rowChar+1 > mouseRow/cellHeight && mouseRow/cellHeight > rowChar-1 && colChar > mouseCol/cellWidth && colChar-2 < mouseCol/cellWidth){
            //if(row == rowChar && col==colChar-1){
            model.updateCharacterLocation(4);
            return;
        }
        //if(row == rowChar && col==colChar+1){
        if(rowChar+1 > mouseRow/cellHeight && mouseRow/cellHeight > rowChar-1 && colChar < mouseCol/cellWidth && colChar+2 > mouseCol/cellWidth){
            model.updateCharacterLocation(6);
            return;
        }
        //if(row == rowChar+1 && col==colChar-1){
        if(rowChar > mouseRow/cellHeight && mouseRow/cellHeight > rowChar-2 && colChar > mouseCol/cellWidth && colChar-2 < mouseCol/cellWidth){
            model.updateCharacterLocation(7);
            return;
        }
        //if(row == rowChar+1 && col==colChar){
        if(rowChar > mouseRow/cellHeight && mouseRow/cellHeight > rowChar-2 && colChar+1 > mouseCol/cellWidth && colChar-1 < mouseCol/cellWidth){
            model.updateCharacterLocation(8);
            return;
        }
        //if(row == rowChar+1 && col==colChar+1){
        if(rowChar > mouseRow/cellHeight && mouseRow/cellHeight > rowChar-2 && colChar < mouseCol/cellWidth && colChar+2 > mouseCol/cellWidth){
            model.updateCharacterLocation(9);
        }
        update();
    }

    /**
     * calling to model PlayHAHAHA function.
     */
    public void playHAHAHA() {
        model.playHAHAHA();
    }
}
