package co.edu.uniquindio.parcial2techsolutions.model.factory;

import co.edu.uniquindio.parcial2techsolutions.model.ServicioTecnologico;

/**
 * Define el método para crear servicios tecnológicos.
 * Las fábricas concretas decidirán qué servicio construir.
 */
public abstract class ServicioFactory {

    public abstract ServicioTecnologico crearServicio();
}