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

    //Consulta
    public String obtenerCiudad(int codigoPostal) {
        //Dado un código postal de una ciudad, mostrar toda su información
        String unaCiudadString = "Ciudad no encontrada.";
        Object posibleCiudad = almacenCiudades.obtenerInformacion(codigoPostal);
        if (posibleCiudad != null) {
            unaCiudadString = posibleCiudad.toString();
        }
        return unaCiudadString;
    }

    public String obtenerCiudadPorPrefijo(int prefijo) {
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
        return listaCiudades.toStringElementos();
    }

    //Estructura
    public String toStringEstructura() {
        return almacenCiudades.toString();
    }

    public String listarClaves() {
        return almacenCiudades.listarClaves().toStringElementos();
    }
}
