package mudanzas.gestores;

import mudanzas.ClaveCliente;
import mudanzas.DatosCliente;
import tdas.Lista;
import tdas.MapeoAUno;

public class GestorCliente {

    //Clase utilizada para la ABM de Clientes
    MapeoAUno almacenClientes;

    public GestorCliente(int tamanoLista) {
        this.almacenClientes = new MapeoAUno(tamanoLista);
    }

    //ALTA
    public boolean altaCliente(ClaveCliente claveCliente, DatosCliente datosCliente) {
        // retorna verdadero si el cliente se creo y falso si no
        return almacenClientes.asociar(claveCliente, datosCliente);
    }

    //BAJA
    public boolean bajaCliente(ClaveCliente claveCliente) {
        return almacenClientes.desasociar(claveCliente);
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

    //MODIFICACIONES
    public boolean modificarNombre(Object claveCliente, String nombre) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if (datosClienteAux != null) {
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            //Forzado a asegurar clase por usar el TDA generico, no se carga nada que no sea cliente así que no se rompe
            datosCliente.setNombre(nombre);
        }
        return datosClienteAux != null;
    }

    public boolean modificarApellido(Object claveCliente, String apellido) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if (datosClienteAux != null) {
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setApellido(apellido);
        }
        return datosClienteAux != null;
    }

    public boolean modificarTelefono(Object claveCliente, int telefono) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if (datosClienteAux != null) {
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setTelefono(telefono);;
        }
        return datosClienteAux != null;
    }

    public boolean modificarEmail(Object claveCliente, String email) {
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if (datosClienteAux != null) {
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setEmail(email);;
        }
        return datosClienteAux != null;
    }

    //De testeo
    public int cantidadPersonas() {
        return almacenClientes.cantElementosCargados();
    }

    public boolean vaciar() {
        return almacenClientes.vaciar();
    }

    public String estructuraToString() {
        return almacenClientes.toStringEstructura();
    }
}
