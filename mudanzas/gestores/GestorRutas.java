package mudanzas.gestores;
import tdas.grafo.GrafoEtiquetado;
import tdas.Lista;

public class GestorRutas {
    //Clase utilizada para el ABM de Rutas
    GrafoEtiquetado almacenRutas;

    public GestorRutas(){
        almacenRutas = new GrafoEtiquetado(null);
    }

    //ALTA
    public boolean altaRuta(Object ciudad1, Object ciudad2, double distancia){
        return almacenRutas.insertarArco(ciudad1, ciudad2, distancia);
    }

    public boolean altaCiudad(Object unaCiudad){
        //Se utiliza cada vez se genera o añade una ciudad en el AVL de GestorCiudad, no se debe usar por separado.
        return almacenRutas.insertarVertice(unaCiudad);
    }

    //BAJA
    public boolean bajaRuta(Object ciudad1, Object ciudad2){
        return almacenRutas.eliminarArco(ciudad1, ciudad2);
    }
    public boolean bajaCiudad(Object unaCiudad){
        //Se utiliza cada vez se elimina una ciudad en el AVL de GestorCiudad, no se debe usar por separado.
        return almacenRutas.eliminarVertice(unaCiudad);
    }

    //MODIFICACIÓN ARREGLAR
    public void modificarRuta(Object ciudad1, Object ciudad2){
        //Modifica la distancia de la ruta entre ciudad1 y ciudad2
        almacenRutas.obtenerArco(ciudad1, ciudad2);
    }

    //CONSULTAS
    public Lista caminoPorMenosCiudades(Object ciudad1, Object ciudad2){
        return almacenRutas.caminoMasCorto(ciudad1, ciudad2);
    }
}
