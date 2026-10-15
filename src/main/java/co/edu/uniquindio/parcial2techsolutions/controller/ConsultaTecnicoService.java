package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.adapter.LegacySupportAdapter;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

/**
 * Gestiona las consultas de disponibilidad de técnicos.
 */
public class ConsultaTecnicoService {

    private final LegacySupportAdapter adapter;

    public ConsultaTecnicoService(LegacySupportAdapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException(
                    "El adaptador no puede ser nulo.");
        }

        this.adapter = adapter;
    }

    /**
     * Consulta la disponibilidad y los datos del técnico.
     */
    public RespuestaTecnico consultarTecnico() {
        return adapter.consultarTecnico();
    }
}
