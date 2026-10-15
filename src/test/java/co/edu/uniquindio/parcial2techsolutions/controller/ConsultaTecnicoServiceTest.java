
package co.edu.uniquindio.parcial2techsolutions.controller;

import co.edu.uniquindio.parcial2techsolutions.adapter.LegacySupportAdapter;
import co.edu.uniquindio.parcial2techsolutions.model.LegacySupportSimulado;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsultaTecnicoServiceTest {

    @Test
    void debeConsultarUnTecnicoDisponible() {
        LegacySupportSimulado sistema = new LegacySupportSimulado(
                "TEC-105|DISPONIBLE|30000"
        );

        ConsultaTecnicoService servicio = new ConsultaTecnicoService(
                new LegacySupportAdapter(sistema)
        );

        RespuestaTecnico respuesta = servicio.consultarTecnico();

        assertNotNull(respuesta);
        assertEquals("TEC-105", respuesta.getCodigoTecnico());
        assertTrue(respuesta.isDisponible());
        assertEquals(30000, respuesta.getCostoDesplazamiento());
    }

    @Test
    void debeConsultarCuandoNoHayTecnicosDisponibles() {
        LegacySupportSimulado sistema = new LegacySupportSimulado(
                "SIN_TECNICO|NO_DISPONIBLE|0"
        );

        ConsultaTecnicoService servicio = new ConsultaTecnicoService(
                new LegacySupportAdapter(sistema)
        );

        RespuestaTecnico respuesta = servicio.consultarTecnico();

        assertNotNull(respuesta);
        assertEquals("SIN_TECNICO", respuesta.getCodigoTecnico());
        assertFalse(respuesta.isDisponible());
        assertEquals(0, respuesta.getCostoDesplazamiento());
    }

    @Test
    void noDebePermitirCrearElServicioConUnAdaptadorNulo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ConsultaTecnicoService(null)
        );
    }

    @Test
    void debePropagarElErrorSiLaRespuestaExternaEsInvalida() {
        LegacySupportSimulado sistema = new LegacySupportSimulado(
                "RESPUESTA_INVALIDA"
        );

        ConsultaTecnicoService servicio = new ConsultaTecnicoService(
                new LegacySupportAdapter(sistema)
        );

        assertThrows(
                IllegalArgumentException.class,
                servicio::consultarTecnico
        );
    }
}
