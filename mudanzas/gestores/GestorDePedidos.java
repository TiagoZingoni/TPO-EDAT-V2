package mudanzas.gestores;

import mudanzas.Ciudad;
import mudanzas.Pedidos;
import mudanzas.SolicitudViaje;
import tdas.Lista;

public class GestorDePedidos {

    GestorRutas gestorRutas;
    GestorCiudades gestorCiudad;

    public GestorDePedidos(GestorRutas unGestorRutas, GestorCiudades unGestorCiudades) {
        //Usa referencias a los gestores de ciudades y rutas generados en el menú
        this.gestorCiudad = unGestorCiudades;
        this.gestorRutas = unGestorRutas;
    }

    //Gestiona los pedidos de cada ciudad y entre ciudades.
    //ALTA
    public boolean altaPedido(int codPostalSalida, int codPostalLlegada, SolicitudViaje unaSolicitud) {
        //inserta una solicitud de la desde codPostalSalida a codPostalLlegada, retorna true si pudo, false si no
        boolean exito = false;
        Pedidos pedidosActual;
        Ciudad ciudadSalida = gestorCiudad.obtenerCiudad(codPostalSalida);
        if (gestorRutas.existeCamino(codPostalSalida, codPostalLlegada)) {
            //Si existe un camino entre dichos codigos postales, agregamos el pedido
            pedidosActual = ciudadSalida.getSolicitudesViajes();//Obtenemos la colección de pedidos de ciudad
            pedidosActual.altaCiudadLlegada(codPostalLlegada);
            pedidosActual.altaPedido(codPostalLlegada, unaSolicitud);
        }
        return exito;
    }

    //BAJA
    public boolean bajaPedido(int codPostalSalida, int codPostalLlegada, int idSolicitud) {
        /*Dada un id ciudadA, un id ciudadB e id solicitud, intenta eliminar la solicituda 
        que va de la ciudad A a la B, retorna verdadero si pudo, falso si la solicitud o 
        ciudad de entrega no existe*/
        boolean exito = false;
        Ciudad ciudadSalida = gestorCiudad.obtenerCiudad(codPostalSalida);
        Pedidos pedidosAux;
        if (ciudadSalida != null) {
            //Si la ciudad existe, probamos eliminar la solicitud
            pedidosAux = ciudadSalida.getSolicitudesViajes();
            if (pedidosAux != null) {
                //Si la lista de pedidos no es nula, buscamos el idSolicitud para eliminarla. Retorna true si se pudo eliminar, false si no existía
                exito = pedidosAux.bajaPedido(codPostalLlegada, idSolicitud);
            }
        }
        return exito;
    }

    //Consultas Pedidos
    public String espacioNecesario(int ciudadA, int ciudadB) {
        /*Dada una ciudad A y una ciudad B mostrar todos los pedidos y calcular cuánto
        espacio total hace falta en el camión. */
        Lista listaPedidos = new Lista(), listaSolicitudes = new Lista();
        int espacioAcumulado = 0;
        String txtRespuesta = "";
        Ciudad posibleCiudad = (Ciudad) almacenCiudades.obtenerInformacion(ciudadA);
        Pedidos pedidosCiudad;
        SolicitudViaje solAux;
        if (posibleCiudad != null) {
            //Recorremos todas las ciudades a la que tenga pedidos ciudad A hasta obtener ciudad B
            pedidosCiudad = posibleCiudad.getSolicitudesViajes();//Obtenemos el arbol de pedidos de A
            listaPedidos = pedidosCiudad.obtenerPedidos(ciudadB);//Obtenemos la lista de pedidos de A a B
            if (!listaPedidos.esVacia()) {
                //Si existen pedidos entre A y B los agregamos al string y cargamos el acumulado
                for (int i = 1; i <= listaPedidos.longitud(); i++) {
                    //Para cada pedido entre A y B
                    solAux = (SolicitudViaje) listaPedidos.recuperar(i);
                    txtRespuesta += solAux.toString(ciudadA, ciudadB);
                    espacioAcumulado += solAux.getCantidadMetrosCubicos();
                }
            }
            txtRespuesta = txtRespuesta + "\nEspacio necesario:" + espacioAcumulado;
        } else {
            txtRespuesta = "Error Ciudad no encontrada o pedidos inexistentes";
        }
        return txtRespuesta;
    }
}
