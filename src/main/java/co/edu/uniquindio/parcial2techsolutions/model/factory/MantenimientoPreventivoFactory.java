package co.edu.uniquindio.parcial2techsolutions.model.factory;

import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoPreventivo;
import co.edu.uniquindio.parcial2techsolutions.model.ServicioTecnologico;

public class MantenimientoPreventivoFactory extends ServicioFactory {

    @Override
    public ServicioTecnologico crearServicio() {
        return new MantenimientoPreventivo();
    }
}
