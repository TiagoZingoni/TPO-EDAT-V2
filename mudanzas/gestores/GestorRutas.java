package mudanzas.gestores;
import tdas.grafo.GrafoEtiquetado;
import tdas.grafo.NodoAdy;
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
        //Obtiene el camino que llegue de la ciudad 1 a la ciudad 2 pasando por la menor cantidad de ciudades
        Lista todosLosCaminos = almacenRutas.listarCaminos(ciudad1, ciudad2);
        Lista caminoPorMenosCiudades = new Lista();
        Lista caminoAux; //Sirve de referencia al camino actual
        int menorLongitudActual = 0; //Referencia a la longitud actual de la ciudad

        for(int i = 1; i<=todosLosCaminos.longitud();i++){
            //Para cada camino dentro de la Lista de caminos
            caminoAux = (Lista) todosLosCaminos.recuperar(i);
            if(caminoAux.longitud() < menorLongitudActual || menorLongitudActual == 0){
                //Si el camino actual es menor que el menos largo hasta ahora, o el primero obtenido, se reemplaza el camino a entregar
                caminoPorMenosCiudades = caminoAux;
                menorLongitudActual = caminoAux.longitud();
            }
        }
        return caminoPorMenosCiudades;
    }
/*ERROR, DEBERÍA USAR LOS ADYACENTES????????? Esto para poder solicitar la menor distancia...¿?*/
    public Lista caminoConMenorDistancia(Object ciudad1, Object ciudad2){
        //Obtiene el camino que va de la ciudad1 a la ciudad2 recorriendo la menor cantidad de km
        Lista todosLosCaminos = almacenRutas.listarCaminos(ciudad1, ciudad2);
        Lista caminoPorMenosCiudades = new Lista();
        Lista caminoAux; //Sirve de referencia al camino actual
        int menosKmRecorridos = 0; //Referencia a los km del camino más corto actual
        int kmRecorridoActual; //Referencia a los km que recorre el camino actual
        NodoAdy nodoAux;

        for(int i = 1; i<=todosLosCaminos.longitud();i++){
            //Para cada camino dentro de la Lista de caminos
            caminoAux = (Lista) todosLosCaminos.recuperar(i);//Camino actual
            kmRecorridoActual = 0; //Se reinicia la variable
            for(int a=1;a<=caminoAux.longitud();a++){
                //Obtenemos la cntidad de km que recorre el camino actual
                nodoAux = (NodoAdy) caminoAux.recuperar(a);
                kmRecorridoActual = kmRecorridoActual + (int) nodoAux.getEtiqueta();
            }
        }
    }
}
