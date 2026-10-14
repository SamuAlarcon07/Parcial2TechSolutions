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

    /**
     * Obtiene el elemento envuelto por este decorador.
     */
    public ElementoTecnologico getElementoDecorado() {
        return elemento;
    }

    /**
     * Comprueba si este decorador o alguno de los elementos
     * que envuelve contiene atención a domicilio.
     */
    public boolean contieneAtencionDomicilio() {
        if (this instanceof AtencionDomicilio) {
            return true;
        }

        if (elemento instanceof ServicioDecorador) {
            ServicioDecorador decorador =
                    (ServicioDecorador) elemento;

            return decorador.contieneAtencionDomicilio();
        }

        if (elemento instanceof PaqueteTecnologico) {
            PaqueteTecnologico paquete =
                    (PaqueteTecnologico) elemento;

            return paquete.contieneAtencionDomicilio();
        }

        return false;
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
