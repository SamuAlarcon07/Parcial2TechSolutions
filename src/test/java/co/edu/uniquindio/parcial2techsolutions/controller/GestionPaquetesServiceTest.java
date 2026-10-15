package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoPreventivo;
import co.edu.uniquindio.parcial2techsolutions.model.PaqueteTecnologico;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestionPaquetesServiceTest {

    @Test
    void debeRegistrarYBuscarUnPaquete() {

        GestionPaquetesService gestion =
                new GestionPaquetesService();

        PaqueteTecnologico paquete = new PaqueteTecnologico(
                "PKG-TEST-001",
                "Paquete de prueba",
                "Paquete para pruebas unitarias."
        );

        gestion.registrarPaquete(paquete);

        assertEquals(
                paquete,
                gestion.buscarPorCodigo("PKG-TEST-001")
        );

        assertEquals(1, gestion.listarPaquetes().size());
    }

    @Test
    void noDebeRegistrarDosPaquetesConElMismoCodigo() {

        GestionPaquetesService gestion =
                new GestionPaquetesService();

        PaqueteTecnologico primero = new PaqueteTecnologico(
                "PKG-TEST-002",
                "Primer paquete",
                "Descripción del primer paquete."
        );

        PaqueteTecnologico segundo = new PaqueteTecnologico(
                "PKG-TEST-002",
                "Segundo paquete",
                "Descripción del segundo paquete."
        );

        gestion.registrarPaquete(primero);

        assertThrows(
                IllegalArgumentException.class,
                () -> gestion.registrarPaquete(segundo)
        );

        assertEquals(1, gestion.listarPaquetes().size());
    }

    @Test
    void debeAgregarUnServicioAlPaqueteRegistrado() {

        GestionPaquetesService gestion =
                new GestionPaquetesService();

        PaqueteTecnologico paquete = new PaqueteTecnologico(
                "PKG-TEST-003",
                "Paquete preventivo",
                "Incluye mantenimiento preventivo."
        );

        gestion.registrarPaquete(paquete);

        gestion.agregarElemento(
                "PKG-TEST-003",
                new MantenimientoPreventivo()
        );

        assertEquals(
                new BigDecimal("80000"),
                paquete.getCosto()
        );

        assertEquals(1, paquete.getElementos().size());
    }

    @Test
    void debeRechazarLaAdicionAUnPaqueteInexistente() {

        GestionPaquetesService gestion =
                new GestionPaquetesService();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestion.agregarElemento(
                        "PKG-INEXISTENTE",
                        new MantenimientoPreventivo()
                )
        );
    }


    @Test
    void noDebeRegistrarUnPaqueteNulo() {
        GestionPaquetesService gestion = new GestionPaquetesService();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestion.registrarPaquete(null)
        );

        assertEquals(0, gestion.listarPaquetes().size());
    }

    @Test
    void debeDevolverNullCuandoElPaqueteNoExiste() {
        GestionPaquetesService gestion = new GestionPaquetesService();

        assertEquals(
                null,
                gestion.buscarPorCodigo("PKG-INEXISTENTE")
        );

        assertEquals(null, gestion.buscarPorCodigo(null));
    }

    @Test
    void noDebePermitirModificarDirectamenteLaListaDePaquetes() {
        GestionPaquetesService gestion = new GestionPaquetesService();

        PaqueteTecnologico paquete = new PaqueteTecnologico(
                "PKG-TEST-004",
                "Paquete de prueba",
                "Prueba de lista protegida."
        );

        gestion.registrarPaquete(paquete);

        assertThrows(
                UnsupportedOperationException.class,
                () -> gestion.listarPaquetes().clear()
        );

        assertEquals(1, gestion.listarPaquetes().size());
    }

}
