package View;

import Server.Configurations;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Class represent a dialog window.
 * using the factory design pattern.
 * creates a new dialog according to the string in the constructor.
 */
public class MyDialog extends Dialog {
    MyViewController mvc;

    /**
     * Class constructor.
     * activates the function according to the string type.
     * @param mc the view controller requested the dialog.
     * @param type the opeartion we wish to create dialog for.
     */
    public MyDialog(MyViewController mc, String type ) {
        mvc=mc;
        if(type.equals("New Game")){
            newGame();
        }
        else if(type.equals("Winner")){
            Winner();
            return;
        }
        else if(type.equals("choose pokemon")){
            ChoosePokemon();
        }
        else if(type.equals("Controllers")){
            Controllers();
        }
        else if(type.equals("About")){
            About();
        }
        else if(type.equals("Properties")){
            Properties();
        }
        else if(type.equals("Shhh..")){
            Shhh();
        }

    }

    /**
     * Creates a new dialog with one button.
     * the dialog holds an image.
     * the dialog will open when the user will request a solution.
     */
    private void Shhh() {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Shhh...");
        ImageView secret= new ImageView();
        try {
            secret.setImage(new Image(new FileInputStream(getClass().getResource("/Images/shhh-its-a-secret.jpg").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load shhh-its-a-secret.jpg image at directory src/Resources/Images.");
        }
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/maze.png");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }
        secret.setFitWidth(200);
        secret.setFitHeight(200);
        ButtonType ok = new ButtonType("between us", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok);
        dialog.getDialogPane().setContent(secret);
        dialog.showAndWait().ifPresent(type -> {
            if (type == ok) {
                dialog.close();
                mvc.playHAHAHA();
            }
        });
    }
    /**
     * Creates a new dialog to help the user change the properties.
     * the dialog holds 3 choice box and connect to the Configuration class.
     */
    public void Properties(){
        Label searchLabel = new Label("Maze solver Searching algorithm: ");
        Label ThreadLabel = new Label("Number of clients: ");
        Label MazeLabel = new Label("Maze generation algorithm: ");

        ChoiceBox<String> choiceBoxSerach=new ChoiceBox<>();
        choiceBoxSerach.getItems().addAll("BreadthFirstSearch","DepthFirstSearch","BestFirstSearch");
        choiceBoxSerach.setValue(Configurations.getInstance().getSearchingAlgorithm());

        ChoiceBox<String> choiceBoxThreads=new ChoiceBox<>();
        choiceBoxThreads.getItems().addAll("1","2","3","4","5","6","7","8","9","10");
        int t=Configurations.getInstance().getNumberOfThreads();
        choiceBoxThreads.setValue(String.valueOf(t));

        ChoiceBox<String> choiceBoxMaze=new ChoiceBox<>();
        choiceBoxMaze.getItems().addAll("EmptyMazeGenerator","SimpleMazeGenerator","MyMazeGenerator");
        choiceBoxMaze.setValue(Configurations.getInstance().getGeneratingAlgorithm());

        ButtonType apply=new ButtonType("Apply", ButtonBar.ButtonData.APPLY);

        // new StackPane
        GridPane secondaryLayout = new GridPane();
        secondaryLayout.addRow(0,searchLabel,choiceBoxSerach);
        secondaryLayout.addRow(1,MazeLabel,choiceBoxMaze);
        secondaryLayout.addRow(2,ThreadLabel,choiceBoxThreads);

        Dialog dialog = new Dialog<>();
        dialog.setTitle("Properties");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/maze.png");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }
        dialog.getDialogPane().getButtonTypes().addAll(apply);
        dialog.getDialogPane().setContent(secondaryLayout);
        dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == apply) {
                        Configurations.getInstance().setNumberOfThreads(choiceBoxThreads.getValue());
                        Configurations.getInstance().setGeneratingAlgorithm(choiceBoxMaze.getValue());
                        Configurations.getInstance().setSearchingAlgorithm(choiceBoxSerach.getValue());
                        mvc.log.debug("Configuration properties changed.");
                        return null;
                    }
                    return null;
                });
        Optional  result = dialog.showAndWait();

    }
    /**
     * Creates a new dialog holding label and 2 images.
     * the dialog will open when the user will click the about button.
     */
    private void About(){
        ImageView Amit = new ImageView();
        ImageView Yuval= new ImageView();
        try {
            Amit.setImage(new Image(new FileInputStream(getClass().getResource("/Images/Amit.jpeg").getFile())));
            Yuval.setImage(new Image(new FileInputStream(getClass().getResource("/Images/Yuval.jpeg").getFile())));

        } catch (FileNotFoundException e) {
            mvc.showException("unable to load Amit and Yuval images at directory src/Resources/Images.");
        }
        Amit.setFitWidth(200);
        Amit.setFitHeight(250);
        Yuval.setFitWidth(200);
        Yuval.setFitHeight(250);
        Label title = new Label("About us");
        title.setFont(new Font("TimesRoman",28));
        Label explain = new Label();
        explain.setText("""
                The game was development by Amit Partuk and Yuval Felendler.
                both ISC students in BGU.
                the Picachu maze game is a strategy thinking game when the goal is to get picachu back to his pokeball.
                you can solve different mazes from 2X2 and to 1000X1000 made by pur maze generation algorithm.""");
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Controllers");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/maze.png");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(title,1,0);
        grid.add(explain,1,1);
        grid.add(Amit,0,2);
        grid.add(Yuval,2,2);
        GridPane.setHalignment(title, HPos.CENTER);
        dialog.getDialogPane().setContent(grid);
        Optional  result = dialog.showAndWait();
    }
    /**
     * Creates a new dialog holding label and 2 images.
     * the dialog will open when the user will click the Controllers button.
     */
    private void Controllers(){
        // Create the custom dialog.
        ImageView num = new ImageView();
        ImageView mouse= new ImageView();
        try {
            num.setImage(new Image(new FileInputStream(getClass().getResource("/Images/numpad.jpg").getFile())));
            mouse.setImage(new Image(new FileInputStream(getClass().getResource("/Images/mouse.png").getFile())));

        } catch (FileNotFoundException e) {
            mvc.showException("unable to load mouse and numpad images at directory src/Resources/Images.");
        }
        num.setFitWidth(120);
        num.setFitHeight(120);
        mouse.setFitWidth(120);
        mouse.setFitHeight(120);
        Label explain = new Label();
        explain.setText("""
                Start a new game - press file -> new or press the New Game button in the main menu.
                Choose maze size by filling the row and column fields make sure you enter at least 2X2 maze.
                Choose the pokemon you trust to complete the journey.
                switch to another pokemon at any time by pressing Option -> Change Pokemon.
                Finish the maze by getting your pokemon to the his Pokeball.
                Save your current maze or load maze you started to solve before at the file section in the menu.
                
                KeyBoard Options:
                \tSound on/off - M .
                \tSolve maze on/off- S .
                \tSwitch pokemon - P .
                \tReturn to normal zoom - Z .
                \tExit the game - X .

                Move your pokemon by using the numpad keys:
                \tright - 6
                \tleft - 4
                \tup-8
                \tdown - 2
                \tdown-left cross - 1
                \tdown-right cross - 3
                \tup-left cross - 7
                \tup-right cross - 9
                
                \tYou can also use the mouse to move your pokemon.
                
                score:
                \tfor every cross move you will get 2 points and for every regular move you will get 3 points.
                \tYour objective is to finish the maze with minimum amount of points.
                \tTrack your points in the score counter in the left upper corner.
                \tSave your score at the end of the maze and see if you beat your friends at the score board.

                Get the maze solution by pressing the map icon in the right bottom corner or go to help ->solve maze.
                pressing it again will make the solution disappear.
                
                Hold CTRL and scroll with your mouse to zoom in/out.

                Turn on/off the sound press the sound icon in the left bottom corner or go to Option -> sound .
                """);
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Controllers");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/maze.png");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }
        ScrollPane scroll = new ScrollPane(explain);
        ButtonType ok = new ButtonType("Got it", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok);
        dialog.setHeight(mvc.mainPane.getHeight()-50);
        dialog.setWidth(mvc.mainPane.getWidth()-50);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(num,1,0);
        grid.add(mouse,2,0);
        grid.add(explain,0,0);
        grid.add(scroll,0,0);
        dialog.getDialogPane().setContent(grid);

        //dialog.setResizable(true);
        Optional result = dialog.showAndWait();
    }
    /**
     * Creates a new dialog .
     * the dialog will open when the user will solve the maze.
     */
    private void Winner() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Save Score?");
        Label congratulation = new Label();
        congratulation.setText("congratulations you finished the maze");
        Label score_label = new Label();
        score_label.setText("Your score is: " + mvc.score.getText());
        Label saveScore_label = new Label();
        saveScore_label.setText("Do you want to enter the table of victories ?");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/maze.png");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }
        TextField name_Text_Field = new TextField();
        name_Text_Field.setPromptText("Insert your name");
        name_Text_Field.setVisible(true);
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType menuButton = new ButtonType("No, I want a new game", ButtonBar.ButtonData.NO);
        ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, menuButton, exit);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(congratulation, 0, 0);
        grid.add(score_label, 0, 1);
        grid.add(saveScore_label, 0, 3);

        grid.add(new Label("Your Name:"), 0, 4);
        grid.add(name_Text_Field, 1, 4);


        Node ScoreNode = dialog.getDialogPane().lookupButton(okButton);
        ScoreNode.setDisable(true);

        name_Text_Field.textProperty().addListener((observable, oldValue, newValue) -> {
            ScoreNode.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return new Pair<>("2", "2");
            } else if(dialogButton==menuButton) {
                return new Pair<>("1", "1");
            }
            else{
                return new Pair<>("0", "0");
            }
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        if (result.get().getKey().equals("0")) {
            mvc.showMainMenu();
            mvc.exit();
        }
        else if(result.get().getKey().equals("1")) {
            newGame();
        }
        else{
            mvc.saveScore(name_Text_Field.getText(), Integer.parseInt(mvc.score.getText()));
            mvc.clearScene();
        }

    }
    /**
     * Creates a new dialog with one button.
     * the dialog holds an image.
     * the dialog will open when the user will request to choose pokemon.
     */
    public void ChoosePokemon(){
        // Create the custom dialog.
        ImageView pokemonImage = new ImageView();
        try {
            pokemonImage.setImage(new Image(new FileInputStream(getClass().getResource("/Images/pikachu.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/pikachu.png ");
        }
        pokemonImage.setFitWidth(50);
        pokemonImage.setFitHeight(50);
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Choose Your Pokemon");
        dialog.setHeaderText("please select your pokemon");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/maze.png");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }
        ButtonType start = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(start);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        String[] st = {"Pikachu", "Bulbasaur", "Butterfree", "Charmander", "Squirtle"};
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(st));
        choiceBox.setValue("Pikachu");
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    try {
                        pokemonImage.setImage(new Image(new FileInputStream(getClass().getResource(mvc.getURlImagePokemon(st[new_val.intValue()])).getFile())));
                    } catch (FileNotFoundException e) {
                        mvc.showException("unable to load the image at directory src/main/resources/Images " + choiceBox.getValue().toString() + ".png" );
                        mvc.log.error("unable to load the image at directory src/main/resources/Images" + choiceBox.getValue().toString() + ".png");
                    }
                });
        grid.add(choiceBox,1,2);
        grid.add(pokemonImage,2,3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            return new Pair<>("pokemon","pokemon");
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        mvc.setPokemon(choiceBox.getValue().toString());
        mvc.charDisplayer.draw();
    }
    /**
     * Creates a new dialog with couple of buttons.
     * the dialog will open when the user will press the new game button.
     */
    public void newGame()  {
        // Create the custom dialog.
        ImageView pokemonImage = new ImageView();
        try {
            pokemonImage.setImage(new Image(new FileInputStream(getClass().getResource("/Images/pikachu.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/pikachu.png ");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/pikachu.png");

        }
        pokemonImage.setFitWidth(50);
        pokemonImage.setFitHeight(50);
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New Game");
        dialog.setHeaderText("please select maze size");
        ButtonType start = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        try {
            stage.getIcons().add(
                    new Image(new FileInputStream(getClass().getResource("/Images/maze.png").getFile())));
        } catch (FileNotFoundException e) {
            mvc.showException("unable to load the image at directory src/main/resources/Images/maze.png");
            mvc.log.error("unable to load the image at directory src/main/resources/Images/maze.png");
        }

        dialog.getDialogPane().getButtonTypes().addAll(start, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        mvc.textField_mazeRows = new TextField();
        mvc.textField_mazeRows.setPromptText("Rows");
        mvc.textField_mazeColumns = new TextField();
        mvc.textField_mazeColumns.setPromptText("Columns");
        String[] st = {"Pikachu", "Bulbasaur", "Butterfree", "Charmander", "Squirtle"};
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(st));
        choiceBox.setValue("Pikachu");
        grid.add(new Label("Rows:"), 0, 0);
        grid.add(mvc.textField_mazeRows, 1, 0);
        grid.add(new Label("Columns:"), 0, 1);
        grid.add(mvc.textField_mazeColumns, 1, 1);
        grid.add(choiceBox,1,2);
        grid.add(pokemonImage,2,3);
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    try {
                        pokemonImage.setImage(new Image(new FileInputStream(getClass().getResource(mvc.getURlImagePokemon(st[new_val.intValue()])).getFile())));
                    } catch (FileNotFoundException e) {
                        mvc.showException("unable to load the image at directory src/main/resources/Images " + choiceBox.getValue().toString() + ".png" );
                        mvc.log.error("unable to load the image at directory src/main/resources/Images" + choiceBox.getValue().toString() + ".png");
                    }
                });

        Node startNode = dialog.getDialogPane().lookupButton(start);
        startNode.setDisable(true);

        mvc.textField_mazeRows.textProperty().addListener((observable, oldValue, newValue) -> {
            startNode.setDisable(newValue.trim().isEmpty());
        });
        mvc.textField_mazeColumns.textProperty().addListener((observable, oldValue, newValue) -> {
            startNode.setDisable(newValue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == start) {
                return new Pair<>(mvc.textField_mazeRows.getText(), mvc.textField_mazeColumns.getText());
            }
            else{
                return new Pair<>("0","0");
            }
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.get().getKey()!="0"){
            mvc.unShowMainMenu();
            mvc.solDisplayer.clear();
            mvc.solDisplayer.showSol=false;
            mvc.setPokemon(choiceBox.getValue().toString());
            mvc.setCharDisplayer(choiceBox.getValue().toString());
            mvc.generateMaze();
            mvc.resetScore();

        }

    }

}
