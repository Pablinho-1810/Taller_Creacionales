package modPago;

/** Creador concreto del Factory Method. */
public class ProcesadorEfectivo extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaEfectivo();
    }
}
