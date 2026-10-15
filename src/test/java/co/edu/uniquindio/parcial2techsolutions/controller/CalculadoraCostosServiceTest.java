
package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.model.AtencionDomicilio;
import co.edu.uniquindio.parcial2techsolutions.model.Cliente;
import co.edu.uniquindio.parcial2techsolutions.model.ElementoTecnologico;
import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoCorrectivo;
import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoPreventivo;
import co.edu.uniquindio.parcial2techsolutions.model.Prioridad;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;
import co.edu.uniquindio.parcial2techsolutions.model.SolicitudServicio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraCostosServiceTest {

    private final CalculadoraCostosService calculadora =
            new CalculadoraCostosService();

    /**
     * Crea una solicitud de prueba con los elementos indicados.
     */
    private SolicitudServicio crearSolicitud(
            String codigo,
            ElementoTecnologico... elementos) {

        Cliente cliente = new Cliente(
                "123456",
                "Cliente de prueba",
                "cliente@prueba.com"
        );

        return new SolicitudServicio(
                codigo,
                LocalDate.now(),
                cliente,
                Arrays.asList(elementos),
                null,
                "Solicitud de prueba",
                Prioridad.MEDIA
        );
    }

    @Test
    void debeCalcularElCostoDeUnServicio() {
        assertEquals(
                new BigDecimal("80000"),
                calculadora.calcularCosto(
                        new MantenimientoPreventivo()
                )
        );
    }

    @Test
    void noDebeCalcularElCostoDeUnElementoNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularCosto(null)
        );
    }

    @Test
    void debeCalcularElCostoDeUnaSolicitud() {
        SolicitudServicio solicitud = crearSolicitud(
                "SOL-CALC-001",
                new MantenimientoPreventivo(),
                new MantenimientoCorrectivo()
        );

        assertEquals(
                new BigDecimal("200000"),
                calculadora.calcularCostoSolicitud(solicitud)
        );
    }

    @Test
    void noDebeCalcularElCostoDeUnaSolicitudNula() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularCostoSolicitud(null)
        );
    }

    @Test
    void debeCalcularElCostoTotalSinDesplazamiento() {
        SolicitudServicio solicitud = crearSolicitud(
                "SOL-CALC-002",
                new MantenimientoCorrectivo()
        );

        assertEquals(
                new BigDecimal("120000"),
                calculadora.calcularCostoTotal(solicitud, null)
        );
    }

    @Test
    void debeSumarElDesplazamientoUnaSolaVez() {
        SolicitudServicio solicitud = crearSolicitud(
                "SOL-CALC-003",
                new AtencionDomicilio(
                        new MantenimientoCorrectivo()
                ),
                new AtencionDomicilio(
                        new MantenimientoPreventivo()
                )
        );

        RespuestaTecnico respuesta = new RespuestaTecnico(
                "TEC-105",
                true,
                30000
        );

        // 120000 + 25000 + 80000 + 25000 + 30000 = 280000.
        assertEquals(
                new BigDecimal("280000"),
                calculadora.calcularCostoTotal(solicitud, respuesta)
        );
    }

    @Test
    void debeRechazarElCalculoDomiciliarioSinRespuestaDelTecnico() {
        SolicitudServicio solicitud = crearSolicitud(
                "SOL-CALC-004",
                new AtencionDomicilio(
                        new MantenimientoCorrectivo()
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularCostoTotal(solicitud, null)
        );
    }

    @Test
    void debeRechazarElCalculoSiElTecnicoNoEstaDisponible() {
        SolicitudServicio solicitud = crearSolicitud(
                "SOL-CALC-005",
                new AtencionDomicilio(
                        new MantenimientoCorrectivo()
                )
        );

        RespuestaTecnico respuesta = new RespuestaTecnico(
                "SIN_TECNICO",
                false,
                0
        );

        assertThrows(
                IllegalStateException.class,
                () -> calculadora.calcularCostoTotal(
                        solicitud,
                        respuesta
                )
        );
    }
}
