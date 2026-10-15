package co.edu.uniquindio.parcial2techsolutions.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Permite construir solicitudes de servicio paso a paso.
 */
public class SolicitudServicioBuilder {

    private String codigo;
    private LocalDate fechaSolicitud;
    private Cliente cliente;
    private final List<ElementoTecnologico> elementos;
    private LocalDate fechaProgramada;
    private String observaciones;
    private Prioridad prioridad;

    public SolicitudServicioBuilder() {
        this.elementos = new ArrayList<>();
        this.fechaSolicitud = LocalDate.now();
        this.prioridad = Prioridad.MEDIA;
    }

    public SolicitudServicioBuilder codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public SolicitudServicioBuilder fechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        return this;
    }

    public SolicitudServicioBuilder cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public SolicitudServicioBuilder agregarElemento(
            ElementoTecnologico elemento) {

        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento tecnológico no puede ser nulo.");
        }

        this.elementos.add(elemento);
        return this;
    }

    public SolicitudServicioBuilder fechaProgramada(
            LocalDate fechaProgramada) {

        this.fechaProgramada = fechaProgramada;
        return this;
    }

    public SolicitudServicioBuilder observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public SolicitudServicioBuilder prioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public SolicitudServicio construir() {
        return new SolicitudServicio(
                codigo,
                fechaSolicitud,
                cliente,
                elementos,
                fechaProgramada,
                observaciones,
                prioridad
        );
    }
}
