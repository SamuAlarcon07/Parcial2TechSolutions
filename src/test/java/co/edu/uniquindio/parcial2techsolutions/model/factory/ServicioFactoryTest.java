
package co.edu.uniquindio.parcial2techsolutions.model.factory;

import co.edu.uniquindio.parcial2techsolutions.model.InstalacionRedes;
import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoCorrectivo;
import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoPreventivo;
import co.edu.uniquindio.parcial2techsolutions.model.RecuperacionInformacion;
import co.edu.uniquindio.parcial2techsolutions.model.ServicioTecnologico;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ServicioFactoryTest {

    @Test
    void debeCrearMantenimientoPreventivo() {

        ServicioFactory factory =
                new MantenimientoPreventivoFactory();

        ServicioTecnologico servicio = factory.crearServicio();

        assertInstanceOf(MantenimientoPreventivo.class, servicio);
        assertEquals("80000", servicio.getCosto().toPlainString());
    }

    @Test
    void debeCrearMantenimientoCorrectivo() {

        ServicioFactory factory =
                new MantenimientoCorrectivoFactory();

        ServicioTecnologico servicio = factory.crearServicio();

        assertInstanceOf(MantenimientoCorrectivo.class, servicio);
        assertEquals("120000", servicio.getCosto().toPlainString());
    }

    @Test
    void debeCrearInstalacionDeRedes() {

        ServicioFactory factory = new InstalacionRedesFactory();

        ServicioTecnologico servicio = factory.crearServicio();

        assertInstanceOf(InstalacionRedes.class, servicio);
        assertEquals("180000", servicio.getCosto().toPlainString());
    }

    @Test
    void debeCrearRecuperacionDeInformacion() {

        ServicioFactory factory =
                new RecuperacionInformacionFactory();

        ServicioTecnologico servicio = factory.crearServicio();

        assertInstanceOf(RecuperacionInformacion.class, servicio);
        assertEquals("150000", servicio.getCosto().toPlainString());
    }
}
