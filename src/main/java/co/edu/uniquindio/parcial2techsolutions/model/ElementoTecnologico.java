package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Define las operaciones comunes de los elementos
 * tecnológicos que ofrece TechSolutions.
 *
 * Esta interfaz permite tratar servicios individuales,
 * paquetes y servicios personalizados de forma uniforme.
 */
public interface ElementoTecnologico {

    String getCodigo();

    String getNombre();

    String getDescripcion();

    BigDecimal getCosto();
}
