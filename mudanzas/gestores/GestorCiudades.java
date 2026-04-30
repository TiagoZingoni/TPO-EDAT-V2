package mudanzas.gestores;

import mudanzas.Ciudad;
import mudanzas.Pedidos;
import mudanzas.SolicitudViaje;
import tdas.Lista;
import tdas.diccionario.Diccionario;

public class GestorCiudades {

    //Las ciudades son almacenadas en un TDA Diccionario que implementa un Árbol AVL.
    private Diccionario almacenCiudades;
    private GestorEscritura gestorEscritura;
    private GestorRutas gestorRutas;//Para agregar y sacar ciudades cuando se da el alta y baja

    public GestorCiudades(GestorEscritura unGestorE, GestorRutas unGestorRutas) {
        almacenCiudades = new Diccionario();
        gestorEscritura = unGestorE;
        gestorRutas = unGestorRutas;
    }

    //ABM Ciudades
    public boolean altaCiudad(Ciudad unaCiudad) {
        //La clave forma parte del dato, por eso no se almacenan por separado
        /*
        Siempre que el codigo postal de la ciudad "unaCiudad" no esté dentro de almacenCiudades, 
        la ciudad se añadirá y retornará true. Sino retorna false y la ciudad no se añade.
         */
        boolean agregada = false;
        agregada = almacenCiudades.insertar(unaCiudad.getCodigoPostal(), unaCiudad);
        if (agregada) {
            gestorRutas.altaCiudad(unaCiudad.getCodigoPostal());//se agrega la ciudad (solo su clave) al grafo de rutas
        }
        //.log alta:
        gestorEscritura.objetoAgregado("Ciudad", unaCiudad.toString(), agregada);
        return agregada;
    }

    public boolean bajaCiudad(int codigoPostal) {
        boolean eliminada = false;
        //Si existe una ciudad con el codigo postal dado se eliminará del almacen y retornara true, sino false.
        eliminada = almacenCiudades.eliminar(codigoPostal);
        if (eliminada) {
            gestorRutas.bajaCiudad(codigoPostal);//se elimina la ciudad del grafo de rutas
        }
        //.log baja
        gestorEscritura.objetoEliminado("Ciudad", codigoPostal + "", eliminada);
        return eliminada;
    }

    /*
    Para la modificación primero buscamos, despues modificamos, para solicitar 
    menos veces el mismo dato se incluye la opción de modificar varios datos a la vez.
     */
    public boolean modificarNombreCiudad(int codigoPostal, String nombreCiudad) {
        boolean modificado = false;
        Ciudad ciudadAModificar = null;
        Object ciudadAModificarAux = almacenCiudades.obtenerInformacion(codigoPostal);
        if (ciudadAModificarAux != null) {
            //Si la ciudad existe
            modificado = true;
            ciudadAModificar = (Ciudad) ciudadAModificarAux;
            ciudadAModificar.setNombreCiudad(nombreCiudad);
        }
        //.log modificación
        if (ciudadAModificar != null) {
            gestorEscritura.objetoModificado("Ciudad", ciudadAModificarAux.toString(), ciudadAModificar.toString(), modificado);
        } else {
            gestorEscritura.objetoModificado("Ciudad", ciudadAModificarAux.toString(), null, modificado);
        }
        return modificado;
    }

    public boolean modificarNombreProvincia(int codigoPostal, String nombreProvincia) {
        boolean modificado = false;
        Ciudad ciudadAModificar = null;
        Object ciudadAModificarAux = almacenCiudades.obtenerInformacion(codigoPostal);
        if (ciudadAModificarAux != null) {
            //Si la ciudad existe
            modificado = true;
            ciudadAModificar = (Ciudad) ciudadAModificarAux;
            ciudadAModificar.setNombreProvincia(nombreProvincia);
        }
        if (ciudadAModificar != null) {
            gestorEscritura.objetoModificado("Ciudad", ciudadAModificarAux.toString(), ciudadAModificar.toString(), modificado);
        } else {
            gestorEscritura.objetoModificado("Ciudad", ciudadAModificarAux.toString(), null, modificado);
        }
        return modificado;
    }

    public boolean modificarNombreCiudadYNombreProvincia(int codigoPostal, String nombreCiudad, String nombreProvincia) {
        boolean modificado = false;
        Ciudad ciudadAModificar = null;
        Object ciudadAModificarAux = almacenCiudades.obtenerInformacion(codigoPostal);
        if (ciudadAModificarAux != null) {
            //Si la ciudad existe
            modificado = true;
            ciudadAModificar = (Ciudad) ciudadAModificarAux;
            ciudadAModificar.setNombreProvincia(nombreProvincia);
            ciudadAModificar.setNombreCiudad(nombreCiudad);
        }
        if (ciudadAModificar != null) {
            gestorEscritura.objetoModificado("Ciudad", ciudadAModificarAux.toString(), ciudadAModificar.toString(), modificado);
        } else {
            gestorEscritura.objetoModificado("Ciudad", ciudadAModificarAux.toString(), null, modificado);
        }
        return modificado;
    }

    //Consultas
    public Ciudad obtenerCiudad(int codigoPostal) {
        //Dado un código postal de una ciudad, retorna la ciudad si existe, sino null
        return (Ciudad) almacenCiudades.obtenerInformacion(codigoPostal);
    }

    public Lista obtenerCiudadPorPrefijo(int prefijo) {
        /*Dado un prefijo, devolver todas las ciudades cuyo código postal comienza con dicho
        prefijo. Por ejemplo si el prefijo es “83” debería considerar listar todas las ciudades
        cuyo código postal esté en el rango 8300 hasta 8399.*/
        Lista listaCiudades = new Lista();
        //El prefijo debe ser menor a 3 y mayor a 1 digito inclusive. Sino se debería usar obtenerCiudad
        if (prefijo >= 100) {
            //Si el prefijo es de 3 digitos
            listaCiudades = almacenCiudades.listarDatosRango(prefijo * 10, (prefijo * 10) + 9);
        } else if (prefijo >= 10) {
            //Si el prefijo es de 2 digitos
            listaCiudades = almacenCiudades.listarDatosRango(prefijo * 100, (prefijo * 100) + 99);
        } else if (prefijo >= 1) {
            //Si el prefijo es de 1 digitos
            listaCiudades = almacenCiudades.listarDatosRango(prefijo * 1000, (prefijo * 1000) + 999);
        }
        return listaCiudades;
    }

    public Lista obtenerCiudades() {
        //Obtenemos todas las ciudades del sistema
        Lista listaCiudades = new Lista();
        listaCiudades = almacenCiudades.listarDatos();
        return listaCiudades;
    }

    //Estructura
    public String toStringEstructura() {
        return almacenCiudades.toString();
    }

    public String listarClaves() {
        return almacenCiudades.listarClaves().toStringElementos();
    }

    //Consultas Pedidos
    public String espacioNecesario(int ciudadA, int ciudadB) {
        /*Dada una ciudad A y una ciudad B mostrar todos los pedidos y calcular cuánto
        espacio total hace falta en el camión. */
        Lista listaPedidos = new Lista();
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
            txtRespuesta = txtRespuesta + "\nEspacio necesario:" + espacioAcumulado + "mts cúbicos";
        } else {
            txtRespuesta = "Error Ciudad no encontrada o pedidos inexistentes";
        }
        return txtRespuesta;
    }
}
