package MountainApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Run extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Mountain App");
        primaryStage.setScene(new Scene(root, 900, 675));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
