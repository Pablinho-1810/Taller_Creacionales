package modPago;

/** Creador concreto del Factory Method. */
public class ProcesadorTarjeta extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaTarjeta();
    }
}
