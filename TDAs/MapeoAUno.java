package TDAs;
import Mudanzas.ClaveCliente;
// MapeoAUno Hash Abierto
public class MapeoAUno {
    private int TAMANO; //Constante del limite
    private NodoHashMapeo[] lista;
    private int cant;

    public MapeoAUno(int tamanoLista){
        this.TAMANO = tamanoLista;
        this.lista = new NodoHashMapeo[TAMANO-1];
        this.cant = 0;
    }

    public boolean asociar(Object ClaveCliente, Object datosCliente){
        /* Recibe un valor que representa a un elemento del dominio y un segundo valor que representa a
        un elemento del rango. Si no existe otro par que contenga a valorDominio, agrega en el mapeo el 
        par (valorDominio, valorRango). Si la operación termina con éxito devuelve verdadero y falso en
        caso contrario.*/
        boolean asociado = false;
        for(int i = 0; i<TAMANO; i++){
            
        }
        return asociado;
    }



    private int funcionHash(ClaveCliente claveCliente){
        //Se opera con clave tipo cadena
        int posicion = 0;
        String cadena = claveCliente.getClaveString();

        return posicion;
    }
}

