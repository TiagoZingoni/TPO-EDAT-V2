package mudanzas.gestores;

import mudanzas.Ciudad;
import mudanzas.ClaveCliente;
import mudanzas.Pedidos;
import mudanzas.SolicitudViaje;
import tdas.Cola;
import tdas.Lista;

public class GestorDePedidos {

    GestorRutas gestorRutas;
    GestorCiudades gestorCiudad;
    GestorEscritura gestorEscritura;

    public GestorDePedidos(GestorRutas unGestorRutas, GestorCiudades unGestorCiudades, GestorEscritura gestorEscritura) {
        //Usa referencias a los gestores de ciudades y rutas generados en el menú
        this.gestorCiudad = unGestorCiudades;
        this.gestorRutas = unGestorRutas;
        this.gestorEscritura = gestorEscritura;
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
        gestorEscritura.objetoAgregado("Solicitud", unaSolicitud.toString(codPostalSalida, codPostalSalida), exito);
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
        gestorEscritura.objetoEliminado("Solicitud", idSolicitud + "", exito);
        return exito;
    }

    //Modificación
    public void modificarFecha(SolicitudViaje unaSolicitudViaje, String unaFecha) {
        SolicitudViaje solAux = unaSolicitudViaje;
        unaSolicitudViaje.setFecha(unaFecha);
        gestorEscritura.objetoModificado("Solicitud", solAux.toString(), unaSolicitudViaje.toString(), true);
    }

    public void modificarCliente(SolicitudViaje unaSolicitudViaje, ClaveCliente unaClave) {
        SolicitudViaje solAux = unaSolicitudViaje;
        unaSolicitudViaje.setCliente(unaClave);
        gestorEscritura.objetoModificado("Solicitud", solAux.toString(), unaSolicitudViaje.toString(), true);

    }

    public void modificarMtsCubicos(SolicitudViaje unaSolicitudViaje, double mts) {
        SolicitudViaje solAux = unaSolicitudViaje;
        unaSolicitudViaje.setCantidadMetros(mts);
        gestorEscritura.objetoModificado("Solicitud", solAux.toString(), unaSolicitudViaje.toString(), true);

    }

    public void modificarCantidadBultos(SolicitudViaje unaSolicitudViaje, int cantidad) {
        SolicitudViaje solAux = unaSolicitudViaje;
        unaSolicitudViaje.setCantidadBultos(cantidad);
        gestorEscritura.objetoModificado("Solicitud", solAux.toString(), unaSolicitudViaje.toString(), true);

    }

    public void modificarDomRetiro(SolicitudViaje unaSolicitudViaje, String nuevoDom) {
        SolicitudViaje solAux = unaSolicitudViaje;
        unaSolicitudViaje.setDomicilioRetiro(nuevoDom);
        gestorEscritura.objetoModificado("Solicitud", solAux.toString(), unaSolicitudViaje.toString(), true);

    }

    public void modificarDomEntrega(SolicitudViaje unaSolicitudViaje, String nuevoDom) {
        SolicitudViaje solAux = unaSolicitudViaje;
        unaSolicitudViaje.setDomicilioEntrega(nuevoDom);
        gestorEscritura.objetoModificado("Solicitud", solAux.toString(), unaSolicitudViaje.toString(), true);

    }

    public void modificarPago(SolicitudViaje unaSolicitudViaje) {
        SolicitudViaje solAux = unaSolicitudViaje;
        unaSolicitudViaje.setPago();//Solo puede cambiar de falso a verdadero        
        gestorEscritura.objetoModificado("Solicitud", solAux.toString(), unaSolicitudViaje.toString(), true);

    }

    //Consultas Pedidos
    public SolicitudViaje obtenerSolicitud(int codPostalSalida, int codPostalLlegada, int idSolicitud) {
        //Dadas dos ciudades busca la solicitud por su id y la devuelve
        SolicitudViaje solicitudBuscada = null;
        Ciudad ciudadSalida = gestorCiudad.obtenerCiudad(codPostalSalida);
        Pedidos pedidosAux;
        Lista listaAux;
        int longAux, i = 1;
        boolean encontrado = false;
        if (ciudadSalida != null) {
            //Si la ciudad existe, buscamos la solicitud
            pedidosAux = ciudadSalida.getSolicitudesViajes();
            if (pedidosAux != null) {
                //Si la lista de pedidos no es nula, buscamos el idSolicitud y la asignamos al retorno si la encontramos
                listaAux = pedidosAux.obtenerPedidos(codPostalLlegada);
                if (listaAux != null && !listaAux.esVacia()) {//Si la lista no es vacía
                    longAux = listaAux.longitud();
                    while (!encontrado && i <= longAux) {
                        solicitudBuscada = (SolicitudViaje) listaAux.recuperar(i);
                        if (solicitudBuscada.getIdSolicitud() == idSolicitud) {
                            //Si la solicitud de la lista es la buscada, salimos del while, sino seguimos buscando
                            encontrado = true;
                        } else {
                            solicitudBuscada = null;
                        }
                        i++;
                    }
                }
            }
        }
        return solicitudBuscada;
    }

