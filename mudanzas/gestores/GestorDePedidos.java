package mudanzas.gestores;

import mudanzas.Ciudad;
import mudanzas.Pedidos;
import mudanzas.SolicitudViaje;
import tdas.Lista;

public class GestorDePedidos {

    //Gestiona los pedidos de cada ciudad y entre ciudades.
    //ALTA
    public boolean altaPedido(int codigoPostal, Pedidos pedidosDeCiudad, SolicitudViaje unaSolicitud) {
        //inserta una solicitud de la ciudad actual a la ciudad daada por parametro. Se debe chequear que el camino exista previamente
        //Si no existe lo agrego, es más optimo esto que buscarlo y después probar
        pedidosDeCiudad.altaCiudadLlegada(codigoPostal);//Si ya existe acá no pasa nada. 
        pedidosDeCiudad.altaPedido(codigoPostal, unaSolicitud);//Podría optimizarse? crear un metodo en mapeoAMuchos sería romper la logica creo.
        return true;
    }

    //BAJA
    public boolean bajaPedido(int codigoPostal, Pedidos pedidosDeCiudad, int idSolicitud) {
        /*Dada un id ciudad e id solicitud, intenta eliminar la solicituda de dicha ciudad,
        retorna verdadero si pudo, falso si la solicitud o ciudad de entrega no existe*/
        return pedidosDeCiudad.bajaPedido(codigoPostal, idSolicitud);
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
