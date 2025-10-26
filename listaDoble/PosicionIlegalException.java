package listaDoble;

public class PosicionIlegalException extends Exception {
    
    // Constructores
    public PosicionIlegalException() {
        super("Posición ilegal en la lista");
    }
    
    public PosicionIlegalException(String mensaje) {
        super(mensaje);
    }
    
    public PosicionIlegalException(int posicion, int tamanio) {
        super(String.format("Posición %d fuera de rango. Tamaño de la lista: %d", 
                           posicion, tamanio));
    }
    
    public PosicionIlegalException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public PosicionIlegalException(int posicion, int tamanio, String operacion) {
        super(String.format("No se puede %s en posición %d. Rango válido: 0-%d", 
                           operacion, posicion, tamanio - 1));
    }
    
    // Métodos utilitarios
    public static void verificarRango(int posicion, int tamanio) 
            throws PosicionIlegalException {
        if (posicion < 0 || posicion >= tamanio) {
            throw new PosicionIlegalException(posicion, tamanio);
        }
    }
    
    public static void verificarRangoInserccion(int posicion, int tamanio) 
            throws PosicionIlegalException {
        if (posicion < 0 || posicion > tamanio) {
            throw new PosicionIlegalException(posicion, tamanio, "insertar");
        }
    }
}