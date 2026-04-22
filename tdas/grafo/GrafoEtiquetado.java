package tdas.grafo;
import tdas.Lista;
import tdas.Cola;

public class GrafoEtiquetado {
    //Grafo no dirigido
    private NodoVert inicio;
    
    public GrafoEtiquetado(NodoVert inicial){
        //Crea un grafo vacio
        this.inicio = inicial;
    }
    
    public boolean insertarVertice(Object nuevoVertice){
        /*Dado un elemento de TipoVertice se lo agrega a la estructura controlando 
        que no se inserten vértices repetidos. Si puede realizar la inserción devuelve 
        verdadero, en caso contrario devuelve falso.*/
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if(aux == null){
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;
    }
    
    public boolean eliminarVertice(Object verticeEliminar){
        /*Eliminar el vertice dado y todos los arcos que le apunten, retorna true si se eliminó
        false si no se pudo*/
        boolean exito = false;
        NodoVert auxBuscado = this.inicio; //Nos da el nodo buscado
        NodoVert auxAnterior = null; //Nos da el nodo anterior al que buscamos, para eliminar caminos
        //Buscamos el nodo del vertice a eliminar
        while(auxBuscado != null && !auxBuscado.getElem().equals(verticeEliminar)){
            auxAnterior = auxBuscado;
            auxBuscado = auxBuscado.getSigVert();
        }
        if(auxBuscado != null){
            if(auxBuscado.getElem().equals(verticeEliminar)){
                //eliminamos todos los arcos del elemento a eliminar
                if(auxBuscado.getPrimerAdy()!=null){
                    eliminarArcosLlegada(auxBuscado, verticeEliminar);//Eliminar todos los arcos que le llegan al vertice a eliminar
                    auxBuscado.setPrimerAdy(null);//Elimina todos los arcos que salen del nodo a eliminar
                }
            }
            if(auxBuscado == this.inicio){
                this.inicio = auxBuscado.getSigVert();//caso especial, buscado es el inicio
            }else
                auxAnterior.setSigVert(auxBuscado.getSigVert()); //Se elimina el vertice buscado
            exito = true;
        }
        return exito;
    }
  
    private boolean eliminarArcosLlegada(NodoVert nodoAEliminar, Object contenidoVertice){
        //Elimina todos los arcos que recibe el nodo que se quiere eliminar, recorriendo los vertices de sus nodos adyacentes
        NodoAdy aux = nodoAEliminar.getPrimerAdy();
        while(aux!=null){
            eliminarArcoAux(aux.getVertice(), contenidoVertice);
            aux = aux.getSigAdy();
        }
        return true;
    }
    
    public boolean eliminarArco(Object origen, Object destino){
        //Intenta eliminar el arco entre los vertices orgien y destino, retorna true si se logró, false si no.
        boolean exito = false;
        //Verificamos si ambos vertices existen
        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;
        NodoVert aux = this.inicio;
 
        //Buscamos los nodos de los vertices
        while((auxOrigen == null || auxDestino ==null) && aux != null){
            if(aux.getElem().equals(origen)) auxOrigen = aux;
            if(aux.getElem().equals(destino)) auxDestino = aux;
            aux = aux.getSigVert();
        }
        //Si existen los dos vertices eliminamos el camino
        if(auxOrigen != null && auxDestino != null){
            exito = eliminarArcoAux(auxOrigen, destino) && eliminarArcoAux(auxDestino, origen);
            //Se elimina, si existe, el arco entre los vertices desde ambos lados
        }
        return exito;
    }
    
    private boolean eliminarArcoAux(NodoVert origen, Object destino){
        NodoAdy adyAux = origen.getPrimerAdy();
        NodoAdy adyAuxAnterior = null;
        boolean exito = false;
        if(adyAux != null){
            if(adyAux.getVertice().getElem().equals(destino)){
                //Si el primer nodo ady es el buscado, se cambia el camino al siguiente
                origen.setPrimerAdy(adyAux.getSigAdy());
                exito = true;
            }else{
                adyAuxAnterior = origen.getPrimerAdy();
                adyAux = adyAux.getSigAdy();
                while((adyAux != null)&&(exito==false)){
                    if(adyAux.getVertice().getElem().equals(destino)){
                        //Si el nodo ady es el buscado, se reemplaza por su siguiente en la lista de adyacentes 
                        adyAuxAnterior.setSigAdy(adyAux.getSigAdy());
                        exito = true;   
                    }else{
                        adyAuxAnterior = adyAux;
                        adyAux = adyAux.getSigAdy();
                    }
                }
            }
        }
        return exito;
    }
    
    public boolean insertarArco(Object vertice1, Object vertice2, Comparable etiqueta){
        /*Dados dos elementos agrega el arco en la estructura con su respectiva etiqueta, 
        sólo si ambos vértices ya existen en el grafo. Si puede realizar la inserción devuelve 
        verdadero, en caso contrario devuelve falso.*/
        boolean insertado = false, insertadoAux = true;
        NodoVert nodoV1, nodoV2;
        NodoAdy nodoAux;
        nodoV1 = ubicarVertice(vertice1);
        nodoV2 = ubicarVertice(vertice2);
        if(nodoV1 != null && nodoV2 != null && !nodoV1.equals(nodoV2)){            
            //Si ninguno de los nodos es vacío, y el arco no va a ser loop, es decir, si el nodo de salida no es el mismo que el de llegada.
            nodoAux = nodoV1.getPrimerAdy();
            while(nodoAux!=null&&insertadoAux){
                //se busca que el arcoo no exista, solo chequeamos un lado, ya que el arco existiría desde los dos vertices
                if(nodoAux.getVertice().equals(nodoV2)){
                    //Si el vertice existe, entonces se corta la iteración y no se inserta el nuevo arco.
                    insertadoAux = false;
                }
                nodoAux = nodoAux.getSigAdy();
            }
            if(insertadoAux){
                insertado = true;
                nodoV1.setPrimerAdy(new NodoAdy(nodoV2, nodoV1.getPrimerAdy(), etiqueta)); //Se agrega el arco de v1 a v2, sin afectar los arcos existentes
                nodoV2.setPrimerAdy(new NodoAdy(nodoV1, nodoV2.getPrimerAdy(), etiqueta)); //Se agrega el arco de v2 a v1, sin afectar los arcos existentes
            }
        }
        return insertado;
    }

    public Comparable obtenerArco(Object vertice1, Object vertice2){
        //Retorna la etiqueta del arco entre dos vertices, si es que existe.
        Comparable etiquetaObtenida = null;
        NodoAdy nodoAux;
        NodoVert nodoV1 = ubicarVertice(vertice1);
        if(nodoV1 != null){
            nodoAux = nodoV1.getPrimerAdy();
            while(nodoAux != null && etiquetaObtenida == null){
                if(nodoAux.getVertice().getElem().equals(vertice2)){
                    //Si el elemento del vertice es igual al elemento destino que buscamos, entonces el arco es este nodo adyacente
                    etiquetaObtenida = nodoAux.getEtiqueta();
                }
                nodoAux = nodoAux.getSigAdy();
            }
        }//Si la etiqueta no existe retorna null, se considera que no tiene por que haber una etiqueta null
        return etiquetaObtenida;
    }
    /*INOMPLETOS  
    public Lista caminoMasCorto(Object origen, Object destino){
        /* Dados dos elementos de TipoVertice (origen y destino), devuelve un camino (lista de vértices)
        que indique el camino que pasa por menos vértices que permite llegar del vértice origen al vértice
        destino. Si hay más de un camino con igual cantidad de vértices, devuelve cualquiera de ellos. Si
        alguno de los vértices no existe o no hay camino posible entre ellos devuelve la lista vacía. /
        Lista camino = new Lista(), caminoAux = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoAdy nodoAux = null;
        if(nodoOrigen != null){ //si el nodo origen existe
            nodoAux = nodoOrigen.getPrimerAdy();
            camino.insertar(nodoAux, 1);
            caminoAux = caminoMasCorto(nodoAux, destino);
            while(nodoAux!=null){
                caminoAux = caminoMasCorto(nodoAux.getVertice(), destino);
                if(caminoAux!=null && caminoAux.longitud()>camino.longitud()){

                }
            }
            if(camino.longitud()>0){
                //Si se pudo insertar
                camino.insertar(nodoOrigen, 1);
            }else{
                camino.vaciar();
            }
        }
        return camino;
    }


    //listar en anchura
    public Lista listarEnAnchura(Object origen){
        Lista visitados = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen);
         NodoAdy nodoAdy;
        if(nodoOrigen!=null){
            nodoAdy = nodoOrigen.getPrimerAdy();
            while(nodoAdy != null){
                anchuraDesde(nodoAdy.getVertice(), visitados);
                nodoAdy = nodoAdy.getSigAdy();
            }
        }
        return visitados;
    }
    private void anchuraDesde(NodoVert inicial, Lista visitados){
        Cola cola = new Cola();
        NodoVert nodoVertAux;
        NodoAdy nodoAdy = inicial.getPrimerAdy();
        cola.poner(inicial);
        while(!cola.esVacia()){
            //mientras cola no sea vacia
            nodoVertAux = (NodoVert) cola.obtenerFrente();
            cola.sacar();
            while(nodoAdy != null){
                if(visitados.localizar(nodoAdy.getVertice())<1){
                    //Si el nodo actual ya se visitó se pasa al siguiente hasta que no hayan más, si no se visito se aplica anchuraDesde
                    visitados.insertar(nodoAdy.getVertice(), visitados.longitud()+1);
                    cola.poner(nodoAdy.getVertice());
                }
                nodoAdy = nodoAdy.getSigAdy();
            }
        }
    }
    */
    
    //Lista de todos los caminos desde un nodo A a un nodo B
    public Lista listarCaminos(Object origen, Object destino){
        //Retorna todos los caminos que unen origen con destino, si no existe ningun camino o alguno de los nodos, retorna una lista vacía
        Lista caminos = new Lista(); //Lista de Todos los caminos de Origen a Destino
        Lista caminoActual = new Lista(); //Lista auxiliar, que será cada camino de Origen a Destino
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        if(nodoOrigen != null && nodoDestino != null){
            //Si ambos nodos existen buscamos todos los caminos que los unen
            listarCaminosAux(nodoOrigen, destino, caminoActual, caminos);
        }
        return caminos;
    }

    private void listarCaminosAux(NodoVert nodo, Object destino, Lista caminoActual, Lista caminos){
        //Carga la lista de caminos con todos los caminos que cumplan la condición de, iniciados en Origen, terminar en Destino
        NodoAdy nodoAdyAux; //Es un nodo auxiliar para recorrer los adyacentes del actual.
        NodoVert nodoVertAux; //Es un nodo auxiliar para almacenar los nodos vert de los adyacentes.
        caminoActual.insertar(nodo.getElem(), caminoActual.longitud()+1);//Guardamos el nodo actual en el camino actual
        if(nodo.getElem().equals(destino)){
            //Si el nodo actual es el de destino insertamos el camino actual en la lista de caminos
            caminos.insertar(caminoActual.clone(),caminos.longitud()+1);
        } else{
            //si no es el nodo actual
            nodoAdyAux = nodo.getPrimerAdy();
            while(nodoAdyAux != null){
                //mientras el nodo adyacente no sea nulo, continua la busqueda por cada camino posible
                nodoVertAux = nodoAdyAux.getVertice();
                if(caminoActual.localizar(nodoVertAux.getElem())<0){
                    //Si el nodo a buscar no forma ya parte de la lista, entramos a buscar sus adyacentes
                    //Aca se evitan los bucles
                    listarCaminosAux(nodoVertAux, destino, caminoActual, caminos);
                }
                nodoAdyAux = nodoAdyAux.getSigAdy();//siguiente nodo ady
            }
        }
        caminoActual.eliminar(caminoActual.longitud());//Se elimina el ultimo elemento puesto antes de volver a la it anterior
    }

   
    private NodoVert ubicarVertice(Object buscado){
        //Recorre la lista de vértices buscando un elemento, y si lo encuentra devuelve el enlace al nodo que lo contiene.
        NodoVert aux = this.inicio;
        while(aux != null && !aux.getElem().equals(buscado)){
            aux = aux.getSigVert();
        }
        return aux;
    }    

    public String toString(){
        //String de la estructura en formato: "salida -> entrada1, entrada2, entrada..."
        String cadena = "";
        NodoVert nodoVertActual = this.inicio;
        NodoAdy nodoAdyActual = null;
        while(nodoVertActual != null){
            cadena += nodoVertActual.getElem().toString()+" -> ";
            nodoAdyActual = nodoVertActual.getPrimerAdy();
            while(nodoAdyActual!=null){
                cadena += (nodoAdyActual.getVertice()).getElem().toString();
                nodoAdyActual = nodoAdyActual.getSigAdy();
                if(nodoAdyActual!=null){
                    //no es necesario, pero para mejorar la legibilidad de la cadena
                    cadena+=", ";
                }
            }
            cadena += "\n";
            nodoVertActual = nodoVertActual.getSigVert();
        }
        return cadena;
    }


   }
