package co.edu.uniquindio.parcial2techsolutions.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DecoradoresTest {

    @Test
    void atencionPrioritariaDebeSumar35000() {

        ElementoTecnologico servicio =
                new AtencionPrioritaria(
                        new MantenimientoCorrectivo()
                );

        assertEquals(
                new BigDecimal("155000"),
                servicio.getCosto()
        );
    }

    @Test
    void soporteTreintaDiasDebeSumar50000() {

        ElementoTecnologico servicio =
                new SoporteTreintaDias(
                        new MantenimientoCorrectivo()
                );

        assertEquals(
                new BigDecimal("170000"),
                servicio.getCosto()
        );
    }

    @Test
    void atencionDomicilioDebeSumar25000() {

        ElementoTecnologico servicio =
                new AtencionDomicilio(
                        new MantenimientoCorrectivo()
                );

        assertEquals(
                new BigDecimal("145000"),
                servicio.getCosto()
        );
    }

    @Test
    void debeSumarCorrectamenteVariosComplementos() {

        ElementoTecnologico servicio =
                new AtencionDomicilio(
                        new SoporteTreintaDias(
                                new AtencionPrioritaria(
                                        new MantenimientoCorrectivo()
                                )
                        )
                );

        assertEquals(
                new BigDecimal("230000"),
                servicio.getCosto()
        );
    }

    @Test
    void decorarNoDebeModificarElServicioOriginal() {

        ElementoTecnologico original =
                new MantenimientoCorrectivo();

        ElementoTecnologico decorado =
                new AtencionPrioritaria(original);

        assertEquals(
                new BigDecimal("120000"),
                original.getCosto()
        );

        assertEquals(
                new BigDecimal("155000"),
                decorado.getCosto()
        );
    }

    @Test
    void decoradorDebeRechazarElementoNulo() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new AtencionPrioritaria(null)
        );
    }

    @Test
    void elOrdenDeLosDecoradoresNoDebeCambiarElCostoFinal() {
        ElementoTecnologico primeraCombinacion =
                new AtencionDomicilio(
                        new AtencionPrioritaria(
                                new MantenimientoCorrectivo()
                        )
                );

        ElementoTecnologico segundaCombinacion =
                new AtencionPrioritaria(
                        new AtencionDomicilio(
                                new MantenimientoCorrectivo()
                        )
                );

        assertEquals(
                new BigDecimal("180000"),
                primeraCombinacion.getCosto()
        );

        assertEquals(
                primeraCombinacion.getCosto(),
                segundaCombinacion.getCosto()
        );
    }

}
