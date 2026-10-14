
package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.EstadoSolicitud;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona el registro, la consulta y la confirmación
 * de solicitudes de servicio.
 */
public class GestionSolicitudesService {

    private final List<SolicitudServicio> solicitudes;

    public GestionSolicitudesService() {
        this.solicitudes = new ArrayList<>();
    }

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

    public List<SolicitudServicio> listarSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    public void cancelarSolicitud(String codigo) {

        SolicitudServicio solicitud = buscarPorCodigo(codigo);

        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "No existe una solicitud con ese código.");
        }

        if (solicitud.getEstado() == EstadoSolicitud.CANCELADA) {
            throw new IllegalStateException(
                    "La solicitud ya está cancelada.");
        }

        solicitud.cambiarEstado(EstadoSolicitud.CANCELADA);
    }

    /**
     * Confirma la solicitud y guarda su costo total.
     * Si requiere atención a domicilio, consulta primero
     * la disponibilidad del técnico.
     */
    public void confirmarSolicitud(
            String codigo,
            ConsultaTecnicoService consultaTecnicoService,
            CalculadoraCostosService calculadoraCostosService) {

        SolicitudServicio solicitud = buscarPorCodigo(codigo);

        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "No existe una solicitud con ese código.");
        }

        if (solicitud.getEstado() == EstadoSolicitud.CANCELADA) {
            throw new IllegalStateException(
                    "No se puede confirmar una solicitud cancelada.");
        }

        if (solicitud.getEstado() == EstadoSolicitud.CONFIRMADA) {
            throw new IllegalStateException(
                    "La solicitud ya está confirmada.");
        }

        if (calculadoraCostosService == null) {
            throw new IllegalArgumentException(
                    "La calculadora de costos es obligatoria.");
        }

        RespuestaTecnico respuestaTecnico = null;

        if (solicitud.requiereAtencionDomicilio()) {

            if (consultaTecnicoService == null) {
                throw new IllegalArgumentException(
                        "Se necesita consultar un técnico "
                                + "para la atención a domicilio.");
            }

            respuestaTecnico =
                    consultaTecnicoService.consultarTecnico();

            if (respuestaTecnico == null
                    || !respuestaTecnico.isDisponible()) {
                throw new IllegalStateException(
                        "No se puede confirmar la solicitud: "
                                + "no hay técnicos disponibles.");
            }
        }

        BigDecimal costoFinal =
                calculadoraCostosService.calcularCostoTotal(
                        solicitud,
                        respuestaTecnico
                );

        solicitud.establecerCostoTotal(costoFinal);
        solicitud.cambiarEstado(EstadoSolicitud.CONFIRMADA);
    }
}
