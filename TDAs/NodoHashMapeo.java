package TDAs;

import Mudanzas.Object;
import Mudanzas.DatosCliente;

public class NodoHashMapeo {
    private Object dominio;
    private DatosCliente rango;
    private NodoHashMapeo enlace;
    //Es igual a Nodo pero en vez de Elem posee dominio y rango
    public NodoHashMapeo(Object Object, DatosCliente datosCliente, NodoHashMapeo enlace){
        this.dominio = Object;
        this.rango = datosCliente;
        this.enlace = enlace;
    }
    //Getters
    public Object getDominio(){
        return this.dominio;
    }
    public DatosCliente getRango(){
        return this.rango;
    }
    public NodoHashMapeo getEnlace(){
        return this.enlace;
    }
    //Setter
    public void setRango(DatosCliente datosCliente){
        this.rango = datosCliente;
    }
    public void setEnlace(NodoHashMapeo enlace){
        this.enlace = enlace;
    }
}
