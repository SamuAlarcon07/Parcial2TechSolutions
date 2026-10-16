package co.edu.uniquindio.parcial2techsolutions.model;

/**
 * Simula temporalmente las respuestas del sistema LegacySupport.
 * Esta clase permite probar el adaptador sin conectar todavía
 * el sistema externo real.
 */
public class LegacySupportSimulado implements LegacySupport {

    private final String respuesta;

    /**
     * Crea una simulación con una respuesta determinada.
     *
     * @param respuesta respuesta que devolverá el sistema simulado
     */
    public LegacySupportSimulado(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Simula la consulta de un técnico según su especialidad.
     *
     * @param codigoEspecialidad código numérico de la especialidad
     * @return respuesta simulada del sistema externo
     */
    @Override
    public String consultarTecnico(int codigoEspecialidad) {
        return respuesta;
    }
}
