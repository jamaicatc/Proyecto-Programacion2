package co.edu.uniquindio.envio;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EnvioApplication extends javafx.application.Application{
    public static Stage mainStage;
    public static Scene sceneLogin;
    public static Scene sceneAdministrador;
    public static Scene sceneUsuario;


    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;

        FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
        sceneLogin = new Scene(login.load());

        FXMLLoader Admin = new FXMLLoader(getClass().getResource("Administrador.fxml"));
        sceneAdministrador = new Scene(Admin.load());

        FXMLLoader User = new FXMLLoader(getClass().getResource("Usuario.fxml"));
        sceneUsuario = new Scene(User.load());

        primaryStage.setScene(sceneLogin);
        primaryStage.setTitle("Incio de Sesión");
        primaryStage.show();
    }

    private static void main(String[] args) {
        launch(args);
    }
}
