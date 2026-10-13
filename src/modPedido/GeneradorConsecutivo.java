package modPedido;
/**
 * PATRON: Singleton.
 * Unica fuente de numeracion de pedidos en todo el programa.
 * Se usa el idiom "Initialization-on-demand holder": es perezoso (la instancia
 * se crea en el primer uso) y seguro entre hilos sin necesidad de synchronized
 * en obtenerInstancia().
 */
public class GeneradorConsecutivo {

    private int contador = 0;

    // Constructor privado: nadie fuera de la clase puede hacer "new".
    private GeneradorConsecutivo() {
        System.out.println("[Consecutivo] Instancia creada.");
    }

    private static class Holder { 
        private static final GeneradorConsecutivo INSTANCIA = new GeneradorConsecutivo();
    }

    public static GeneradorConsecutivo obtenerInstancia() {
        return Holder.INSTANCIA;
    }

    // synchronized: dos hilos nunca reciben el mismo numero.
    public synchronized String siguiente() {
        contador++;
        return String.format("PED-%04d", contador);
    }
}
