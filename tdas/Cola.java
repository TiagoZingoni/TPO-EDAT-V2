package tdas;

public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    public Cola(){
        this.frente = null;
        this.fin = null;
    }
    public boolean poner(Object elem){
        Nodo aux = new Nodo(elem, null);
        if(frente == null){
            frente = aux;
            fin = frente;
        }else{
            fin.setEnlace(aux);
            fin = aux;
        }
        return true;
    }
    public boolean sacar(){
        boolean exito = false;
        if(frente != null){
            if(frente.getEnlace()!=null){
                frente = frente.getEnlace();
            }else{
                frente = null;
                fin = null;
            }
            exito = true;
        }
        return exito;
    }
    public Object obtenerFrente(){
        Object elem = null;
        if(frente != null)
            elem = frente.getElemento();
        return elem;
    }
    public boolean esVacia(){
        return (frente == null);
    }
    public void vaciar(){
        frente = null;
        fin = null;
    }
    public Cola clone(){
        Cola clon = new Cola();
        if(this.frente!=null){
            Nodo n = this.frente;
            clon.frente = new Nodo(n.getElemento(), null);
            Nodo aux = clon.frente;
            while(n.getEnlace()!=null){
                n = n.getEnlace();
                aux.setEnlace(new Nodo(n.getElemento(), null));
                aux = aux.getEnlace();
            }
            clon.fin = aux;
        }
        return clon;
    }
    
    public String toString(){
        String txt = "";
        if(frente != null){
            Nodo aux = frente;
            txt = "[ ";
            while(aux != null){
                txt += aux.getElemento().toString();
                aux = aux.getEnlace();
                if(aux!=null)
                    txt+=", ";
            }
            txt +="]";
        }
        return txt;
    }
}
