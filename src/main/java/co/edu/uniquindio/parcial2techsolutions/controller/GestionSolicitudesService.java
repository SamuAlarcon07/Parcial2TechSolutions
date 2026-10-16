package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.EstadoSolicitud;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.parcial2techsolutions.model.ElementoTecnologico;
import co.edu.uniquindio.parcial2techsolutions.model.PaqueteTecnologico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona el registro, la consulta, la cancelación
 * y la confirmación de solicitudes de servicio.
 */
public class GestionSolicitudesService {

    private final List<SolicitudServicio> solicitudes;

    public GestionSolicitudesService() {
        this.solicitudes = new ArrayList<>();
    }

    /**
     * Registra una solicitud si sus datos son válidos
     * y su código no está repetido.
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
     * Devuelve una vista no modificable de las solicitudes.
     */
    public List<SolicitudServicio> listarSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    /**
     * Cancela una solicitud existente.
     */
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
     * Confirma una solicitud y registra su costo final.
     *
     * Si requiere atención a domicilio, consulta la disponibilidad
     * del técnico para la especialidad del primer servicio
     * incluido en la solicitud.
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

            String especialidad = obtenerEspecialidad(solicitud);

            respuestaTecnico =
                    consultaTecnicoService.consultarTecnico(especialidad);

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

    /**
     * Obtiene el nombre del primer servicio real de la solicitud.
     * Si el elemento es un paquete, busca el primer servicio incluido.
     */
    private String obtenerEspecialidad(SolicitudServicio solicitud) {

        for (ElementoTecnologico elemento : solicitud.getElementos()) {

            if (elemento instanceof PaqueteTecnologico paquete) {

                for (ElementoTecnologico incluido : paquete.getElementos()) {
                    return incluido.getNombre();
                }

            } else if (elemento.getNombre() != null
                    && !elemento.getNombre().isBlank()) {

                return elemento.getNombre();
            }
        }

        throw new IllegalStateException(
                "No se encontró un servicio para determinar "
                        + "la especialidad del técnico.");
    }
}