    public Lista listaDePedidos(int ciudadA, int ciudadB) {
        /* Dada una ciudad A y una ciudad B retorna todos los pedidos entre estas. */
        Ciudad ciudadSalida = gestorCiudad.obtenerCiudad(ciudadA);
        Pedidos pedidosAux;
        Lista listaDePedidos = new Lista(), listaAux;
        if (ciudadSalida != null) {
            //Si la ciudad existe, buscamos la lista
            pedidosAux = ciudadSalida.getSolicitudesViajes();
            if (pedidosAux != null) {
                listaAux = pedidosAux.obtenerPedidos(ciudadB);//Obtengo la lista de pedidos a la ciudadB
                if (listaAux != null && !listaAux.esVacia()) {
                    listaDePedidos = listaAux.clone();//Retornamos un clon de la lista
                }
            }
        }
        return listaDePedidos;
    }

    public double tramoPerfecto(int ciudadA, Cola colaCiudades, double mtsRestantes) {
        /*Dados un codigo postal, y una coleccion de codigos postales, retorna si existe alguna 
        solicitud viaje que vaya de la ciudadA a cualquier ciudad de colaCiudades. Ademas de existir una solicitud
        se debe retornar cuantos mts cubicos requiere, si el valor es negativo es porque no existe o se excede
        de la capacidad. Si es positivo o 0, quiere decir que ese tramo es perfecto*/
        Ciudad ciudadSalida = gestorCiudad.obtenerCiudad(ciudadA);
        Pedidos pedidosAux;
        double menoresMtsSolicitados = 0, mtsActual; //Se deben revisar todas las solicitudes, si es que existen, y restar a mtsRestantes, solo la menor de todas. Busco el mejor caso posible
        Lista listaAux;
        Cola colaCiudadesAux = colaCiudades.clone();//Para no alterar la cola dada por parametro
        int codPostalAux;
        SolicitudViaje solicitudActual;
        if (ciudadSalida != null) {
            pedidosAux = ciudadSalida.getSolicitudesViajes();
            if (pedidosAux != null) {
                while (!colaCiudadesAux.esVacia()) {
                    //Se chequea hasta que se termine la colección, ya que se debe verificar cada solicitud posible
                    codPostalAux = (int) colaCiudadesAux.obtenerFrente();//Nos quedamos con la ciudadActual de colaCiudades
                    colaCiudadesAux.sacar();//retiramos de la cola  
                    listaAux = pedidosAux.obtenerPedidos(codPostalAux);
                    if (listaAux != null && !listaAux.esVacia()) {
                        //Con que la lista de pedidos existea y NO sea nulo, buscamos los menos mts cubicos requeridos de la lista
                        for (int i = 1; i <= listaAux.longitud(); i++) {
                            //Para cada SolicitudViaje
                            solicitudActual = (SolicitudViaje) listaAux.recuperar(i);
                            mtsActual = solicitudActual.getCantidadMetrosCubicos();
                            if (menoresMtsSolicitados > mtsActual || menoresMtsSolicitados == 0) {
                                menoresMtsSolicitados = mtsActual;
                            }
                        }
                    }
                }
            }
        }
        if (menoresMtsSolicitados == 0) {
            //Si menoresMtsSolicitados es 0 quiere decir que no habían pedidos directametne, retorna 0
            mtsRestantes = -1;
        } else {
            mtsRestantes = mtsRestantes - menoresMtsSolicitados;//Si el menor caso ocupa más de los disponibles retorna negativo
        }
        return mtsRestantes;
    }

    public String toStringEstructura() {
        /*Por cada ciudad imprime el mapeo a muchos y sus solicitudes, solo los id de las 3 "tablas": 
        *salida: 
        *   C1 -> pedido1, pedido2, pedido3
        *   izq: C2, der: C3
        *   C2 -> p1,p2,p3
        *   izq: C4, der: C5
         */
        String estructura = "";
        Lista listaCiudades = gestorCiudad.obtenerCiudades();
        Ciudad ciudadActual;
        Pedidos pedidosActual;
        for (int i = 1; i <= listaCiudades.longitud(); i++) {
            //Para cada ciudad
            ciudadActual = (Ciudad) listaCiudades.recuperar(i);//Ciudad actual
            estructura += "Ciudad: " + ciudadActual.getCodigoPostal() + "\n\t"; //Agregamos para que ciudad es la estructura
            pedidosActual = ciudadActual.getSolicitudesViajes();
            if (pedidosActual != null) {//Si hay pedidos:
                estructura += pedidosActual.toStringEstructura();//Agregamos toda la estructura del pedido actual
                //Para cada cada lista de pedidos
            } else {
                estructura += "Sin estructuras cargadas.\n";
            }
            estructura += "\n";//Separa de la ciudad anterior
        }
        return estructura;
    }
}
