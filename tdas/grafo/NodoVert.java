package tdas.grafo;

public class NodoVert {

    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object elemento, NodoVert siguiente, NodoAdy primerAdyacente) {
        this.elem = elemento;
        this.sigVertice = siguiente;
        this.primerAdy = primerAdyacente;
    }

    public Object getElem() {
        return this.elem;
    }

    public NodoVert getSigVert() {
        return sigVertice;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setSigVert(NodoVert sigVert) {
        this.sigVertice = sigVert;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }
    public String toString(){
        return this.elem.toString();
    }
}
