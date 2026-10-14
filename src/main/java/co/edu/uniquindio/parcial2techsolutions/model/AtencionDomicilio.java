package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Añade el complemento de atención a domicilio
 * a un servicio o paquete tecnológico.
 */
public class AtencionDomicilio extends ServicioDecorador {

    private static final BigDecimal COSTO_ADICIONAL =
            new BigDecimal("25000");

    public AtencionDomicilio(ElementoTecnologico elemento) {
        super(elemento);
    }

    @Override
    public BigDecimal getCosto() {
        return super.getCosto().add(COSTO_ADICIONAL);
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion()
                + " - Atención a domicilio";
    }
}