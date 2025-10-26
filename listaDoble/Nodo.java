package listaDoble;

public class Nodo<T> {
    //atributos
    private T valor;
    private Nodo<T> siguiente; // referencia al siguiente nodo
    private Nodo<T> anterior;  // referencia al nodo anterior
    
    //constructores
    public Nodo() {
        valor = null;
        siguiente = null;
        anterior = null;
    }
    
    // Constructor sobrecargado para inicializar con valor
    public Nodo(T valor) {
        this.valor = valor;
        this.siguiente = null;
        this.anterior = null;
    }
    
    // Constructor completo
    public Nodo(T valor, Nodo<T> anterior, Nodo<T> siguiente) {
        this.valor = valor;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }
    
    //getter y setter
    public T getValor() {
        return valor;
    }
    
    public void setValor(T valor) {
        this.valor = valor;
    }
    
    public Nodo<T> getSiguiente() {
        return siguiente;
    }
    
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
    
    public Nodo<T> getAnterior() {
        return anterior;
    }
    
    public void setAnterior(Nodo<T> anterior) {
        this.anterior = anterior;
    }
    
    // Métodos utilitarios adicionales
    
    /**
     * Verifica si el nodo tiene un nodo siguiente
     * @return true si tiene siguiente, false en caso contrario
     */
    public boolean tieneSiguiente() {
        return siguiente != null;
    }
    
    /**
     * Verifica si el nodo tiene un nodo anterior
     * @return true si tiene anterior, false en caso contrario
     */
    public boolean tieneAnterior() {
        return anterior != null;
    }
    
    /**
     * Verifica si el nodo es un nodo cabeza (no tiene anterior)
     * @return true si es cabeza, false en caso contrario
     */
    public boolean esCabeza() {
        return anterior == null;
    }
    
    /**
     * Verifica si el nodo es un nodo cola (no tiene siguiente)
     * @return true si es cola, false en caso contrario
     */
    public boolean esCola() {
        return siguiente == null;
    }
    
    /**
     * Desconecta el nodo de la lista
     * Ajusta las referencias de los nodos adyacentes
     */
    public void desconectar() {
        if (anterior != null) {
            anterior.setSiguiente(siguiente);
        }
        if (siguiente != null) {
            siguiente.setAnterior(anterior);
        }
        this.anterior = null;
        this.siguiente = null;
    }
    
    @Override
    public String toString() {
        return "Nodo{" + 
               "valor=" + valor + 
               ", anterior=" + (anterior != null ? "↶" : "null") +
               ", siguiente=" + (siguiente != null ? "↷" : "null") +
               '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Nodo<?> otro = (Nodo<?>) obj;
        
        // Comparar valores, manejando nulls
        if (valor == null) {
            return otro.valor == null;
        } else {
            return valor.equals(otro.valor);
        }
    }
    
    @Override
    public int hashCode() {
        return valor != null ? valor.hashCode() : 0;
    }
}