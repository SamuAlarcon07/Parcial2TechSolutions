module co.edu.uniquindio.parcial2techsolutions {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.parcial2techsolutions to javafx.fxml;
    opens co.edu.uniquindio.parcial2techsolutions.viewController to javafx.fxml;
    opens co.edu.uniquindio.parcial2techsolutions.model to javafx.base, javafx.fxml;
    exports co.edu.uniquindio.parcial2techsolutions;
}