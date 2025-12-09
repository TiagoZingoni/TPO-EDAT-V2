package TDAs;

public class NodoHashMapeo {
    private Object dominio;
    private Object rango;
    private NodoHashMapeo enlace;
    //Es igual a Nodo pero en vez de Elem posee dominio y rango
    public NodoHashMapeo(Object dominio, Object rango, NodoHashMapeo enlace){
        this.dominio = dominio;
        this.rango = rango;
        this.enlace = enlace;
    }
    //Getters
    public Object getDominio(){
        return this.dominio;
    }
    public Object getRango(){
        return this.rango;
    }
    public NodoHashMapeo getEnlace(){
        return this.enlace;
    }
    //Setter
    public void setRango(Object rango){
        this.rango = rango;
    }
    public void setEnlace(NodoHashMapeo enlace){
        this.enlace = enlace;
    }
}
