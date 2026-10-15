package co.edu.uniquindio.parcial2techsolutions.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CatalogoServiciosTest {

    @Test
    void debeCargarLosCuatroServiciosIniciales() {

        CatalogoServicios catalogo = new CatalogoServicios();

        assertEquals(4, catalogo.getServicios().size());
    }

    @Test
    void debeBuscarUnServicioPorSuCodigo() {

        CatalogoServicios catalogo = new CatalogoServicios();

        ElementoTecnologico servicio =
                catalogo.buscarPorCodigo("SER-001");

        assertNotNull(servicio);
        assertEquals("Mantenimiento preventivo", servicio.getNombre());
    }

    @Test
    void debeRechazarUnServicioConCodigoDuplicado() {

        CatalogoServicios catalogo = new CatalogoServicios();

        assertThrows(
                IllegalArgumentException.class,
                () -> catalogo.agregarServicio(
                        new MantenimientoPreventivo()
                )
        );
    }

    @Test
    void noDebeAgregarUnServicioNulo() {

        CatalogoServicios catalogo = new CatalogoServicios();

        assertThrows(
                IllegalArgumentException.class,
                () -> catalogo.agregarServicio(null)
        );
    }


    @Test
    void debeDevolverNullCuandoElServicioNoExiste() {
        CatalogoServicios catalogo = new CatalogoServicios();

        assertEquals(
                null,
                catalogo.buscarPorCodigo("SER-999")
        );

        assertEquals(
                null,
                catalogo.buscarPorCodigo(null)
        );
    }

    @Test
    void noDebePermitirModificarDirectamenteLaListaDeServicios() {
        CatalogoServicios catalogo = new CatalogoServicios();

        assertThrows(
                UnsupportedOperationException.class,
                () -> catalogo.getServicios().clear()
        );

        assertEquals(4, catalogo.getServicios().size());
    }

}
