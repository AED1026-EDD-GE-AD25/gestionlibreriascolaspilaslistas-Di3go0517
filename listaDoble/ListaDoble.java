package listaDoble;

public class ListaDoble<T> {
    //atributos
    private Nodo<T> cabeza;
    private Nodo<T> cola; // Agregamos referencia a la cola para mejor performance
    private int tamanio;
    
    //constructor por defecto
    public ListaDoble(){
        cabeza = null;
        cola = null;
        tamanio = 0;
    }
    
    //getter y setter
    public int getTamanio() {
        return tamanio;
    }
    
    public boolean esVacia(){    
        return cabeza == null;
    }
    
    //agrega un nuevo nodo al final de la lista (versión optimizada)
    public void agregar(T valor){
        Nodo<T> nuevo = new Nodo<>();
        nuevo.setValor(valor);
        
        if (esVacia()){
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola); // Establecer referencia anterior
            cola = nuevo;
        }
        tamanio++;
    }
    
    /*
     * Inserta un nuevo nodo en la lista
     * @param valor: valor a agregar
     * @param pos: indica la posicion en donde se insertará el nodo
     * @throws : PosicionIlegalException en caso de que la posicion no exista
     */  
    public void insertar(T valor, int pos) throws PosicionIlegalException{
        if (pos < 0 || pos > tamanio){
            throw new PosicionIlegalException();
        }
        
        Nodo<T> nuevo = new Nodo<>();
        nuevo.setValor(valor);
        
        if(pos == 0){ //insertar al principio
            if (esVacia()) {
                cabeza = nuevo;
                cola = nuevo;
            } else {
                nuevo.setSiguiente(cabeza);
                cabeza.setAnterior(nuevo);
                cabeza = nuevo;
            }
        } else if(pos == tamanio){ // al final
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        } else { // en medio
            Nodo<T> aux = obtenerNodo(pos - 1);
            Nodo<T> siguiente = aux.getSiguiente();
            
            aux.setSiguiente(nuevo);
            nuevo.setAnterior(aux);
            nuevo.setSiguiente(siguiente);
            siguiente.setAnterior(nuevo);
        }
        tamanio++;
    }
    
    /*
     * Elimina un nodo de una determinada posicion
     * @param pos: posicion a eliminar
     * @throws PosicionIlegalException
     */
    public T remover(int pos) throws PosicionIlegalException{
        if (pos < 0 || pos >= tamanio){
            throw new PosicionIlegalException();
        }
        
        T valorEliminado;
        
        if (pos == 0) { // Eliminar cabeza
            valorEliminado = cabeza.getValor();
            cabeza = cabeza.getSiguiente();
            if (cabeza != null) {
                cabeza.setAnterior(null);
            } else {
                cola = null; // Lista queda vacía
            }
        } else if (pos == tamanio - 1) { // Eliminar cola
            valorEliminado = cola.getValor();
            cola = cola.getAnterior();
            cola.setSiguiente(null);
        } else { // Eliminar en medio
            Nodo<T> aux = obtenerNodo(pos);
            valorEliminado = aux.getValor();
            
            Nodo<T> anterior = aux.getAnterior();
            Nodo<T> siguiente = aux.getSiguiente();
            
            anterior.setSiguiente(siguiente);
            siguiente.setAnterior(anterior);
        }
        
        tamanio--;
        return valorEliminado;
    }
    
    /*
     * Elimina un nodo de la lista buscandolo por su valor
     * @param valor: valor a eliminar
     * @return: si lo encuentra retorna la posición eliminada, si no retorna -1
     */
    public int remover(T valor) throws PosicionIlegalException{
        int posicion = buscar(valor);
        if (posicion != -1) {
            remover(posicion);
        }
        return posicion;
    }
    
    /*
     * Busca un valor en la lista y retorna su posición
     * @param valor: valor a buscar
     * @return: posición del valor o -1 si no se encuentra
     */
    public int buscar(T valor) {
        Nodo<T> aux = cabeza;
        int posicion = 0;
        
        while (aux != null) {
            if (aux.getValor().equals(valor)) {
                return posicion;
            }
            aux = aux.getSiguiente();
            posicion++;
        }
        return -1;
    }
    
    /*
     * Devuelve el valor de una determinada posicion
     * @param pos: posicion
     * @return : el valor de tipo T
     * @throws PosicionIlegalException
     */
    public T getValor(int pos) throws PosicionIlegalException{
        return obtenerNodo(pos).getValor();
    }
    
    // Método auxiliar para obtener nodo en posición específica
    private Nodo<T> obtenerNodo(int pos) throws PosicionIlegalException {
        if (pos < 0 || pos >= tamanio) {
            throw new PosicionIlegalException();
        }
        
        // Optimización: si está en la segunda mitad, empezar desde cola
        if (pos > tamanio / 2) {
            Nodo<T> aux = cola;
            for (int i = tamanio - 1; i > pos; i--) {
                aux = aux.getAnterior();
            }
            return aux;
        } else {
            Nodo<T> aux = cabeza;
            for (int i = 0; i < pos; i++) {
                aux = aux.getSiguiente();
            }
            return aux;
        }
    }
    
    public void limpiar(){
        cabeza = null;
        cola = null;
        tamanio = 0;
    }
    
    /*
     * Regresa todos los datos de la lista en forma de String
     */
    @Override
    public String toString() {
        if (esVacia()) {
            return "Lista vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        Nodo<T> aux = cabeza;
        
        sb.append("[");
        while (aux != null) {
            sb.append(aux.getValor());
            if (aux.getSiguiente() != null) {
                sb.append(" <-> ");
            }
            aux = aux.getSiguiente();
        }
        sb.append("]");
        
        return sb.toString();
    }
    
    /*
     * busca un valor en la lista
     * @return true si contiene ese valor, false si no
     */
    public boolean contiene(T valor){
        return buscar(valor) != -1;
    }
    
    // Métodos adicionales útiles
    
    public T getPrimero() throws PosicionIlegalException {
        if (esVacia()) {
            throw new PosicionIlegalException("Lista vacía");
        }
        return cabeza.getValor();
    }
    
    public T getUltimo() throws PosicionIlegalException {
        if (esVacia()) {
            throw new PosicionIlegalException("Lista vacía");
        }
        return cola.getValor();
    }
    
    public void agregarAlInicio(T valor) {
        insertar(valor, 0);
    }
    
    public T removerPrimero() throws PosicionIlegalException {
        return remover(0);
    }
    
    public T removerUltimo() throws PosicionIlegalException {
        return remover(tamanio - 1);
    }
}