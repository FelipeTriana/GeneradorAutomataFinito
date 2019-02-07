package ListClass;

import ListClass.Node;

/**
 * Clase encargada de la construccion de la estructura grafo por medio de objetos de la clase Node 
 * @author Felipe Triana
 */
public class Grafo {

    private Node primero, ultimo;

    public Grafo() {
        primero = ultimo = null;
    }

    boolean isVoid() {
        return primero == null;
    }

    public Node getPrimer() {
        return primero;
    }

    public Node getUltimo() {
        return ultimo;
    }

    public boolean endOfList(Node x) {
        return x.getData() == null;      //Si el objeto en el campo dato es null nos encontramos en el estado final de la construccion
    }

    public void agregarAlFinal(Node nuevo) {
        if (isVoid()) {
            primero = nuevo;
        } else {
            Node aux = primero;
            while (aux.getLink1() != null) {
                aux = aux.getLink1();
            }
            aux.setLink1(nuevo);
        }
    }

    public void setPrimero(Node primero) {
        this.primero = primero;
    }

    public void setUltimo(Node ultimo) {
        this.ultimo = ultimo;
    }

}
