package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Añade el complemento de atención prioritaria
 * a un servicio o paquete tecnológico.
 */
public class AtencionPrioritaria extends ServicioDecorador {

    private static final BigDecimal COSTO_ADICIONAL =
            new BigDecimal("35000");

    public AtencionPrioritaria(ElementoTecnologico elemento) {
        super(elemento);
    }

    @Override
    public BigDecimal getCosto() {
        return super.getCosto().add(COSTO_ADICIONAL);
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion()
                + " - Atención prioritaria";
    }
}