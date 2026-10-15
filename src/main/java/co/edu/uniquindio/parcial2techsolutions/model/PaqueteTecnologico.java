
package co.edu.uniquindio.parcial2techsolutions.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un paquete tecnológico que puede contener
 * servicios individuales u otros paquetes.
 */
public class PaqueteTecnologico implements ElementoTecnologico {

    private final String codigo;
    private final String nombre;
    private final String descripcion;
    private final List<ElementoTecnologico> elementos;

    public PaqueteTecnologico(
            String codigo,
            String nombre,
            String descripcion) {

        if (codigo == null || codigo.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código y el nombre son obligatorios."
            );
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.elementos = new ArrayList<>();
    }

    /**
     * Agrega un servicio o paquete, evitando ciclos.
     */
    public void agregarElemento(ElementoTecnologico elemento) {

        if (elemento == null) {
            throw new IllegalArgumentException(
                    "El elemento no puede ser nulo."
            );
        }

        if (elemento == this) {
            throw new IllegalArgumentException(
                    "Un paquete no puede contenerse a sí mismo."
            );
        }

        if (contieneEstePaquete(elemento)) {
            throw new IllegalArgumentException(
                    "La operación produciría un ciclo entre paquetes."
            );
        }

        elementos.add(elemento);
    }

    /**
     * Comprueba si agregar el elemento provocaría un ciclo.
     * También revisa paquetes envueltos en decoradores.
     */
    private boolean contieneEstePaquete(ElementoTecnologico elemento) {

        if (elemento == this) {
            return true;
        }

        if (elemento instanceof PaqueteTecnologico) {
            return ((PaqueteTecnologico) elemento)
                    .contienePaquete(this);
        }

        if (elemento instanceof ServicioDecorador) {
            ServicioDecorador decorador =
                    (ServicioDecorador) elemento;

            return contieneEstePaquete(
                    decorador.getElementoDecorado()
            );
        }

        return false;
    }

    /**
     * Comprueba recursivamente si este paquete contiene
     * el paquete indicado, incluso dentro de decoradores.
     */
    private boolean contienePaquete(PaqueteTecnologico objetivo) {

        for (ElementoTecnologico elemento : elementos) {

            if (elemento == objetivo) {
                return true;
            }

            if (elemento instanceof PaqueteTecnologico) {
                if (((PaqueteTecnologico) elemento)
                        .contienePaquete(objetivo)) {
                    return true;
                }
            }

            if (elemento instanceof ServicioDecorador) {
                ServicioDecorador decorador =
                        (ServicioDecorador) elemento;

                if (contienePaqueteEnvuelto(decorador, objetivo)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Busca un paquete dentro de una cadena de decoradores.
     */
    private boolean contienePaqueteEnvuelto(
            ServicioDecorador decorador,
            PaqueteTecnologico objetivo) {

        ElementoTecnologico decorado =
                decorador.getElementoDecorado();

        if (decorado == objetivo) {
            return true;
        }

        if (decorado instanceof PaqueteTecnologico) {
            return ((PaqueteTecnologico) decorado)
                    .contienePaquete(objetivo);
        }

        if (decorado instanceof ServicioDecorador) {
            return contienePaqueteEnvuelto(
                    (ServicioDecorador) decorado,
                    objetivo
            );
        }

        return false;
    }

    /**
     * Comprueba si este paquete contiene atención a domicilio,
     * incluso dentro de decoradores o paquetes anidados.
     */
    public boolean contieneAtencionDomicilio() {

        for (ElementoTecnologico elemento : elementos) {

            if (elemento instanceof ServicioDecorador) {
                ServicioDecorador decorador =
                        (ServicioDecorador) elemento;

                if (decorador.contieneAtencionDomicilio()) {
                    return true;
                }
            }

            if (elemento instanceof PaqueteTecnologico) {
                PaqueteTecnologico paquete =
                        (PaqueteTecnologico) elemento;

                if (paquete.contieneAtencionDomicilio()) {
                    return true;
                }
            }
        }

        return false;
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

    /**
     * Calcula el costo total de todos los elementos del paquete.
     */
    @Override
    public BigDecimal getCosto() {

        BigDecimal total = BigDecimal.ZERO;

        for (ElementoTecnologico elemento : elementos) {
            total = total.add(elemento.getCosto());
        }

        return total;
    }

    /**
     * Devuelve los elementos sin permitir modificaciones directas.
     */
    public List<ElementoTecnologico> getElementos() {
        return Collections.unmodifiableList(elementos);
    }
}
