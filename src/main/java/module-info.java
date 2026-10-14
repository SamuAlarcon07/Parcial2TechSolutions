module co.edu.uniquindio.parcial2techsolutions {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.parcial2techsolutions to javafx.fxml;
    exports co.edu.uniquindio.parcial2techsolutions;
}