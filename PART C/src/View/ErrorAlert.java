package View;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class represents an error type alert window.
 * extends the Alert class.
 * will raise an errorAlert with the string given in the constructor.
 */
public class ErrorAlert extends Alert {

    public ErrorAlert(String exp) {
        super(AlertType.ERROR);
        this.setTitle("Error");
        this.setHeight(500);
        this.setWidth(500);
        this.setHeaderText("OOPS , its seems we run into a problem");
        this.setContentText(exp);
        this.setResizable(true);
        this.showAndWait();
    }
}
