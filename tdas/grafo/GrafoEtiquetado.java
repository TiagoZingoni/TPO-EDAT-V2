package tdas.grafo;

import tdas.Cola;
import tdas.Lista;

public class GrafoEtiquetado {

    //Grafo no dirigido
    private NodoVert inicio;

    public GrafoEtiquetado(NodoVert inicial) {
        //Crea un grafo vacio
        this.inicio = inicial;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        /*Dado un elemento de TipoVertice se lo agrega a la estructura controlando 
        que no se inserten vértices repetidos. Si puede realizar la inserción devuelve 
        verdadero, en caso contrario devuelve falso.*/
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    public boolean eliminarVertice(Object verticeEliminar) {
        /*Eliminar el vertice dado y todos los arcos que le apunten, retorna true si se eliminó
        false si no se pudo*/
        boolean exito = false;
        NodoVert auxBuscado = this.inicio; //Nos da el nodo buscado
        NodoVert auxAnterior = null; //Nos da el nodo anterior al que buscamos, para eliminar caminos
        //Buscamos el nodo del vertice a eliminar
        while (auxBuscado != null && !auxBuscado.getElem().equals(verticeEliminar)) {
            auxAnterior = auxBuscado;
            auxBuscado = auxBuscado.getSigVert();
        }
        if (auxBuscado != null) {
            if (auxBuscado.getElem().equals(verticeEliminar)) {
                //eliminamos todos los arcos del elemento a eliminar
                if (auxBuscado.getPrimerAdy() != null) {
                    eliminarArcosLlegada(auxBuscado, verticeEliminar);//Eliminar todos los arcos que le llegan al vertice a eliminar
                    auxBuscado.setPrimerAdy(null);//Elimina todos los arcos que salen del nodo a eliminar
                }
            }
            if (auxBuscado == this.inicio) {
                this.inicio = auxBuscado.getSigVert();//caso especial, buscado es el inicio
            } else {
                auxAnterior.setSigVert(auxBuscado.getSigVert()); //Se elimina el vertice buscado

            }
            exito = true;
        }
        return exito;
    }

    private boolean eliminarArcosLlegada(NodoVert nodoAEliminar, Object contenidoVertice) {
        //Elimina todos los arcos que recibe el nodo que se quiere eliminar, recorriendo los vertices de sus nodos adyacentes
        NodoAdy aux = nodoAEliminar.getPrimerAdy();
        while (aux != null) {
            eliminarArcoAux(aux.getVertice(), contenidoVertice);
            aux = aux.getSigAdy();
        }
        return true;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        //Intenta eliminar el arco entre los vertices orgien y destino, retorna true si se logró, false si no.
        boolean exito = false;
        //Verificamos si ambos vertices existen
        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;
        NodoVert aux = this.inicio;

        //Buscamos los nodos de los vertices
        while ((auxOrigen == null || auxDestino == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxOrigen = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxDestino = aux;
            }
            aux = aux.getSigVert();
        }
        //Si existen los dos vertices eliminamos el camino
        if (auxOrigen != null && auxDestino != null) {
            exito = eliminarArcoAux(auxOrigen, destino) && eliminarArcoAux(auxDestino, origen);
            //Se elimina, si existe, el arco entre los vertices desde ambos lados
        }
        return exito;
    }

    private boolean eliminarArcoAux(NodoVert origen, Object destino) {
        NodoAdy adyAux = origen.getPrimerAdy();
        NodoAdy adyAuxAnterior = null;
        boolean exito = false;
        if (adyAux != null) {
            if (adyAux.getVertice().getElem().equals(destino)) {
                //Si el primer nodo ady es el buscado, se cambia el camino al siguiente
                origen.setPrimerAdy(adyAux.getSigAdy());
                exito = true;
            } else {
                adyAuxAnterior = origen.getPrimerAdy();
                adyAux = adyAux.getSigAdy();
                while ((adyAux != null) && (exito == false)) {
                    if (adyAux.getVertice().getElem().equals(destino)) {
                        //Si el nodo ady es el buscado, se reemplaza por su siguiente en la lista de adyacentes 
                        adyAuxAnterior.setSigAdy(adyAux.getSigAdy());
                        exito = true;
                    } else {
                        adyAuxAnterior = adyAux;
                        adyAux = adyAux.getSigAdy();
                    }
                }
            }
        }
        return exito;
    }

    public boolean insertarArco(Object vertice1, Object vertice2, Comparable etiqueta) {
        /*Dados dos elementos agrega el arco en la estructura con su respectiva etiqueta, 
        sólo si ambos vértices ya existen en el grafo. Si puede realizar la inserción devuelve 
        verdadero, en caso contrario devuelve falso.*/
        boolean insertado = false, insertadoAux = true;
        NodoVert nodoV1, nodoV2;
        NodoAdy nodoAux;
        nodoV1 = ubicarVertice(vertice1);
        nodoV2 = ubicarVertice(vertice2);
        if (nodoV1 != null && nodoV2 != null && !nodoV1.equals(nodoV2)) {
            //Si ninguno de los nodos es vacío, y el arco no va a ser loop, es decir, si el nodo de salida no es el mismo que el de llegada.
            nodoAux = nodoV1.getPrimerAdy();
            while (nodoAux != null && insertadoAux) {
                //se busca que el arcoo no exista, solo chequeamos un lado, ya que el arco existiría desde los dos vertices
                if (nodoAux.getVertice().equals(nodoV2)) {
                    //Si el vertice existe, entonces se corta la iteración y no se inserta el nuevo arco.
                    insertadoAux = false;
                }
                nodoAux = nodoAux.getSigAdy();
            }
            if (insertadoAux) {
                insertado = true;
                nodoV1.setPrimerAdy(new NodoAdy(nodoV2, nodoV1.getPrimerAdy(), etiqueta)); //Se agrega el arco de v1 a v2, sin afectar los arcos existentes
                nodoV2.setPrimerAdy(new NodoAdy(nodoV1, nodoV2.getPrimerAdy(), etiqueta)); //Se agrega el arco de v2 a v1, sin afectar los arcos existentes
            }
        }
        return insertado;
    }

    public Comparable obtenerArco(Object vertice1, Object vertice2) {
        //Retorna la etiqueta del arco entre dos vertices, si es que existe.
        Comparable etiquetaObtenida = null;
        NodoAdy nodoAux;
        NodoVert nodoV1 = ubicarVertice(vertice1);
        if (nodoV1 != null) {
            nodoAux = nodoV1.getPrimerAdy();
            while (nodoAux != null && etiquetaObtenida == null) {
                if (nodoAux.getVertice().getElem().equals(vertice2)) {
                    //Si el elemento del vertice es igual al elemento destino que buscamos, entonces el arco es este nodo adyacente
                    etiquetaObtenida = nodoAux.getEtiqueta();
                }
                nodoAux = nodoAux.getSigAdy();
            }
        }//Si la etiqueta no existe retorna null, se considera que no tiene por que haber una etiqueta null
        return etiquetaObtenida;
    }

    //===========================CAMINOS===========================//
    public Lista caminoMasCorto(Object vertice1, Object vertice2) {
        //Retorna el camino más corto del vertice1 al vertice2. Entre dos caminos de igual longitud devuelve cualquiera. Si no existe un camino,
        //o alguno de los vertices, se retorna una lista vacía.
        Lista camino = new Lista(); //Lista a retornar con el camino hallado
        NodoVert aux = this.inicio;
        NodoVert nodoOrigen = ubicarVertice(vertice1);
        NodoVert nodoDestino = ubicarVertice(vertice2);
        if ((nodoOrigen != null && nodoDestino != null) && !(vertice1.equals(vertice2))) {
            camino = caminoAnchura(nodoOrigen, nodoDestino);
        } else if ((nodoOrigen != null && nodoDestino != null) && vertice1.equals(vertice2)) {
            camino.insertar(vertice1, 1);
        }
        return camino;
    }

    private Lista caminoAnchura(NodoVert origen, NodoVert destino) {
        Cola cola = new Cola();//En vez de ser cada nodo como sería en BFS, es una cola de listas, para tener los caminos
        Lista visitados = new Lista();//nodos visitados
        boolean encontrado = false;//si todavía no se encontró es false, si se encontró , true
        Lista caminoResultado = new Lista();

        //La cola mantendrá caminos, se empieza con un camino inicial
        Lista caminoInicial = new Lista();
        caminoInicial.insertar(origen.getElem(), 1);

        cola.poner(caminoInicial);
        visitados.insertar(origen.getElem(), 1);

        while (!cola.esVacia() && !encontrado) {
            //Se saca el camino de la lista
            Lista caminoActual = (Lista) cola.obtenerFrente();
            cola.sacar();

            NodoVert nodoActual = ubicarVertice(caminoActual.recuperar(caminoActual.longitud())); //del camino actual nos quedamos con el utlimo ingresado

            if (nodoActual.equals(destino)) {
                encontrado = true;
                caminoResultado = caminoActual;
            } else {
                //Si el nodo no era el buscado se sigue con sus adyacentes
                NodoAdy nodoAux = nodoActual.getPrimerAdy();
                while (nodoAux != null) {
                    NodoVert nodoVertAux = nodoAux.getVertice();

                    if (visitados.localizar(nodoVertAux.getElem()) <= 0) {
                        visitados.insertar(nodoVertAux.getElem(), visitados.longitud() + 1);
                        //Por cada vecino encontrado se clona el camino actual y se agrega el vecino
                        Lista nuevoCamino = caminoActual.clone();
                        nuevoCamino.insertar(nodoVertAux.getElem(), nuevoCamino.longitud() + 1);

                        cola.poner(nuevoCamino);//Se agrega la lista a la cola
                    }
                    nodoAux = nodoAux.getSigAdy();
                }
            }
        }
        return caminoResultado;
    }

    //Lista de todos los caminos desde un nodo A a un nodo B
    public Lista listarCaminos(Object origen, Object destino) {
        //Retorna todos los caminos que unen origen con destino, si no existe ningun camino o alguno de los nodos, retorna una lista vacía
        Lista caminos = new Lista(); //Lista de Todos los caminos de Origen a Destino
        Lista caminoActual = new Lista(); //Lista auxiliar, que será cada camino de Origen a Destino
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        if (nodoOrigen != null && nodoDestino != null) {
            //Si ambos nodos existen buscamos todos los caminos que los unen
            listarCaminosAux(nodoOrigen, destino, caminoActual, caminos);
        }
        return caminos;
    }

    private void listarCaminosAux(NodoVert nodo, Object destino, Lista caminoActual, Lista caminos) {
        //Carga la lista de caminos con todos los caminos que cumplan la condición de, iniciados en Origen, terminar en Destino
        NodoAdy nodoAdyAux; //Es un nodo auxiliar para recorrer los adyacentes del actual.
        NodoVert nodoVertAux; //Es un nodo auxiliar para almacenar los nodos vert de los adyacentes.
        caminoActual.insertar(nodo.getElem(), caminoActual.longitud() + 1);//Guardamos el nodo actual en el camino actual
        if (nodo.getElem().equals(destino)) {
            //Si el nodo actual es el de destino insertamos el camino actual en la lista de caminos
            caminos.insertar(caminoActual.clone(), caminos.longitud() + 1);
        } else {
            //si no es el nodo actual
            nodoAdyAux = nodo.getPrimerAdy();
            while (nodoAdyAux != null) {
                //mientras el nodo adyacente no sea nulo, continua la busqueda por cada camino posible
                nodoVertAux = nodoAdyAux.getVertice();
                if (caminoActual.localizar(nodoVertAux.getElem()) < 0) {
                    //Si el nodo a buscar no forma ya parte de la lista, entramos a buscar sus adyacentes
                    //Aca se evitan los bucles
                    listarCaminosAux(nodoVertAux, destino, caminoActual, caminos);
                }
                nodoAdyAux = nodoAdyAux.getSigAdy();//siguiente nodo ady
            }
        }
        caminoActual.eliminar(caminoActual.longitud());//Se elimina el ultimo elemento puesto antes de volver a la it anterior
    }

    public Lista caminoMenorRecorrido(Object origen, Object destino) {
        //Retorna el camino entre origen y destino que menor recorrido tenga, la ultima posicion del arreglo es la distancia
        Lista camino = new Lista(); //Lista a retornar con el camino hallado
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        if ((nodoOrigen != null && nodoDestino != null) && !origen.equals(destino)) {
            caminoMenorRecorridoAux(nodoOrigen, nodoDestino, new Lista(), camino, 0, 0);
        } else if ((nodoOrigen != null && nodoDestino != null) && origen.equals(destino)) {
            camino.insertar(destino, 1);
            camino.insertar(0, 2);
        }
        return camino;
    }

    private double caminoMenorRecorridoAux(NodoVert nodoActual, NodoVert destino, Lista caminoActual, Lista mejorCamino, double mejorDistancia, double distanciaAcumulada) {
        NodoAdy nodoAdyAux = nodoActual.getPrimerAdy();
        NodoVert nodoVertAux;
        double distanciaAux;
        if (mejorDistancia == 0 || distanciaAcumulada < mejorDistancia) {
            //Si la distancia acumulada no es mayor a la mejor distancia o si es 0
            caminoActual.insertar(nodoActual.getElem(), caminoActual.longitud() + 1);

            if (nodoActual.equals(destino)) {
                //Si llegamos al objetivo reemplazamos y modificamos la distancia
                mejorDistancia = distanciaAcumulada;
                mejorCamino.vaciar();
                for (int i = 1; i <= caminoActual.longitud(); i++) {
                    //se reemplaza el anterior mejor camino
                    mejorCamino.insertar((caminoActual.recuperar(i)), i);
                }
                mejorCamino.insertar(mejorDistancia, mejorCamino.longitud() + 1);//se inserta al final la distancia
            } else {
                while (nodoAdyAux != null) {
                    //para cada nodo adyacente
                    nodoVertAux = nodoAdyAux.getVertice();
                    if (caminoActual.localizar(nodoVertAux.getElem()) < 0) {
                        //Si el nodo actual no está ya en el camino
                        distanciaAux = (double) nodoAdyAux.getEtiqueta();//se obtiene la distancia a este vertice
                        mejorDistancia = caminoMenorRecorridoAux(nodoVertAux, destino, caminoActual, mejorCamino, mejorDistancia, (distanciaAcumulada + distanciaAux));
                    }
                    nodoAdyAux = nodoAdyAux.getSigAdy();
                }
            }
            caminoActual.eliminar(caminoActual.longitud());
        }
        return mejorDistancia;
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean existe = false;
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        if ((nodoOrigen != null && nodoDestino != null) && !origen.equals(destino)) {
            existe = existeCaminoAux(nodoOrigen, destino, new Lista());
        } else if ((nodoOrigen != null && nodoDestino != null) && origen.equals(destino)) {
            existe = true;
        }
        return existe;
    }

    private boolean existeCaminoAux(NodoVert origen, Object buscado, Lista nodosVisitados) {
        //Recorre el grafo desde todos los alcanzables desde origen hasta hallar buscado o terminar, retorna true si encontró, false si no
        boolean existe = false;
        NodoAdy nodoAdyAux = origen.getPrimerAdy();
        NodoVert nodoVertAux;
        if (nodosVisitados.localizar(origen.getElem()) < 0) {
            //Si el nodo actual no existe en la lista de visitados lo agrego, y sigo, si ya existe retorno falso
            nodosVisitados.insertar(origen.getElem(), nodosVisitados.longitud() + 1);
            //Guardo por los que ya pase para no entrar en bucle
            while (nodoAdyAux != null && !existe) {
                //Mientras hayan caminos y no sepamos que llega o no llega a buscado
                nodoVertAux = nodoAdyAux.getVertice();
                if ((nodoVertAux.getElem()).equals(buscado)) {
                    //Si el nodo buscado es el actual
                    existe = true;
                } else {
                    //Sino seguimos
                    existe = existeCaminoAux(nodoVertAux, buscado, nodosVisitados);
                }
                nodoAdyAux = nodoAdyAux.getSigAdy();
            }
        }
        return existe;
    }

    public boolean existeCaminoLista(Cola caminoParametro) {
        //Dado un camino (Cola) retorna true si existe en el grafo, false si no.
        Cola camino = caminoParametro.clone();//Para no vaciar la cola se trabaja sobre un clone
        boolean exito = true;
        NodoVert nodoActual, nodoAux;
        NodoAdy nodoAdyActual = null;
        if (camino != null && !camino.esVacia()) {
            nodoActual = ubicarVertice(camino.obtenerFrente());
            camino.sacar();
            if (nodoActual != null) {
                nodoAdyActual = nodoActual.getPrimerAdy();
            }
            while (exito && !camino.esVacia()) {
                //Mientras no se compruebe que el camino existe o no exsite
                if (nodoAdyActual == null) {
                    exito = false; //Si recorrimos todos los posibles y el camino no siguio, se retorna falso
                } else {
                    nodoAux = nodoAdyActual.getVertice();
                    if (nodoAux.getElem().equals(camino.obtenerFrente())) {
                        //Si el tramo de la ruta se encontro, se pasa con la siguiente
                        camino.sacar();
                        nodoActual = nodoAux;
                        nodoAdyActual = nodoActual.getPrimerAdy();//Nos paramos sobre los nuevos posibles caminos
                    } else {
                        nodoAdyActual = nodoAdyActual.getSigAdy();
                    }
                }
            }

        }
        return exito;
    }
    //=========================FIN CAMINOS=========================//

    private NodoVert ubicarVertice(Object buscado) {
        //Recorre la lista de vértices buscando un elemento, y si lo encuentra devuelve el enlace al nodo que lo contiene.
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVert();
        }
        return aux;
    }

