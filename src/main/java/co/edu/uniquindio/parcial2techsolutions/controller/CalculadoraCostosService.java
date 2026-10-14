
package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.ElementoTecnologico;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

import java.math.BigDecimal;

/**
 * Centraliza el cálculo de costos de los servicios,
 * paquetes, complementos y desplazamientos.
 */
public class CalculadoraCostosService {

    /**
     * Calcula el costo de un elemento tecnológico.
     */
    public BigDecimal calcularCosto(ElementoTecnologico elemento) {

        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento tecnológico no puede ser nulo.");
        }

        return elemento.getCosto();
    }

    /**
     * Calcula el costo de los elementos de una solicitud,
     * sin incluir desplazamiento.
     */
    public BigDecimal calcularCostoSolicitud(
            SolicitudServicio solicitud) {

        if (solicitud == null) {
            throw new IllegalArgumentException(
                    "La solicitud no puede ser nula.");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (ElementoTecnologico elemento : solicitud.getElementos()) {
            total = total.add(elemento.getCosto());
        }

        return total;
    }

    /**
     * Calcula el costo final de una solicitud.
     * El desplazamiento se suma una sola vez cuando
     * la solicitud requiere atención a domicilio.
     */
    public BigDecimal calcularCostoTotal(
            SolicitudServicio solicitud,
            RespuestaTecnico respuestaTecnico) {

        BigDecimal total = calcularCostoSolicitud(solicitud);

        if (!solicitud.requiereAtencionDomicilio()) {
            return total;
        }

        if (respuestaTecnico == null) {
            throw new IllegalArgumentException(
                    "Se necesita la respuesta del técnico "
                            + "para calcular el desplazamiento.");
        }

        if (!respuestaTecnico.isDisponible()) {
            throw new IllegalStateException(
                    "No se puede calcular el costo final: "
                            + "no hay un técnico disponible.");
        }

        BigDecimal costoDesplazamiento = BigDecimal.valueOf(
                respuestaTecnico.getCostoDesplazamiento()
        );

        return total.add(costoDesplazamiento);
    }
}
