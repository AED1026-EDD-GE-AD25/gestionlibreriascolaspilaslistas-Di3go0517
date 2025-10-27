package cola;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    public Cola() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    public void encolar(T valor) {
        Nodo<T> nuevo = new Nodo<>();
        nuevo.setValor(valor);
        
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        tamanio++;
    }

    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T valor = frente.getValor();
        frente = frente.getSiguiente();
        
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return valor;
    }

    public T frente() {
        if (estaVacia()) {
            return null;
        }
        return frente.getValor();
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void limpiar() {
        frente = null;
        fin = null;
        tamanio = 0;
    }
}