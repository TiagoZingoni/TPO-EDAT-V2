package tdas.mapeoAMuchos;
import tdas.Lista;

public class MapeoAMuchos {

    //Utilizado para almacenar la información de las Ciudades.
    private NodoAVLMAM raiz;

    public MapeoAMuchos() {
        this.raiz = null;
    }

    public boolean existeClave(Comparable buscado) {
        /* devuelve verdadero si en la estructura se encuentra almacenado un 
        elemento con la clave recibida por parámetro, caso contrario devuelve falso.*/
        boolean encontrado = false;
        if (raiz != null) {
            if (buscado.compareTo(raiz.getClave()) == 0) {
                encontrado = true;
            } else {
                if (buscado.compareTo(raiz.getClave()) < 0) {
                    encontrado = existeClaveAux(raiz.getIzquierdo(), buscado);
                } else {
                    encontrado = existeClaveAux(raiz.getDerecho(), buscado);
                }
            }
        }
        return encontrado;
    }

    private boolean existeClaveAux(NodoAVLMAM nodo, Comparable buscado) {
        boolean aparece = false;
        if (nodo != null) {
            aparece = buscado.compareTo(nodo.getClave()) == 0;
            if (nodo != null && !aparece) {
                if (buscado.compareTo(nodo.getClave()) < 0) {
                    aparece = existeClaveAux(nodo.getIzquierdo(), buscado);
                } else {
                    aparece = existeClaveAux(nodo.getDerecho(), buscado);
                }
            }
        }
        return aparece;
    }

