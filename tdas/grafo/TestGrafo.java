package tdas.grafo;

import tdas.Lista;

public class TestGrafo {

    public static void main(String[] args) {
        System.out.println("=== TEST BFS DESDE NODO (GrafoEtiquetado) ===");

        GrafoEtiquetado g = new GrafoEtiquetado(null);
        
        g.insertarVertice("A");
        g.insertarVertice("B");
        g.insertarVertice("C");
        g.insertarVertice("D");
        g.insertarVertice("E");
        g.insertarVertice("F");

        g.insertarArco("A", "F", 1);
        g.insertarArco("A", "C", 1);
        g.insertarArco("F", "C", 1);
        g.insertarArco("A", "B", 1);
        g.insertarArco("B", "E", 1);
        g.insertarArco("B", "D", 1);
        
        System.out.println(g.listarCaminos("A", "E").toString());
    }
}