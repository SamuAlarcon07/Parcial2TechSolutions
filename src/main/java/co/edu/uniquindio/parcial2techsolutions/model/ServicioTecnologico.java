package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Clase abstracta que representa un servicio tecnológico
 * ofrecido por TechSolutions.
 *
 * Define los atributos y comportamientos comunes
 * de los diferentes tipos de servicios.
 */
public abstract class ServicioTecnologico implements ElementoTecnologico {

    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal costoBase;

    /**
     * Construye un servicio tecnológico con sus datos básicos.
     *
     * @param codigo identificador único del servicio
     * @param nombre nombre del servicio
     * @param descripcion descripción del servicio
     * @param costoBase costo base del servicio
     */
    public ServicioTecnologico(String codigo, String nombre,
                               String descripcion, BigDecimal costoBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getCostoBase() {
        return costoBase;
    }

    @Override
    public BigDecimal getCosto() {
        return costoBase;
    }
}