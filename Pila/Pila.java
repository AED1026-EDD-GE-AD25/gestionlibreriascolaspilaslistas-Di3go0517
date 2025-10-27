package pila;

public class Pila<T> {
    private Nodo<T> cima;
    private int tamanio;

    public Pila() {
        cima = null;
        tamanio = 0;
    }

    public void apilar(T valor) {
        Nodo<T> nuevo = new Nodo<>();
        nuevo.setValor(valor);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tamanio++;
    }

    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T valor = cima.getValor();
        cima = cima.getSiguiente();
        tamanio--;
        return valor;
    }

    public T cima() {
        if (estaVacia()) {
            return null;
        }
        return cima.getValor();
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void limpiar() {
        cima = null;
        tamanio = 0;
    }
}