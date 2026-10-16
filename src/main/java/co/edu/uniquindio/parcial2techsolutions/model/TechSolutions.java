package co.edu.uniquindio.parcial2techsolutions.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la empresa TechSolutions y centraliza
 * el acceso a la información principal del sistema.
 */
public class TechSolutions {

    private final String nombre;
    private final CatalogoServicios catalogoServicios;
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<PaqueteTecnologico> paquetes = new ArrayList<>();
    private final List<SolicitudServicio> solicitudes = new ArrayList<>();

    /**
     * Construye la empresa con su catálogo de servicios.
     *
     * @param nombre nombre de la empresa
     */
    public TechSolutions(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre de la empresa es obligatorio."
            );
        }

        this.nombre = nombre;
        this.catalogoServicios = new CatalogoServicios();
    }

    public String getNombre() {
        return nombre;
    }

    public CatalogoServicios getCatalogoServicios() {
        return catalogoServicios;
    }
}
