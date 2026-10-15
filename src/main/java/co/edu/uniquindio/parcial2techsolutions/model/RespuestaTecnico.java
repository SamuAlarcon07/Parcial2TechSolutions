package co.edu.uniquindio.parcial2techsolutions.model;

/**
 * Representa la respuesta normalizada de la consulta de un técnico.
 */
public class RespuestaTecnico {

    private final String codigoTecnico;
    private final boolean disponible;
    private final int costoDesplazamiento;

    public RespuestaTecnico(
            String codigoTecnico,
            boolean disponible,
            int costoDesplazamiento) {

        if (codigoTecnico == null || codigoTecnico.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código del técnico es obligatorio.");
        }

        if (costoDesplazamiento < 0) {
            throw new IllegalArgumentException(
                    "El costo de desplazamiento no puede ser negativo.");
        }

        this.codigoTecnico = codigoTecnico;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }

    public String getCodigoTecnico() {
        return codigoTecnico;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public int getCostoDesplazamiento() {
        return costoDesplazamiento;
    }
}
