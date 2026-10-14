package co.edu.uniquindio.parcial2techsolutions.model;

/**
 * Representa a un cliente registrado en TechSolutions.
 */
public class Cliente {

    private final String documento;
    private final String nombreCompleto;
    private final String correoElectronico;

    /**
     * Construye un cliente con sus datos básicos.
     *
     * @param documento identificación del cliente
     * @param nombreCompleto nombre completo del cliente
     * @param correoElectronico correo electrónico del cliente
     */
    public Cliente(String documento, String nombreCompleto,
                   String correoElectronico) {

        if (documento == null || documento.isEmpty()) {
            throw new IllegalArgumentException(
                    "El documento del cliente es obligatorio."
            );
        }

        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio."
            );
        }

        if (correoElectronico == null || correoElectronico.isEmpty()) {
            throw new IllegalArgumentException(
                    "El correo electrónico del cliente es obligatorio."
            );
        }

        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }
}
