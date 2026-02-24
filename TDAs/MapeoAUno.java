package TDAs;

import Mudanzas.ClaveCliente;

// MapeoAUno Hash Abierto
public class MapeoAUno {
    private int TAMANO; //Constante del limite
    private NodoHashMapeo[] tabla;
    private int cant;

    public MapeoAUno(int tamanoLista){
        this.TAMANO = tamanoLista;
        this.tabla = new NodoHashMapeo[TAMANO];
        this.cant = 0;
    }

    public boolean asociar(Object dominio, Object rango){
        /* Recibe un valor que representa a un elemento del dominio y un segundo valor que representa a
        un elemento del rango. Si no existe otro par que contenga a valorDominio, agrega en el mapeo el 
        par (valorDominio, valorRango). Si la operación termina con éxito devuelve verdadero y falso en
        caso contrario.*/
        int posicion = funcionHash(dominio); //lugar donde iría el nuevo par
        boolean asociado = true;
        NodoHashMapeo actual = tabla[posicion]; //Nodo que estamos evaluando actualmente

        while(actual!=null && asociado){
            // Buscamos que el dominio no exista, así como agregarlo al final de la "lista" de objetos
            if(actual.getDominio().equals(dominio)){
                asociado = false;
            }
            actual = actual.getEnlace();
        }
        if(asociado){
            // Si el nodo no existía lo agrego como primero
            tabla[posicion] = new NodoHashMapeo(dominio,rango,tabla[posicion]);
            cant++;// +1 en cantidad de objetos en la tabla si se agrego
        }
        return asociado; //true si se agregó, false si no se agrego porque ya existía
    }

    public boolean desasociar(Object dominio){
        /* Elimina el par cuyo dominio coincida con el valor recibido por parámetro. Si lo encuentra y la
        operación de eliminación termina con éxito devuelve verdadero y falso en caso contrario.*/
        boolean desasociado = false;
        int posicion = funcionHash(dominio);// Lugar del que se eliminaría el par de dominio
        NodoHashMapeo actual = tabla[posicion]; // Nodo que estamos evaluando actualmente
        NodoHashMapeo anterior = null; //Nodo que sirve de referencia para eliminar nodo actual si es el que buscamos borrar
        
        while(actual != null && !desasociado){
            if((actual.getDominio()).equals(dominio)){// si es el que buscamos eliminar
                if(anterior == null){
                    tabla[posicion] = actual.getEnlace();//si es el primero de la lista
                }else{
                    anterior.setEnlace(actual.getEnlace());//si está en el medio o al final
                }
                cant--;
                desasociado = true;
            }
            anterior = actual; //nodo que sera anterior al siguiente
            actual = actual.getEnlace();
        }
        return desasociado;
    }

    public Object obtenerValor(Object dominio){
        /*si en el mapeo se encuentra almacenado algún par cuyo dominio es valorDominio, esta operación
        devuelve el valor de rango asociado a él. Precondición: valorDominio está en el mapeo (si no existe,
        no se puede asegurar el funcionamiento de la operación).*/
        Object rango = null;// valor buscado
        int posicion = funcionHash(dominio);//ubicación del nodo buscado
        NodoHashMapeo actual = tabla[posicion]; //Nodo que estamos evaluando actualmente
        
        while(actual != null){
            if(actual.getDominio().equals(dominio)){
                rango = actual.getRango(); //obtengo el valor buscado
                actual = null; //finalizo el while
            }else{
                actual = actual.getEnlace();
            }

        }
        return rango;
    }

    public Lista obtenerConjuntoDominio(){
        // devuelve una lista con todos los valores de tipo dominio almacenados en el mapeo.
        int cantAgregados = 0, posicion=0;//posicion = pos actual en la tabla
        Lista lista = new Lista();
        NodoHashMapeo nodoActual = null;

        while(cantAgregados < cant && posicion<TAMANO){
        //itera mientras el limite sea menor a la cantidad de elementos en la tabla, para que no recorra toda la tabla si ya no quedan más elementos
            nodoActual=tabla[posicion];
            while(nodoActual!=null){
                lista.insertar(nodoActual.getDominio(),cantAgregados+1);
                cantAgregados++;//se suma un agregado a la table
                nodoActual = nodoActual.getEnlace();
            }
            posicion++;
        }
        return lista;
    }
    
    public Lista obtenerConjuntoRango(){
        //devuelve un conjunto con la unión de todos los valores de tipo rango almacenados en el mapeo.
        int cantAgregados = 0, posicion=0;//posicion = pos actual en la tabla
        Lista lista = new Lista();
        NodoHashMapeo nodoActual = null;

        while(cantAgregados < cant && posicion<TAMANO){
        //itera mientras el limite sea menor a la cantidad de elementos en la tabla, para que no recorra toda la tabla si ya no quedan más elementos
            nodoActual=tabla[posicion];
            while(nodoActual!=null){
                lista.insertar(nodoActual.getRango(),cantAgregados+1);
                cantAgregados++;//se suma un agregado a la table
                nodoActual = nodoActual.getEnlace();
            }
            posicion++;
        }
        return lista;
    }

    public boolean esVacio(){
        //devuelve falso si hay al menos un par cargado en el mapeo y verdadero en caso contrario.
        return cant==0;
    }

    public int longitud(){
        //retorna el tamaño total de la tabla
        return TAMANO;
    }

    public int cantElementosCargados(){
        //retorna solo la cantidad de elementos cargados
        return cant;
    }

    //METODO DE PRUEBAS
    @Override
    public String toString(){
        String cadena = "";
        int cantAgregados = 0, posicion=0;//posicion = pos actual en la tabla
        NodoHashMapeo nodoActual = null;

        while(cantAgregados < cant && posicion<TAMANO){
        //itera mientras el limite sea menor a la cantidad de elementos en la tabla, para que no recorra toda la tabla si ya no quedan más elementos
            nodoActual=tabla[posicion];
            while(nodoActual!=null){
                cadena = cadena + nodoActual.getRango().toString()+nodoActual.getDominio().toString()+"\n";
                cantAgregados++;//se suma un agregado a la tabla
                nodoActual = nodoActual.getEnlace();
            }
            posicion++;
        }
        return cadena;
    }
    public boolean vaciar(){
        for(int i = 0; i<TAMANO;i++){
            tabla[i]=null;
        }
        cant = 0;
        return true;
    }
    private int funcionHash(Object dominio){
        /* Como la función hash deberá estar definida sólo para el tipo del
        dominio uso .datosFuncionHash() para mantener la solicitud del string generalizada*/
        //suma de los valores ASCII para cada carácter de la cadena y resultado MOD TAMANIO como clave
        String cadena = dominio.toString();
        int suma = 0, limite = cadena.length();
        //Para cada caracter de cadena:
        for(int i =0;i<limite; i++){
            suma = suma + cadena.charAt(i);
        }
        return Math.abs(suma%TAMANO);
    }
}

