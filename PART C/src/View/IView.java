package View;

import ViewModel.MyViewModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public interface IView {
    void setViewModel(MyViewModel viewModel);
    void setStage(Stage s);
    void resizeW(double size);
    void resizeH(double size);
    void newGame();
    void Zoom(ScrollEvent scrollEvent);
    void solveMaze();
    void exit();
    void keyPressed(KeyEvent keyEvent);
    void dragChar(MouseEvent mouseEvent);
    void update();
    void Winner();


}
