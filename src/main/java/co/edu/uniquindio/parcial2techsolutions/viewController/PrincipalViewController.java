package co.edu.uniquindio.parcial2techsolutions.viewController;

import co.edu.uniquindio.parcial2techsolutions.model.CatalogoServicios;
import co.edu.uniquindio.parcial2techsolutions.model.ElementoTecnologico;
import co.edu.uniquindio.parcial2techsolutions.model.PaqueteTecnologico;
import co.edu.uniquindio.parcial2techsolutions.model.TechSolutions;
import co.edu.uniquindio.parcial2techsolutions.controller.GestionPaquetesService;
import co.edu.uniquindio.parcial2techsolutions.controller.GestionSolicitudesService;
import co.edu.uniquindio.parcial2techsolutions.controller.CalculadoraCostosService;
import co.edu.uniquindio.parcial2techsolutions.controller.ConsultaTecnicoService;
import co.edu.uniquindio.parcial2techsolutions.adapter.LegacySupportAdapter;
import co.edu.uniquindio.parcial2techsolutions.model.LegacySupportSimulado;
import co.edu.uniquindio.parcial2techsolutions.model.Cliente;
import co.edu.uniquindio.parcial2techsolutions.model.Prioridad;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicioBuilder;
import co.edu.uniquindio.parcial2techsolutions.model.EstadoSolicitud;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.animation.Interpolator;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.animation.Animation;
import javafx.animation.KeyValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 * Controlador principal de TechSolutions.
 * Gestiona el catálogo, la búsqueda, el registro de servicios,
 * los detalles y la administración de paquetes.
 */
public class PrincipalViewController {

    private static final String MORADO = "#7028e4";
    private static final String MORADO_OSCURO = "#32166f";
    private static final String TEXTO_SECUNDARIO = "#625779";

    private static final double ANCHO_MINIMO_TARJETA = 260;
    private static final double ESPACIO_TARJETAS = 16;
    private static final double PADDING_TARJETA = 24;
    private static final double ALTO_TARJETA = 260;
    private static final double ALTO_MINIMO_TARJETA_PAQUETE = 330;

    private final TechSolutions empresa =
            new TechSolutions("TechSolutions");

    private final CatalogoServicios catalogoServicios =
            empresa.getCatalogoServicios();

    private final GestionPaquetesService gestionPaquetes =
            new GestionPaquetesService();

    private final GestionSolicitudesService gestionSolicitudes =
            new GestionSolicitudesService();

    private final CalculadoraCostosService calculadoraCostos =
            new CalculadoraCostosService();

    /* Respuesta simulada del sistema externo mientras no exista conexión real. */
    private final ConsultaTecnicoService consultaTecnico =
            new ConsultaTecnicoService(
                    new LegacySupportAdapter(
                            new LegacySupportSimulado("TEC-105|DISPONIBLE|30000")
                    )
            );

    private final ObservableList<SolicitudServicio> solicitudes =
            FXCollections.observableArrayList();

    @FXML private TextField campoBusquedaSolicitudes;
    @FXML private TableView<SolicitudServicio> tablaSolicitudes;
    @FXML private TableColumn<SolicitudServicio, String> colCodigo;
    @FXML private TableColumn<SolicitudServicio, Cliente> colCliente;
    @FXML private TableColumn<SolicitudServicio, LocalDate> colFecha;
    @FXML private TableColumn<SolicitudServicio, Prioridad> colPrioridad;
    @FXML private TableColumn<SolicitudServicio, EstadoSolicitud> colEstado;
    @FXML private TableColumn<SolicitudServicio, BigDecimal> colCosto;
    @FXML private Label textoTotalSolicitudes;
    @FXML private Label textoPendientes;
    @FXML private Label textoConfirmadas;
    @FXML private Label textoCanceladas;
    @FXML private Label textoServiciosDisponibles;
    @FXML private Label textoPaquetesCreados;
    @FXML private Label textoSolicitudesRegistradas;

    @FXML
    private TabPane tabPrincipal;

    @FXML
    private Tab tabServicios;

    @FXML
    private Tab tabPaquetes;

    @FXML
    private Tab tabSolicitudes;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private FlowPane contenedorServicios;

    @FXML
    private FlowPane contenedorPaquetes;

    @FXML
    private Label horaLabel;

    @FXML
    private Label fechaLabel;

    private Timeline reloj;

    @FXML
    private ImageView logoPrincipal;

    /**
     * Vuelve a la pestaña Inicio y anima el logo.
     */
    @FXML
    private void volverAInicio(MouseEvent event) {

        // Volver a la primera pestaña: Inicio.
        if (tabPrincipal != null) {
            tabPrincipal.getSelectionModel().select(0);
        }

        // Animación pop del logo.
        if (logoPrincipal != null) {
            ScaleTransition encoger = new ScaleTransition(
                    Duration.millis(90),
                    logoPrincipal
            );
            encoger.setToX(0.82);
            encoger.setToY(0.82);

            ScaleTransition crecer = new ScaleTransition(
                    Duration.millis(180),
                    logoPrincipal
            );
            crecer.setToX(1.12);
            crecer.setToY(1.12);

            ScaleTransition normal = new ScaleTransition(
                    Duration.millis(130),
                    logoPrincipal
            );
            normal.setToX(1.0);
            normal.setToY(1.0);

            SequentialTransition pop = new SequentialTransition(
                    encoger,
                    crecer,
                    normal
            );

            pop.play();
        }
    }

    @FXML
    private void animarEntradaLogo() {
        if (logoPrincipal != null) {
            logoPrincipal.setScaleX(1.08);
            logoPrincipal.setScaleY(1.08);
        }
    }

    @FXML
    private void animarSalidaLogo() {
        if (logoPrincipal != null) {
            logoPrincipal.setScaleX(1.0);
            logoPrincipal.setScaleY(1.0);
        }
    }


    /**
     * Obtiene el icono SVG que se encuentra al inicio del gráfico
     * de una pestaña.
     */
    private SVGPath obtenerIcono(MouseEvent event) {
        if (!(event.getSource() instanceof HBox contenedor)) {
            return null;
        }

        for (Node nodo : contenedor.getChildren()) {
            if (nodo instanceof SVGPath icono) {
                return icono;
            }
        }

        return null;
    }

    /**
     * Ilumina y amplía ligeramente el icono al pasar el cursor.
     */
    @FXML
    private void animarHoverEntrada(MouseEvent event) {
        SVGPath icono = obtenerIcono(event);

        if (icono == null) {
            return;
        }

        ScaleTransition animacion =
                new ScaleTransition(Duration.millis(140), icono);

        animacion.setToX(1.12);
        animacion.setToY(1.12);
        animacion.setInterpolator(Interpolator.EASE_OUT);
        animacion.play();
    }

    /**
     * Devuelve el icono a su tamaño original al retirar el cursor.
     */
    @FXML
    private void animarHoverSalida(MouseEvent event) {
        SVGPath icono = obtenerIcono(event);

        if (icono == null) {
            return;
        }

        ScaleTransition animacion =
                new ScaleTransition(Duration.millis(140), icono);

        animacion.setToX(1.0);
        animacion.setToY(1.0);
        animacion.setInterpolator(Interpolator.EASE_BOTH);
        animacion.play();
    }

    /**
     * Ejecuta el efecto pop en el icono de una pestaña seleccionada.
     */
    private void animarPop(Tab pestaña) {
        if (pestaña == null || !(pestaña.getGraphic() instanceof HBox contenedor)) {
            return;
        }

        SVGPath icono = null;

        for (Node nodo : contenedor.getChildren()) {
            if (nodo instanceof SVGPath svg) {
                icono = svg;
                break;
            }
        }

        if (icono == null) {
            return;
        }

        ScaleTransition encoger =
                new ScaleTransition(Duration.millis(80), icono);
        encoger.setToX(0.82);
        encoger.setToY(0.82);

        ScaleTransition crecer =
                new ScaleTransition(Duration.millis(150), icono);
        crecer.setToX(1.16);
        crecer.setToY(1.16);
        crecer.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition normal =
                new ScaleTransition(Duration.millis(120), icono);
        normal.setToX(1.0);
        normal.setToY(1.0);
        normal.setInterpolator(Interpolator.EASE_BOTH);

        SequentialTransition pop =
                new SequentialTransition(encoger, crecer, normal);

        pop.play();
    }

    @FXML
    private StackPane contenedorBanner;

    private Timeline animacionBanner;

