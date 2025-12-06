package TDAs.Diccionario;

public class NodoAVLDicc {
    Comparable clave;
    Object dato;
    int altura;
    NodoAVLDicc izquierdo;
    NodoAVLDicc derecho;
    
    public NodoAVLDicc(Comparable id, Object elemento){
        clave = id;
        dato = elemento;
        izquierdo = null;
        derecho = null;
        altura = 0;
    }
    
    public Comparable getClave() {
        return clave;
    }
    
    public void auxCabmio(Comparable id, Object dato){
        //Solo se utiliza para reemplazo al eliminar
        clave = id;
        this.dato = dato;
    }
    
    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public int getAltura() {
        return altura;
    }

    public void recalcularAltura(){
        //recalcula altura
        if(izquierdo!=null)
            this.altura = this.izquierdo.getAltura()-1;
        else if(derecho != null)
            this.altura = this.derecho.getAltura()-1;
    }

    public NodoAVLDicc getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVLDicc izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoAVLDicc getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVLDicc derecho) {
        this.derecho = derecho;
    }
    
    public int calcularBalance(){
        /*Indica el balance del nodo mediante la diferencia de altura de su hijo izquierdo y su hiijo derecho.
        Balance 1 : implica que la altura subárbol izquierdo es mayor que la altura 
        subárbol derecho (el árbol cae apenas hacia la izquierda).
        Balance 0 : implica que las alturas de los dos subárboles son iguales 
        (el árbol está bien balanceado).
        Balance -1 : implica que la altura del subárbol derecho es mayor que la 
        altura del subárbol izquierdo(el árbol cae apenas hacia la derecha)
        Balance de 2 0 -2 indica nodo desbalanceado
        */
        int balance = (this.getIzquierdo().getAltura()) - (this.getDerecho().getAltura());
        return balance;
    }
}
