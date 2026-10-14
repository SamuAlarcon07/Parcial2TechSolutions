package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona el registro y la consulta de solicitudes de servicio.
 */
public class GestionSolicitudesService {

    private final List<SolicitudServicio> solicitudes;

    public GestionSolicitudesService() {
        this.solicitudes = new ArrayList<>();
    }

    /**
     * Registra una solicitud si su código no está repetido.
     */
    public void registrarSolicitud(SolicitudServicio solicitud) {

        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "La solicitud no puede ser nula.");
        }

        if (buscarPorCodigo(solicitud.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe una solicitud con ese código.");
        }

        solicitudes.add(solicitud);
    }

    /**
     * Busca una solicitud mediante su código.
     */
    public SolicitudServicio buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        for (SolicitudServicio solicitud : solicitudes) {
            if (solicitud.getCodigo().equals(codigo)) {
                return solicitud;
            }
        }

        return null;
    }

    /**
     * Devuelve las solicitudes registradas sin permitir
     * modificaciones directas a la lista interna.
     */
    public List<SolicitudServicio> listarSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    /**
     * Cancela una solicitud registrada.
     */
    public void cancelarSolicitud(String codigo) {

        SolicitudServicio solicitud = buscarPorCodigo(codigo);

        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "No existe una solicitud con ese código.");
        }

        solicitud.cambiarEstado(
                co.edu.uniquindio.parcial2techsolutions.model.EstadoSolicitud.CANCELADA
        );
    }
}
