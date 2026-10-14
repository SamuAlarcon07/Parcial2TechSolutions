package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.ElementoTecnologico;
import co.edu.uniquindio.parcial2techsolutions.model.PaqueteTecnologico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona la creación y consulta de paquetes tecnológicos.
 */
public class GestionPaquetesService {

    private final List<PaqueteTecnologico> paquetes;

    public GestionPaquetesService() {
        this.paquetes = new ArrayList<>();
    }

    /**
     * Registra un paquete y evita códigos duplicados.
     */
    public void registrarPaquete(PaqueteTecnologico paquete) {

        if (paquete == null) {
            throw new IllegalArgumentException(
                    "El paquete no puede ser nulo.");
        }

        if (buscarPorCodigo(paquete.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un paquete con ese código.");
        }

        paquetes.add(paquete);
    }

    /**
     * Busca un paquete por su código.
     */
    public PaqueteTecnologico buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        for (PaqueteTecnologico paquete : paquetes) {
            if (paquete.getCodigo().equals(codigo)) {
                return paquete;
            }
        }

        return null;
    }

    /**
     * Devuelve los paquetes registrados.
     */
    public List<PaqueteTecnologico> listarPaquetes() {
        return Collections.unmodifiableList(paquetes);
    }

    /**
     * Agrega un elemento a un paquete registrado.
     */
    public void agregarElemento(
            String codigoPaquete,
            ElementoTecnologico elemento) {

        PaqueteTecnologico paquete = buscarPorCodigo(codigoPaquete);

        if (paquete == null) {
            throw new IllegalArgumentException(
                    "No existe un paquete con ese código.");
        }

        paquete.agregarElemento(elemento);
    }
}
