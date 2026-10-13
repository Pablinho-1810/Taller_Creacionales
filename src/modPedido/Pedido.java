package modPedido;
import java.util.ArrayList;
import java.util.List;


public class Pedido {

    private final String id;
    private final String cliente;
    private final TipoEntrega tipoEntrega;
    private final String direccion;
    private final List<ItemPedido> items;
    private final String notas;
    private final int cupon;
    private final double propina;

    private Pedido(String id, String cliente, TipoEntrega tipoEntrega, String direccion,
                   List<ItemPedido> items, String notas, int cupon, double propina) {
        this.id = id;
        this.cliente = cliente;
        this.tipoEntrega = tipoEntrega;
        this.direccion = direccion;
        // Copia defensiva: el pedido no comparte la lista con el Builder ni con otro pedido.
        this.items = new ArrayList<>(items);
        this.notas = notas;
        this.cupon = cupon;
        this.propina = propina;
    }

    public String getId() {
        return id;
    }

    public double calcularTotal() {
        double subtotal = 0;
        for (ItemPedido item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal * (100 - cupon) / 100 + propina;
    }

    public void mostrarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ").append(id)
          .append(" | Cliente: ").append(cliente)
          .append(" | Entrega: ").append(tipoEntrega);
        if (tipoEntrega == TipoEntrega.DOMICILIO) {
            sb.append(" (").append(direccion).append(")");
        }
        System.out.println(sb);
        for (ItemPedido item : items) {
            System.out.println("  - " + item.getCantidad() + " x " + item.getNombre()
                    + " ($" + (long) item.getPrecioUnitario() + ")");
        }
        if (!notas.isEmpty()) {
            System.out.println("  Notas: " + notas);
        }
        System.out.println("  Cupon: " + cupon + "% | Propina: $" + (long) propina
                + " | Total: $" + (long) calcularTotal());
    }

    // ---------- Reto extra: Prototype ----------

    /** Agrega un item a este pedido (pensado para el pedido clonado). */
    public void agregarItem(ItemPedido item) {
        items.add(item);
    }

    /**
     * Copia el pedido con un numero nuevo. No copia el cupon (uso unico).
     * El constructor copia la lista, asi que agregar items al clon no afecta al original.
     */
    public Pedido clonar() {
        return new Pedido(GeneradorConsecutivo.obtenerInstancia().siguiente(),
                cliente, tipoEntrega, direccion, items, notas, 0, propina);
    }

    // ---------- Builder ----------

    public static class Builder {

        private String cliente;
        private TipoEntrega tipoEntrega = TipoEntrega.RECOGER;
        private String direccion = "";
        private final List<ItemPedido> items = new ArrayList<>();
        private String notas = "";
        private int cupon = 0;
        private double propina = 0;

        public Builder conCliente(String cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder conTipoEntrega(TipoEntrega tipoEntrega) {
            this.tipoEntrega = tipoEntrega;
            return this;
        }

        public Builder conDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder agregarItem(ItemPedido item) {
            this.items.add(item);
            return this;
        }

        public Builder conNotas(String notas) {
            this.notas = notas;
            return this;
        }

        public Builder conCupon(int cupon) {
            if (cupon < 0 || cupon > 100) {
                throw new IllegalArgumentException("El cupon debe estar entre 0 y 100");
            }
            this.cupon = cupon;
            return this;
        }

        public Builder conPropina(double propina) {
            this.propina = propina;
            return this;
        }

        public Pedido construir() {
            // 1. Validar (en el orden exigido) ANTES de pedir el consecutivo.
            if (cliente == null || cliente.trim().isEmpty()) { 
                throw new IllegalStateException("El cliente es obligatorio");
            }
            if (items.isEmpty()) {
                throw new IllegalStateException("El pedido debe tener al menos un item");
            }
            if (tipoEntrega == TipoEntrega.DOMICILIO
                    && (direccion == null || direccion.trim().isEmpty())) {
                throw new IllegalStateException("El domicilio requiere direccion");
            }
            // 2. Solo un pedido valido consume numero.
            String id = GeneradorConsecutivo.obtenerInstancia().siguiente();
            return new Pedido(id, cliente, tipoEntrega, direccion, items, notas, cupon, propina);
        }
    }
}
