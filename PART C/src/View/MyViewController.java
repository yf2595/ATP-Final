package View;

import Model.Victors;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.security.PublicKey;
import java.sql.Struct;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable,IView, Observer {
    @FXML
    public StackPane stack;
    @FXML
    public Pane GamePain;
    @FXML
    public Pane mainMenuPain;
    @FXML
    public Pane sidePain;
    @FXML
    public BorderPane mainPane;
    @FXML
    public ImageView soundImage;
    @FXML
    public ImageView solveImage;
    @FXML
    public ImageView returnZoom;
    @FXML
    public MenuItem solveM;
    @FXML
    public MenuItem chooseP;
    @FXML
    public MenuItem saveM;
    @FXML
    public TextField textField_mazeRows;
    @FXML
    public TextField textField_mazeColumns;
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public GoalDisplayer goalDisplayer;
    @FXML
    public SolutionDisplayer solDisplayer;
    @FXML
    public CharacterDisplayer charDisplayer;
    @FXML
    public Label welcome1;
    @FXML
    public Label score;
    @FXML
    public Label welcome2;
    @FXML
    public Button Howto;
    @FXML
    public Button Properties;
    @FXML
    public Button newGame;


    public boolean soundOn =true;
    private boolean savePosition;

    private Maze maze = null;
    private Stage mainStage;
    private MyViewModel viewModel;
    private double mouseX;
    private double mouseY;
    private double oldScaleX;
    private double oldScaleY;
    private Point MDPosition;
    private Point solPosition;
    private Point charPosition;
    private Point goalPosition;
    public final Logger log= LogManager.getLogger(MyViewController.class);


    /**
     * initialize a new MyDialog class to show the properties window.
     * the user will activate this function when he will press the properties button.
     */
    public void myProperties(){
        new MyDialog(this,"Properties");
    }

    /**
     * an override function.
     * initialize new points.
     * set the score to 0.
     * save the initialize scales of the gamePain.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        score.setText("0");
        MDPosition=new Point();
        solPosition=new Point();
        charPosition=new Point();
        goalPosition=new Point();
        savePosition=false;
        oldScaleX=GamePain.getScaleX();
        oldScaleY=GamePain.getScaleY();
    }

    /**
     * setting the stage attribute.
     * initialize the size of the stage.
     * @param s the stage the viewController is working at.
     */
    public void setStage(Stage s){
        mainStage=s;
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        mainStage.setHeight(height*0.8);
        mainStage.setWidth(width*0.9);
        mainStage.centerOnScreen();
        fitScene();

    }

    /**
     * Adjusting all the panes in the fxml to the size of the stage.
     */
    private void fitScene() {
        mainPane.setPrefHeight(mainStage.getHeight());
        mainPane.setPrefWidth(mainStage.getWidth());
        stack.setPrefHeight(mainPane.getHeight());
        stack.setPrefWidth(mainPane.getWidth());
        mainMenuPain.setPrefHeight(stack.getHeight());
        mainMenuPain.setPrefWidth(stack.getWidth());
        GamePain.setPrefHeight(stack.getHeight());
        GamePain.setPrefWidth(stack.getWidth());
    }

    /**
     * This function activates when the user press the music icon or press the M.
     * this function will adjust the sound attributes accourding to the soundOn boolean variable.
     */
    public void sound(){
        if(soundOn){
            Image soundoff= null;
            try {
                soundoff = new Image(new FileInputStream(getClass().getResource("/Images/soundOff.png").getFile()));
            } catch (FileNotFoundException e) {
                log.error("unable to load the image in the directory src/main/resources/Images/soundOff.png ");
                showException("unable to load the image in the directory src/main/resources/Images/soundOff.png ");
            }
            soundImage.setImage(soundoff);
            soundOn =false;
        }
        else {
            Image soundOn= null;
            try {
                soundOn = new Image((new FileInputStream(getClass().getResource("/Images/soundOn.png").getFile())));
            } catch (FileNotFoundException e) {
                log.error("unable to load the image in the directory src/main/resources/Images/soundOn.png ");
                showException("unable to load the image in the directory src/main/resources/Images/soundOn.png ");

            }
            soundImage.setImage(soundOn);
            this.soundOn =true;
        }
        viewModel.soundProperties();

    }

    /**
     * set the viewModel attribute.
     * add this class to be an observer of the viewModel.
     * @param viewModel
     */
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    /**
     * This function will save the current maze by calling the ViewModel save function.
     * if there is no maze to save or there was a problem in the saving - an alert will be thrown to the screen.
     */
    public void save(){
        if (maze==null){
            showAlert("warning",null,"please generate a new maze or load saved maze.");
            return;
        }
        try {
            viewModel.save();
        } catch (IOException e) {
            showException("unable to save the game please check your path");
        }
    }


    /**
     * This function will load a saved maze by calling the ViewModel load function.
     * if there was a problem in the loading - an alert will be thrown to the screen.
     */
    public void load() {
        try {
            viewModel.load();
        } catch (Exception e) {
            showException("unable to load the game please try again or check your path");
        }
    }

    /**
     * This function will be called after loading a maze in order to update all the displays and restart the game.
     */
    public void mazeLoaded(){
        clearScene();
        resetScore();
        update();
        solDisplayer.clear();
        setAllDisplayers(viewModel.getMaze());
        unShowMainMenu();
    }
    /**
     * This function will set all the displays maze attributes into a new maze and will activate draw.
     */
    public void setAllDisplayers(Maze maze){
        try {
            this.maze = maze;
            solDisplayer.setSolution(viewModel.getSolution());
            solDisplayer.showSol=false;
            charDisplayer.setMaze(maze);
            charDisplayer.draw();
            goalDisplayer.setMaze(maze);
            goalDisplayer.draw();
            solDisplayer.setMaze(maze);
            mazeDisplayer.drawMaze(maze);

        }
        catch (Exception e){
            showException("unable to show the solution please try again.");
        }
    }

    /**
     * initialize a new MyDialog class to show the controllers window.
     * the user will activate this function when he will press the controllers button.
     */
    public void openControllers(){
        new MyDialog(this,"Controllers");
        log.info("Controllers window opened");
    }
    /**
     * initialize a new MyDialog class to show the about window.
     * the user will activate this function when he will press the about button.
     */
    public void openAboutGame(){
        new MyDialog(this,"About");
        log.info("About window opened");

    }

    /**
     * this function will call the viewModel reset score method.
     */
    public void resetScore(){
        viewModel.resetScore();
    }

    /**
     * this function will activate when the user request to start a new game.
     * the function will set all the key points positions.
     * return to regular zoom.
     * open a new dialog which will ask the user about the properties of his new maze.
     */
    public void newGame()  {
        if(!savePosition){
            MDPosition.setLocation(mazeDisplayer.getLayoutX(),mazeDisplayer.getLayoutY());
            solPosition.setLocation(solDisplayer.getLayoutX(),solDisplayer.getLayoutY());
            charPosition.setLocation(charDisplayer.getLayoutX(),charDisplayer.getLayoutY());
            goalPosition.setLocation(goalDisplayer.getLayoutX(),goalDisplayer.getLayoutY());
        }
        savePosition=true;
        returnRegularZoom();
        new MyDialog(this,"New Game");
        log.info("New game started");
    }

    /**
     * this function will return the all the displayes in the gamePain to normal zoom.
     */
    public void returnRegularZoom() {
        Scale newScale = new Scale();
        newScale.setX(oldScaleX);
        newScale.setY(oldScaleY);
        newScale.setPivotX(oldScaleX);
        newScale.setPivotY(oldScaleY);
        GamePain.getTransforms().clear();
        GamePain.getTransforms().add(newScale);
    }

    /**
     * This function will zoom into or out of the gamePain according to the user scroll event.
     * @param scrollEvent will use a delta to know how much the user wants to scroll in or out.
     */
    public void Zoom(ScrollEvent scrollEvent) {
        mainStage.addEventHandler(scrollEvent.SCROLL,event ->{
            if(event.isControlDown()){
                double delta=event.getDeltaY()/100000;
                Scale newScale = new Scale();
                newScale.setX(GamePain.getScaleX() + delta);
                newScale.setY(GamePain.getScaleY() + delta);
                newScale.setPivotX(event.getX());
                newScale.setPivotY(event.getY());
                GamePain.getTransforms().add(newScale);
            }
        });

    }

    /**
     * This function will be called from the main when the primery stage will be resized.
     * its will adjust all the displays to the new Width.
     * @param size
     */
    public void resizeW(double size){
        mazeDisplayer.setWidth(mazeDisplayer.getWidth()+size);
        charDisplayer.setWidth(charDisplayer.getWidth()+size);
        goalDisplayer.setWidth(goalDisplayer.getWidth()+size);
        solDisplayer.setWidth(solDisplayer.getWidth()+size);


        if(maze != null){
            mazeDisplayer.draw();
            charDisplayer.draw();
            goalDisplayer.draw();
            solDisplayer.draw();
        }
    }
    /**
     * This function will be called from the main when the primery stage will be resized.
     * its will adjust all the displays to the new Height.
     * @param size
     */
    public void resizeH(double size){
        mazeDisplayer.setHeight(mazeDisplayer.getHeight()+size);
        charDisplayer.setHeight(charDisplayer.getHeight()+size);
        goalDisplayer.setHeight(goalDisplayer.getHeight()+size);
        solDisplayer.setHeight(solDisplayer.getHeight()+size);

        if(maze != null){
            mazeDisplayer.draw();
            charDisplayer.draw();
            goalDisplayer.draw();
            solDisplayer.draw();
        }
    }

    /**
     * This function will show the main menu pane and will disable the visibility of the game pain and all the other game components.
     */
    public void showMainMenu(){
        resetScore();
        stack.getChildren().clear();
        stack.getChildren().add(mainMenuPain);
        mainMenuPain.setVisible(true);
        solveM.setVisible(false);
        chooseP.setVisible(false);
        saveM.setVisible(false);
        solveImage.setVisible(false);
        returnZoom.setVisible(false);
        score.setVisible(false);
    }
    /**
     * This function will show the game pain and all the other game components.
     * and will disable the visibility of the mainMenu pain.
     */
    public void unShowMainMenu(){
        mainMenuPain.setVisible(false);
        stack.getChildren().clear();
        stack.getChildren().add(GamePain);
        GamePain.setVisible(true);
        solveImage.setVisible(true);
        returnZoom.setVisible(true);
        score.setVisible(true);
        solveM.setVisible(true);
        chooseP.setVisible(true);
        saveM.setVisible(true);
    }

    /**
     * This function will generate a new maze according to the new game dialog text fields.
     * if the properites of the rows and columns are illegal the function will show an alert to the screen and return to main menu.
     */
    public void generateMaze() {
        try {
            int rows = Integer.parseInt(textField_mazeRows.getText());
            int cols = Integer.parseInt(textField_mazeColumns.getText());
            if(cols <2 || rows <2){
                throw new Exception();
            }
            viewModel.generateMaze(rows, cols);
        }
        catch (Exception e){
            showException("please insert valid row and column\nfor more information please visit the help button in the menu");
            showMainMenu();
            newGame();
        }
    }

    /**
     * this is an helper function.
     * if the maze was generated well. all the displays will be drawn with the new maze.
     * also the music will start.
     */
    public void mazeGenerated(){
        maze = viewModel.getMaze();
        charDisplayer.setMaze(maze);
        charDisplayer.draw();
        goalDisplayer.setMaze(maze);
        goalDisplayer.draw();
        solDisplayer.setMaze(maze);
        mazeDisplayer.drawMaze(maze);
        viewModel.MusicPlay();
    }

    /**
     * This function will open a new dialog to help the user choose a new pokemon from the list.
     */
    public void choosePokemon(){
        if(maze==null){
            showAlert("warning",null,"please generate maze first");
        }
        else{
            new MyDialog(this,"choose pokemon");
        }
    }

    /**
     * This function will solve the maze if asked by the user and will send it to the viewModel.
     * if there is a problem to create a solution or there is no maze an alert will pop.
     */
    public void solveMaze() {
        if (maze == null) {
            showAlert("warning",null,"please generate maze first");
        }
        else {
            try {
                if(solDisplayer.showSol==false){
                    new MyDialog(this,"Shhh..");
                }
                viewModel.solveMaze();
            }
            catch (Exception e){
                showException("unable to load the solution please try again.");
            }
        }
    }

    public void mazeSolved(){
        solDisplayer.setSolution(viewModel.getSolution());
        solDisplayer.changeSol();
        mazeDisplayer.draw();
        charDisplayer.draw();
        goalDisplayer.draw();
        solDisplayer.draw();
    }

    /**
     * this function will create a new ErrorAlert object and pop it to the window with the message sent in the param.
     * @param exp the messsage to be shown in the alert.
     */
    public void showException(String exp){
        new ErrorAlert(exp);
    }

    /**
     * this function will create a new InfoAlert object and pop it to the window with the message sent in the param.
     * @param title the title of the alert window.
     * @param header the header of the alert window.
     * @param message the message to be shown in the alert window.
     */
    public void showAlert(String title,String header,String message) {
        try {
            new InfoAlert(title,header,message);
        } catch (FileNotFoundException e) {
            showException("unable to load the image at directory src/main/resources/Images/maze.png");
            log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }
    }

    /**
     * This function will clear all the displays.
     */
    public void clearScene(){
        mazeDisplayer.clear();
        charDisplayer.clear();
        solDisplayer.clear();
        goalDisplayer.clear();
    }

    /**
     * This function will show to the user a dialog window which represents the victory table of this maze.
     * @param v the victory table.
     */
    private void showVictorTable(Victors v) {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Victors Table");
        ImageView cup= new ImageView();
        try {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
            cup.setImage(new Image(new FileInputStream(getClass().getResource("/Images/cup.png").getFile())));
        } catch (FileNotFoundException e) {
            showException("unable to load Cup image at directory src/Resources/Images.");
        }
        cup.setFitWidth(120);
        cup.setFitHeight(120);
        Label victorsTable = new Label("Victors Table");
        victorsTable.setFont(new Font("Serif",28));
        Label vTable = new Label(v.toString());
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(victorsTable,1,0);
        grid.add(vTable,0,1);
        grid.add(cup,2,1);
        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait().ifPresent(type -> {
            if (type == ok) {
                clearScene();
                showMainMenu();
            }
        });
        clearScene();
        showMainMenu();
    }


    /**
     * this function will raise a new alert window to confirm that the player want to exit the game and close the program.
     * by pressing ok the function will call viewModel close servers and then exit the GUI.
     */
    public void exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setContentText("are you sure you want to close ?");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                viewModel.closeServers();
            }
        });

    }

    /**
     * this function will get the mouse x and y coordinates.
     * @param mouseEvent
     */
    public void mousePressed(MouseEvent mouseEvent){
        mouseX=mouseEvent.getX();
        mouseY=mouseEvent.getY();
    }

    /**
     * This function will get the key event happen by the user and will activate the right function if needed.
     * by pressing certien keys the function will call the viewModel moveCharacter method to try move the character.
     * @param keyEvent
     */
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.X){
            exit();
            return;
        }
        if(keyEvent.getCode()==KeyCode.Z){
            returnRegularZoom();
            return;
        }
        if(keyEvent.getCode()==KeyCode.S){
            solveMaze();
            return;
        }
        if(keyEvent.getCode()==KeyCode.M){
            sound();
            return;
        }
        if(keyEvent.getCode()==KeyCode.P){
            choosePokemon();
            return;
        }
        else{
            viewModel.moveCharacter(keyEvent);
        }
        keyEvent.consume();
    }

    /**
     * This function will help drag the charDispalyer and move the call the viewModel moveCharacterWithMouse to move the character.
     * @param mouseEvent
     */
    public void dragChar(MouseEvent mouseEvent) {
        charDisplayer.setMouseTransparent(true);
        viewModel.moveCharacterWithMouse(mouseEvent,mazeDisplayer);
        mouseEvent.consume();
    }

    /**
     * An override function to operate this class according to the observers change.
     * @param o
     * @param arg An string that describe the operation that the update happen for.
     */
    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "finish":
                viewModel.update();
                update();
                viewModel.finished();
                Winner();
                break;

            case "char moved":
                viewModel.update();
                update();
                break;

            case "generate maze":
                mazeGenerated();
                break;

            case "score saved":
                showVictorTable(viewModel.getVictors());
                break;

            case "maze loaded":
                mazeLoaded();
                break;

            case "servers closed":
                mainStage.close();
                break;

            case "maze solved":
                mazeSolved();

            case "score changed":
                score.setText(viewModel.getscore().toString());
        }
    }

    /**
     * Sets the focus on the mazeDisplayer after the mouse clicked.
     * @param mouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    /**
     * Function that update the class attributes using the viewModel methods.
     */
    public void update() {
        int rowFromViewModel = viewModel.getRowChar();
        int colFromViewModel = viewModel.getColChar();
        charDisplayer.set_player_position(rowFromViewModel, colFromViewModel);
        mazeDisplayer.setPlayerPosition(rowFromViewModel,colFromViewModel);
    }

    /**
     * This function is activated when the user solved the maze.
     */
    public void Winner() {
        returnRegularZoom();
        new MyDialog(this,"Winner");

    }

    /**
     * A function that save the player name and score using the viewModel methos.
     * @param name players name.
     * @param scoreSave players score.
     */
    public void saveScore(String name, Integer scoreSave) {
        try {
            viewModel.saveScore(name,scoreSave);
        } catch (Exception e) {
            log.error("unable to save score for "+name);
            showException("we are sorry , its seems there was a problem at saving your score");
            showMainMenu();
        }
    }

    /**
     * A function that will get the Pokemon name and return a path to the pokemon image.
     * @param pokemonName the pokemon name.
     * @return the path to the pokemon image.
     */
    public String getURlImagePokemon(String pokemonName){
        if (pokemonName.equals("Pikachu")) {
            return "/Images/pikachu.png";
        }
        if (pokemonName.equals("Bulbasaur")){
            return "/Images/bulbasaur.png";
        }
        if (pokemonName.equals("Butterfree")){
            return "/Images/Butterfree.png";
        }
        if (pokemonName.equals("Charmander")){
            return "/Images/charmander.png";
        }
        else{
            return "/Images/Squirtle.png";
        }
    }

    /**
     * A function that will set the charDisplayer image according to the pokemon name.
     * @param pokemon string , the pokemon name.
     */
    public void setCharDisplayer(String pokemon)  {
        charDisplayer.setName(pokemon);
        String URL = getURlImagePokemon(pokemon);
        charDisplayer.setImageFileNamePlayer(URL);
        //mazeDisplayer.setImageFileNamePlayer(URL);
        log.info("pokemon changed to "+pokemon);
    }

    /**
     * A function that will update the charDisplayer image and call draw.
     * @param pokemon string, the pokemon name.
     */
    public void setPokemon(String pokemon)
    {
        if (!charDisplayer.getName().equals(pokemon)) {
            setCharDisplayer(pokemon);
            mazeDisplayer.draw();
        }
    }

    /**
     * A function that activates the show solution sound.
     */
    public void playHAHAHA() {
        viewModel.playHAHAHA();
    }
}






