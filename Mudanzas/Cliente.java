package Mudanzas;

public class Cliente {
    //Para aplicar el hasmap
    private ClaveCliente clave;
    private DatosCliente datos;
    
    public Cliente(ClaveCliente claveCliente, DatosCliente datosCliente){
        this.clave = claveCliente;
        this.datos = datosCliente;
    }

    //setter
    public void setDatosCliente(DatosCliente datosCliente){
        this.datos = datosCliente;
    }
}
