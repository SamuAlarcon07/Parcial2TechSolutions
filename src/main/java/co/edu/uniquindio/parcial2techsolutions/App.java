package co.edu.uniquindio.parcial2techsolutions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Cargar la vista principal.
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("main-view.fxml")
        );

        Parent root = loader.load();

        // Crear la escena.
        Scene scene = new Scene(root);

        // Cargar la hoja de estilos CSS.
        URL hojaEstilos = App.class.getResource("styles.css");

        if (hojaEstilos != null) {
            scene.getStylesheets().add(
                    hojaEstilos.toExternalForm()
            );
        }

        // Configurar el título de la ventana.
        stage.setTitle("TechSolutions - Gestión de servicios");

        // Cargar el logo como icono de la ventana.
        URL recursoLogo = App.class.getResource(
                "/co/edu/uniquindio/parcial2techsolutions/images/logo.png"
        );

        if (recursoLogo != null) {
            Image icono = new Image(recursoLogo.toExternalForm());

            if (!icono.isError()) {
                stage.getIcons().add(icono);
            }
        } else {
            System.err.println(
                    "No se encontró el logo de TechSolutions."
            );
        }

        // Configurar el tamaño mínimo.
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        // Asignar la escena a la ventana.
        stage.setScene(scene);

        // Abrir la ventana maximizada.
        stage.setMaximized(true);

        // Mostrar la aplicación.
        stage.show();
    }

    /**
     * Configura el icono de TechSolutions en cualquier ventana.
     *
     * @param ventana ventana a la que se le asignará el icono.
     */
    public static void configurarIcono(Stage ventana) {
        URL recursoLogo = App.class.getResource(
                "/co/edu/uniquindio/parcial2techsolutions/images/logo.png"
        );

        if (recursoLogo != null) {
            Image icono = new Image(recursoLogo.toExternalForm());

            if (!icono.isError()) {
                ventana.getIcons().add(icono);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
