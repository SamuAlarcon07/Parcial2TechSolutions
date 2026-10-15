package co.edu.uniquindio.parcial2techsolutions.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SolicitudServicioBuilderTest {

    @Test
    void debeConstruirUnaSolicitudConDatosValidos() {

        Cliente cliente = new Cliente(
                "111222",
                "Cliente de prueba",
                "builder@prueba.com"
        );

        SolicitudServicio solicitud =
                new SolicitudServicioBuilder()
                        .codigo("SOL-BUILDER-001")
                        .fechaSolicitud(LocalDate.of(2026, 10, 14))
                        .cliente(cliente)
                        .agregarElemento(new MantenimientoPreventivo())
                        .prioridad(Prioridad.ALTA)
                        .observaciones("Solicitud construida con Builder")
                        .construir();

        assertNotNull(solicitud);
        assertEquals("SOL-BUILDER-001", solicitud.getCodigo());
        assertEquals(cliente, solicitud.getCliente());
        assertEquals(1, solicitud.getElementos().size());
        assertEquals(Prioridad.ALTA, solicitud.getPrioridad());
        assertEquals(
                EstadoSolicitud.PENDIENTE,
                solicitud.getEstado()
        );
    }

    @Test
    void debeAsignarValoresPredeterminados() {

        Cliente cliente = new Cliente(
                "333444",
                "Otro cliente",
                "otro@prueba.com"
        );

        SolicitudServicio solicitud =
                new SolicitudServicioBuilder()
                        .codigo("SOL-BUILDER-002")
                        .cliente(cliente)
                        .agregarElemento(new MantenimientoPreventivo())
                        .construir();

        assertEquals(Prioridad.MEDIA, solicitud.getPrioridad());
        assertEquals(LocalDate.now(), solicitud.getFechaSolicitud());
    }

    @Test
    void noDebeConstruirUnaSolicitudSinCodigo() {

        Cliente cliente = new Cliente(
                "555666",
                "Cliente sin código",
                "sin.codigo@prueba.com"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new SolicitudServicioBuilder()
                        .cliente(cliente)
                        .agregarElemento(new MantenimientoPreventivo())
                        .construir()
        );
    }

    @Test
    void noDebePermitirAgregarUnElementoNulo() {

        SolicitudServicioBuilder builder =
                new SolicitudServicioBuilder();

        assertThrows(
                IllegalArgumentException.class,
                () -> builder.agregarElemento(null)
        );
    }
}
