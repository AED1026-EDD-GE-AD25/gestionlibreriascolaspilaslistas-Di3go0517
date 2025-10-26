package miPrincipal.modelo;

import java.util.Objects;

public class Libro {
    // Atributos
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponible;
    private int anioPublicacion;
    private String editorial;
    private String genero;
    
    // Constructores
    public Libro() {
        this.titulo = "";
        this.autor = "";
        this.isbn = "";
        this.disponible = true;
        this.anioPublicacion = 0;
        this.editorial = "";
        this.genero = "";
    }
    
    public Libro(String titulo, String autor, String isbn) {
        this.titulo = titulo != null ? titulo : "";
        this.autor = autor != null ? autor : "";
        this.isbn = isbn != null ? isbn : "";
        this.disponible = true;
        this.anioPublicacion = 0;
        this.editorial = "";
        this.genero = "";
    }
    
    public Libro(String titulo, String autor, String isbn, int anioPublicacion, 
                 String editorial, String genero) {
        this.titulo = titulo != null ? titulo : "";
        this.autor = autor != null ? autor : "";
        this.isbn = isbn != null ? isbn : "";
        this.disponible = true;
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial != null ? editorial : "";
        this.genero = genero != null ? genero : "";
    }
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo != null ? titulo : "";
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor != null ? autor : "";
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn != null ? isbn : "";
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = Math.max(0, anioPublicacion); // No años negativos
    }
    
    public String getEditorial() {
        return editorial;
    }
    
    public void setEditorial(String editorial) {
        this.editorial = editorial != null ? editorial : "";
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero != null ? genero : "";
    }
    
    // Métodos de negocio
    public void prestar() {
        if (disponible) {
            disponible = false;
            System.out.println("Libro prestado: " + titulo);
        } else {
            System.out.println("El libro no está disponible para préstamo: " + titulo);
        }
    }
    
    public void devolver() {
        if (!disponible) {
            disponible = true;
            System.out.println("Libro devuelto: " + titulo);
        } else {
            System.out.println("El libro ya estaba disponible: " + titulo);
        }
    }
    
    public boolean validarISBN() {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        
        // Eliminar guiones y espacios
        String isbnLimpio = isbn.replaceAll("[\\s-]", "");
        
        // Verificar formato básico de ISBN (10 o 13 dígitos)
        return isbnLimpio.matches("^(\\d{10}|\\d{13})$");
    }
    
    public String obtenerInformacionCorta() {
        return String.format("\"%s\" por %s", titulo, autor);
    }
    
    public String obtenerInformacionCompleta() {
        return String.format(
            "Título: %s\n" +
            "Autor: %s\n" +
            "ISBN: %s\n" +
            "Disponible: %s\n" +
            "Año: %d\n" +
            "Editorial: %s\n" +
            "Género: %s",
            titulo, autor, isbn, disponible ? "Sí" : "No", 
            anioPublicacion, editorial, genero
        );
    }
    
    // Métodos sobreescritos
    @Override
    public String toString() {
        return String.format("Libro{titulo='%s', autor='%s', isbn='%s', disponible=%s}", 
                           titulo, autor, isbn, disponible);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Libro libro = (Libro) obj;
        
        // Dos libros son iguales si tienen el mismo ISBN
        return Objects.equals(isbn, libro.isbn);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
    
    // Método para comparar por título (útil para ordenamiento)
    public int compararPorTitulo(Libro otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }
    
    // Método para comparar por autor (útil para ordenamiento)
    public int compararPorAutor(Libro otro) {
        return this.autor.compareToIgnoreCase(otro.autor);
    }
    
    // Método para verificar si el libro es clásico (más de 50 años)
    public boolean esClasico() {
        int añoActual = java.time.Year.now().getValue();
        return (anioPublicacion > 0 && (añoActual - anioPublicacion) >= 50);
    }
    
    // Método estático para crear libro con validación
    public static Libro crearLibroValidado(String titulo, String autor, String isbn) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        
        return new Libro(titulo.trim(), autor.trim(), isbn.trim());
    }
}