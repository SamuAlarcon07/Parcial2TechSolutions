package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Añade soporte técnico durante 30 días
 * a un servicio o paquete tecnológico.
 */
public class SoporteTreintaDias extends ServicioDecorador {

    private static final BigDecimal COSTO_ADICIONAL =
            new BigDecimal("50000");

    public SoporteTreintaDias(ElementoTecnologico elemento) {
        super(elemento);
    }

    @Override
    public BigDecimal getCosto() {
        return super.getCosto().add(COSTO_ADICIONAL);
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion()
                + " - Soporte técnico durante 30 días";
    }
}