
package co.edu.uniquindio.parcial2techsolutions.model;

/**
 * Define el contrato del sistema externo LegacySupport.
 * Recibe un código numérico de especialidad y devuelve
 * la información del técnico en formato de texto.
 */
public interface LegacySupport {

    String consultarTecnico(int codigoEspecialidad);
}
