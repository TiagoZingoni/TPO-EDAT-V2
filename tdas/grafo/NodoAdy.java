package tdas.grafo;

public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdy;
    private Comparable etiqueta;

    public NodoAdy(NodoVert vertice, NodoAdy sigAdy, Comparable etiqueta) {
        this.vertice = vertice;
        this.sigAdy = sigAdy;
        this.etiqueta = etiqueta;
    }

    public NodoVert getVertice() {
        return vertice;
    }

    public NodoAdy getSigAdy() {
        return sigAdy;
    }

    public Comparable getEtiqueta() {
        return etiqueta;
    }

    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public void setSigAdy(NodoAdy sigAdy) {
        this.sigAdy = sigAdy;
    }

    public void setEtiqueta(Comparable etiqueta) {
        this.etiqueta = etiqueta;
    }
}