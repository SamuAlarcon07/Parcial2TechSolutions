package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Representa el servicio de instalación de redes
 * ofrecido por TechSolutions.
 */
public class InstalacionRedes extends ServicioTecnologico {

    public InstalacionRedes() {
        super(
                "SER-003",
                "Instalación de redes",
                "Servicio de instalación y configuración de redes informáticas.",
                new BigDecimal("180000")
        );
    }
}
