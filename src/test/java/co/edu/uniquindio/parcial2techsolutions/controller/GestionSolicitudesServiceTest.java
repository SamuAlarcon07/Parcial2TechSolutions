package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.Cliente;
import co.edu.uniquindio.parcial2techsolutions.model.EstadoSolicitud;
import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoCorrectivo;
import co.edu.uniquindio.parcial2techsolutions.model.Prioridad;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GestionSolicitudesServiceTest {

    @Test
    void debeConfirmarSolicitudSinAtencionDomicilio() {

        // Arrange: preparar los objetos necesarios.
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

        // Act: confirmar la solicitud.
        gestion.confirmarSolicitud(
                "SOL-TEST-001",
                null,
                calculadora
        );

        // Assert: verificar los resultados.
        assertEquals(
                EstadoSolicitud.CONFIRMADA,
                solicitud.getEstado()
        );

        assertEquals(
                new BigDecimal("120000"),
                solicitud.getCostoTotal()
        );
    }
}
