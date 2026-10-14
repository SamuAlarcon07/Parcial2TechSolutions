package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;

/**
 * Clase base para los complementos de los servicios.
 * Delega los datos del elemento original y permite
 * modificar su comportamiento, como el costo.
 */
public abstract class ServicioDecorador implements ElementoTecnologico {

    protected final ElementoTecnologico elemento;

    public ServicioDecorador(ElementoTecnologico elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento tecnológico no puede ser nulo."
            );
        }

        this.elemento = elemento;
    }

    @Override
    public String getCodigo() {
        return elemento.getCodigo();
    }

    @Override
    public String getNombre() {
        return elemento.getNombre();
    }

    @Override
    public String getDescripcion() {
        return elemento.getDescripcion();
    }

    @Override
    public BigDecimal getCosto() {
        return elemento.getCosto();
    }
}