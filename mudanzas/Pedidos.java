package mudanzas;

import tdas.mapeoAMuchos.MapeoAMuchos;
import tdas.Lista;

public class Pedidos {
    /*
    Esta clase es la que almacena los pedidos a cada ciudad, la idea es que cada ciudad almacenada en el Diccionario
    posea su propio mapeo a muchos de pedidos, donde cada nodo del arbol es una ciudad, y cada lista de estos
    nodos serán los pedidos.
    */
    private MapeoAMuchos solicitudesViaje;
   
    public Pedidos(){
        this.solicitudesViaje = new MapeoAMuchos();
    }

    public boolean altaCiudadLlegada(int codigoPostal){
        //agrega una ciudad (solo su id) a la que van a haber pedidos desde la ciudad que tenga esta instacia Pedidos
        return solicitudesViaje.insertar(codigoPostal, new Lista());
    }
    public boolean bajaCiudadLlegada(int codigoPostal){
        //permite eliminar una ciudad y todos sus pedidos del arbol de Pedidos
        return solicitudesViaje.eliminar(codigoPostal);
    }
    public boolean altaPedido(int codigoPostal, SolicitudViaje unSolicitudViaje){
        /*
        Intenta agregar una solicitud de viaje a un destino particular
        Retorna true si el destino existe, false si no, la solicitud de viaje se agrega siempre que exista el destino
        */
        return solicitudesViaje.asociarALista(codigoPostal, unSolicitudViaje);
    }
    public boolean bajaPedido(int codigoPostal, int idSolicitud){
        //si el codigo postal existe, y dentro de este se encuentra la idSolicitud, se elimina la solicitud y retorna true
        Lista lista = solicitudesViaje.obtenerLista(codigoPostal);
        boolean baja = false;
        if(lista != null){
            baja = lista.eliminar(lista.localizar(idSolicitud));
        }
        return baja;
    }
}
