package miPrincipal.servicio;
import listaDoble.ListaDoble;
import listaDoble.PosicionIlegalException;
import pila.Pila;
import cola.Cola;
import miPrincipal.modelo.Libro;

public class ServicioDatos{
    
    private ListaDoble<Libro> lista;
    private Cola<Libro> cola;
    private Pila<Libro> pila;

    public ServicioDatos() {
        lista = new ListaDoble<>();
        cola = new Cola<>();
        pila = new Pila<>();
    }

    // Métodos para la lista
    public void agregarALista(Libro valor) {
        lista.agregar(valor);
    }

    public Libro obtenerDeLista(int indice) throws PosicionIlegalException {
        return lista.getValor(indice);
    }

    public int tamanioLista() {
        return lista.getTamanio();
    }

    public boolean listaEstaVacia() {
        return lista.esVacia();
    }

    // Métodos para la cola
    public void agregarACola(Libro valor) {
        cola.encolar(valor);
    }

    public Libro obtenerDeCola() {
        return cola.frente();
    }

    public Libro removerDeCola() {
        return cola.desencolar();
    }

    public int tamanioCola() {
        return cola.getTamanio();
    }

    public boolean colaEstaVacia() {
        return cola.estaVacia();
    }

    // Métodos para la pila
    public void agregarAPila(Libro valor) {
        pila.apilar(valor);
    }

    public Libro obtenerDePila() {
        return pila.cima();
    }

    public Libro removerDePila() {
        return pila.desapilar();
    }

    public int tamanioPila() {
        return pila.getTamanio();
    }

    public boolean pilaEstaVacia() {
        return pila.estaVacia();
    }
}