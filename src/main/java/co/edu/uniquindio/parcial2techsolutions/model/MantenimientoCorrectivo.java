package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Representa el servicio de mantenimiento correctivo
 * ofrecido por TechSolutions.
 */
public class MantenimientoCorrectivo extends ServicioTecnologico {

    public MantenimientoCorrectivo() {
        super(
                "SER-002",
                "Mantenimiento correctivo",
                "Servicio de diagnóstico y reparación de fallas en equipos de cómputo.",
                new BigDecimal("120000")
        );
    }
}
