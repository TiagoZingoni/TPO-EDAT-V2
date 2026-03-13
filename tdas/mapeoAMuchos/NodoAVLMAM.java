package tdas.mapeoAMuchos;
import tdas.Lista;
public class NodoAVLMAM {
    //Nodo del arbol avl de Mapeo a Muchos

    Comparable clave;
    Lista listaDatos;
    int altura;     //longitud del camino más largo desde el nodo a una hoja.
    NodoAVLMAM izquierdo;  //hijo izquierdo
    NodoAVLMAM derecho;    //hijo derecho

    public NodoAVLMAM(Comparable id) {
        clave = id;
        listaDatos = new Lista();
        izquierdo = null;
        derecho = null;
        altura = 0;
    }
    public NodoAVLMAM(Comparable id, Lista listaElementos) {
        clave = id;
        listaDatos = listaElementos;
        izquierdo = null;
        derecho = null;
        altura = 0;
    }

    public Comparable getClave() {
        return clave;
    }

    public void auxCabmio(Comparable id, Lista lista) {
        //Solo se utiliza para reemplazo al eliminar
        clave = id;
        this.listaDatos = lista;
    }

    public Lista getLista() {
        return this.listaDatos;
    }

    public void setLista(Lista lista) {
        this.listaDatos = lista;
    }
    
    public void agregarDato(Object dato){
        listaDatos.insertar(dato, listaDatos.longitud()+1);
    }

    public int getAltura() {
        return altura;
    }

    public void recalcularAltura() {
        //recalcula altura
        int alturaIzq = 0, alturaDer = 0;
        if (izquierdo != null) {
            alturaIzq = this.izquierdo.getAltura() + 1;
        }
        if (derecho != null) {
            alturaDer = this.derecho.getAltura() + 1;
        }
        altura = Math.max(alturaIzq, alturaDer);
    }

    public NodoAVLMAM getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVLMAM izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoAVLMAM getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVLMAM derecho) {
        this.derecho = derecho;
    }

    public int calcularBalance() {
        /*Indica el balance del nodo mediante la diferencia de altura de su hijo izquierdo y su hiijo derecho.
        Balance 1 : implica que la altura subárbol izquierdo es mayor que la altura 
        subárbol derecho (el árbol cae apenas hacia la izquierda).
        Balance 0 : implica que las alturas de los dos subárboles son iguales 
        (el árbol está bien balanceado).
        Balance -1 : implica que la altura del subárbol derecho es mayor que la 
        altura del subárbol izquierdo(el árbol cae apenas hacia la derecha)
        Balance de 2 0 -2 indica nodo desbalanceado
         */
        //la altura de nulo es -1
        int balance = 0;
        if (this.getIzquierdo() != null && this.getDerecho() != null) {
            balance = (this.getIzquierdo().getAltura()) - (this.getDerecho().getAltura());
        } else if (this.getIzquierdo() == null && this.getDerecho() != null) {
            balance = -1 - (this.getDerecho().getAltura());
        } else if (this.getIzquierdo() != null && this.getDerecho() == null) {
            balance = (this.getIzquierdo().getAltura()) + 1; //altura hi - (-1), por nulo hd
        }
        return balance;
    }
}
