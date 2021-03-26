package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Adapted From:
        // Answer: https://stackoverflow.com/a/40299547/10827766
        // User: https://stackoverflow.com/users/2299040/sahil-manchanda
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));

        ((Controller) loader.getController()).setStage(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
