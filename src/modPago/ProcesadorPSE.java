package modPago;

/** Creador concreto del Factory Method. */
public class ProcesadorPSE extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaPSE();
    }
}
