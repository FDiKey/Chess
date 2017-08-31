import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * Created by malyshev.ko on 25.08.2017.
 */
public class App extends Application{
    public static void main(String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage.setTitle("HW");
        Board board = new Board();
        stage.setScene(new Scene(board, 450,450));
        stage.show();
    }
}
