package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Representa el servicio de mantenimiento preventivo
 * ofrecido por TechSolutions.
 */
public class MantenimientoPreventivo extends ServicioTecnologico {

    public MantenimientoPreventivo() {
        super(
                "SER-001",
                "Mantenimiento preventivo",
                "Servicio de mantenimiento preventivo para equipos de cómputo.",
                new BigDecimal("80000")
        );
    }
}
