package co.edu.uniquindio.parcial2techsolutions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Administra el catálogo de servicios tecnológicos
 * disponibles en TechSolutions.
 */
public class CatalogoServicios {

    private final List<ElementoTecnologico> servicios;

    public CatalogoServicios() {
        servicios = new ArrayList<>();

        cargarServiciosIniciales();
    }

    /**
     * Registra los cuatro servicios iniciales del sistema.
     */
    private void cargarServiciosIniciales() {
        servicios.add(new MantenimientoPreventivo());
        servicios.add(new MantenimientoCorrectivo());
        servicios.add(new InstalacionRedes());
        servicios.add(new RecuperacionInformacion());
    }

    /**
     * Agrega un elemento al catálogo si su código no existe.
     *
     * @param servicio elemento tecnológico que se desea registrar
     */
    public void agregarServicio(ElementoTecnologico servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio no puede ser nulo."
            );
        }

        if (buscarPorCodigo(servicio.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un servicio con ese código."
            );
        }

        servicios.add(servicio);
    }

    /**
     * Busca un servicio por su código.
     *
     * @param codigo código del elemento que se desea consultar
     * @return el elemento encontrado o null si no existe
     */
    public ElementoTecnologico buscarPorCodigo(String codigo) {
        for (ElementoTecnologico servicio : servicios) {
            if (servicio.getCodigo().equals(codigo)) {
                return servicio;
            }
        }

        return null;
    }

    /**
     * Obtiene una vista no modificable del catálogo.
     *
     * @return lista de elementos registrados
     */
    public List<ElementoTecnologico> getServicios() {
        return Collections.unmodifiableList(servicios);
    }
}