    /**
     * Inicia el desplazamiento continuo del degradado del banner de bienvenida.
     */
    private void iniciarAnimacionBanner() {
        if (contenedorBanner == null) {
            return;
        }

        javafx.beans.property.DoubleProperty fase =
                new javafx.beans.property.SimpleDoubleProperty(0.0);

        fase.addListener((observable, anterior, actual) ->
                contenedorBanner.setBackground(
                        crearDegradadoBanner(actual.doubleValue())
                )
        );

        contenedorBanner.setBackground(crearDegradadoBanner(0.0));

        animacionBanner = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(fase, 0.0)
                ),
                new KeyFrame(
                        Duration.seconds(8),
                        new KeyValue(fase, 1.0, Interpolator.LINEAR)
                )
        );

        animacionBanner.setCycleCount(Animation.INDEFINITE);
        animacionBanner.setAutoReverse(true);
        animacionBanner.play();
    }

    /**
     * Crea un degradado ancho para que el cambio de posición se vea como
     * un movimiento horizontal de los tonos morados.
     */
    private Background crearDegradadoBanner(double fase) {
        double inicioX = -1.0 + (2.0 * fase);

        LinearGradient degradado = new LinearGradient(
                inicioX, 0.0,
                inicioX + 1.8, 1.0,
                true,
                CycleMethod.REPEAT,
                new Stop(0.0, Color.web("#32116F")),
                new Stop(0.35, Color.web("#7028E4")),
                new Stop(0.65, Color.web("#8D42F5")),
                new Stop(1.0, Color.web("#A45BFF"))
        );

        return new Background(
                new BackgroundFill(
                        degradado,
                        new CornerRadii(20),
                        Insets.EMPTY
                )
        );
    }

    @FXML
    private void initialize() {

        iniciarAnimacionBanner();
        iniciarReloj();

        campoBusqueda.textProperty().addListener(
                (observable, anterior, nuevo) ->
                        filtrarServicios(nuevo)
        );

        contenedorServicios.widthProperty().addListener(
                (observable, anterior, nuevo) ->
                        ajustarAnchoTarjetas(nuevo.doubleValue())
        );

        contenedorServicios.sceneProperty().addListener(
                (observable, escenaAnterior, escenaNueva) -> {
                    if (escenaNueva != null) {
                        ajustarAnchoTarjetas(
                                contenedorServicios.getWidth()
                        );
                    }
                }
        );

        registrarPlantillasPaquetes();
        mostrarPlantillasPaquetes();
        configurarTablaSolicitudes();
        if (campoBusquedaSolicitudes != null) {
            campoBusquedaSolicitudes.textProperty().addListener(
                    (obs, anterior, nuevo) -> filtrarSolicitudes(nuevo));
        }
        actualizarResumenSolicitudes();
        actualizarResumenInicio();

        contenedorServicios.applyCss();
        ajustarAnchoTarjetas(contenedorServicios.getWidth());

        if (logoPrincipal != null) {
            logoPrincipal.setCursor(Cursor.HAND);
        }

        tabPrincipal.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionada) -> {
                    if (seleccionada != null) {
                        animarPop(seleccionada);
                    }
                });
    }

    /**
     * Inicia el reloj del encabezado y actualiza la hora cada segundo.
     */
    private void iniciarReloj() {
        actualizarReloj();

        reloj = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        evento -> actualizarReloj()
                )
        );
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }

    /**
     * Actualiza la hora y la fecha del encabezado.
     */
    private void actualizarReloj() {
        LocalDateTime ahora = LocalDateTime.now();

        if (horaLabel != null) {
            horaLabel.setText(
                    ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
        }

        if (fechaLabel != null) {
            String fecha = ahora.format(
                    DateTimeFormatter.ofPattern(
                            "EEEE, d 'de' MMMM 'de' yyyy",
                            new Locale("es", "CO")
                    )
            );

            fechaLabel.setText(
                    fecha.substring(0, 1).toUpperCase(new Locale("es", "CO"))
                            + fecha.substring(1)
            );
        }
    }

    /**
     * Ajusta el ancho de las tarjetas según el espacio disponible.
     * Conserva la altura de las tarjetas originales del FXML.
     */
    private void ajustarAnchoTarjetas(double anchoDisponible) {
        if (anchoDisponible <= 0
                || contenedorServicios.getChildren().isEmpty()) {
            return;
        }

        double anchoUtil = anchoDisponible
                - contenedorServicios.getInsets().getLeft()
                - contenedorServicios.getInsets().getRight();

        if (anchoUtil <= 0) {
            return;
        }

        int columnas = Math.max(
                1,
                (int) Math.floor(
                        (anchoUtil + ESPACIO_TARJETAS)
                                / (ANCHO_MINIMO_TARJETA
                                + ESPACIO_TARJETAS)
                )
        );

        double anchoTarjeta = (
                anchoUtil - (columnas - 1) * ESPACIO_TARJETAS
        ) / columnas;

        for (Node nodo : contenedorServicios.getChildren()) {
            if (!(nodo instanceof VBox tarjeta)) {
                continue;
            }

            tarjeta.setMinWidth(300);
            tarjeta.setPrefWidth(anchoTarjeta);
            tarjeta.setMaxWidth(anchoTarjeta);

            boolean tieneImagen = tarjeta.getChildren().stream()
                    .anyMatch(elemento -> elemento instanceof StackPane);

            // Las tarjetas del FXML y las creadas desde Java deben medir lo mismo.
            // Las originales tienen 300 px de alto; se aplica la misma medida
            // a las tarjetas dinámicas.
            if (tarjeta.getStyleClass().contains("service-card")) {
                tarjeta.setMinHeight(300);
                tarjeta.setPrefHeight(300);
                tarjeta.setMaxHeight(300);
            }

            double anchoImagen = Math.max(
                    180,
                    anchoTarjeta - PADDING_TARJETA
            );

            for (Node elemento : tarjeta.getChildren()) {
                if (elemento instanceof StackPane contenedorImagen) {
                    contenedorImagen.setPrefWidth(anchoImagen);
                    contenedorImagen.setMinWidth(anchoImagen);
                    contenedorImagen.setMaxWidth(anchoImagen);

                    for (Node hijo : contenedorImagen.getChildren()) {
                        if (hijo instanceof ImageView imagen) {
                            imagen.setFitWidth(anchoImagen);

                            Node clip = imagen.getClip();

                            if (clip instanceof Rectangle rectangulo) {
                                rectangulo.setWidth(anchoImagen);
                            }
                        }
                    }
                }
            }
        }

        contenedorServicios.requestLayout();
    }

    /**
     * Filtra los servicios por nombre, código o descripción.
     */
    private void filtrarServicios(String texto) {
        String busqueda = texto == null
                ? ""
                : texto.trim().toLowerCase();

        for (Node nodo : contenedorServicios.getChildren()) {
            if (nodo instanceof VBox tarjeta) {
                StringBuilder contenido = new StringBuilder();

                for (Node elemento : tarjeta.getChildren()) {
                    if (elemento instanceof Label etiqueta) {
                        contenido.append(etiqueta.getText()).append(" ");
                    }
                }

                boolean coincide = contenido.toString()
                        .toLowerCase()
                        .contains(busqueda);

                tarjeta.setVisible(coincide);
                tarjeta.setManaged(coincide);
            }
        }

        contenedorServicios.requestLayout();
    }

    /**
     * Abre el formulario para registrar un servicio.
     */
    @FXML
    private void nuevoServicio() {
        Dialog<ButtonType> dialogo = new Dialog<>();

        dialogo.setTitle("TechSolutions | Nuevo servicio");
        dialogo.setHeaderText(null);

        ButtonType cancelar = new ButtonType(
                "Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE
        );

        ButtonType guardar = new ButtonType(
                "Guardar servicio", ButtonBar.ButtonData.OK_DONE
        );

        dialogo.getDialogPane().getButtonTypes()
                .addAll(cancelar, guardar);

        Label titulo = new Label("Registrar nuevo servicio");
        titulo.setStyle(
                "-fx-font-size: 22px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO_OSCURO + ";"
        );

        Label subtitulo = new Label(
                "Ingresa los datos del servicio tecnológico."
        );
        subtitulo.setWrapText(true);
        subtitulo.setStyle(
                "-fx-font-size: 12px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
        );

        TextField nombre = new TextField();
        nombre.setPromptText("Ej. Instalación de cámaras");
        estilizarCampo(nombre);

        TextField codigo = new TextField(generarCodigoServicio());
        codigo.setEditable(false);
        codigo.setFocusTraversable(false);
        codigo.setStyle(
                "-fx-background-color: #f0e7ff;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 8;"
                        + "-fx-border-radius: 8;"
                        + "-fx-border-color: #e0d1ff;"
                        + "-fx-padding: 10;"
        );

        TextArea descripcion = new TextArea();
        descripcion.setPromptText(
                "Describe las características del servicio..."
        );
        descripcion.setWrapText(true);
        descripcion.setPrefRowCount(4);
        descripcion.setPrefWidth(285);
        descripcion.setStyle(
                "-fx-control-inner-background: white;"
                        + "-fx-background-radius: 8;"
                        + "-fx-border-radius: 8;"
                        + "-fx-border-color: #ded5ef;"
                        + "-fx-font-size: 13px;"
        );

        TextField precio = new TextField();
        precio.setPromptText("Ej. 200000");
        estilizarCampo(precio);

        GridPane formulario = new GridPane();
        formulario.setHgap(12);
        formulario.setVgap(10);

        agregarCampo(formulario, "Nombre del servicio", nombre, 0);
        agregarCampo(formulario, "Código del servicio", codigo, 1);
        agregarCampo(formulario, "Descripción", descripcion, 2);
        agregarCampo(formulario, "Precio (COP)", precio, 3);

        VBox contenido = new VBox(10, titulo, subtitulo, formulario);
        contenido.setPadding(new Insets(8));
        contenido.setPrefWidth(440);
        contenido.setStyle("-fx-background-color: white;");

        var panel = dialogo.getDialogPane();
        panel.setContent(contenido);
        panel.setPrefWidth(510);
        panel.setStyle(
                "-fx-background-color: white;"
                        + "-fx-padding: 20;"
        );

        Button botonGuardar = (Button) panel.lookupButton(guardar);

        botonGuardar.addEventFilter(
                ActionEvent.ACTION,
                evento -> {
                    if (nombre.getText().trim().isEmpty()) {
                        mostrarError("El nombre es obligatorio.");
                        evento.consume();
                        nombre.requestFocus();
                        return;
                    }

                    if (descripcion.getText().trim().isEmpty()) {
                        mostrarError("La descripción es obligatoria.");
                        evento.consume();
                        descripcion.requestFocus();
                        return;
                    }

                    try {
                        double valor = convertirPrecio(precio.getText());

                        if (!Double.isFinite(valor) || valor <= 0) {
                            mostrarError(
                                    "El precio debe ser un número positivo."
                            );
                            evento.consume();
                            precio.requestFocus();
                        }
                    } catch (NumberFormatException ex) {
                        mostrarError(
                                "Introduce un precio válido, por ejemplo 200000."
                        );
                        evento.consume();
                        precio.requestFocus();
                    }
                }
        );

        dialogo.setOnShown(evento -> {
            Button botonCancelar = (Button) panel.lookupButton(cancelar);

            botonGuardar.setStyle(
                    "-fx-background-color: " + MORADO + ";"
                            + "-fx-text-fill: white;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 8;"
                            + "-fx-padding: 10 16 10 16;"
            );

            botonCancelar.setStyle(
                    "-fx-background-color: #f0e7ff;"
                            + "-fx-text-fill: " + MORADO + ";"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 8;"
                            + "-fx-padding: 10 16 10 16;"
            );

            nombre.requestFocus();
        });

        Optional<ButtonType> resultado = dialogo.showAndWait();

        if (resultado.isPresent() && resultado.get() == guardar) {
            String nombreServicio = nombre.getText().trim();
            String descripcionServicio = descripcion.getText().trim();
            double precioServicio = convertirPrecio(precio.getText());
            String codigoServicio = codigo.getText();

            ElementoTecnologico servicioPersonalizado =
                    new ElementoTecnologico() {
                        @Override
                        public String getCodigo() {
                            return codigoServicio;
                        }

                        @Override
                        public String getNombre() {
                            return nombreServicio;
                        }

                        @Override
                        public String getDescripcion() {
                            return descripcionServicio;
                        }

                        @Override
                        public BigDecimal getCosto() {
                            return BigDecimal.valueOf(precioServicio);
                        }
                    };

            try {
                catalogoServicios.agregarServicio(servicioPersonalizado);
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
                return;
            }

            VBox tarjeta = crearTarjetaServicio(
                    nombreServicio,
                    codigoServicio,
                    descripcionServicio,
                    precioServicio
            );

            contenedorServicios.getChildren().add(tarjeta);
            actualizarResumenInicio();

            ajustarAnchoTarjetas(contenedorServicios.getWidth());
            campoBusqueda.clear();

            mostrarConfirmacion(
                    "Servicio registrado correctamente.\n\n"
                            + "Código: " + codigoServicio + "\n"
                            + "Nombre: " + nombreServicio
            );
        }
    }

    private void estilizarCampo(TextField campo) {
        campo.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 8;"
                        + "-fx-border-radius: 8;"
                        + "-fx-border-color: #ded5ef;"
                        + "-fx-padding: 10;"
                        + "-fx-font-size: 13px;"
        );
    }

    private void agregarCampo(
            GridPane formulario,
            String texto,
            Node control,
            int fila) {

        Label etiqueta = new Label(texto);
        etiqueta.setWrapText(true);
        etiqueta.setStyle(
                "-fx-font-size: 12px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO_OSCURO + ";"
        );

        GridPane.setMargin(etiqueta, new Insets(8, 0, 0, 0));
        formulario.add(etiqueta, 0, fila);
        formulario.add(control, 1, fila);

        if (control instanceof TextField campo) {
            campo.setPrefWidth(285);
        } else if (control instanceof TextArea area) {
            area.setPrefWidth(285);
        }
    }

    private double convertirPrecio(String texto) {
        String valor = texto.trim();

        if (valor.matches("\\d{1,3}(\\.\\d{3})+")) {
            valor = valor.replace(".", "");
        } else {
            valor = valor.replace(",", ".");
        }

        return Double.parseDouble(valor);
    }

    private String generarCodigoServicio() {
        int mayor = 0;

        for (Node nodo : contenedorServicios.getChildren()) {
            if (nodo instanceof VBox tarjeta) {
                for (Node elemento : tarjeta.getChildren()) {
                    if (elemento instanceof Label etiqueta) {
                        String texto = etiqueta.getText();

                        if (texto != null
                                && texto.matches("SER-\\d{3,}")) {
                            int numero = Integer.parseInt(
                                    texto.substring(4)
                            );
                            mayor = Math.max(mayor, numero);
                        }
                    }
                }
            }
        }

        return String.format("SER-%03d", mayor + 1);
    }

    /**
     * Crea una tarjeta para un servicio nuevo.
     *
     * El botón conserva el mismo aspecto visual que los botones
     * de las tarjetas originales y queda alineado en la parte inferior.
     */
    private VBox crearTarjetaServicio(
            String nombre,
            String codigo,
            String descripcion,
            double precio) {

        /*
         * Se reutilizan las mismas clases CSS del FXML:
         * service-card, service-image-container, service-image-icon,
         * service-name, service-code, service-description y service-price.
         * Así la tarjeta nueva hereda el mismo diseño que las originales.
         */
        VBox tarjeta = new VBox(8);
        tarjeta.getStyleClass().add("service-card");
        tarjeta.setId("tarjeta-servicio-dinamica");

        tarjeta.setMinWidth(300);
        tarjeta.setPrefWidth(320);
        tarjeta.setMaxWidth(320);

        tarjeta.setMinHeight(300);
        tarjeta.setPrefHeight(300);
        tarjeta.setMaxHeight(300);

        tarjeta.setFillWidth(true);

        // Área superior de la imagen, con las mismas dimensiones del FXML.
        StackPane areaImagen = new StackPane();
        areaImagen.getStyleClass().add("service-image-container");
        areaImagen.setMinHeight(138);
        areaImagen.setPrefHeight(138);
        areaImagen.setMaxHeight(138);
        areaImagen.setMaxWidth(Double.MAX_VALUE);

        Label icono = new Label("◇");
        icono.getStyleClass().add("service-image-icon");
        StackPane.setAlignment(icono, Pos.BOTTOM_LEFT);
        StackPane.setMargin(icono, new Insets(0, 0, 10, 10));
        areaImagen.getChildren().add(icono);

        Label etiquetaNombre = new Label(nombre);
        etiquetaNombre.getStyleClass().add("service-name");
        etiquetaNombre.setWrapText(true);
        etiquetaNombre.setMaxWidth(Double.MAX_VALUE);

        Label etiquetaCodigo = new Label(codigo);
        etiquetaCodigo.getStyleClass().add("service-code");

        Label etiquetaDescripcion = new Label(descripcion);
        etiquetaDescripcion.getStyleClass().add("service-description");
        etiquetaDescripcion.setWrapText(true);
        etiquetaDescripcion.setMaxHeight(38);
        etiquetaDescripcion.setMaxWidth(Double.MAX_VALUE);

        // Igual que las tarjetas originales: el espacio va antes del precio.
        Region espacioFlexible = new Region();
        VBox.setVgrow(espacioFlexible, Priority.ALWAYS);

        Label etiquetaPrecio = new Label(formatearPrecio(precio));
        etiquetaPrecio.getStyleClass().add("service-price");

        Button botonDetalles = new Button("Ver detalles     →");
        botonDetalles.getStyleClass().add("secondary-button");
        botonDetalles.setMaxWidth(Double.MAX_VALUE);
        botonDetalles.setMinHeight(36);
        botonDetalles.setPrefHeight(36);
        botonDetalles.setMaxHeight(36);
        botonDetalles.setOnAction(evento ->
                mostrarDetalles(
                        nombre,
                        codigo,
                        descripcion,
                        etiquetaPrecio.getText(),
                        "• Servicio registrado en el catálogo.\n"
                                + "• " + descripcion
                )
        );

        tarjeta.getChildren().addAll(
                areaImagen,
                etiquetaNombre,
                etiquetaCodigo,
                etiquetaDescripcion,
                espacioFlexible,
                etiquetaPrecio,
                botonDetalles
        );

        return tarjeta;
    }

    private String formatearPrecio(double precio) {
        return "$ " + String.format("%,.0f", precio)
                .replace(",", ".");
    }

    /**
     * Muestra los detalles de un servicio o paquete.
     */
    private void mostrarDetalles(
            String nombre,
            String codigo,
            String descripcion,
            String precio,
            String caracteristicas) {

        Alert ventana = new Alert(Alert.AlertType.NONE);
        ventana.setTitle("TechSolutions | Detalles");
        ventana.setHeaderText(null);

        Label titulo = new Label(nombre);
        titulo.setWrapText(true);
        titulo.setStyle(
                "-fx-font-size: 21px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #24104f;"
        );

        Label etiquetaCodigo = new Label(codigo);
        etiquetaCodigo.setStyle(
                "-fx-background-color: #f0e7ff;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-font-size: 12px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-padding: 6 12 6 12;"
                        + "-fx-background-radius: 12;"
        );

        Label tituloDescripcion =
                crearTituloSeccion("Descripción del servicio");

        Label textoDescripcion = new Label(descripcion);
        textoDescripcion.setWrapText(true);
        textoDescripcion.setMaxWidth(Double.MAX_VALUE);
        textoDescripcion.setMinHeight(Region.USE_PREF_SIZE);
        textoDescripcion.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
                        + "-fx-line-spacing: 3px;"
        );

        Label tituloCaracteristicas =
                crearTituloSeccion("¿Qué incluye?");

        Label textoCaracteristicas = new Label(caracteristicas);
        textoCaracteristicas.setWrapText(true);
        textoCaracteristicas.setMaxWidth(Double.MAX_VALUE);
        textoCaracteristicas.setMinHeight(Region.USE_PREF_SIZE);
        textoCaracteristicas.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
                        + "-fx-line-spacing: 4px;"
        );

        Label tituloPrecio = new Label("Precio del servicio");
        tituloPrecio.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
        );

        Label textoPrecio = new Label(precio);
        textoPrecio.setStyle(
                "-fx-font-size: 26px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO + ";"
        );

        VBox bloquePrecio = new VBox(5, tituloPrecio, textoPrecio);
        bloquePrecio.setStyle(
                "-fx-background-color: #f7f2ff;"
                        + "-fx-background-radius: 12;"
                        + "-fx-padding: 15;"
        );

        VBox contenido = new VBox(
                12,
                titulo,
                etiquetaCodigo,
                tituloDescripcion,
                textoDescripcion,
                tituloCaracteristicas,
                textoCaracteristicas,
                bloquePrecio
        );

        contenido.setPadding(new Insets(4));
        contenido.setMinWidth(440);
        contenido.setPrefWidth(440);
        contenido.setMaxWidth(440);
        contenido.setStyle("-fx-background-color: white;");

        ScrollPane desplazamiento = new ScrollPane(contenido);
        desplazamiento.setFitToWidth(true);
        desplazamiento.setPannable(true);
        desplazamiento.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );
        desplazamiento.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );
        desplazamiento.setPrefViewportWidth(460);
        desplazamiento.setPrefViewportHeight(460);
        desplazamiento.setMaxHeight(560);
        desplazamiento.setStyle(
                "-fx-background-color: white; -fx-background: white;"
        );

        var panel = ventana.getDialogPane();
        panel.setContent(desplazamiento);
        panel.getButtonTypes().setAll(ButtonType.OK);
        panel.setMinWidth(510);
        panel.setPrefWidth(540);
        panel.setStyle(
                "-fx-background-color: white;"
                        + "-fx-padding: 18;"
        );

        ventana.setOnShown(evento -> {
            Button botonCerrar =
                    (Button) panel.lookupButton(ButtonType.OK);

            botonCerrar.setText("Cerrar");
            botonCerrar.setStyle(
                    "-fx-background-color: " + MORADO + ";"
                            + "-fx-text-fill: white;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 8;"
                            + "-fx-padding: 9 25 9 25;"
            );
        });

        ventana.showAndWait();
    }

    private Label crearTituloSeccion(String texto) {
        Label titulo = new Label(texto);
        titulo.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO_OSCURO + ";"
        );
        return titulo;
    }

    @FXML
    private void verDetallesPreventivo() {
        mostrarDetalles(
                "Mantenimiento preventivo",
                "SER-001",
                "Revisión y limpieza de equipos, optimización del rendimiento "
                        + "y prevención de fallas.",
                "$ 80.000",
                "• Limpieza interna y externa de los equipos.\n"
                        + "• Revisión de componentes.\n"
                        + "• Optimización del rendimiento.\n"
                        + "• Prevención de fallas futuras."
        );
    }

    @FXML
    private void verDetallesCorrectivo() {
        mostrarDetalles(
                "Mantenimiento correctivo",
                "SER-002",
                "Diagnóstico y reparación de fallas en equipos de cómputo.",
                "$ 120.000",
                "• Diagnóstico de problemas técnicos.\n"
                        + "• Identificación de componentes defectuosos.\n"
                        + "• Reparación de fallas.\n"
                        + "• Comprobación del funcionamiento."
        );
    }

    @FXML
    private void verDetallesRedes() {
        mostrarDetalles(
                "Instalación de redes",
                "SER-003",
                "Instalación y configuración de redes locales cableadas "
                        + "e inalámbricas.",
                "$ 180.000",
                "• Instalación de cableado de red.\n"
                        + "• Configuración de dispositivos de red.\n"
                        + "• Conexión de equipos.\n"
                        + "• Verificación de conectividad."
        );
    }

    @FXML
    private void verDetallesInformacion() {
        mostrarDetalles(
                "Recuperación de información",
                "SER-004",
                "Recuperación de datos en dispositivos de almacenamiento "
                        + "dañados o inaccesibles.",
                "$ 150.000",
                "• Evaluación del dispositivo.\n"
                        + "• Análisis de pérdida de información.\n"
                        + "• Intento de recuperación de archivos.\n"
                        + "• Verificación de los datos recuperados."
        );
    }

    /**
     * Muestra las plantillas de paquetes disponibles.
     */
    private void mostrarPlantillasPaquetes() {
        if (contenedorPaquetes == null
                || !contenedorPaquetes.getChildren().isEmpty()) {
            return;
        }

        contenedorPaquetes.getChildren().addAll(
                crearTarjetaPlantilla(
                        "Mantenimiento esencial",
                        "Mantenimiento preventivo y correctivo para mantener "
                                + "los equipos en buen estado.",
                        200000,
                        List.of(
                                "Mantenimiento preventivo",
                                "Mantenimiento correctivo"
                        )
                ),
                crearTarjetaPlantilla(
                        "Conectividad total",
                        "Instalación de redes y mantenimiento preventivo "
                                + "para oficinas y negocios.",
                        260000,
                        List.of(
                                "Instalación de redes",
                                "Mantenimiento preventivo"
                        )
                ),
                crearTarjetaPlantilla(
                        "Protección de información",
                        "Recuperación de información y mantenimiento "
                                + "preventivo de los equipos.",
                        230000,
                        List.of(
                                "Recuperación de información",
                                "Mantenimiento preventivo"
                        )
                ),
                crearTarjetaPlantilla(
                        "Solución integral",
                        "Solución completa de mantenimiento, redes y "
                                + "recuperación de información.",
                        530000,
                        List.of(
                                "Mantenimiento preventivo",
                                "Mantenimiento correctivo",
                                "Instalación de redes",
                                "Recuperación de información"
                        )
                )
        );
    }

    private VBox crearTarjetaPlantilla(
            String nombre,
            String descripcion,
            double precio,
            List<String> nombresServicios) {

        VBox tarjeta = new VBox(9);
        tarjeta.setPrefWidth(300);
        tarjeta.setMinWidth(ANCHO_MINIMO_TARJETA);
        tarjeta.setMaxWidth(340);
        tarjeta.setMinHeight(ALTO_MINIMO_TARJETA_PAQUETE);
        tarjeta.setPrefHeight(ALTO_MINIMO_TARJETA_PAQUETE);
        tarjeta.setMaxHeight(ALTO_MINIMO_TARJETA_PAQUETE);
        tarjeta.setFillWidth(true);
        tarjeta.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 16;"
                        + "-fx-padding: 18;"
                        + "-fx-border-color: #eee6fb;"
                        + "-fx-border-radius: 16;"
                        + "-fx-effect: dropshadow(gaussian, "
                        + "rgba(35,20,70,0.10), 12, 0, 0, 3);"
        );

        Label icono = new Label("◇");
        icono.setMinSize(44, 44);
        icono.setPrefSize(44, 44);
        icono.setAlignment(Pos.CENTER);
        icono.setStyle(
                "-fx-background-color: #f0e7ff;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-font-size: 25px;"
                        + "-fx-background-radius: 12;"
        );

        Label etiquetaPlantilla = new Label("PAQUETE RECOMENDADO");
        etiquetaPlantilla.setStyle(
                "-fx-font-size: 9px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-background-color: #f0e7ff;"
                        + "-fx-padding: 5 8 5 8;"
                        + "-fx-background-radius: 8;"
        );

        Label nombreEtiqueta = new Label(nombre);
        nombreEtiqueta.setWrapText(true);
        nombreEtiqueta.setMaxWidth(Double.MAX_VALUE);
        nombreEtiqueta.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #24104f;"
        );

        Label descripcionEtiqueta = new Label(descripcion);
        descripcionEtiqueta.setWrapText(true);
        descripcionEtiqueta.setMaxWidth(Double.MAX_VALUE);
        descripcionEtiqueta.setMinHeight(Region.USE_PREF_SIZE);
        descripcionEtiqueta.setStyle(
                "-fx-font-size: 12px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
                        + "-fx-line-spacing: 2px;"
        );

        Region separador = new Region();
        separador.setMinHeight(1);
        separador.setMaxHeight(1);
        separador.setStyle("-fx-background-color: #eee7f7;");

        Label cantidad = new Label(
                "✓   " + nombresServicios.size()
                        + (nombresServicios.size() == 1
                        ? " servicio incluido" : " servicios incluidos")
        );
        cantidad.setStyle(
                "-fx-font-size: 12px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
        );

        Label precioEtiqueta = new Label(formatearPrecio(precio));
        precioEtiqueta.setStyle(
                "-fx-font-size: 22px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO + ";"
        );

        Region espacioFlexible = new Region();
        VBox.setVgrow(espacioFlexible, Priority.ALWAYS);

        Button botonDetalles = new Button("Ver detalles     →");
        botonDetalles.setMaxWidth(Double.MAX_VALUE);
        botonDetalles.setMinHeight(38);
        botonDetalles.setPrefHeight(38);
        botonDetalles.setMaxHeight(38);
        botonDetalles.setStyle(
                "-fx-background-color: #f0e7ff;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 9;"
                        + "-fx-padding: 10;"
                        + "-fx-cursor: hand;"
        );

        botonDetalles.setOnAction(evento -> {
            StringBuilder servicios = new StringBuilder();
            for (String nombreServicio : nombresServicios) {
                servicios.append("✓  ")
                        .append(nombreServicio)
                        .append("\n");
            }

            mostrarDetalles(
                    nombre,
                    "PLANTILLA",
                    descripcion,
                    formatearPrecio(precio),
                    "Servicios incluidos:\n\n" + servicios
            );
        });

        tarjeta.getChildren().addAll(
                icono,
                etiquetaPlantilla,
                nombreEtiqueta,
                descripcionEtiqueta,
                separador,
                cantidad,
                precioEtiqueta,
                espacioFlexible,
                botonDetalles
        );

        return tarjeta;
    }

    @FXML
    private void nuevoPaquete() {
        Dialog<ButtonType> dialogo = new Dialog<>();
        dialogo.setTitle("TechSolutions | Crear paquete");
        dialogo.setHeaderText(null);

        ButtonType cancelar = new ButtonType(
                "Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE
        );

        ButtonType guardar = new ButtonType(
                "Crear paquete", ButtonBar.ButtonData.OK_DONE
        );

        dialogo.getDialogPane().getButtonTypes()
                .addAll(cancelar, guardar);

        Label titulo = new Label("Crear paquete tecnológico");
        titulo.setStyle(
                "-fx-font-size: 22px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO_OSCURO + ";"
        );

        Label subtitulo = new Label(
                "Define el paquete y selecciona los servicios que incluirá."
        );
        subtitulo.setWrapText(true);
        subtitulo.setStyle(
                "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
        );

        TextField codigo = new TextField(generarCodigoPaquete());
        codigo.setEditable(false);
        estilizarCampo(codigo);

        TextField nombre = new TextField();
        nombre.setPromptText("Ej. Paquete de mantenimiento integral");
        estilizarCampo(nombre);

        TextArea descripcion = new TextArea();
        descripcion.setPromptText("Describe qué ofrece este paquete...");
        descripcion.setWrapText(true);
        descripcion.setPrefRowCount(3);
        descripcion.setStyle(
                "-fx-control-inner-background: white;"
                        + "-fx-border-color: #ded5ef;"
                        + "-fx-background-radius: 8;"
        );

        GridPane formulario = new GridPane();
        formulario.setHgap(12);
        formulario.setVgap(10);

        agregarCampo(formulario, "Código", codigo, 0);
        agregarCampo(formulario, "Nombre", nombre, 1);
        agregarCampo(formulario, "Descripción", descripcion, 2);

        Label tituloServicios = new Label("Servicios disponibles");
        tituloServicios.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO_OSCURO + ";"
        );

        VBox opcionesServicios = new VBox(8);
        Map<CheckBox, ElementoTecnologico> seleccion =
                new LinkedHashMap<>();

        for (ElementoTecnologico elemento :
                catalogoServicios.getServicios()) {

            CheckBox opcion = new CheckBox(
                    elemento.getNombre() + " — "
                            + formatearPrecio(
                            elemento.getCosto().doubleValue()
                    )
            );

            opcion.setWrapText(true);
            opcion.setStyle(
                    "-fx-text-fill: #32166f;"
                            + "-fx-font-size: 12px;"
            );

            seleccion.put(opcion, elemento);
            opcionesServicios.getChildren().add(opcion);
        }

        if (seleccion.isEmpty()) {
            opcionesServicios.getChildren().add(
                    new Label("No hay servicios en el catálogo.")
            );
        }

        VBox contenido = new VBox(
                12,
                titulo,
                subtitulo,
                formulario,
                tituloServicios,
                opcionesServicios
        );

        contenido.setPadding(new Insets(8));
        contenido.setPrefWidth(480);

        dialogo.getDialogPane().setContent(contenido);
        dialogo.getDialogPane().setPrefWidth(560);
        dialogo.getDialogPane().setStyle(
                "-fx-background-color: #FFFFFF;"
                        + "-fx-padding: 18;"
                        + "-fx-font-family: 'Segoe UI';"
                        + "-fx-border-color: #E8E1F5;"
                        + "-fx-border-width: 1;"
        );

        estilizarDialogoFormulario(dialogo, cancelar, guardar);

        Button botonGuardar =
                (Button) dialogo.getDialogPane().lookupButton(guardar);

        botonGuardar.addEventFilter(ActionEvent.ACTION, evento -> {
            if (nombre.getText().trim().isEmpty()) {
                mostrarError("El nombre del paquete es obligatorio.");
                evento.consume();
                nombre.requestFocus();
                return;
            }

            if (descripcion.getText().trim().isEmpty()) {
                mostrarError("La descripción del paquete es obligatoria.");
                evento.consume();
                descripcion.requestFocus();
                return;
            }

            boolean hayServicios = seleccion.keySet()
                    .stream()
                    .anyMatch(CheckBox::isSelected);

            if (!hayServicios) {
                mostrarError(
                        "Selecciona al menos un servicio para el paquete."
                );
                evento.consume();
            }
        });

        Optional<ButtonType> resultado = dialogo.showAndWait();

        if (resultado.isPresent() && resultado.get() == guardar) {
            try {
                PaqueteTecnologico paquete = new PaqueteTecnologico(
                        codigo.getText(),
                        nombre.getText().trim(),
                        descripcion.getText().trim()
                );

                for (Map.Entry<CheckBox, ElementoTecnologico> entrada
                        : seleccion.entrySet()) {
                    if (entrada.getKey().isSelected()) {
                        paquete.agregarElemento(entrada.getValue());
                    }
                }

                gestionPaquetes.registrarPaquete(paquete);
                actualizarResumenInicio();

                contenedorPaquetes.getChildren().add(
                        crearTarjetaPaquete(paquete)
                );

                mostrarConfirmacion(
                        "Paquete creado correctamente.\n\n"
                                + "Código: " + paquete.getCodigo() + "\n"
                                + "Nombre: " + paquete.getNombre() + "\n"
                                + "Costo total: "
                                + formatearPrecio(
                                paquete.getCosto().doubleValue()
                        )
                );

            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        }
    }

    private String generarCodigoPaquete() {
        return String.format(
                "PAQ-%03d",
                gestionPaquetes.listarPaquetes().size() + 1
        );
    }

    private VBox crearTarjetaPaquete(PaqueteTecnologico paquete) {
        VBox tarjeta = new VBox(9);
        tarjeta.setPrefWidth(300);
        tarjeta.setMinWidth(ANCHO_MINIMO_TARJETA);
        tarjeta.setMaxWidth(340);
        tarjeta.setMinHeight(ALTO_MINIMO_TARJETA_PAQUETE);
        tarjeta.setPrefHeight(ALTO_MINIMO_TARJETA_PAQUETE);
        tarjeta.setMaxHeight(ALTO_MINIMO_TARJETA_PAQUETE);
        tarjeta.setFillWidth(true);
        tarjeta.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 16;"
                        + "-fx-padding: 18;"
                        + "-fx-border-color: #eee6fb;"
                        + "-fx-border-radius: 16;"
                        + "-fx-effect: dropshadow(gaussian, "
                        + "rgba(35,20,70,0.10), 12, 0, 0, 3);"
        );

        Label icono = new Label("◇");
        icono.setMinSize(44, 44);
        icono.setPrefSize(44, 44);
        icono.setAlignment(Pos.CENTER);
        icono.setStyle(
                "-fx-background-color: #f0e7ff;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-font-size: 25px;"
                        + "-fx-background-radius: 12;"
        );

        Label etiquetaTipo = new Label("PAQUETE PERSONALIZADO");
        etiquetaTipo.setStyle(
                "-fx-font-size: 9px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-background-color: #f0e7ff;"
                        + "-fx-padding: 5 8 5 8;"
                        + "-fx-background-radius: 8;"
        );

        Label nombre = new Label(paquete.getNombre());
        nombre.setWrapText(true);
        nombre.setMaxWidth(Double.MAX_VALUE);
        nombre.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #24104f;"
        );

        Label codigo = new Label(paquete.getCodigo());
        codigo.setStyle(
                "-fx-font-size: 11px;"
                        + "-fx-text-fill: #8577a4;"
        );

        Label descripcion = new Label(paquete.getDescripcion());
        descripcion.setWrapText(true);
        descripcion.setMaxWidth(Double.MAX_VALUE);
        descripcion.setMinHeight(Region.USE_PREF_SIZE);
        descripcion.setStyle(
                "-fx-font-size: 12px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
                        + "-fx-line-spacing: 2px;"
        );

        Region separador = new Region();
        separador.setMinHeight(1);
        separador.setMaxHeight(1);
        separador.setStyle("-fx-background-color: #eee7f7;");

        Label cantidad = new Label(
                "✓   " + paquete.getElementos().size()
                        + (paquete.getElementos().size() == 1
                        ? " servicio incluido" : " servicios incluidos")
        );
        cantidad.setStyle(
                "-fx-font-size: 12px;"
                        + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
        );

        Label precio = new Label(
                formatearPrecio(paquete.getCosto().doubleValue())
        );
        precio.setStyle(
                "-fx-font-size: 22px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: " + MORADO + ";"
        );

        Region espacioFlexible = new Region();
        VBox.setVgrow(espacioFlexible, Priority.ALWAYS);

        Button detalles = new Button("Ver detalles     →");
        detalles.setMaxWidth(Double.MAX_VALUE);
        detalles.setMinHeight(38);
        detalles.setPrefHeight(38);
        detalles.setMaxHeight(38);
        detalles.setStyle(
                "-fx-background-color: #f0e7ff;"
                        + "-fx-text-fill: " + MORADO + ";"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 9;"
                        + "-fx-padding: 10;"
                        + "-fx-cursor: hand;"
        );
        detalles.setOnAction(evento -> mostrarDetallesPaquete(paquete));

        tarjeta.getChildren().addAll(
                icono,
                etiquetaTipo,
                nombre,
                codigo,
                descripcion,
                separador,
                cantidad,
                precio,
                espacioFlexible,
                detalles
        );

        return tarjeta;
    }

    private void mostrarDetallesPaquete(PaqueteTecnologico paquete) {
        StringBuilder elementos = new StringBuilder();

        for (ElementoTecnologico elemento : paquete.getElementos()) {
            elementos.append("• ")
                    .append(elemento.getNombre())
                    .append(" — ")
                    .append(formatearPrecio(
                            elemento.getCosto().doubleValue()
                    ))
                    .append("\n");
        }

        mostrarDetalles(
                paquete.getNombre(),
                paquete.getCodigo(),
                paquete.getDescripcion(),
                formatearPrecio(paquete.getCosto().doubleValue()),
                "Servicios incluidos:\n" + elementos
        );
    }


    /** Registra las cuatro plantillas visuales como paquetes reales seleccionables. */
    private void registrarPlantillasPaquetes() {
        if (!gestionPaquetes.listarPaquetes().isEmpty()) return;
        registrarPlantilla("PAQ-001", "Mantenimiento esencial",
                "Mantenimiento preventivo y correctivo para mantener los equipos en buen estado.",
                List.of("SER-001", "SER-002"));
        registrarPlantilla("PAQ-002", "Conectividad total",
                "Instalación de redes y mantenimiento preventivo.",
                List.of("SER-003", "SER-001"));
        registrarPlantilla("PAQ-003", "Protección de información",
                "Recuperación de información y mantenimiento preventivo.",
                List.of("SER-004", "SER-001"));
        registrarPlantilla("PAQ-004", "Solución integral",
                "Mantenimiento, instalación de redes y recuperación de información.",
                List.of("SER-001", "SER-002", "SER-003", "SER-004"));
    }

    private void registrarPlantilla(String codigo, String nombre, String descripcion,
                                    List<String> codigosServicios) {
        PaqueteTecnologico paquete = new PaqueteTecnologico(codigo, nombre, descripcion);
        for (String codigoServicio : codigosServicios) {
            for (ElementoTecnologico elemento : catalogoServicios.getServicios()) {
                if (codigoServicio.equals(elemento.getCodigo())) {
                    paquete.agregarElemento(elemento);
                    break;
                }
            }
        }
        if (!paquete.getElementos().isEmpty()) {
            gestionPaquetes.registrarPaquete(paquete);
        }
    }

    private void configurarTablaSolicitudes() {
        if (tablaSolicitudes == null) return;
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaSolicitud"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costoTotal"));
        colCliente.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override protected void updateItem(Cliente cliente, boolean empty) {
                super.updateItem(cliente, empty);
                setText(empty || cliente == null ? null : cliente.getNombreCompleto());
            }
        });
        colCosto.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override protected void updateItem(BigDecimal costo, boolean empty) {
                super.updateItem(costo, empty);
                setText(empty || costo == null ? null : formatearPrecio(costo.doubleValue()));
            }
        });
        tablaSolicitudes.setItems(solicitudes);
        solicitudes.setAll(gestionSolicitudes.listarSolicitudes());
    }

    private void filtrarSolicitudes(String texto) {
        if (tablaSolicitudes == null) return;
        String filtro = texto == null ? "" : texto.trim().toLowerCase();
        solicitudes.setAll(gestionSolicitudes.listarSolicitudes().stream()
                .filter(s -> s.getCodigo().toLowerCase().contains(filtro)
                        || s.getCliente().getNombreCompleto().toLowerCase().contains(filtro)
                        || s.getCliente().getDocumento().toLowerCase().contains(filtro))
                .toList());
    }

    private void actualizarResumenSolicitudes() {
        List<SolicitudServicio> todas = gestionSolicitudes.listarSolicitudes();

        if (textoTotalSolicitudes != null) {
            textoTotalSolicitudes.setText(String.valueOf(todas.size()));
        }
        if (textoPendientes != null) {
            textoPendientes.setText(String.valueOf(todas.stream()
                    .filter(s -> s.getEstado() == EstadoSolicitud.PENDIENTE).count()));
        }
        if (textoConfirmadas != null) {
            textoConfirmadas.setText(String.valueOf(todas.stream()
                    .filter(s -> s.getEstado() == EstadoSolicitud.CONFIRMADA).count()));
        }
        if (textoCanceladas != null) {
            textoCanceladas.setText(String.valueOf(todas.stream()
                    .filter(s -> s.getEstado() == EstadoSolicitud.CANCELADA).count()));
        }

        if (tablaSolicitudes != null) {
            // Reasignar la misma lista observable evita que la tabla quede
            // desconectada si otro método cambió sus elementos.
            tablaSolicitudes.setItems(solicitudes);
            solicitudes.setAll(todas);
            tablaSolicitudes.refresh();
        }

        actualizarResumenInicio();
    }

    /**
     * Actualiza los indicadores de la pantalla de inicio con los datos reales.
     * Las cuatro plantillas iniciales no se cuentan como paquetes personalizados.
     */
    private void actualizarResumenInicio() {
        if (textoServiciosDisponibles != null) {
            textoServiciosDisponibles.setText(
                    String.valueOf(catalogoServicios.getServicios().size())
            );
        }

        if (textoPaquetesCreados != null) {
            long paquetesPersonalizados = gestionPaquetes.listarPaquetes()
                    .stream()
                    .filter(paquete -> {
                        String codigo = paquete.getCodigo();
                        if (codigo == null || !codigo.matches("PAQ-\\d+")) {
                            return true;
                        }
                        try {
                            return Integer.parseInt(codigo.substring(4)) > 4;
                        } catch (NumberFormatException ex) {
                            return true;
                        }
                    })
                    .count();

            textoPaquetesCreados.setText(
                    String.valueOf(paquetesPersonalizados)
            );
        }

        if (textoSolicitudesRegistradas != null) {
            textoSolicitudesRegistradas.setText(
                    String.valueOf(gestionSolicitudes.listarSolicitudes().size())
            );
        }
    }

    /** Abre el formulario de solicitud con selección de servicios, paquetes o ambos. */
    @FXML
    private void nuevaSolicitud() {
        Dialog<ButtonType> dialogo = new Dialog<>();
        dialogo.setTitle("TechSolutions | Nueva solicitud");
        dialogo.setHeaderText(null);
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType guardar = new ButtonType("Registrar solicitud", ButtonBar.ButtonData.OK_DONE);
        dialogo.getDialogPane().getButtonTypes().addAll(cancelar, guardar);

        Label titulo = new Label("Registrar nueva solicitud");
        titulo.setStyle("-fx-font-size:22px;-fx-font-weight:bold;-fx-text-fill:" + MORADO_OSCURO + ";");
        Label subtitulo = new Label("Ingresa los datos del cliente y elige servicios individuales o paquetes completos.");
        subtitulo.setWrapText(true);
        subtitulo.setStyle("-fx-text-fill:" + TEXTO_SECUNDARIO + ";");

        TextField documento = new TextField(); documento.setPromptText("Documento de identidad"); estilizarCampo(documento);
        TextField nombre = new TextField(); nombre.setPromptText("Nombre completo"); estilizarCampo(nombre);
        TextField correo = new TextField(); correo.setPromptText("Correo electrónico"); estilizarCampo(correo);
        DatePicker fecha = new DatePicker(); fecha.setPromptText("Fecha programada (opcional)"); fecha.setMaxWidth(Double.MAX_VALUE);
        ComboBox<Prioridad> prioridad = new ComboBox<>(FXCollections.observableArrayList(Prioridad.values()));
        prioridad.setValue(Prioridad.MEDIA); prioridad.setMaxWidth(Double.MAX_VALUE);
        TextArea observaciones = new TextArea(); observaciones.setPromptText("Describe el problema o los detalles...");
        observaciones.setWrapText(true); observaciones.setPrefRowCount(3);

        // Características complementarias seleccionables para cada solicitud.
        CheckBox complementoPrioritario = new CheckBox("Atención prioritaria — $ 35.000");
        CheckBox complementoSoporte = new CheckBox("Soporte técnico durante 30 días — $ 50.000");
        CheckBox complementoDomicilio = new CheckBox("Atención a domicilio — $ 25.000");
        VBox opcionesComplementarias = new VBox(8,
                complementoPrioritario, complementoSoporte, complementoDomicilio);
        Label tituloComplementos = crearTituloSeccion("Características complementarias");
        Label notaComplementos = new Label(
                "Selecciona los adicionales que deseas incluir en la solicitud.");
        notaComplementos.setWrapText(true);
        notaComplementos.setStyle("-fx-text-fill:" + TEXTO_SECUNDARIO + ";-fx-font-size:12px;");

        RadioButton opcionIndividual = new RadioButton("Servicios individuales");
        RadioButton opcionPaquetes = new RadioButton("Paquetes tecnológicos");
        RadioButton opcionAmbos = new RadioButton("Combinar servicios y paquetes");
        ToggleGroup grupo = new ToggleGroup();
        opcionIndividual.setToggleGroup(grupo); opcionPaquetes.setToggleGroup(grupo); opcionAmbos.setToggleGroup(grupo);
        opcionIndividual.setSelected(true);

        VBox listaServicios = new VBox(8);
        Map<CheckBox, ElementoTecnologico> checksServicios = new LinkedHashMap<>();
        for (ElementoTecnologico elemento : catalogoServicios.getServicios()) {
            CheckBox check = new CheckBox(elemento.getNombre() + " — " + formatearPrecio(elemento.getCosto().doubleValue()));
            checksServicios.put(check, elemento); listaServicios.getChildren().add(check);
        }

        VBox listaPaquetes = new VBox(8);
        Map<CheckBox, PaqueteTecnologico> checksPaquetes = new LinkedHashMap<>();
        for (PaqueteTecnologico paquete : gestionPaquetes.listarPaquetes()) {
            CheckBox check = new CheckBox(paquete.getNombre() + " — " + formatearPrecio(paquete.getCosto().doubleValue()));
            checksPaquetes.put(check, paquete); listaPaquetes.getChildren().add(check);
        }

        Label tituloServicios = crearTituloSeccion("Servicios disponibles");
        Label tituloPaquetes = crearTituloSeccion("Paquetes disponibles");
        Label total = new Label("Total estimado: $ 0");
        total.setStyle("-fx-font-size:18px;-fx-font-weight:bold;-fx-text-fill:" + MORADO + ";");
        Runnable recalcular = () -> {
            boolean verServicios = opcionIndividual.isSelected() || opcionAmbos.isSelected();
            boolean verPaquetes = opcionPaquetes.isSelected() || opcionAmbos.isSelected();
            listaServicios.setVisible(verServicios); listaServicios.setManaged(verServicios);
            tituloServicios.setVisible(verServicios); tituloServicios.setManaged(verServicios);
            listaPaquetes.setVisible(verPaquetes); listaPaquetes.setManaged(verPaquetes);
            tituloPaquetes.setVisible(verPaquetes); tituloPaquetes.setManaged(verPaquetes);
            Map<String, ElementoTecnologico> unicos = new LinkedHashMap<>();
            if (verServicios) checksServicios.forEach((c,e) -> { if (c.isSelected()) unicos.put(e.getCodigo(),e); });
            if (verPaquetes) checksPaquetes.forEach((c,p) -> { if (c.isSelected()) p.getElementos().forEach(e -> unicos.put(e.getCodigo(),e)); });
            BigDecimal suma = unicos.values().stream().map(ElementoTecnologico::getCosto).reduce(BigDecimal.ZERO, BigDecimal::add);
            suma = suma.add(costoComplementos(
                    complementoPrioritario.isSelected(),
                    complementoSoporte.isSelected(),
                    complementoDomicilio.isSelected()));
            total.setText("Total estimado: " + formatearPrecio(suma.doubleValue()));
        };
        grupo.selectedToggleProperty().addListener((o,a,b) -> recalcular.run());
        checksServicios.keySet().forEach(c -> c.selectedProperty().addListener((o,a,b) -> recalcular.run()));
        checksPaquetes.keySet().forEach(c -> c.selectedProperty().addListener((o,a,b) -> recalcular.run()));
        complementoPrioritario.selectedProperty().addListener((o,a,b) -> recalcular.run());
        complementoSoporte.selectedProperty().addListener((o,a,b) -> recalcular.run());
        complementoDomicilio.selectedProperty().addListener((o,a,b) -> recalcular.run());

        GridPane formulario = new GridPane(); formulario.setHgap(12); formulario.setVgap(10);
        agregarCampo(formulario, "Documento", documento, 0);
        agregarCampo(formulario, "Nombre completo", nombre, 1);
        agregarCampo(formulario, "Correo electrónico", correo, 2);
        agregarCampo(formulario, "Fecha programada", fecha, 3);
        agregarCampo(formulario, "Prioridad", prioridad, 4);
        agregarCampo(formulario, "Observaciones", observaciones, 5);

        VBox contenido = new VBox(12, titulo, subtitulo, formulario,
                crearTituloSeccion("¿Qué deseas solicitar?"), opcionIndividual, opcionPaquetes, opcionAmbos,
                tituloServicios, listaServicios, tituloPaquetes, listaPaquetes,
                tituloComplementos, notaComplementos, opcionesComplementarias, total);
        contenido.setPadding(new Insets(8)); contenido.setPrefWidth(480);
        ScrollPane scroll = new ScrollPane(contenido); scroll.setFitToWidth(true); scroll.setPrefViewportHeight(500);
        dialogo.getDialogPane().setContent(scroll);
        dialogo.getDialogPane().setPrefWidth(560);
        dialogo.getDialogPane().setMinWidth(540);
        dialogo.getDialogPane().setStyle(
                "-fx-background-color: #FFFFFF;"
                        + "-fx-padding: 18;"
                        + "-fx-font-family: 'Segoe UI';"
                        + "-fx-border-color: #E8E1F5;"
                        + "-fx-border-width: 1;"
        );
        scroll.setStyle(
                "-fx-background-color: #FFFFFF;"
                        + "-fx-background: #FFFFFF;"
                        + "-fx-border-color: transparent;"
        );
        contenido.setStyle("-fx-background-color: #FFFFFF;");
        estilizarDialogoFormulario(dialogo, cancelar, guardar);

        Button botonGuardar = (Button) dialogo.getDialogPane().lookupButton(guardar);
        botonGuardar.addEventFilter(ActionEvent.ACTION, evento -> {
            if (documento.getText().trim().isEmpty() || nombre.getText().trim().isEmpty() || correo.getText().trim().isEmpty()) {
                mostrarError("Documento, nombre y correo son obligatorios."); evento.consume(); return;
            }
            if (!correo.getText().trim().matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
                mostrarError("Ingresa un correo electrónico válido."); evento.consume(); return;
            }
            if (fecha.getValue() != null && fecha.getValue().isBefore(LocalDate.now())) {
                mostrarError("La fecha programada no puede ser anterior a hoy."); evento.consume(); return;
            }
            boolean hayServicio = (opcionIndividual.isSelected() || opcionAmbos.isSelected())
                    && checksServicios.entrySet().stream().anyMatch(e -> e.getKey().isSelected());
            boolean hayPaquete = (opcionPaquetes.isSelected() || opcionAmbos.isSelected())
                    && checksPaquetes.keySet().stream().anyMatch(CheckBox::isSelected);
            if (!hayServicio && !hayPaquete) { mostrarError("Selecciona al menos un servicio o paquete."); evento.consume(); }
        });

        recalcular.run();
        Optional<ButtonType> resultado = dialogo.showAndWait();
        if (resultado.isEmpty() || resultado.get() != guardar) return;

        try {
            Map<String, ElementoTecnologico> elementosUnicos = new LinkedHashMap<>();
            if (opcionIndividual.isSelected() || opcionAmbos.isSelected()) {
                checksServicios.forEach((check, elemento) -> { if (check.isSelected()) elementosUnicos.put(elemento.getCodigo(), elemento); });
            }
            if (opcionPaquetes.isSelected() || opcionAmbos.isSelected()) {
                checksPaquetes.forEach((check, paquete) -> { if (check.isSelected()) paquete.getElementos().forEach(e -> elementosUnicos.put(e.getCodigo(), e)); });
            }
            BigDecimal costoAdicional = costoComplementos(
                    complementoPrioritario.isSelected(),
                    complementoSoporte.isSelected(),
                    complementoDomicilio.isSelected());

            StringBuilder observacionesRecibo = new StringBuilder(observaciones.getText().trim());
            if (complementoPrioritario.isSelected() || complementoSoporte.isSelected()
                    || complementoDomicilio.isSelected()) {
                if (!observacionesRecibo.isEmpty()) observacionesRecibo.append("\n\n");
                observacionesRecibo.append("CARACTERÍSTICAS COMPLEMENTARIAS SELECCIONADAS:\n");
                if (complementoPrioritario.isSelected()) observacionesRecibo.append("• Atención prioritaria — $ 35.000\n");
                if (complementoSoporte.isSelected()) observacionesRecibo.append("• Soporte técnico durante 30 días — $ 50.000\n");
                if (complementoDomicilio.isSelected()) observacionesRecibo.append("• Atención a domicilio — $ 25.000\n");
            }

            Cliente cliente = new Cliente(documento.getText().trim(), nombre.getText().trim(), correo.getText().trim());
            String codigo = String.format("SOL-%03d", siguienteConsecutivoSolicitud());
            SolicitudServicioBuilder builder = new SolicitudServicioBuilder()
                    .codigo(codigo).fechaSolicitud(LocalDate.now()).cliente(cliente)
                    .prioridad(prioridad.getValue()).observaciones(observacionesRecibo.toString());
            if (fecha.getValue() != null) builder.fechaProgramada(fecha.getValue());
            elementosUnicos.values().forEach(builder::agregarElemento);
            SolicitudServicio solicitud = builder.construir();
            BigDecimal costo = elementosUnicos.values().stream().map(ElementoTecnologico::getCosto).reduce(BigDecimal.ZERO, BigDecimal::add);
            costo = costo.add(costoAdicional);
            solicitud.establecerCostoTotal(costo);
            gestionSolicitudes.registrarSolicitud(solicitud);
            actualizarResumenSolicitudes();
            mostrarConfirmacion("Solicitud registrada correctamente.\nCódigo: " + codigo + "\nCliente: " + cliente.getNombreCompleto()
                    + "\nServicios incluidos: " + elementosUnicos.size() + "\nCosto total: " + formatearPrecio(costo.doubleValue()));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage() == null ? "No fue posible registrar la solicitud." : ex.getMessage());
        }
    }

    /** Calcula el costo de las características complementarias seleccionadas. */
    private BigDecimal costoComplementos(boolean prioritario, boolean soporte, boolean domicilio) {
        BigDecimal total = BigDecimal.ZERO;
        if (prioritario) total = total.add(BigDecimal.valueOf(35000));
        if (soporte) total = total.add(BigDecimal.valueOf(50000));
        if (domicilio) total = total.add(BigDecimal.valueOf(25000));
        return total;
    }

    private int siguienteConsecutivoSolicitud() {
        int mayor = 0;
        for (SolicitudServicio solicitud : gestionSolicitudes.listarSolicitudes()) {
            String codigo = solicitud.getCodigo();
            if (codigo != null && codigo.matches("SOL-\\d+")) {
                try { mayor = Math.max(mayor, Integer.parseInt(codigo.substring(4))); }
                catch (NumberFormatException ignored) { }
            }
        }
        return mayor + 1;
    }


    /** Muestra un recibo detallado de la solicitud seleccionada. */
    @FXML
    private void verDetallesSolicitud() {
        SolicitudServicio solicitud = obtenerSolicitudSeleccionada();
        if (solicitud == null) {
            mostrarError("Selecciona una solicitud de la tabla para ver sus detalles.");
            return;
        }

        StringBuilder detalle = new StringBuilder();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal subtotalComplementos = BigDecimal.ZERO;

        detalle.append("                 TECHSOLUTIONS\n")
                .append("                RECIBO DE SERVICIO\n")
                .append("================================================\n\n")
                .append("DATOS DE LA SOLICITUD\n")
                .append("------------------------------------------------\n")
                .append("Código: ").append(solicitud.getCodigo()).append("\n")
                .append("Fecha de solicitud: ").append(solicitud.getFechaSolicitud()).append("\n")
                .append("Fecha programada: ").append(solicitud.getFechaProgramada() == null
                        ? "Sin programar" : solicitud.getFechaProgramada()).append("\n")
                .append("Prioridad: ").append(solicitud.getPrioridad()).append("\n")
                .append("Estado: ").append(solicitud.getEstado()).append("\n\n")
                .append("DATOS DEL CLIENTE\n")
                .append("------------------------------------------------\n")
                .append("Nombre: ").append(solicitud.getCliente().getNombreCompleto()).append("\n")
                .append("Documento: ").append(solicitud.getCliente().getDocumento()).append("\n")
                .append("Correo: ").append(solicitud.getCliente().getCorreoElectronico()).append("\n\n")
                .append("SERVICIOS, PAQUETES Y COMPLEMENTOS\n")
                .append("------------------------------------------------\n");

        for (ElementoTecnologico elemento : solicitud.getElementos()) {
            detalle.append("\n• ").append(elemento.getNombre()).append("\n")
                    .append("  Precio registrado: ")
                    .append(formatearPrecio(elemento.getCosto().doubleValue())).append("\n");

            if (elemento instanceof PaqueteTecnologico paquete) {
                detalle.append("  Tipo: Paquete tecnológico\n")
                        .append("  Servicios incluidos:\n");
                for (ElementoTecnologico incluido : paquete.getElementos()) {
                    detalle.append("    - ").append(incluido.getNombre())
                            .append(" — ")
                            .append(formatearPrecio(incluido.getCosto().doubleValue()))
                            .append("\n");
                }
            } else {
                detalle.append("  Características complementarias:\n");
                String nombre = elemento.getNombre() == null ? "" : elemento.getNombre().toLowerCase();
                boolean hayComplemento = false;
                if (nombre.contains("prioritaria") || nombre.contains("prioridad")) {
                    detalle.append("    ✓ Atención prioritaria\n");
                    subtotalComplementos = subtotalComplementos.add(BigDecimal.valueOf(35000));
                    hayComplemento = true;
                }
                if (nombre.contains("30 días") || nombre.contains("30 dias") || nombre.contains("soporte técnico") || nombre.contains("soporte tecnico")) {
                    detalle.append("    ✓ Soporte técnico durante 30 días\n");
                    subtotalComplementos = subtotalComplementos.add(BigDecimal.valueOf(50000));
                    hayComplemento = true;
                }
                if (nombre.contains("domicilio") || nombre.contains("a domicilio")) {
                    detalle.append("    ✓ Atención a domicilio\n");
                    subtotalComplementos = subtotalComplementos.add(BigDecimal.valueOf(25000));
                    hayComplemento = true;
                }
                if (!hayComplemento) {
                    detalle.append("    Sin complementos identificables en el nombre\n");
                }
            }
            subtotal = subtotal.add(elemento.getCosto());
        }

        detalle.append("\nOBSERVACIONES\n")
                .append("------------------------------------------------\n")
                .append(solicitud.getObservaciones() == null || solicitud.getObservaciones().isBlank()
                        ? "Sin observaciones" : solicitud.getObservaciones())
                .append("\n\nRESUMEN ECONÓMICO\n")
                .append("------------------------------------------------\n")
                .append("Subtotal de elementos registrados: ")
                .append(formatearPrecio(subtotal.doubleValue())).append("\n")
                .append("Total registrado en la solicitud: ")
                .append(formatearPrecio(solicitud.getCostoTotal().doubleValue())).append("\n");

        if (solicitud.requiereAtencionDomicilio()) {
            detalle.append("\nLa solicitud requiere atención a domicilio.\n")
                    .append("El costo de desplazamiento se calcula al confirmar.\n");
        }
        detalle.append("\n================================================\n")
                .append("             Gracias por confiar en nosotros.");

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("TechSolutions | Recibo de solicitud");
        alerta.setHeaderText("Recibo de la solicitud " + solicitud.getCodigo());
        TextArea texto = new TextArea(detalle.toString());
        texto.setEditable(false);
        texto.setWrapText(true);
        texto.setPrefColumnCount(58);
        texto.setPrefRowCount(26);
        texto.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 12px;");
        alerta.getDialogPane().setContent(texto);
        alerta.getDialogPane().setPrefWidth(680);
        alerta.getDialogPane().setPrefHeight(650);
        estilizarAlerta(alerta, false);
        alerta.showAndWait();
    }

    /**
     * Consulta la disponibilidad del técnico mediante el adaptador configurado.
     * Actualmente la respuesta procede de LegacySupportSimulado.
     */
    @FXML
    private void consultarTecnicoDisponible() {
        try {
            var respuestaTecnico = consultaTecnico.consultarTecnico("mantenimiento preventivo");

            Alert alerta = new Alert(
                    respuestaTecnico.isDisponible()
                            ? Alert.AlertType.INFORMATION
                            : Alert.AlertType.WARNING
            );
            alerta.setTitle("TechSolutions | Consulta de técnicos");
            alerta.setHeaderText(
                    respuestaTecnico.isDisponible()
                            ? "Técnico disponible"
                            : "No hay técnicos disponibles"
            );
            alerta.setContentText(
                    "Código del técnico: " + respuestaTecnico.getCodigoTecnico()
                            + "\nEstado: "
                            + (respuestaTecnico.isDisponible()
                            ? "DISPONIBLE" : "NO DISPONIBLE")
                            + "\nCosto de desplazamiento: "
                            + formatearPrecio(
                            respuestaTecnico.getCostoDesplazamiento()
                    )
            );
            estilizarAlerta(alerta, !respuestaTecnico.isDisponible());
            alerta.showAndWait();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(
                    "No fue posible consultar la disponibilidad del técnico.\n"
                            + (ex.getMessage() == null
                            ? "Error de consulta." : ex.getMessage())
            );
        }
    }

    /** Confirma una solicitud utilizando el servicio de costos y el adaptador de técnicos. */
    @FXML
    private void confirmarSolicitudSeleccionada() {
        SolicitudServicio solicitud = obtenerSolicitudSeleccionada();
        if (solicitud == null) {
            mostrarError("Selecciona una solicitud para confirmarla.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar solicitud");
        confirmacion.setHeaderText("¿Confirmar " + solicitud.getCodigo() + "?");
        confirmacion.setContentText(
                solicitud.requiereAtencionDomicilio()
                        ? "Se consultará la disponibilidad del técnico y se calculará el desplazamiento."
                        : "Se calculará el costo final de los servicios y paquetes seleccionados.");
        estilizarAlerta(confirmacion, false);

        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isEmpty() || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {
            // Calcula los elementos y, si aplica, el desplazamiento una sola vez.
            gestionSolicitudes.confirmarSolicitud(
                    solicitud.getCodigo(), consultaTecnico, calculadoraCostos);

            BigDecimal costoFinal = solicitud.getCostoTotal();
            String observacionesSolicitud = solicitud.getObservaciones() == null
                    ? "" : solicitud.getObservaciones().toLowerCase();

            // Atención prioritaria: $35.000.
            if (observacionesSolicitud.contains("atención prioritaria")
                    || observacionesSolicitud.contains("atencion prioritaria")) {
                costoFinal = costoFinal.add(BigDecimal.valueOf(35000));
            }

            // Soporte técnico durante 30 días: $50.000.
            if (observacionesSolicitud.contains("soporte técnico durante 30 días")
                    || observacionesSolicitud.contains("soporte tecnico durante 30 dias")) {
                costoFinal = costoFinal.add(BigDecimal.valueOf(50000));
            }

            // Atención a domicilio: $25.000. El desplazamiento ya se calculó arriba.
            if (observacionesSolicitud.contains("atención a domicilio")
                    || observacionesSolicitud.contains("atencion a domicilio")) {
                costoFinal = costoFinal.add(BigDecimal.valueOf(25000));
            }

            solicitud.establecerCostoTotal(costoFinal);
            actualizarResumenSolicitudes();
            mostrarConfirmacion("La solicitud " + solicitud.getCodigo()
                    + "\nCosto final: "
                    + formatearPrecio(solicitud.getCostoTotal().doubleValue()));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage() == null
                    ? "No fue posible confirmar la solicitud." : ex.getMessage());
        }
    }

    /** Cancela la solicitud seleccionada después de pedir confirmación. */
    @FXML
    private void cancelarSolicitudSeleccionada() {
        SolicitudServicio solicitud = obtenerSolicitudSeleccionada();
        if (solicitud == null) {
            mostrarError("Selecciona una solicitud para cancelarla.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cancelar solicitud");
        confirmacion.setHeaderText("¿Cancelar " + solicitud.getCodigo() + "?");
        confirmacion.setContentText("Esta acción cambiará su estado a CANCELADA.");
        estilizarAlerta(confirmacion, false);

        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isEmpty() || respuesta.get() != ButtonType.OK) {
            return;
        }

        try {
            gestionSolicitudes.cancelarSolicitud(solicitud.getCodigo());
            actualizarResumenSolicitudes();
            mostrarConfirmacion("La solicitud " + solicitud.getCodigo()
                    + " fue cancelada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage() == null
                    ? "No fue posible cancelar la solicitud." : ex.getMessage());
        }
    }

    private SolicitudServicio obtenerSolicitudSeleccionada() {
        return tablaSolicitudes == null
                ? null : tablaSolicitudes.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void abrirSolicitudes() {
        tabPrincipal.getSelectionModel().select(tabSolicitudes);
    }

    @FXML
    private void abrirServicios() {
        tabPrincipal.getSelectionModel().select(tabServicios);
    }

    @FXML
    private void abrirPaquetes() {
        tabPrincipal.getSelectionModel().select(tabPaquetes);
    }

    /**
     * Aplica el diseño morado y blanco de TechSolutions a los formularios
     * emergentes de creación de paquetes y registro de solicitudes.
     */
    private void estilizarDialogoFormulario(
            Dialog<ButtonType> dialogo,
            ButtonType tipoCancelar,
            ButtonType tipoGuardar) {

        var panel = dialogo.getDialogPane();
        panel.setStyle(
                "-fx-background-color: #FFFFFF;"
                        + "-fx-padding: 18;"
                        + "-fx-font-family: 'Segoe UI';"
                        + "-fx-border-color: #E8E1F5;"
                        + "-fx-border-width: 1;"
        );

        dialogo.setOnShown(evento -> {
            Button botonGuardar = (Button) panel.lookupButton(tipoGuardar);
            Button botonCancelar = (Button) panel.lookupButton(tipoCancelar);

            if (botonGuardar != null) {
                botonGuardar.setStyle(
                        "-fx-background-color: #7028E4;"
                                + "-fx-text-fill: white;"
                                + "-fx-font-family: 'Segoe UI';"
                                + "-fx-font-size: 12px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-background-radius: 8;"
                                + "-fx-border-radius: 8;"
                                + "-fx-padding: 10 18 10 18;"
                                + "-fx-cursor: hand;"
                );
                botonGuardar.setOnMouseEntered(e -> botonGuardar.setStyle(
                        "-fx-background-color: #5B21B6;"
                                + "-fx-text-fill: white;"
                                + "-fx-font-family: 'Segoe UI';"
                                + "-fx-font-size: 12px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-background-radius: 8;"
                                + "-fx-padding: 10 18 10 18;"
                                + "-fx-cursor: hand;"
                ));
                botonGuardar.setOnMouseExited(e -> botonGuardar.setStyle(
                        "-fx-background-color: #7028E4;"
                                + "-fx-text-fill: white;"
                                + "-fx-font-family: 'Segoe UI';"
                                + "-fx-font-size: 12px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-background-radius: 8;"
                                + "-fx-padding: 10 18 10 18;"
                                + "-fx-cursor: hand;"
                ));
            }

            if (botonCancelar != null) {
                botonCancelar.setStyle(
                        "-fx-background-color: #F0E7FF;"
                                + "-fx-text-fill: #7028E4;"
                                + "-fx-font-family: 'Segoe UI';"
                                + "-fx-font-size: 12px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-background-radius: 8;"
                                + "-fx-border-radius: 8;"
                                + "-fx-padding: 10 18 10 18;"
                                + "-fx-cursor: hand;"
                );
                botonCancelar.setOnMouseEntered(e -> botonCancelar.setStyle(
                        "-fx-background-color: #E5D5FF;"
                                + "-fx-text-fill: #5B21B6;"
                                + "-fx-font-family: 'Segoe UI';"
                                + "-fx-font-size: 12px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-background-radius: 8;"
                                + "-fx-padding: 10 18 10 18;"
                                + "-fx-cursor: hand;"
                ));
                botonCancelar.setOnMouseExited(e -> botonCancelar.setStyle(
                        "-fx-background-color: #F0E7FF;"
                                + "-fx-text-fill: #7028E4;"
                                + "-fx-font-family: 'Segoe UI';"
                                + "-fx-font-size: 12px;"
                                + "-fx-font-weight: bold;"
                                + "-fx-background-radius: 8;"
                                + "-fx-padding: 10 18 10 18;"
                                + "-fx-cursor: hand;"
                ));
            }
        });
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("TechSolutions | Aviso");
        alerta.setHeaderText("No se pudo completar la operación");
        alerta.setContentText(mensaje);
        estilizarAlerta(alerta, true);
        alerta.showAndWait();
    }

    private void mostrarConfirmacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("TechSolutions | Confirmación");
        alerta.setHeaderText("Operación completada");
        alerta.setContentText(mensaje);
        estilizarAlerta(alerta, false);
        alerta.showAndWait();
    }

    private void estilizarAlerta(Alert alerta, boolean esError) {
        var panel = alerta.getDialogPane();
        panel.setMinWidth(400);
        panel.setPrefWidth(440);
        panel.setStyle(
                "-fx-background-color: #FFFFFF;"
                        + "-fx-padding: 18;"
                        + "-fx-font-family: 'Segoe UI';"
                        + "-fx-border-color: #E8E1F5;"
                        + "-fx-border-width: 1;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-radius: 12;"
        );

        Label encabezado = (Label) panel.lookup(".header-panel .label");
        if (encabezado != null) {
            encabezado.setStyle(
                    "-fx-font-family: 'Segoe UI';"
                            + "-fx-font-size: 16px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-text-fill: " + (esError ? "#B91C1C" : MORADO_OSCURO) + ";"
            );
            encabezado.setWrapText(true);
            encabezado.setMaxWidth(350);
        }

        Label contenido = (Label) panel.lookup(".content.label");
        if (contenido != null) {
            contenido.setStyle(
                    "-fx-font-family: 'Segoe UI';"
                            + "-fx-font-size: 13px;"
                            + "-fx-text-fill: " + TEXTO_SECUNDARIO + ";"
                            + "-fx-line-spacing: 3px;"
            );
            contenido.setWrapText(true);
            contenido.setMaxWidth(360);
        }

        alerta.setOnShown(evento -> {
            Button botonAceptar = (Button) panel.lookupButton(ButtonType.OK);
            Button botonCancelar = (Button) panel.lookupButton(ButtonType.CANCEL);

            if (botonAceptar != null) {
                botonAceptar.setText("Aceptar");
                aplicarEstiloBotonAlerta(botonAceptar, true);
            }
            if (botonCancelar != null) {
                botonCancelar.setText("Cancelar");
                aplicarEstiloBotonAlerta(botonCancelar, false);
            }
        });
    }

    /** Aplica estilos consistentes a los botones de las alertas. */
    private void aplicarEstiloBotonAlerta(Button boton, boolean principal) {
        String estiloNormal = principal
                ? "-fx-background-color: #7028E4; -fx-text-fill: white;"
                : "-fx-background-color: #F0E7FF; -fx-text-fill: #7028E4;";
        String estiloHover = principal
                ? "-fx-background-color: #5B21B6; -fx-text-fill: white;"
                : "-fx-background-color: #E5D5FF; -fx-text-fill: #5B21B6;";

        String base = "-fx-font-family: 'Segoe UI';"
                + "-fx-font-size: 12px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8;"
                + "-fx-border-radius: 8;"
                + "-fx-padding: 9 22 9 22;"
                + "-fx-cursor: hand;";

        boton.setStyle(estiloNormal + base);
        boton.setOnMouseEntered(e -> boton.setStyle(estiloHover + base));
        boton.setOnMouseExited(e -> boton.setStyle(estiloNormal + base));
    }
}