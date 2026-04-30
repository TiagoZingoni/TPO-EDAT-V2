package mudanzas.gestores;

import mudanzas.ClaveCliente;
import mudanzas.DatosCliente;
import tdas.Lista;
import tdas.MapeoAUno;

public class GestorCliente {

    //Clase utilizada para la ABM de Clientes
    MapeoAUno almacenClientes;
    GestorEscritura gestorEsc;

    public GestorCliente(int tamanoLista, GestorEscritura gestorEscritura) {
        this.almacenClientes = new MapeoAUno(tamanoLista);
        gestorEsc = gestorEscritura; //Para el log
    }

    //ALTA
    public boolean altaCliente(ClaveCliente claveCliente, DatosCliente datosCliente) {
        // retorna verdadero si el cliente se creo y falso si no
        boolean exito = almacenClientes.asociar(claveCliente, datosCliente);//Para el log
        gestorEsc.objetoAgregado("Cliente", claveCliente.toString() + datosCliente.toString(), exito);
        return exito;
    }

    //BAJA
    public boolean bajaCliente(ClaveCliente claveCliente) {
        boolean exito = almacenClientes.desasociar(claveCliente);
        gestorEsc.objetoEliminado("Cliente", claveCliente.toString(), exito);//Para el log
        return exito;
    }

    //CONSULTAS
    public boolean existeCliente(ClaveCliente unaClave) {
        //Retorna true si el cliente existe en la estructura, false si no.
        return almacenClientes.existeDominio(unaClave);
    }

    public Lista listarClientes() {
        //lista todos los clientes almacenados
        Lista listaClaves = almacenClientes.obtenerConjuntoDominio(), listaDatos = almacenClientes.obtenerConjuntoRango();
        Lista listaClientes = new Lista();
        int limite = listaClaves.longitud(); // sirve cualquiera de las 2 longitudes
        for (int i = 1; i <= limite; i++) {
            //Generamos la lista de String de cliente
            listaClientes.insertar("P;" + listaClaves.recuperar(i).toString() + listaDatos.recuperar(i).toString(), i);
        }
        return listaClientes;
    }

    public Object obtenerCliente(ClaveCliente unaClave) {
        //Retorna la información del cliente dado
        return almacenClientes.obtenerValor(unaClave);
    }

    //MODIFICACIONES
    public boolean modificarNombre(Object claveCliente, String nombre) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        boolean resultado = datosClienteAux != null;
        if (resultado) {//Si el cliente existe
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            //Forzado a asegurar clase por usar el TDA generico, no se carga nada que no sea cliente así que no se rompe
            datosCliente.setNombre(nombre);
            gestorEsc.objetoModificado("Cliente", claveCliente.toString() + datosClienteAux.toString(), claveCliente.toString() + datosCliente.toString(), resultado);
        } else {
            //Si no se pudo
            gestorEsc.objetoModificado("Cliente", claveCliente.toString(), null, resultado);
        }
        return resultado;
    }

    public boolean modificarApellido(Object claveCliente, String apellido) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        boolean resultado = datosClienteAux != null;
        if (resultado) {
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setApellido(apellido);
            gestorEsc.objetoModificado("Cliente", claveCliente.toString() + datosClienteAux.toString(), claveCliente.toString() + datosCliente.toString(), resultado);
        } else {
            //Si no se pudo
            gestorEsc.objetoModificado("Cliente", claveCliente.toString(), null, resultado);
        }
        return resultado;
    }

    public boolean modificarTelefono(Object claveCliente, String telefono) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        boolean resultado = datosClienteAux != null;
        if (resultado) {
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setTelefono(telefono);
            gestorEsc.objetoModificado("Cliente", claveCliente.toString() + datosClienteAux.toString(), claveCliente.toString() + datosCliente.toString(), resultado);
        } else {
            //Si no se pudo
            gestorEsc.objetoModificado("Cliente", claveCliente.toString(), null, resultado);
        }
        return resultado;
    }

    public boolean modificarEmail(Object claveCliente, String email) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        boolean resultado = datosClienteAux != null;
        if (resultado) {
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setEmail(email);
            gestorEsc.objetoModificado("Cliente", claveCliente.toString() + datosClienteAux.toString(), claveCliente.toString() + datosCliente.toString(), resultado);
        } else {
            //Si no se pudo
            gestorEsc.objetoModificado("Cliente", claveCliente.toString(), null, resultado);
        }
        return resultado;
    }

    //De testeo
    public int cantidadPersonas() {
        return almacenClientes.cantElementosCargados();
    }

    public boolean vaciar() {
        return almacenClientes.vaciar();
    }

    public String toStringEstructura() {
        return almacenClientes.toStringEstructura();
    }
}
