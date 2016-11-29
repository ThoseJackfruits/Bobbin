package text_engine.ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Jack on 11/17/2016.
 * Represents a user's session in the application.
 */
public class Session extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 300);
    }
}
