package Mudanzas;
import TDAs.MapeoAUno;
import TDAs.Lista;
public class GestorCliente {
    //Clase utilizada para la ABM de Clientes
    MapeoAUno almacenClientes;

    public GestorCliente(int tamanoLista){
        this.almacenClientes = new MapeoAUno(tamanoLista);
    }

    //ALTA
    public boolean altaCliente(ClaveCliente claveCliente, DatosCliente datosCliente){
        // retorna verdadero si el cliente se creo y falso si no
        return almacenClientes.asociar(claveCliente, datosCliente);
    }
    //BAJA
    public boolean bajaCliente(ClaveCliente claveCliente){
        return almacenClientes.desasociar(claveCliente);
    }
    //CONSULTAS
    public String listarClientes(){
        //lista todos los clientes almacenados
        String lista = "";
        Lista listaClaves = almacenClientes.obtenerConjuntoDominio(), listaDatos = almacenClientes.obtenerConjuntoRango();
        int limite = listaClaves.longitud(); // sirve cualquiera de las 2 longitudes
        for(int i = 1; i <= limite; i++){
            //Generamos la listas
            lista = lista+"P;"+listaClaves.recuperar(i).toString() + listaDatos.recuperar(i).toString()+"\n";
        }
        return lista;
    }

    //MODIFICACIONES
    public boolean modificarNombre(Object claveCliente, String nombre){
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if(datosClienteAux != null){
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            //Forzado a asegurar clase por usar el TDA generico, no se carga nada que no sea cliente así que no se rompe
            datosCliente.setNombre(nombre);
        }
        return datosClienteAux != null;
    }
    public boolean modificarApellido(Object claveCliente, String apellido){
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if(datosClienteAux != null){
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setApellido(apellido);
        }
        return datosClienteAux != null;
    }
    public boolean modificarTelefono(Object claveCliente, int telefono){
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if(datosClienteAux != null){
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setTelefono(telefono);;
        }
        return datosClienteAux != null;
    }
    public boolean modificarEmail(Object claveCliente, String email){
        //Si el cliente existe modifica y retorna verdadero, sino falso
        Object datosClienteAux = almacenClientes.obtenerValor(claveCliente);
        if(datosClienteAux != null){
            DatosCliente datosCliente = (DatosCliente) datosClienteAux;
            datosCliente.setEmail(email);;
        }
        return datosClienteAux != null;
    }
    
    //De testeo
    public int cantidadPersonas(){
        return almacenClientes.cantElementosCargados();
    }
    public boolean vaciar(){
        return almacenClientes.vaciar();
    }
    public String estructuraToString(){
        return almacenClientes.toStringEstructura();
    }
}