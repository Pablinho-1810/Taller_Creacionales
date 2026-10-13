package modPago;

/** R6: creador nuevo. Se agrega sin tocar ninguna clase existente. */
public class ProcesadorNequi extends ProcesadorPago {

    @Override
    protected PasarelaPago crearPasarela() {
        return new PasarelaNequi();
    }
}