    public String toString() {
        //String de la estructura en formato: "salida -> entrada1, entrada2, entrada..."
        String cadena = "";
        NodoVert nodoVertActual = this.inicio;
        NodoAdy nodoAdyActual = null;
        while (nodoVertActual != null) {
            cadena += nodoVertActual.getElem().toString() + " -> ";
            nodoAdyActual = nodoVertActual.getPrimerAdy();
            while (nodoAdyActual != null) {
                cadena += (nodoAdyActual.getVertice()).getElem().toString();
                nodoAdyActual = nodoAdyActual.getSigAdy();
                if (nodoAdyActual != null) {
                    //no es necesario, pero para mejorar la legibilidad de la cadena
                    cadena += ", ";
                }
            }
            cadena += "\n";
            nodoVertActual = nodoVertActual.getSigVert();
        }
        return cadena;
    }

    public String toStringEstructura() {
        //String de la estructura en formato: "salida ->  (etiqueta) entrada1, (etiqueta) entrada2, (etiqueta) entrada..."
        String cadena = "";
        NodoVert nodoVertActual = this.inicio;
        NodoAdy nodoAdyActual = null;
        Comparable etiqueta;
        while (nodoVertActual != null) {
            cadena += nodoVertActual.getElem().toString() + " -> ";
            nodoAdyActual = nodoVertActual.getPrimerAdy();
            while (nodoAdyActual != null) {
                cadena += "(" + nodoAdyActual.getEtiqueta().toString() + ") " + (nodoAdyActual.getVertice()).getElem().toString();
                nodoAdyActual = nodoAdyActual.getSigAdy();
                if (nodoAdyActual != null) {
                    //no es necesario, pero para mejorar la legibilidad de la cadena
                    cadena += ", ";
                }
            }
            cadena += "\n";
            nodoVertActual = nodoVertActual.getSigVert();
        }
        return cadena;
    }
}
