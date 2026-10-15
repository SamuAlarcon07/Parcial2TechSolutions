
package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.adapter.LegacySupportAdapter;
import co.edu.uniquindio.parcial2techsolutions.model.AtencionDomicilio;
import co.edu.uniquindio.parcial2techsolutions.model.Cliente;
import co.edu.uniquindio.parcial2techsolutions.model.EstadoSolicitud;
import co.edu.uniquindio.parcial2techsolutions.model.LegacySupportSimulado;
import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoCorrectivo;
import co.edu.uniquindio.parcial2techsolutions.model.Prioridad;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestionSolicitudesServiceTest {

    @Test
    void debeConfirmarSolicitudSinAtencionDomicilio() {

        Cliente cliente = new Cliente(
                "123456",
                "Cliente de prueba",
                "cliente@prueba.com"
        );

        SolicitudServicio solicitud = new SolicitudServicio(
                "SOL-TEST-001",
                LocalDate.now(),
                cliente,
                Collections.singletonList(
                        new MantenimientoCorrectivo()
                ),
                null,
                "Prueba de confirmación",
                Prioridad.MEDIA
        );

        GestionSolicitudesService gestion =
                new GestionSolicitudesService();

        CalculadoraCostosService calculadora =
                new CalculadoraCostosService();

        gestion.registrarSolicitud(solicitud);

        gestion.confirmarSolicitud(
                "SOL-TEST-001",
                null,
                calculadora
        );

        assertEquals(
                EstadoSolicitud.CONFIRMADA,
                solicitud.getEstado()
        );

        assertEquals(
                new BigDecimal("120000"),
                solicitud.getCostoTotal()
        );
    }

    @Test
    void noDebeConfirmarSolicitudSiNoHayTecnicoDisponible() {

        Cliente cliente = new Cliente(
                "654321",
                "Cliente de prueba",
                "cliente2@prueba.com"
        );

        SolicitudServicio solicitud = new SolicitudServicio(
                "SOL-TEST-002",
                LocalDate.now(),
                cliente,
                Collections.singletonList(
                        new AtencionDomicilio(
                                new MantenimientoCorrectivo()
                        )
                ),
                null,
                "Solicitud a domicilio",
                Prioridad.ALTA
        );

        GestionSolicitudesService gestion =
                new GestionSolicitudesService();

        CalculadoraCostosService calculadora =
                new CalculadoraCostosService();

        LegacySupportSimulado sistema =
                new LegacySupportSimulado(
                        "SIN_TECNICO|NO_DISPONIBLE|0"
                );

        ConsultaTecnicoService consulta =
                new ConsultaTecnicoService(
                        new LegacySupportAdapter(sistema)
                );

        gestion.registrarSolicitud(solicitud);

        assertThrows(
                IllegalStateException.class,
                () -> gestion.confirmarSolicitud(
                        "SOL-TEST-002",
                        consulta,
                        calculadora
                )
        );

        assertEquals(
                EstadoSolicitud.PENDIENTE,
                solicitud.getEstado()
        );
    }

    @Test
    void debeCobrarElDesplazamientoUnaSolaVez() {

        Cliente cliente = new Cliente(
                "789123",
                "Cliente de prueba",
                "cliente3@prueba.com"
        );

        SolicitudServicio solicitud = new SolicitudServicio(
                "SOL-TEST-003",
                LocalDate.now(),
                cliente,
                Arrays.asList(
                        new AtencionDomicilio(
                                new MantenimientoCorrectivo()
                        ),
                        new AtencionDomicilio(
                                new MantenimientoCorrectivo()
                        )
                ),
                null,
                "Solicitud con dos servicios a domicilio",
                Prioridad.ALTA
        );

        GestionSolicitudesService gestion =
                new GestionSolicitudesService();

        CalculadoraCostosService calculadora =
                new CalculadoraCostosService();

        LegacySupportSimulado sistema =
                new LegacySupportSimulado(
                        "TEC-105|DISPONIBLE|30000"
                );

        ConsultaTecnicoService consulta =
                new ConsultaTecnicoService(
                        new LegacySupportAdapter(sistema)
                );

        gestion.registrarSolicitud(solicitud);

        gestion.confirmarSolicitud(
                "SOL-TEST-003",
                consulta,
                calculadora
        );

        // Dos mantenimientos: 2 x 120000 = 240000.
        // Dos complementos a domicilio: 2 x 25000 = 50000.
        // Desplazamiento: 30000, cobrado una sola vez.
        assertEquals(
                new BigDecimal("320000"),
                solicitud.getCostoTotal()
        );

        assertEquals(
                EstadoSolicitud.CONFIRMADA,
                solicitud.getEstado()
        );
    }


    @Test
    void noDebeCancelarUnaSolicitudQueYaEstaCancelada() {
        Cliente cliente = new Cliente(
                "111111",
                "Cliente de prueba",
                "cliente4@prueba.com"
        );

        SolicitudServicio solicitud = new SolicitudServicio(
                "SOL-TEST-004",
                LocalDate.now(),
                cliente,
                Collections.singletonList(new MantenimientoCorrectivo()),
                null,
                "Prueba de cancelación",
                Prioridad.MEDIA
        );

        GestionSolicitudesService gestion = new GestionSolicitudesService();
        gestion.registrarSolicitud(solicitud);

        gestion.cancelarSolicitud("SOL-TEST-004");

        assertThrows(
                IllegalStateException.class,
                () -> gestion.cancelarSolicitud("SOL-TEST-004")
        );

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }

    @Test
    void noDebeConfirmarUnaSolicitudYaConfirmada() {
        Cliente cliente = new Cliente(
                "222222",
                "Cliente de prueba",
                "cliente5@prueba.com"
        );

        SolicitudServicio solicitud = new SolicitudServicio(
                "SOL-TEST-005",
                LocalDate.now(),
                cliente,
                Collections.singletonList(new MantenimientoCorrectivo()),
                null,
                "Prueba de doble confirmación",
                Prioridad.MEDIA
        );

        GestionSolicitudesService gestion = new GestionSolicitudesService();
        CalculadoraCostosService calculadora = new CalculadoraCostosService();

        gestion.registrarSolicitud(solicitud);

        gestion.confirmarSolicitud(
                "SOL-TEST-005",
                null,
                calculadora
        );

        assertThrows(
                IllegalStateException.class,
                () -> gestion.confirmarSolicitud(
                        "SOL-TEST-005",
                        null,
                        calculadora
                )
        );

        assertEquals(EstadoSolicitud.CONFIRMADA, solicitud.getEstado());
    }

    @Test
    void noDebeConfirmarUnaSolicitudCancelada() {
        Cliente cliente = new Cliente(
                "333333",
                "Cliente de prueba",
                "cliente6@prueba.com"
        );

        SolicitudServicio solicitud = new SolicitudServicio(
                "SOL-TEST-006",
                LocalDate.now(),
                cliente,
                Collections.singletonList(new MantenimientoCorrectivo()),
                null,
                "Prueba de solicitud cancelada",
                Prioridad.MEDIA
        );

        GestionSolicitudesService gestion = new GestionSolicitudesService();
        CalculadoraCostosService calculadora = new CalculadoraCostosService();

        gestion.registrarSolicitud(solicitud);
        gestion.cancelarSolicitud("SOL-TEST-006");

        assertThrows(
                IllegalStateException.class,
                () -> gestion.confirmarSolicitud(
                        "SOL-TEST-006",
                        null,
                        calculadora
                )
        );

        assertEquals(EstadoSolicitud.CANCELADA, solicitud.getEstado());
    }

}
