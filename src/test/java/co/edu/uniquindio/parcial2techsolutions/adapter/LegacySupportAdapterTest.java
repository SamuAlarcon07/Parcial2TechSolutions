package co.edu.uniquindio.parcial2techsolutions.adapter;

import co.edu.uniquindio.parcial2techsolutions.model.LegacySupportSimulado;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LegacySupportAdapterTest {

    @Test
    void debeInterpretarTecnicoDisponible() {

        LegacySupportAdapter adapter =
                new LegacySupportAdapter(
                        new LegacySupportSimulado(
                                "TEC-105|DISPONIBLE|30000"
                        )
                );

        RespuestaTecnico respuesta = adapter.consultarTecnico();

        assertEquals("TEC-105", respuesta.getCodigoTecnico());
        assertTrue(respuesta.isDisponible());
        assertEquals(30000, respuesta.getCostoDesplazamiento());
    }

    @Test
    void debeInterpretarTecnicoNoDisponible() {

        LegacySupportAdapter adapter =
                new LegacySupportAdapter(
                        new LegacySupportSimulado(
                                "SIN_TECNICO|NO_DISPONIBLE|0"
                        )
                );

        RespuestaTecnico respuesta = adapter.consultarTecnico();

        assertEquals("SIN_TECNICO", respuesta.getCodigoTecnico());
        assertFalse(respuesta.isDisponible());
        assertEquals(0, respuesta.getCostoDesplazamiento());
    }

    @Test
    void debeRechazarUnaRespuestaConFormatoIncorrecto() {

        LegacySupportAdapter adapter =
                new LegacySupportAdapter(
                        new LegacySupportSimulado(
                                "TEC-105|DISPONIBLE"
                        )
                );

        assertThrows(
                IllegalArgumentException.class,
                adapter::consultarTecnico
        );
    }

    @Test
    void debeRechazarUnCostoNoNumerico() {

        LegacySupportAdapter adapter =
                new LegacySupportAdapter(
                        new LegacySupportSimulado(
                                "TEC-105|DISPONIBLE|ABC"
                        )
                );

        assertThrows(
                IllegalArgumentException.class,
                adapter::consultarTecnico
        );
    }

    @Test
    void debeRechazarUnEstadoDesconocido() {

        LegacySupportAdapter adapter =
                new LegacySupportAdapter(
                        new LegacySupportSimulado(
                                "TEC-105|QUIZA|30000"
                        )
                );

        assertThrows(
                IllegalArgumentException.class,
                adapter::consultarTecnico
        );
    }

    @Test
    void constructorDebeRechazarSistemaNulo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new LegacySupportAdapter(null)
        );
    }
}
