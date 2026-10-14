
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

    public PaqueteTecnologico(String codigo, String nombre,
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

        if (elemento instanceof PaqueteTecnologico
                && ((PaqueteTecnologico) elemento).contienePaquete(this)) {
            throw new IllegalArgumentException(
                    "La operación produciría un ciclo entre paquetes."
            );
        }

        elementos.add(elemento);
    }

    /**
     * Comprueba recursivamente si este paquete contiene
     * el paquete indicado.
     */
    private boolean contienePaquete(PaqueteTecnologico objetivo) {
        for (ElementoTecnologico elemento : elementos) {
            if (elemento == objetivo) {
                return true;
            }

            if (elemento instanceof PaqueteTecnologico
                    && ((PaqueteTecnologico) elemento)
                    .contienePaquete(objetivo)) {
                return true;
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

    @Override
    public BigDecimal getCosto() {
        BigDecimal total = BigDecimal.ZERO;

        for (ElementoTecnologico elemento : elementos) {
            total = total.add(elemento.getCosto());
        }

        return total;
    }

    public List<ElementoTecnologico> getElementos() {
        return Collections.unmodifiableList(elementos);
    }
}