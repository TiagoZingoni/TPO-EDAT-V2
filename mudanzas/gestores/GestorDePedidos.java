package mudanzas.gestores;

import mudanzas.entidades.Ciudad;
import mudanzas.entidades.ClaveCliente;
import mudanzas.entidades.Pedidos;
import mudanzas.entidades.SolicitudViaje;
import tdas.lineales.Cola;
import tdas.lineales.Lista;

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
            exito = true;
        }
        gestorEscritura.objetoAgregado("Solicitud", unaSolicitud.toString(codPostalSalida, codPostalLlegada), exito);
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

    private Lista listaDePedidos(int ciudadA, int ciudadB) {
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

    public String pedidosYEspacio(int ciudadA, int ciudadB) {
        //Para la consulta Pedidos entre dos ciudades y espacio necesario
        Lista listaAux = this.listaDePedidos(ciudadA, ciudadB);
        SolicitudViaje solActual;
        double mtsNecesarios = 0;
        String pedidosYEspacioSt = "";
        if (listaAux != null) {
            pedidosYEspacioSt += "Lista de Pedidos entre: " + ciudadA + " y " + ciudadB + "\n";
            for (int i = 1; i <= listaAux.longitud(); i++) {
                solActual = (SolicitudViaje) listaAux.recuperar(i);
                mtsNecesarios += solActual.getCantidadMetrosCubicos();
                pedidosYEspacioSt += solActual.toString(ciudadA, ciudadB) + "\n";
            }
            pedidosYEspacioSt += "Se necesita un espacio minimo de " + mtsNecesarios + " mts cubicos\n";
        } else {
            pedidosYEspacioSt = "No se encontraron pedidos entre: " + ciudadA + " y " + ciudadB;
        }
        return pedidosYEspacioSt;
    }

    public String posiblesPedidosXEspacio(int codPostalSalida, int codPostalLlegada, double mtsEntrada) {
        Lista listaCiudades = gestorRutas.caminoConMenorDistancia(codPostalSalida, codPostalLlegada);
        Lista listaAux;
        String posiblesPedidosXEspacioSt = "";
        SolicitudViaje solActual, solicitudAux;
        double mtsNecesarios = 0, mtsDisponibles = mtsEntrada;
        Cola colaRutaAux = new Cola();
        int ciudadSalidaAux,
                ciudadLlegadaAux;

        if (listaCiudades != null && !listaCiudades.esVacia()) {
            //Obtenemos cuanto sobra de espacio, si es que sobra
            listaAux = this.listaDePedidos(codPostalSalida, codPostalLlegada);
            if (listaAux != null && !listaAux.esVacia()) {//Si hay pedidos:
                posiblesPedidosXEspacioSt += "Para los Pedidos entre: " + codPostalSalida + " y " + codPostalLlegada + " sobran: \n";
                for (int i = 1; i <= listaAux.longitud(); i++) {
                    //Para cada solicitud viaje de la lista:
                    solActual = (SolicitudViaje) listaAux.recuperar(i);
                    mtsNecesarios += solActual.getCantidadMetrosCubicos();
                }
            } else {
                posiblesPedidosXEspacioSt += "No hay pedidos entre" + codPostalSalida + " y " + codPostalLlegada + " sobran:\n";
            }
            mtsDisponibles = mtsDisponibles - mtsNecesarios; //Metros cubicos que nos quedan disponible post proceso
            posiblesPedidosXEspacioSt += mtsDisponibles + "mts cubicos\n";
            if (mtsDisponibles > 0) {
                posiblesPedidosXEspacioSt += "Pedidos que ocupan menos que eso y quedan de pasada:\n";
                for (int i = 1; i <= listaCiudades.longitud(); i++) {
                    //agrego cada ciudad a una cola, para poder después obtener los pedidos
                    colaRutaAux.poner(listaCiudades.recuperar(i));
                }
                int nAux = 1;
                while (!colaRutaAux.esVacia()) {
                    //Buscamos todas las solicitudes de viaje dentro de la ruta obtenida
                    ciudadSalidaAux = (int) colaRutaAux.obtenerFrente();//Para la ciudad actual chequeamos las que faltan de la ruta
                    nAux++;//Se empieza desde 2
                    for (int a = nAux; a <= listaCiudades.longitud(); a++) {
                        //Para cada elemento de la lista chequeamos sus siguientes solicitudes
                        ciudadLlegadaAux = (int) listaCiudades.recuperar(a);
                        if (!(ciudadSalidaAux == codPostalSalida && ciudadLlegadaAux == codPostalLlegada)) {
                            //Para no volver a chequear todos los pedidos de la ciudadA y B original entre si
                            listaAux = this.listaDePedidos(ciudadSalidaAux, ciudadLlegadaAux);
                            if (listaAux != null) {
                                for (int i = 1; i <= listaAux.longitud(); i++) {
                                    //Para cada pedido nuevo, chequeamos si cuple con el requisito, si si la imprimimos
                                    solicitudAux = (SolicitudViaje) listaAux.recuperar(i);
                                    if (solicitudAux.getCantidadMetrosCubicos() <= mtsDisponibles) {
                                        posiblesPedidosXEspacioSt += solicitudAux.toString(ciudadSalidaAux, ciudadLlegadaAux) + "\n";
                                    }
                                }
                            }
                        }
                    }
                    colaRutaAux.sacar();//Ya usado
                }
            }

        } else {
            posiblesPedidosXEspacioSt += "No existen pedidos o camino entre " + codPostalSalida + " y " + codPostalLlegada;
        }
        return posiblesPedidosXEspacioSt;
    }

    private double tramoPerfecto(int ciudadA, Cola colaCiudades, double mtsRestantes) {
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

    public String caminoPerfecto(Cola colaCiudadesEntrada, double metrosRestantes) {
        String caminoPerfectoSt = "";
        boolean caminoPerfecto = true;
        int ciudadActual;
        if (gestorRutas.caminoPosible(colaCiudadesEntrada.clone())) {
            //Si el camino ingresado por parametro existe
            while (caminoPerfecto && !colaCiudadesEntrada.esVacia()) {
                //Para cada ciudad de colaCiudadesEntrada, menos la ultima, y mientras siga siendo un camino perfecto verificamos que:
                ciudadActual = (int) colaCiudadesEntrada.obtenerFrente();
                colaCiudadesEntrada.sacar();
                if (!colaCiudadesEntrada.esVacia()) {
                    //Si el sacado no es el ultimo de la lista, se revisa camino perfecto
                    metrosRestantes = this.tramoPerfecto(ciudadActual, colaCiudadesEntrada, metrosRestantes);
                }
                caminoPerfecto = metrosRestantes >= 0;
            }
            if (caminoPerfecto) {
                caminoPerfectoSt += "El camino dado SI es un camino perfecto\n";
            } else {
                caminoPerfectoSt += "El camino dado NO es un camino perfecto\n";
            }
        } else {
            caminoPerfectoSt += "Ruta no encontrada\n";
        }
        return caminoPerfectoSt;
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
