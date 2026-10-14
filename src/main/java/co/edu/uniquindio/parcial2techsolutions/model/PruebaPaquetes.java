
package co.edu.uniquindio.parcial2techsolutions.model;

public class PruebaPaquetes {

    public static void main(String[] args) {

        MantenimientoPreventivo preventivo =
                new MantenimientoPreventivo();

        MantenimientoCorrectivo correctivo =
                new MantenimientoCorrectivo();

        InstalacionRedes redes =
                new InstalacionRedes();

        PaqueteTecnologico mantenimiento =
                new PaqueteTecnologico(
                        "PAQ-001",
                        "Paquete de mantenimiento",
                        "Mantenimiento preventivo y correctivo"
                );

        mantenimiento.agregarElemento(preventivo);
        mantenimiento.agregarElemento(correctivo);

        PaqueteTecnologico empresarial =
                new PaqueteTecnologico(
                        "PAQ-002",
                        "Paquete empresarial",
                        "Mantenimiento e instalación de redes"
                );

        empresarial.agregarElemento(mantenimiento);
        empresarial.agregarElemento(redes);

        System.out.println(
                "Costo del paquete de mantenimiento: $"
                        + mantenimiento.getCosto()
        );

        System.out.println(
                "Costo del paquete empresarial: $"
                        + empresarial.getCosto()
        );
    }
}
