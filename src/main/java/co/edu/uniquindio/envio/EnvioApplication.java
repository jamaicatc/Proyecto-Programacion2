package co.edu.uniquindio.envio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EnvioApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EnvioApplication.class.getResource("EnvioApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Administrador");
        stage.setScene(scene);
        stage.show();
    }
}
