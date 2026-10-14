package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Representa el servicio de recuperación de información
 * ofrecido por TechSolutions.
 */
public class RecuperacionInformacion extends ServicioTecnologico {

    public RecuperacionInformacion() {
        super(
                "SER-004",
                "Recuperación de información",
                "Servicio de recuperación de información y archivos digitales.",
                new BigDecimal("150000")
        );
    }
}
