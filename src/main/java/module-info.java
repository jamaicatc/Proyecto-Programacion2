module envio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens co.edu.uniquindio.envio to javafx.fxml;
    exports co.edu.uniquindio.envio;
    opens co.edu.uniquindio.envio.viewcontroller;
    exports co.edu.uniquindio.envio.viewcontroller;
}