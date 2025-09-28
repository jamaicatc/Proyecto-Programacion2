module co.edu.uniquindio.envio {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.envio to javafx.fxml;
    exports co.edu.uniquindio.envio;
}