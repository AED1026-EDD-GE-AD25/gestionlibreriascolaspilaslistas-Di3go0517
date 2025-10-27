package miPrincipal.modelo;

import listaDoble.ListaDoble;
import listaDoble.PosicionIlegalException;
import pila.Pila;
import cola.Cola;
import utilerias.Fecha;
import miPrincipal.servicio.ServicioDatos;
import java.util.Scanner;

public class Libreria {
    ServicioDatos dataService;
    ListaDoble<Libro> listaLibros;
    Cola<Libro> colaLibros;
    Pila<Libro> pilaLibrosEliminados;
    Scanner scanner; 

    public Libreria() {
        dataService = new ServicioDatos();
        scanner = new Scanner(System.in);
        listaLibros = new ListaDoble<>();
        colaLibros = new Cola<>();
        pilaLibrosEliminados = new Pila<>();
    }

    public void agregarLibro(Libro libro) {
        if (libro != null) {
            listaLibros.agregar(libro);
        }
    }
    
    public boolean agregarLibroCola(Libro libro) {
        if (libro != null) {
            colaLibros.encolar(libro);
            return true;
        }
        return false;
    }

    public ListaDoble<Libro> obtenerLibros() {
        return listaLibros;
    }

    public Libro obtenerLibroCola() {
        if (!colaLibros.estaVacia()) {
            return colaLibros.desencolar();
        } else {
            return null;
        }
    }
    
    public Libro obtenerLibroPila() {
        if (!pilaLibrosEliminados.estaVacia()) {
            return pilaLibrosEliminados.desapilar();
        } else {
            return null;
        }
    }

    public Libro crearLibro(String titulo, String autor, String isbn) {
        if (titulo == null || titulo.trim().isEmpty() || 
            autor == null || autor.trim().isEmpty() || 
            isbn == null || isbn.trim().isEmpty()) {
            return null;
        }
        
        try {
            if (buscarLibroPorISBN(isbn) != null) {
                return null;
            }
            
            Libro nuevoLibro = new Libro(titulo, autor, isbn);
            return nuevoLibro;
            
        } catch (Exception e) {
            return null;
        }
    }

    public Pedido crearPedido(Libro libro, Fecha fecha) {
        if (libro == null || fecha == null) {
            return null;
        }
        
        try {
            if (!listaLibros.contiene(libro)) {
                return null;
            }
            
            Pedido pedido = new Pedido(libro, fecha);
            return pedido;
            
        } catch (Exception e) {
            return null;
        }
    }

    public boolean devolverLibro(Libro libro) throws PosicionIlegalException {
        if (libro == null) {
            return false;
        }
        
        try {
            int posicion = listaLibros.buscar(libro);
            if (posicion != -1) {
                listaLibros.remover(posicion);
                return true;
            } else {
                return false;
            }
        } catch (PosicionIlegalException e) {
            throw e;
        }
    }

    public Libro eliminarUltimoLibro() throws PosicionIlegalException {
        try {
            if (listaLibros.esVacia()) {
                return null;
            }
            
            int ultimaPosicion = listaLibros.getTamanio() - 1;
            Libro libroEliminado = listaLibros.remover(ultimaPosicion);
            pilaLibrosEliminados.apilar(libroEliminado);
            return libroEliminado;
            
        } catch (PosicionIlegalException e) {
            throw e;
        }
    }

    public Libro deshacerEliminarLibro() {
        if (!pilaLibrosEliminados.estaVacia()) {
            Libro libroRestaurado = pilaLibrosEliminados.desapilar();
            if (libroRestaurado != null) {
                listaLibros.agregar(libroRestaurado);
                return libroRestaurado;
            }
        }
        return null;
    }

    public void buscarLibro(String isbn) {
        // Método para interfaz de usuario
    }
    
    private Libro buscarLibroPorISBN(String isbn) throws PosicionIlegalException {
        for (int i = 0; i < listaLibros.getTamanio(); i++) {
            Libro libro = listaLibros.getValor(i);
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
    
    public int getTotalLibros() {
        return listaLibros.getTamanio();
    }
    
    public int getTotalReservas() {
        return colaLibros.getTamanio();
    }
    
    public int getTotalEliminadosPendientes() {
        return pilaLibrosEliminados.getTamanio();
    }
}