package modPago;

/** Producto concreto del Factory Method. */
public class PasarelaEfectivo implements PasarelaPago {

    @Override
    public String nombre() {
        return "Efectivo";
    }

    @Override
    public boolean cobrar(double monto) {
        return true;
    }
}
