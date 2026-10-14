package co.edu.uniquindio.parcial2techsolutions.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una solicitud de servicios realizada por un cliente.
 */
public class SolicitudServicio {

    private final String codigo;
    private final LocalDate fechaSolicitud;
    private final Cliente cliente;
    private final List<ElementoTecnologico> elementos;
    private LocalDate fechaProgramada;
    private String observaciones;
    private Prioridad prioridad;
    private EstadoSolicitud estado;

    public SolicitudServicio(
            String codigo,
            LocalDate fechaSolicitud,
            Cliente cliente,
            List<ElementoTecnologico> elementos,
            LocalDate fechaProgramada,
            String observaciones,
            Prioridad prioridad) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la solicitud es obligatorio.");
        }

        if (fechaSolicitud == null) {
            throw new IllegalArgumentException(
                    "La fecha de solicitud es obligatoria.");
        }

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente es obligatorio.");
        }

        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException(
                    "La solicitud debe tener al menos un elemento.");
        }

        if (elementos.contains(null)) {
            throw new IllegalArgumentException(
                    "La solicitud no puede contener elementos nulos.");
        }

        this.codigo = codigo;
        this.fechaSolicitud = fechaSolicitud;
        this.cliente = cliente;
        this.elementos = new ArrayList<>(elementos);
        this.fechaProgramada = fechaProgramada;
        this.observaciones = observaciones;
        this.prioridad = prioridad;
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ElementoTecnologico> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void programarFecha(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public void actualizarObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void cambiarPrioridad(Prioridad prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException(
                    "La prioridad no puede ser nula.");
        }

        this.prioridad = prioridad;
    }

    public void cambiarEstado(EstadoSolicitud estado) {
        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado no puede ser nulo.");
        }

        this.estado = estado;
    }
}
