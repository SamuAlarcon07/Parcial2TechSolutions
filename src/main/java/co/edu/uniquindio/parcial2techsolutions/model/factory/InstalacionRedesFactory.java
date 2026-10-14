package co.edu.uniquindio.parcial2techsolutions.model.factory;

import co.edu.uniquindio.parcial2techsolutions.model.InstalacionRedes;
import co.edu.uniquindio.parcial2techsolutions.model.ServicioTecnologico;

public class InstalacionRedesFactory extends ServicioFactory {

    @Override
    public ServicioTecnologico crearServicio() {
        return new InstalacionRedes();
    }
}