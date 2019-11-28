

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        final Parent root = loader.load();
        primaryStage.setTitle("CTL Exercise Tool");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}