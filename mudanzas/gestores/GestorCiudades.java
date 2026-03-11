package mudanzas.gestores;

import mudanzas.Ciudad;
import tdas.Lista;
import tdas.diccionario.Diccionario;

public class GestorCiudades {

    //Las ciudades son almacenadas en un TDA Diccionario que implementa un Árbol AVL.
    private Diccionario almacenCiudades;

    public GestorCiudades() {
        almacenCiudades = new Diccionario();
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
        return agregada;
    }

    public boolean bajaCiudad(int codigoPostal) {
        boolean eliminada = false;
        //Si existe una ciudad con el codigo postal dado se eliminará del almacen y retornara true, sino false.
        eliminada = almacenCiudades.eliminar(codigoPostal);
        return eliminada;
    }

    /*
    Para la modificación primero buscamos, despues modificamos, para solicitar 
    menos veces el mismo dato se incluye la opción de modificar varios datos a la vez.
     */
    public boolean modificarNombreCiudad(int codigoPostal, String nombreCiudad) {
        boolean modificado = false;
        Ciudad ciudadAModificar;
        Object ciudadAModificarAux = almacenCiudades.obtenerInformacion(codigoPostal);
        if (ciudadAModificarAux != null) {
            //Si la ciudad existe
            modificado = true;
            ciudadAModificar = (Ciudad) ciudadAModificarAux;
            ciudadAModificar.setNombreCiudad(nombreCiudad);
        }
        return modificado;
    }

    public boolean modificarNombreProvincia(int codigoPostal, String nombreProvincia) {
        boolean modificado = false;
        Ciudad ciudadAModificar;
        Object ciudadAModificarAux = almacenCiudades.obtenerInformacion(codigoPostal);
        if (ciudadAModificarAux != null) {
            //Si la ciudad existe
            modificado = true;
            ciudadAModificar = (Ciudad) ciudadAModificarAux;
            ciudadAModificar.setNombreProvincia(nombreProvincia);
        }
        return modificado;
    }

    public boolean modificarNombreCiudadYNombreProvincia(int codigoPostal, String nombreCiudad, String nombreProvincia) {
        boolean modificado = false;
        Ciudad ciudadAModificar;
        Object ciudadAModificarAux = almacenCiudades.obtenerInformacion(codigoPostal);
        if (ciudadAModificarAux != null) {
            //Si la ciudad existe
            modificado = true;
            ciudadAModificar = (Ciudad) ciudadAModificarAux;
            ciudadAModificar.setNombreProvincia(nombreProvincia);
            ciudadAModificar.setNombreCiudad(nombreCiudad);
        }
        return modificado;
    }

    //Estructura
    public String toStringEstructura() {
        return almacenCiudades.toString();
    }

    public Lista listarClaves() {
        return almacenCiudades.listarClaves();
    }
}
