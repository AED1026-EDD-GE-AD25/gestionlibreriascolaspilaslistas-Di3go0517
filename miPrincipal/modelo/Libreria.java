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

    // Métodos para la lista principal de libros
    public void agregarLibro(Libro libro) {
        if (libro != null) {
            try {
                listaLibros.agregar(libro);
                System.out.println("Libro agregado: " + libro.getTitulo());
            } catch (Exception e) {
                System.out.println("Error al agregar libro: " + e.getMessage());
            }
        }
    }
    
    public boolean agregarLibroCola(Libro libro) {
        if (libro != null) {
            try {
                colaLibros.encolar(libro);
                System.out.println("Libro agregado a cola de reserva: " + libro.getTitulo());
                return true;
            } catch (Exception e) {
                System.out.println("Error al agregar libro a la cola: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public ListaDoble<Libro> obtenerLibros() {
        return listaLibros;
    }

    public Libro obtenerLibroCola() {
        try {
            if (!colaLibros.estaVacia()) {
                Libro libro = colaLibros.desencolar();
                System.out.println("Libro obtenido de cola: " + libro.getTitulo());
                return libro;
            } else {
                System.out.println("No hay libros en la cola de reserva");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al obtener libro de la cola: " + e.getMessage());
            return null;
        }
    }
    
    public Libro obtenerLibroPila() {
        try {
            if (!pilaLibrosEliminados.estaVacia()) {
                Libro libro = pilaLibrosEliminados.desapilar();
                System.out.println("Libro obtenido de pila: " + libro.getTitulo());
                return libro;
            } else {
                System.out.println("No hay libros en la pila de eliminados");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al obtener libro de la pila: " + e.getMessage());
            return null;
        }
    }

    // Método para crear libro (factory method)
    public Libro crearLibro(String titulo, String autor, String isbn) {
        if (titulo == null || titulo.trim().isEmpty() || 
            autor == null || autor.trim().isEmpty() || 
            isbn == null || isbn.trim().isEmpty()) {
            System.out.println("Error: Todos los campos son obligatorios");
            return null;
        }
        
        try {
            // Verificar si el ISBN ya existe
            if (buscarLibroPorISBN(isbn) != null) {
                System.out.println("Error: Ya existe un libro con el ISBN " + isbn);
                return null;
            }
            
            Libro nuevoLibro = new Libro(titulo, autor, isbn);
            System.out.println("Libro creado: " + nuevoLibro.getTitulo());
            return nuevoLibro;
            
        } catch (Exception e) {
            System.out.println("Error al crear libro: " + e.getMessage());
            return null;
        }
    }

    public Pedido crearPedido(Libro libro, Fecha fecha) {
        if (libro == null || fecha == null) {
            System.out.println("Error: Libro y fecha son requeridos para crear pedido");
            return null;
        }
        
        try {
            // Verificar si el libro existe en la librería
            if (!listaLibros.contiene(libro)) {
                System.out.println("Error: El libro no existe en la librería");
                return null;
            }
            
            Pedido pedido = new Pedido(libro, fecha);
            System.out.println("Pedido creado: " + pedido);
            return pedido;
            
        } catch (Exception e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
            return null;
        }
    }

    public boolean devolverLibro(Libro libro) throws PosicionIlegalException {
        if (libro == null) {
            System.out.println("Error: Libro no puede ser nulo");
            return false;
        }
        
        try {
            // Buscar el libro en la lista
            int posicion = listaLibros.buscar(libro);
            if (posicion != -1) {
                Libro libroEliminado = listaLibros.remover(posicion);
                System.out.println("Libro devuelto: " + libroEliminado.getTitulo());
                return true;
            } else {
                System.out.println("Error: Libro no encontrado en la librería");
                return false;
            }
        } catch (PosicionIlegalException e) {
            System.out.println("Error al devolver libro: " + e.getMessage());
            throw e;
        }
    }

    public Libro eliminarUltimoLibro() throws PosicionIlegalException {
        try {
            if (listaLibros.esVacia()) {
                System.out.println("No hay libros para eliminar");
                return null;
            }
            
            // Obtener el último libro
            int ultimaPosicion = listaLibros.getTamanio() - 1;
            Libro libroEliminado = listaLibros.remover(ultimaPosicion);
            
            // Guardar en pila para posible deshacer
            pilaLibrosEliminados.apilar(libroEliminado);
            
            System.out.println("Último libro eliminado: " + libroEliminado.getTitulo());
            return libroEliminado;
            
        } catch (PosicionIlegalException e) {
            System.out.println("Error al eliminar último libro: " + e.getMessage());
            throw e;
        }
    }

    public Libro deshacerEliminarLibro() {
        try {
            Libro libroRestaurado = pilaLibrosEliminados.desapilar();
            if (libroRestaurado != null) {
                listaLibros.agregar(libroRestaurado);
                System.out.println("Eliminación deshecha. Libro restaurado: " + libroRestaurado.getTitulo());
                return libroRestaurado;
            }
        } catch (Exception e) {
            System.out.println("Error al deshacer eliminación: " + e.getMessage());
        }
        return null;
    }

    public void buscarLibro(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("Error: ISBN no puede estar vacío");
            return;
        }
        
        try {
            Libro libroEncontrado = buscarLibroPorISBN(isbn);
            if (libroEncontrado != null) {
                System.out.println("Libro encontrado: " + libroEncontrado);
            } else {
                System.out.println("No se encontró ningún libro con ISBN: " + isbn);
            }
        } catch (PosicionIlegalException e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
    }
    
    // Método auxiliar para buscar libro por ISBN
    private Libro buscarLibroPorISBN(String isbn) throws PosicionIlegalException {
        for (int i = 0; i < listaLibros.getTamanio(); i++) {
            Libro libro = listaLibros.getValor(i);
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
    
    // Métodos adicionales útiles
    public int getTotalLibros() {
        return listaLibros.getTamanio();
    }
    
    public int getTotalReservas() {
        // Asumiendo que Cola tiene un método getTamanio()
        // Si no, necesitarías implementarlo
        return colaLibros.getTamanio();
    }
    
    public int getTotalEliminadosPendientes() {
        return pilaLibrosEliminados.getTamanio();
    }
    
    public void mostrarEstadisticas() {
        System.out.println("=== ESTADÍSTICAS DE LA LIBRERÍA ===");
        System.out.println("Total de libros: " + getTotalLibros());
        System.out.println("Libros en reserva: " + getTotalReservas());
        System.out.println("Eliminaciones por deshacer: " + getTotalEliminadosPendientes());
    }
    
    public void limpiarSistema() {
        listaLibros.limpiar();
        colaLibros.limpiar();
        pilaLibrosEliminados.limpiar();
        System.out.println("Sistema limpiado completamente");
    }
}