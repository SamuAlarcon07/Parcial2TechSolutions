package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.ElementoTecnologico;

import java.math.BigDecimal;

/**
 * Centraliza el cálculo de los costos de los elementos tecnológicos.
 */
public class CalculadoraCostosService {

    /**
     * Calcula el costo total de un servicio, paquete o elemento decorado.
     */
    public BigDecimal calcularCosto(ElementoTecnologico elemento) {

        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento tecnológico no puede ser nulo.");
        }

        return elemento.getCosto();
    }
}
