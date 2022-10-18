import Model.IModel;
import Model.MyModel;
import Server.Configurations;
import View.IView;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load the fxml file
        Configurations.getInstance().setPath("./resources/config.properties");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        //set the window title and image
        primaryStage.setTitle("Maze2D game");
        primaryStage.getIcons().add(new Image(getClass().getResource("Images/maze.png").toURI().toString()));
        //set window`s size
        int height=1000;
        int width=800;
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        double scWidth = screenSize.getWidth();
        double scHeight = screenSize.getHeight();
        primaryStage.setWidth(scWidth-200);
        primaryStage.setHeight(scHeight+100);
        primaryStage.setScene(new Scene(root, height, width));
        primaryStage.show();

        //initialize MVVM
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        IView view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        view.setStage(primaryStage);

        //listeners for resizing the window.
        primaryStage.widthProperty().addListener((obs,oldVal,newVal)->{
            double delta=newVal.doubleValue()-oldVal.doubleValue();
            view.resizeW(delta);

        });

        primaryStage.heightProperty().addListener((obs,oldVal,newVal)->{
            double delta=newVal.doubleValue()-oldVal.doubleValue();
            view.resizeH(delta);

        });

        //handler for main window close request
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit");
                alert.setContentText("are you sure you want to close ?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        viewModel.closeServers();
                        primaryStage.close();
                    }
                    else{
                        we.consume();

                    }
                });
            }
        });
    }

    public static void main(String[] args) {

        launch(args);
    }
}
