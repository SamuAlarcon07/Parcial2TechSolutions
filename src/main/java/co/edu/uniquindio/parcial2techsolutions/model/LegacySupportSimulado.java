
package co.edu.uniquindio.parcial2techsolutions.model;

/**
 * Simula las respuestas del sistema externo LegacySupport.
 * Se utiliza mientras no exista una conexión real.
 */
public class LegacySupportSimulado implements LegacySupport {

    private final String respuesta;

    public LegacySupportSimulado(String respuesta) {
        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La respuesta simulada no puede estar vacía.");
        }

        this.respuesta = respuesta;
    }

    @Override
    public String consultarTecnico() {
        return respuesta;
    }
}
