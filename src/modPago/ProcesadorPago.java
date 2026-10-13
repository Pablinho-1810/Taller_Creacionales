package modPago;
import modPedido. *;

/**
 * PATRON: Factory Method (clase creadora).
 * procesar() es la plantilla del flujo comun, escrita UNA sola vez.
 * crearPasarela() es el factory method: cada subclase decide que producto crear.
 */
public abstract class ProcesadorPago {

    // Factory Method: lo unico que cambia entre medios de pago.
    protected abstract PasarelaPago crearPasarela();

    public final void procesar(Pedido pedido) {
        PasarelaPago pasarela = crearPasarela();
        double total = pedido.calcularTotal();
        System.out.println("Procesando " + pedido.getId() + " con " + pasarela.nombre()
                + " por $" + (long) total);
        boolean aprobado = pasarela.cobrar(total);
        System.out.println("  -> Pago " + (aprobado ? "APROBADO" : "RECHAZADO"));
    }
}
