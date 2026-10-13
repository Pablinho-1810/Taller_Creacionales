package modPago;

public interface PasarelaPago {
    String nombre();

    boolean cobrar(double monto);
}
