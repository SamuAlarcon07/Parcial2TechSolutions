package co.edu.uniquindio.parcial2techsolutions.model.factory;

import co.edu.uniquindio.parcial2techsolutions.model.MantenimientoCorrectivo;
import co.edu.uniquindio.parcial2techsolutions.model.ServicioTecnologico;

public class MantenimientoCorrectivoFactory extends ServicioFactory {

    @Override
    public ServicioTecnologico crearServicio() {
        return new MantenimientoCorrectivo();
    }
}