    public boolean insertar(Comparable clave, Lista listaElementos) {
        /* recibe la clave que es única y el dato (información asociada a ella).
        Si no existe en la estructura un elemento con igual clave, agrega el par 
        (clave, dato) a la estructura. Si la operación termina con éxito devuelve
        verdadero y falso en caso contrario.*/
        boolean insertado = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVLMAM(clave, listaElementos);
        } else {
            insertado = insertarAux(this.raiz, clave, listaElementos);
        }
        //Rotaciones para asegurar balance si se insertó el elemento
        if (insertado) {
            this.raiz = ajustaAVL(clave, this.raiz);
        }
        return insertado;
    }

    private boolean insertarAux(NodoAVLMAM nodo, Comparable clave, Lista listaElementos) {
        //precondicion de n no nulo
        boolean insertado = true;
        if (clave.compareTo(nodo.getClave()) == 0) {
            //Si el elemento ya está en el arbol no se puede agegar, se retorna false.
            insertado = false;
        } else if (clave.compareTo(nodo.getClave()) < 0) {
            //Si el elemento es menor que el elemento en el nodo actual se pasa a su HI
            //Si no tiene HI se crea un nodo para almacenar "elemento" y se coloca como HI del actual
            if (nodo.getIzquierdo() != null) {
                insertado = insertarAux(nodo.getIzquierdo(), clave, listaElementos);
            } else {
                nodo.setIzquierdo(new NodoAVLMAM(clave, listaElementos));
            }
        } else {
            //Si el elemento es mayor que el elemento en el nodo actual se pasa a su HD
            //Si no tiene HD se crea un nodo para almacenar "elemento" y se coloca como HD del actual
            if (nodo.getDerecho() != null) {
                insertado = insertarAux(nodo.getDerecho(), clave, listaElementos);
            } else {
                nodo.setDerecho(new NodoAVLMAM(clave, listaElementos));
            }
        }
        return insertado;
    }

    private NodoAVLMAM ajustaAVL(Comparable clave, NodoAVLMAM nodo) {
        /* 
        Función auxiliar para implementar las funciones de inserción y de borrado. 
        Consiste en ajustar los nodos que existen desde el nodo conteniendo la clave e hasta el nodo raiz del subarbol actual
         */
        if (nodo != null) {
            //Se baja por el camino en el que estaría el nodo(clave)
            //No hace falta comparar si la clave existe ya que solo retornaría null
            if (clave.compareTo(nodo.getClave()) > 0) {
                nodo.setDerecho(ajustaAVL(clave, nodo.getDerecho()));
            } else if (clave.compareTo(nodo.getClave()) < 0) {
                nodo.setIzquierdo(ajustaAVL(clave, nodo.getIzquierdo()));
            }
            nodo.recalcularAltura(); //si se rotó se debería recalcular la altura de los nodos recorridos
            int balance = nodo.calcularBalance(); //Para determinar si se debe rotar

            //Si el arbol cae por izquierda
            if (balance == 2) {
                if (nodo.getIzquierdo().calcularBalance() >= 0) {
                    nodo = rotacionDerecha(nodo);
                } else {
                    nodo = rotacionIzquierdaDerecha(nodo);
                }
            }//Si el arbol cae por derecha
            else if (balance == -2) {
                if (nodo.getDerecho().calcularBalance() <= 0) {
                    nodo = rotacionIzquierda(nodo);
                } else {
                    nodo = rotacionDerechaIzquierda(nodo);
                }
            }
        }
        return nodo;
    }

    //Inicio ELIMINAR
    public boolean eliminar(Comparable clave) {
        //Si encuentra el elemento por clave lo elimina y reacomoda el arbol, retornando true, si no existe dicho elemento retorna false.
        boolean eliminado = true;
        if (this.raiz == null) {
            eliminado = false;
        } else {
            eliminado = eliminarAux(this.raiz, this.raiz, clave);
        }
        if (eliminado) {
            this.raiz = ajustaAVL(clave, this.raiz);
        }
        return eliminado;
    }

    private boolean eliminarAux(NodoAVLMAM n, NodoAVLMAM padre, Comparable clave) {
        //recorrido iterativo del arbol.
        boolean eliminado = true;
        if (clave.compareTo(n.getClave()) == 0) {
            //Cuando estamos sobre el elemento buscado lo eliminamos y reacomodamos el arbol según se necesite.
            if (n.getIzquierdo() == null && n.getDerecho() == null) //caso 1, el elemento es una hoja del arbol.
            {
                eliminado = caso1(padre, clave);
            } else if ((n.getIzquierdo() != null && n.getDerecho() == null)) //caso 2 Izq, el elemento tiene solo hijo izquierdo.
            {
                eliminado = caso2(n.getIzquierdo(), padre, clave);
            } else if (n.getIzquierdo() == null && n.getDerecho() != null) //caso 2 Der, el elemento tiene solo hijo derecho.
            {
                eliminado = caso2(n.getDerecho(), padre, clave);
            } else if (n.getIzquierdo() != null && n.getDerecho() != null) //caso 3, el elemento tiene 2 hijos.
            {
                eliminado = caso3(n, padre, clave);
            }
        } else if (clave.compareTo(n.getClave()) < 0) {
            //Si n no es el nodo, se continua el recorrido del arbol hasta encontrarlo o terminarlo.
            if (n.getIzquierdo() != null) {
                eliminado = eliminarAux(n.getIzquierdo(), n, clave);
            } else {
                eliminado = false;
            }
        } else {
            if (n.getDerecho() != null) {
                eliminado = eliminarAux(n.getDerecho(), n, clave);
            } else {
                eliminado = false;
            }
        }
        return eliminado;
    }

    private boolean caso1(NodoAVLMAM padre, Comparable elemento) {
        //Compara el elemento del hijo Derecho, si es el solicitado lo borra, sino elimina el izquierdo.
        if (padre.getDerecho() != null && elemento.compareTo(padre.getDerecho().getClave()) == 0) {
            padre.setDerecho(null);
        } else {
            padre.setIzquierdo(null);
        }
        return true;
    }

    private boolean caso2(NodoAVLMAM nuevoHijo, NodoAVLMAM padre, Comparable elemento) {
        //Elimina el nodo solicitado y su hijo pasa a tomar su lugar en el arbol.
        if (padre.getDerecho() != null && elemento.compareTo(padre.getDerecho().getClave()) == 0) {
            padre.setDerecho(nuevoHijo);
        } else {
            padre.setIzquierdo(nuevoHijo);
        }
        return true;
    }

    private boolean caso3(NodoAVLMAM nodoReemp, NodoAVLMAM padre, Comparable elemento) {
        //Reemplaza el nodo a eliminar por el menor elemento de su subarbol derecho.
        NodoAVLMAM aux = padre;
        //Si su hijo derecho no es el que queremos reemplazar, buscamoss el padre del que queremos reemplazar.
        if (nodoReemp.getDerecho().getIzquierdo() != null) {
            aux = buscarPadreCandidato(nodoReemp.getDerecho());
            //Con el padre de dicho elemento menor reemplazamos el que queremos eliminar.
            nodoReemp.auxCabmio(aux.getIzquierdo().getClave(), aux.getIzquierdo().getLista());
            //eliminamos el duplicado que quedo del que menor elemento, y si tenia un hijo derecho lo dejamos como izquierdo del padre.
            if (aux.getIzquierdo().getDerecho() != null) {
                aux.setIzquierdo(aux.getIzquierdo().getDerecho());
            } else {
                aux.setIzquierdo(null);
            }
        } else {
            nodoReemp.auxCabmio(nodoReemp.getDerecho().getClave(), nodoReemp.getDerecho().getLista());
            if (nodoReemp.getDerecho().getDerecho() != null) {
                nodoReemp.setDerecho(nodoReemp.getDerecho().getDerecho());
            } else {
                nodoReemp.setDerecho(null);
            }
        }
        return true;
    }

    private NodoAVLMAM buscarPadreCandidato(NodoAVLMAM padreCandidato) {
        //busca el padre del menor elemento del subarbol derecho del nodo a eliminar.
        if (padreCandidato.getIzquierdo() != null && padreCandidato.getIzquierdo().getIzquierdo() != null) {
            padreCandidato = buscarPadreCandidato(padreCandidato);
        }
        return padreCandidato;
    }

    //fin ELIMINAR.

    public Object obtenerInformacion(Comparable buscado) {
        /* si en la estructura se encuentra almacenado un elemento con la clave 
        recibida por parámetro,devuelve la información asociada a ella. Si no 
        existe esa clave, no se puede asegurar que no exista mediante este metodo*/
        Object contenidoBuscado = null;
        if (raiz != null) {
            if (buscado.compareTo(raiz.getClave()) == 0) {
                contenidoBuscado = raiz.getLista();
            } else {
                if (buscado.compareTo(raiz.getClave()) < 0) {
                    contenidoBuscado = obtenerInformacionAux(raiz.getIzquierdo(), buscado);
                } else {
                    contenidoBuscado = obtenerInformacionAux(raiz.getDerecho(), buscado);
                }
            }
        }
        return contenidoBuscado;
    }

    private Object obtenerInformacionAux(NodoAVLMAM nodo, Comparable idBuscado) {
        //Recursiva hasta encontrar datos, o fin de recorrido
        Object contenidoBuscado = null;
        boolean aparece = false;
        if (nodo != null) {
            aparece = idBuscado.compareTo(nodo.getClave()) == 0;
            if (nodo != null && !aparece) {
                if (idBuscado.compareTo(nodo.getClave()) < 0) {
                    contenidoBuscado = existeClaveAux(nodo.getIzquierdo(), idBuscado);
                } else {
                    contenidoBuscado = existeClaveAux(nodo.getDerecho(), idBuscado);
                }
            } else {
                contenidoBuscado = nodo.getLista();
            }
        }
        return contenidoBuscado;
    }

    public boolean esVacio() {
        //devuelve falso si hay al menos un elemento cargado en la estructura y verdadero en caso contrario.
        return this.raiz == null;
    }

    public Lista listarClaves() {
        //Retorna una lista ordenada de las claves.
        Lista listado = new Lista();
        listarClavesAux(this.raiz, listado);
        return listado;
    }

    private void listarClavesAux(NodoAVLMAM n, Lista listado) {
        //Carga la lista de claves.
        if (n != null) {
            listarClavesAux(n.getIzquierdo(), listado);
            listado.insertar(n.getClave(), listado.longitud() + 1);
            listarClavesAux(n.getDerecho(), listado);
        }
    }

    public Lista listarListas() {
        //Retorna una lista ordenada de los datos de cada elemento.
        Lista listado = new Lista();
        listarDatosAux(this.raiz, listado);
        return listado;
    }

    private void listarDatosAux(NodoAVLMAM n, Lista listado) {
        //Carga la lista de datos.
        if (n != null) {
            listarDatosAux(n.getIzquierdo(), listado);
            listado.insertar(n.getLista(), listado.longitud() + 1);
            listarDatosAux(n.getDerecho(), listado);
        }
    }

    //Metodo de testeo:
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVLMAM n) {
        String s = "";
        if (n != null) {
            NodoAVLMAM der = n.getDerecho(), izq = n.getIzquierdo();
            //visita el nodo n
            s += n.getClave() + "->";
            if (izq != null) {
                s += "izq: " + izq.getClave() + ", ";
            }
            if (der != null) {
                s += "der: " + der.getClave();
            }
            s += "\n" + toStringAux(izq) + toStringAux(der);
        }
        return s;
    }

    //Rotaciones para incersión y eliminación
    private NodoAVLMAM rotacionIzquierda(NodoAVLMAM r) {
        //el parámetro r representa al pivote. Una vez efectuada la rotación, el algoritmo devuelve la nueva raíz del subárbol.
        NodoAVLMAM h = r.getDerecho();
        NodoAVLMAM temp = h.getIzquierdo();
        h.setIzquierdo(r); //El hijo izquierdo del nodo h pasa a ser el valor original de r
        r.setDerecho(temp); //El hijo derecho del nodo r pasa a ser el valor original de h
        r.recalcularAltura();//ajusta la altura de la raiz original
        h.recalcularAltura();
        return h;
    }

    private NodoAVLMAM rotacionDerecha(NodoAVLMAM r) {
        //el parámetro r representa al pivote. Una vez efectuada la rotación, el algoritmo devuelve la nueva raíz del subárbol.
        NodoAVLMAM h = r.getIzquierdo();
        NodoAVLMAM temp = h.getDerecho();
        h.setDerecho(r); //El hijo derecho del nodo h pasa a ser el valor original de r
        r.setIzquierdo(temp); //El hijo izquierdo del nodo r pasa a ser el valor original de h
        r.recalcularAltura();//ajusta la altura de la raiz original
        h.recalcularAltura();
        return h;
    }

    private NodoAVLMAM rotacionIzquierdaDerecha(NodoAVLMAM r) {
        //Esta rotación se aplica cuando el nodo padre está caído hacia la izquierda (balance 2) y su hijo izquierdo está caído hacia el lado contrario (balance -1).
        NodoAVLMAM hijoIzq = null;
        NodoAVLMAM nodoRetorno = null;
        if (r != null && r.getIzquierdo() != null) {
            hijoIzq = r.getIzquierdo();
            nodoRetorno = r.getIzquierdo().getDerecho();//Nodo que queda como raiz.
            this.rotacionIzquierda(hijoIzq);
            r.setIzquierdo(nodoRetorno);
            this.rotacionDerecha(r);
        }
        return nodoRetorno;
    }

    private NodoAVLMAM rotacionDerechaIzquierda(NodoAVLMAM r) {
        //Esta rotación se aplica cuando el nodo padre está caído a la derecha (balance -2) y su hijo derecho está caído hacia el lado contrario (balance 1).
        NodoAVLMAM hijoDer = null;
        NodoAVLMAM nodoRetorno = null;
        if (r != null && r.getDerecho() != null) {
            hijoDer = r.getDerecho();
            nodoRetorno = hijoDer.getIzquierdo();//Nodo que queda como raiz.
            this.rotacionDerecha(hijoDer);
            r.setDerecho(nodoRetorno);
            this.rotacionIzquierda(r);
        }
        return nodoRetorno;
    }
}

