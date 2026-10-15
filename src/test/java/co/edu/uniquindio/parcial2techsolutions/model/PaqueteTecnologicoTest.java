package co.edu.uniquindio.parcial2techsolutions.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PaqueteTecnologicoTest {

    @Test
    void debeSumarCorrectamenteLosServiciosDelPaquete() {

        PaqueteTecnologico paquete = new PaqueteTecnologico(
                "PKG-001",
                "Paquete básico",
                "Paquete de mantenimiento."
        );

        paquete.agregarElemento(new MantenimientoPreventivo());
        paquete.agregarElemento(new MantenimientoCorrectivo());

        assertEquals(
                new BigDecimal("200000"),
                paquete.getCosto()
        );
    }

    @Test
    void debeCalcularElCostoDePaquetesAnidados() {

        PaqueteTecnologico paqueteInterno = new PaqueteTecnologico(
                "PKG-002",
                "Paquete de mantenimiento",
                "Incluye mantenimiento preventivo."
        );

        paqueteInterno.agregarElemento(
                new MantenimientoPreventivo()
        );

        PaqueteTecnologico paqueteExterno = new PaqueteTecnologico(
                "PKG-003",
                "Paquete empresarial",
                "Incluye mantenimiento y recuperación."
        );

        paqueteExterno.agregarElemento(paqueteInterno);
        paqueteExterno.agregarElemento(
                new RecuperacionInformacion()
        );

        assertEquals(
                new BigDecimal("230000"),
                paqueteExterno.getCosto()
        );
    }

    @Test
    void noDebePermitirCiclosEntrePaquetes() {

        PaqueteTecnologico paqueteA = new PaqueteTecnologico(
                "PKG-004",
                "Paquete A",
                "Primer paquete."
        );

        PaqueteTecnologico paqueteB = new PaqueteTecnologico(
                "PKG-005",
                "Paquete B",
                "Segundo paquete."
        );

        paqueteA.agregarElemento(paqueteB);

        assertThrows(
                IllegalArgumentException.class,
                () -> paqueteB.agregarElemento(paqueteA)
        );
    }

    @Test
    void debeDetectarAtencionDomicilioEnUnPaqueteAnidado() {

        PaqueteTecnologico paqueteInterno = new PaqueteTecnologico(
                "PKG-006",
                "Paquete interno",
                "Incluye atención a domicilio."
        );

        paqueteInterno.agregarElemento(
                new AtencionDomicilio(
                        new MantenimientoCorrectivo()
                )
        );

        PaqueteTecnologico paqueteExterno = new PaqueteTecnologico(
                "PKG-007",
                "Paquete externo",
                "Contiene el paquete interno."
        );

        paqueteExterno.agregarElemento(paqueteInterno);

        assertTrue(
                paqueteExterno.contieneAtencionDomicilio()
        );
    }

    @Test
    void noDebeDetectarAtencionDomicilioSiNoExiste() {

        PaqueteTecnologico paquete = new PaqueteTecnologico(
                "PKG-008",
                "Paquete sin domicilio",
                "Solo contiene mantenimiento preventivo."
        );

        paquete.agregarElemento(new MantenimientoPreventivo());

        assertFalse(
                paquete.contieneAtencionDomicilio()
        );
    }


    @Test
    void noDebePermitirCiclosCuandoUnPaqueteEstaEnvueltoEnUnDecorador() {
        PaqueteTecnologico paqueteA = new PaqueteTecnologico(
                "PKG-009",
                "Paquete A",
                "Primer paquete."
        );

        PaqueteTecnologico paqueteB = new PaqueteTecnologico(
                "PKG-010",
                "Paquete B",
                "Segundo paquete."
        );

        paqueteA.agregarElemento(paqueteB);

        assertThrows(
                IllegalArgumentException.class,
                () -> paqueteB.agregarElemento(
                        new AtencionDomicilio(paqueteA)
                )
        );
    }

}
