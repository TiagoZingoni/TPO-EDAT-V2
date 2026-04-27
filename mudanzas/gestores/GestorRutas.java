package mudanzas.gestores;

import tdas.Lista;
import tdas.grafo.GrafoEtiquetado;

public class GestorRutas {

    //Clase utilizada para el ABM de Rutas
    GrafoEtiquetado almacenRutas;

    public GestorRutas() {
        //Crea el grafo etiquetado sobre el que se trabaja 
        almacenRutas = new GrafoEtiquetado(null);
    }

    //ALTA
    public boolean altaRuta(Object ciudad1, Object ciudad2, double distancia) {
        return almacenRutas.insertarArco(ciudad1, ciudad2, distancia);
    }

    public boolean altaCiudad(Object unaCiudad) {
        //Se utiliza cada vez se genera o añade una ciudad en el AVL de GestorCiudad, no se debe usar por separado.
        return almacenRutas.insertarVertice(unaCiudad);
    }

    //BAJA
    public boolean bajaRuta(Object ciudad1, Object ciudad2) {
        return almacenRutas.eliminarArco(ciudad1, ciudad2);
    }

    public boolean bajaCiudad(Object unaCiudad) {
        //Se utiliza cada vez se elimina una ciudad en el AVL de GestorCiudad, no se debe usar por separado.
        return almacenRutas.eliminarVertice(unaCiudad);
    }

    //MODIFICACIÓN
    public boolean modificarRuta(Object ciudad1, Object ciudad2, double kms) {
        //Modifica la distancia de la ruta entre ciudad1 y ciudad2
        boolean modificado = almacenRutas.eliminarArco(ciudad2, ciudad2);
        if (modificado) {
            //si el arco se elimino
            almacenRutas.insertarArco(ciudad1, ciudad2, kms);
        }
        return modificado;
    }

    //CONSULTAS
    public Lista caminoPorMenosCiudades(Object ciudad1, Object ciudad2) {
        //Obtiene el camino que llegue de la ciudad 1 a la ciudad 2 pasando por la menor cantidad de ciudades
        Lista listaAux = almacenRutas.caminoMasCorto(ciudad1, ciudad2);
        return listaAux;
    }

    public Lista caminoConMenorDistancia(Object ciudad1, Object ciudad2) {
        //Obtiene el camino que va de la ciudad1 a la ciudad2 recorriendo la menor cantidad de km,
        //retorna un string de ciudades, y el ultimo elemento es la cantidad de km.
        return almacenRutas.caminoMenorRecorrido(ciudad1, ciudad2);
    }

    public Lista caminosPasanPorCiudad(Object ciudad1, Object ciudad2, Object ciudad3) {
        //Obtener todos los caminos posibles para llegar de 1 a 2 que pasen por una ciudad 3 dada sin pasar dos veces por la misma ciudad
        Lista caminosDe1A2 = almacenRutas.listarCaminos(ciudad1, ciudad2);//Tomamos todos los caminos de 1 a 2
        Lista listaAux = new Lista();
        for (int i = 1; i <= caminosDe1A2.longitud(); i++) {
            //Para cada camino de la lista obtenida, si pasa por 3 lo agrego al String
            listaAux = (Lista) caminosDe1A2.recuperar(i);
            if (listaAux.localizar(ciudad3) > 0) {
                listaAux.insertar(ciudad3, listaAux.longitud());//si pasa por 3 agregamos el camino a la lista
            }
        }
        return listaAux;
    }

    public boolean recorridoMenorA(Object ciudad1, Object ciudad2, double km) {
        //Verificar si es posible llegar de A a B recorriendo como máximo una cantidad km de kilómetros
        Lista listaAux = almacenRutas.caminoMenorRecorrido(ciudad1, ciudad2);
        boolean posible = (listaAux != null && (double) listaAux.recuperar(listaAux.longitud()) <= km);
        return posible;
    }

    public boolean existeCamino(Object ciudad1, Object ciudad2) {
        //Retorna si eixste o no un camino entre dos ciudades si recorrer de más innecesariamente
        return almacenRutas.existeCamino(ciudad1, ciudad2);
    }
}
