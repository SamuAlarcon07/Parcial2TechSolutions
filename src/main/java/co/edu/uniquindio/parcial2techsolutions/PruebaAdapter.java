package co.edu.uniquindio.parcial2techsolutions;

import co.edu.uniquindio.parcial2techsolutions.adapter.LegacySupportAdapter;
import co.edu.uniquindio.parcial2techsolutions.model.LegacySupport;
import co.edu.uniquindio.parcial2techsolutions.model.LegacySupportSimulado;
import co.edu.uniquindio.parcial2techsolutions.model.RespuestaTecnico;

public class PruebaAdapter {

    public static void main(String[] args) {

        LegacySupport sistema = new LegacySupportSimulado(
                "TEC-105|DISPONIBLE|30000"
        );

        LegacySupportAdapter adapter =
                new LegacySupportAdapter(sistema);

        RespuestaTecnico respuesta = adapter.consultarTecnico();

        System.out.println("Código: " + respuesta.getCodigoTecnico());
        System.out.println("Disponible: " + respuesta.isDisponible());
        System.out.println(
                "Desplazamiento: $" + respuesta.getCostoDesplazamiento()
        );
    }
}
