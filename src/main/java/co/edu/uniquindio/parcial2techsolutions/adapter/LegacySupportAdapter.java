
package co.edu.uniquindio.parcial2techsolutions.adapter;

import co.edu.uniquindio.parcial2techsolutions.model.LegacySupport;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

import java.util.Locale;

/**
 * Adapta la interfaz de LegacySupport a las necesidades
 * de consulta de técnicos de TechSolutions.
 */
public class LegacySupportAdapter {

    private final LegacySupport legacySupport;

    public LegacySupportAdapter(LegacySupport legacySupport) {
        if (legacySupport == null) {
            throw new IllegalArgumentException(
                    "LegacySupport no puede ser nulo.");
        }

        this.legacySupport = legacySupport;
    }

    /**
     * Convierte el nombre de la especialidad en un código numérico,
     * consulta el sistema externo y estructura su respuesta.
     */
    public RespuestaTecnico consultarTecnico(String especialidad) {

        int codigoEspecialidad = obtenerCodigoEspecialidad(especialidad);

        String respuesta = legacySupport.consultarTecnico(codigoEspecialidad);

        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La respuesta de LegacySupport está vacía.");
        }

        String[] partes = respuesta.split("\\|", -1);

        if (partes.length != 3) {
            throw new IllegalArgumentException(
                    "La respuesta de LegacySupport tiene un formato inválido.");
        }

        String codigoTecnico = partes[0].trim();
        String estado = partes[1].trim();
        int costoDesplazamiento;

        try {
            costoDesplazamiento = Integer.parseInt(partes[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "El costo de desplazamiento no es válido.", e);
        }

        boolean disponible;

        if ("DISPONIBLE".equalsIgnoreCase(estado)) {
            disponible = true;
        } else if ("NO_DISPONIBLE".equalsIgnoreCase(estado)) {
            disponible = false;
        } else {
            throw new IllegalArgumentException(
                    "El estado del técnico no es válido.");
        }

        return new RespuestaTecnico(
                codigoTecnico,
                disponible,
                costoDesplazamiento
        );
    }

    /**
     * Traduce los nombres utilizados por TechSolutions
     * a códigos numéricos para el proveedor externo.
     */
    private int obtenerCodigoEspecialidad(String especialidad) {

        if (especialidad == null || especialidad.isBlank()) {
            throw new IllegalArgumentException(
                    "Debes indicar una especialidad.");
        }

        String nombre = especialidad.trim().toLowerCase(Locale.ROOT);

        return switch (nombre) {
            case "mantenimiento de equipos",
                 "mantenimiento preventivo",
                 "mantenimiento correctivo" -> 1;

            case "redes y conectividad",
                 "instalación de redes",
                 "instalacion de redes" -> 2;

            case "recuperación de información",
                 "recuperacion de informacion" -> 3;

            case "instalación de software",
                 "instalacion de software" -> 4;

            default -> throw new IllegalArgumentException(
                    "No existe un código para la especialidad: "
                            + especialidad);
        };
    }
}
