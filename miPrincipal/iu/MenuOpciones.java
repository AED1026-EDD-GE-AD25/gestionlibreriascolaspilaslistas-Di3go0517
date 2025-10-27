package miPrincipal.iu;
import miPrincipal.servicio.ServicioDatos;
import miPrincipal.modelo.Libro;
import miPrincipal.modelo.Pedido;
import miPrincipal.modelo.Libreria;
import java.util.Scanner;
import utilerias.Fecha;
import listaDoble.ListaDoble;
import listaDoble.PosicionIlegalException;
import cola.Cola;
import pila.Pila;

public class MenuOpciones {
    static Scanner scanner = new Scanner(System.in);
    static private Libreria libreria = new Libreria();

    public static void agregarLibro() {
        System.out.println("=== AGREGAR LIBRO ===");
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();
        System.out.print("Ingrese el ISBN del libro: ");
        String isbn = scanner.nextLine();
        
        Libro nuevoLibro = libreria.crearLibro(titulo, autor, isbn);
        if (nuevoLibro != null) {
            libreria.agregarLibro(nuevoLibro);
            System.out.println("Libro agregado exitosamente: " + nuevoLibro.getTitulo());
        } else {
            System.out.println("Error: No fue posible crear el libro");
        }
    }
    
    public static void mostrarLibros() throws PosicionIlegalException {
        System.out.println("=== LIBROS EN LA LIBRERÍA ===");
        ListaDoble<Libro> libros = libreria.obtenerLibros();
        
        if (libros.esVacia()) {
            System.out.println("No hay libros en la librería.");
            return;
        }
        
        System.out.println("Total de libros: " + libros.getTamanio());
        System.out.println("------------------------------");
        
        for (int i = 0; i < libros.getTamanio(); i++) {
            Libro libro = libros.getValor(i);
            System.out.println((i + 1) + ". " + libro);
        }
    }

    public static void agregarLibroCola() {
        System.out.println("=== AGREGAR LIBRO A COLA DE RESERVA ===");
        System.out.print("Ingrese el título del libro a reservar: ");
        String titulo = scanner.nextLine();
        
        ListaDoble<Libro> libros = libreria.obtenerLibros();
        Libro libroEncontrado = null;
        
        try {
            for (int i = 0; i < libros.getTamanio(); i++) {
                Libro libro = libros.getValor(i);
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    libroEncontrado = libro;
                    break;
                }
            }
            
            if (libroEncontrado != null) {
                boolean agregado = libreria.agregarLibroCola(libroEncontrado);
                if (agregado) {
                    System.out.println("Libro agregado a la cola de reserva: " + libroEncontrado.getTitulo());
                } else {
                    System.out.println("No se pudo agregar el libro a la cola");
                }
            } else {
                System.out.println("Libro no encontrado en la librería");
            }
        } catch (PosicionIlegalException e) {
            System.out.println("Error al buscar libros: " + e.getMessage());
        }
    }

    public static Libro obtenerLibroCola() {
        System.out.println("=== OBTENER LIBRO DE COLA DE RESERVA ===");
        try {
            Libro libro = libreria.obtenerLibroCola();
            if (libro != null) {
                System.out.println("Libro obtenido de la cola: " + libro);
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

    public static void mostrarReservaLibros() {
        System.out.println("=== COLA DE RESERVA DE LIBROS ===");
       
        System.out.println("Función en desarrollo...");
        

    }

    public static void crearPedido() {
        System.out.println("=== CREAR PEDIDO ===");
        System.out.print("Ingrese el título del libro para el pedido: ");
        String tituloPedido = scanner.nextLine();
        System.out.print("Ingrese el autor del libro para el pedido: ");
        String autorPedido = scanner.nextLine();
        System.out.print("Ingrese el ISBN del libro para el pedido: ");
        String isbnPedido = scanner.nextLine();
        
        Libro libroPedido = libreria.crearLibro(tituloPedido, autorPedido, isbnPedido);
        Pedido pedido = null;
        
        if (libroPedido != null) {
            pedido = libreria.crearPedido(libroPedido, new Fecha());
            if (pedido != null) {
                System.out.println("Pedido creado exitosamente: " + pedido);
            } else {
                System.out.println("No fue posible crear el pedido");
            }
        } else {
            System.out.println("Error: no fue posible crear el Libro");
        }
    }

    public static void devolverLibro() throws PosicionIlegalException {
        System.out.println("=== DEVOLVER LIBRO ===");
        System.out.print("Ingrese el título del libro a devolver: ");
        String titulo = scanner.nextLine();
        
        ListaDoble<Libro> libros = libreria.obtenerLibros();
        Libro libroEncontrado = null;
        
        for (int i = 0; i < libros.getTamanio(); i++) {
            Libro libro = libros.getValor(i);
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                libroEncontrado = libro;
                break;
            }
        }
        
        if (libroEncontrado != null) {
            boolean devuelto = libreria.devolverLibro(libroEncontrado);
            if (devuelto) {
                System.out.println("Libro devuelto exitosamente: " + libroEncontrado.getTitulo());
            } else {
                System.out.println("No se pudo devolver el libro");
            }
        } else {
            System.out.println("Libro no encontrado en la librería");
        }
    }

    public static Libro eliminarUltimoLibro() throws PosicionIlegalException {
        System.out.println("=== ELIMINAR ÚLTIMO LIBRO ===");
        try {
            Libro libroEliminado = libreria.eliminarUltimoLibro();
            if (libroEliminado != null) {
                System.out.println("Libro eliminado: " + libroEliminado.getTitulo());
                return libroEliminado;
            } else {
                System.out.println("No hay libros para eliminar");
                return null;
            }
        } catch (PosicionIlegalException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
            throw e;
        }
    }

    public static void deshacerEliminarLibro() {
        System.out.println("=== DESHACER ELIMINACIÓN ===");
        try {
            Libro libroRestaurado = libreria.deshacerEliminarLibro();
            if (libroRestaurado != null) {
                System.out.println("Eliminación deshecha. Libro restaurado: " + libroRestaurado.getTitulo());
            } else {
                System.out.println("No hay eliminaciones para deshacer");
            }
        } catch (Exception e) {
            System.out.println("Error al deshacer eliminación: " + e.getMessage());
        }
    }
    
    public static void mostrarMenu() {
        System.out.println("=== SISTEMA DE GESTIÓN DE LIBRERÍA ===");
        System.out.println("1. Agregar libro");
        System.out.println("2. Mostrar todos los libros");
        System.out.println("3. Reservar libro (agregar a cola)");
        System.out.println("4. Obtener libro reservado (de cola)");
        System.out.println("5. Mostrar reservas");
        System.out.println("6. Crear pedido");
        System.out.println("7. Devolver libro");
        System.out.println("8. Eliminar último libro");
        System.out.println("9. Deshacer eliminación");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    public static boolean ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    agregarLibro();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    agregarLibroCola();
                    break;
                case 4:
                    obtenerLibroCola();
                    break;
                case 5:
                    mostrarReservaLibros();
                    break;
                case 6:
                    crearPedido();
                    break;
                case 7:
                    devolverLibro();
                    break;
                case 8:
                    eliminarUltimoLibro();
                    break;
                case 9:
                    deshacerEliminarLibro();
                    break;
                case 0:
                    System.out.println("¡Hasta pronto!");
                    return false;
                default:
                    System.out.println("Opción inválida");
            }
        } catch (PosicionIlegalException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return true;
    }
}