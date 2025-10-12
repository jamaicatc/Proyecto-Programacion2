module co.edu.uniquindio.envio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens co.edu.uniquindio.envio to javafx.fxml;
    exports co.edu.uniquindio.envio;
    opens co.edu.uniquindio.envio.viewcontroller;
    exports co.edu.uniquindio.envio.viewcontroller;
}