package co.edu.uniquindio.parcial2techsolutions.model;

/**
 * Representa la empresa TechSolutions y centraliza
 * el acceso a la información principal del sistema.
 */
public class TechSolutions {

    private final String nombre;
    private final CatalogoServicios catalogoServicios;

    /**
     * Construye la empresa con su catálogo de servicios.
     *
     * @param nombre nombre de la empresa
     */
    public TechSolutions(String nombre) {
        if (nombre == null || nombre.isBlank()) {
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
