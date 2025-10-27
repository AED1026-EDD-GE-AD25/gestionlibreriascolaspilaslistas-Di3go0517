package listaDoble;

public class Nodo<T> {
    private T valor;
    private Nodo<T> siguiente; 
    private Nodo<T> anterior;  
    
    public Nodo() {
        valor = null;
        siguiente = null;
        anterior = null;
    }
    
    public Nodo(T valor) {
        this.valor = valor;
        this.siguiente = null;
        this.anterior = null;
    }
    
    public Nodo(T valor, Nodo<T> anterior, Nodo<T> siguiente) {
        this.valor = valor;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }
    
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
    
    
 
    public boolean tieneSiguiente() {
        return siguiente != null;
    }
    
  
    public boolean tieneAnterior() {
        return anterior != null;
    }
    

    public boolean esCabeza() {
        return anterior == null;
    }
    
 
    public boolean esCola() {
        return siguiente == null;
    }
    

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