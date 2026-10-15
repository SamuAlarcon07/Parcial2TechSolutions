package co.edu.uniquindio.parcial2techsolutions.adapter;

import co.edu.uniquindio.parcial2techsolutions.model.LegacySupport;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

/**
 * Convierte la respuesta textual de LegacySupport
 * en un objeto que puede utilizar TechSolutions.
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

    public RespuestaTecnico consultarTecnico() {

        String respuesta = legacySupport.consultarTecnico();

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
}
