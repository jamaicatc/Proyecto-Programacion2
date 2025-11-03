package co.edu.uniquindio.envio;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EnvioApplication extends javafx.application.Application{
    public static Stage mainStage;
    public static Scene sceneLogin;
    public static Scene sceneAdministrador;
    public static Scene sceneUsuario;
    public static Scene sceneRepartidor;
    public static Scene sceneUsuarioGestionPerfil;
    public static Scene sceneUsuarioGestionarMetodosPago;


    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;

        FXMLLoader login = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/login/Login.fxml"));
        sceneLogin = new Scene(login.load());

        FXMLLoader Admin = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/administrador/Administrador.fxml"));
        sceneAdministrador = new Scene(Admin.load());

        FXMLLoader User = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/usuario/Usuario.fxml"));
        sceneUsuario = new Scene(User.load());

        FXMLLoader Repartidor = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/repartidor/Repartidor.fxml"));
        sceneRepartidor = new Scene(Repartidor.load());

        FXMLLoader UserProfile = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/usuario/UsuarioGestionPerfil.fxml"));
        sceneUsuarioGestionPerfil = new Scene(UserProfile.load());

        FXMLLoader UserMetodosPago = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/usuario/UsuarioGestionarMetodosPago.fxml"));
        sceneUsuarioGestionarMetodosPago = new Scene(UserMetodosPago.load());

        primaryStage.setScene(sceneLogin);
        primaryStage.setTitle("Incio de Sesión");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
