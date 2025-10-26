package miPrincipal.modelo;

import utilerias.Fecha;
import java.util.Objects;

public class Pedido {
    private Libro libro;
    private Fecha fechaPedido;
    private String idPedido;
    private String estado;
    private static int contadorPedidos = 0;
    
    // Estados posibles del pedido
    public static final String PENDIENTE = "PENDIENTE";
    public static final String PROCESADO = "PROCESADO";
    public static final String ENTREGADO = "ENTREGADO";
    public static final String CANCELADO = "CANCELADO";
    
    // Constructores
    public Pedido() {
        this.libro = null;
        this.fechaPedido = new Fecha(); // Fecha actual por defecto
        this.estado = PENDIENTE;
        this.idPedido = generarIdPedido();
    }
    
    public Pedido(Libro libro, Fecha fechaPedido) {
        this.libro = libro;
        this.fechaPedido = fechaPedido != null ? fechaPedido : new Fecha();
        this.estado = PENDIENTE;
        this.idPedido = generarIdPedido();
    }
    
    public Pedido(Libro libro, Fecha fechaPedido, String estado) {
        this.libro = libro;
        this.fechaPedido = fechaPedido != null ? fechaPedido : new Fecha();
        this.estado = estado != null ? estado : PENDIENTE;
        this.idPedido = generarIdPedido();
    }
    
    // Getters y Setters
    public Libro getLibro() {
        return libro;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    public Fecha getFecha() {
        return fechaPedido;
    }
    
    public void setFecha(Fecha fechaPedido) {
        this.fechaPedido = fechaPedido != null ? fechaPedido : new Fecha();
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        if (esEstadoValido(estado)) {
            this.estado = estado;
        } else {
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }
    }
    
    public String getIdPedido() {
        return idPedido;
    }
    
    // Métodos de negocio
    public void procesar() {
        if (PENDIENTE.equals(this.estado)) {
            this.estado = PROCESADO;
            System.out.println("Pedido procesado: " + idPedido);
        } else {
            System.out.println("No se puede procesar el pedido. Estado actual: " + estado);
        }
    }
    
    public void entregar() {
        if (PROCESADO.equals(this.estado)) {
            this.estado = ENTREGADO;
            System.out.println("Pedido entregado: " + idPedido);
        } else {
            System.out.println("No se puede entregar el pedido. Estado actual: " + estado);
        }
    }
    
    public void cancelar() {
        if (!ENTREGADO.equals(this.estado)) {
            this.estado = CANCELADO;
            System.out.println("Pedido cancelado: " + idPedido);
        } else {
            System.out.println("No se puede cancelar un pedido ya entregado");
        }
    }
    
    public boolean estaPendiente() {
        return PENDIENTE.equals(estado);
    }
    
    public boolean estaProcesado() {
        return PROCESADO.equals(estado);
    }
    
    public boolean estaEntregado() {
        return ENTREGADO.equals(estado);
    }
    
    public boolean estaCancelado() {
        return CANCELADO.equals(estado);
    }
    
    public boolean puedeCancelarse() {
        return !ENTREGADO.equals(estado);
    }
    
    // Métodos de validación
    private boolean esEstadoValido(String estado) {
        return PENDIENTE.equals(estado) || 
               PROCESADO.equals(estado) || 
               ENTREGADO.equals(estado) || 
               CANCELADO.equals(estado);
    }
    
    private String generarIdPedido() {
        contadorPedidos++;
        return "PED-" + String.format("%04d", contadorPedidos);
    }
    
    // Métodos de información
    public String obtenerInformacionResumida() {
        return String.format("Pedido %s - %s - %s", 
                           idPedido, 
                           libro != null ? libro.getTitulo() : "Sin libro", 
                           estado);
    }
    
    public String obtenerInformacionCompleta() {
        return String.format(
            "ID Pedido: %s\n" +
            "Libro: %s\n" +
            "Autor: %s\n" +
            "ISBN: %s\n" +
            "Fecha: %s\n" +
            "Estado: %s",
            idPedido,
            libro != null ? libro.getTitulo() : "No asignado",
            libro != null ? libro.getAutor() : "No asignado",
            libro != null ? libro.getIsbn() : "No asignado",
            fechaPedido != null ? fechaPedido.toString() : "No asignada",
            estado
        );
    }
    
    // Métodos sobreescritos
    @Override
    public String toString() {
        return String.format("Pedido{id='%s', libro='%s', fecha=%s, estado='%s'}", 
                           idPedido,
                           libro != null ? libro.getTitulo() : "null",
                           fechaPedido != null ? fechaPedido.toString() : "null",
                           estado);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Pedido pedido = (Pedido) obj;
        
        // Dos pedidos son iguales si tienen el mismo ID
        return Objects.equals(idPedido, pedido.idPedido);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idPedido);
    }
    
    // Métodos estáticos
    public static int getTotalPedidosCreados() {
        return contadorPedidos;
    }
    
    public static void reiniciarContador() {
        contadorPedidos = 0;
    }
    
    // Método para crear pedido con validación
    public static Pedido crearPedidoValidado(Libro libro, Fecha fecha) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        
        return new Pedido(libro, fecha);
    }
    
    // Método para verificar si el pedido está vencido (más de 30 días)
    public boolean estaVencido() {
        if (fechaPedido == null) {
            return false;
        }
        
        // Asumiendo que Fecha tiene métodos para calcular diferencia
        // Esta es una implementación simplificada
        Fecha hoy = new Fecha(); // Fecha actual
        int diasDiferencia = fechaPedido.diasDiferencia(hoy);
        
        return diasDiferencia > 30 && !ENTREGADO.equals(estado);
    }
}