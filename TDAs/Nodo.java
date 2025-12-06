package TDAs;

public class Nodo {
    private Object elemento;
    private Nodo enlace;
    
    //Constructor
    public Nodo(Object elemento, Nodo enlace){
        this.elemento = elemento;
        this.enlace = enlace;
    }
    
    //Observadores
    public Object getElemento(){
        return this.elemento;
    }
    public Nodo getEnlace(){
        return this.enlace;
    }
    
    //Modificadores
    public void setElemento(Object elemento){
        this.elemento = elemento;
    }
    public void setEnlace(Nodo enlace){
        this.enlace = enlace;
    }
}
