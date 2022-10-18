package View;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * Class represents an info type alert window.
 * extends the Alert class.
 * will raise an infoAlert with the string given in the constructor.
 */
public class InfoAlert extends Alert {

    public InfoAlert(String title, String header, String message) throws FileNotFoundException {
        super(Alert.AlertType.INFORMATION);
        this.setTitle(title);
        this.setHeight(500);
        this.setWidth(500);
        this.setHeaderText(header);
        this.setContentText(message);
        this.setResizable(true);
        this.showAndWait();
    }
}
