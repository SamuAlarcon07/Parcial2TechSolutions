package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.adapter.LegacySupportAdapter;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

/**
 * Servicio encargado de consultar la disponibilidad de técnicos
 * sin exponer los detalles del sistema externo LegacySupport.
 */
public class ConsultaTecnicoService {

    private final LegacySupportAdapter adapter;

    /**
     * Recibe el adaptador que conecta con el sistema externo.
     *
     * @param adapter adaptador de LegacySupport
     */
    public ConsultaTecnicoService(LegacySupportAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Consulta un técnico para la especialidad indicada.
     *
     * @param especialidad nombre de la especialidad requerida
     * @return respuesta con el código, la disponibilidad
     *         y el costo de desplazamiento del técnico
     */
    public RespuestaTecnico consultarTecnico(String especialidad) {
        return adapter.consultarTecnico(especialidad);
    }
}
