package tdas.diccionario;

import tdas.lineales.Lista;

public class Diccionario {

    //Utilizado para almacenar la información de las Ciudades.
    private NodoAVLDicc raiz;

    public Diccionario() {
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

    private boolean existeClaveAux(NodoAVLDicc nodo, Comparable buscado) {
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

    public boolean insertar(Comparable clave, Object elemento) {
        /* recibe la clave que es única y el dato (información asociada a ella).
        Si no existe en la estructura un elemento con igual clave, agrega el par 
        (clave, dato) a la estructura. Si la operación termina con éxito devuelve
        verdadero y falso en caso contrario.*/
        boolean[] insertado = new boolean[1];
        insertado[0] = true;//Es false solo si no se pudo insertar
        this.raiz = insertarAux(this.raiz, clave, elemento, insertado);

        return insertado[0];
    }

    private NodoAVLDicc insertarAux(NodoAVLDicc nodo, Comparable clave, Object dato, boolean[] insertado) {
        int comparado;
        NodoAVLDicc nRetorno = nodo;
        if (nodo != null) {
            comparado = clave.compareTo(nodo.getClave());//para no recalcular todas las veces

            if (comparado < 0) {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), clave, dato, insertado));//si es menor
            } else if (comparado > 0) {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), clave, dato, insertado));//Si el elemento es mayor
            } else {
                //Si el elemento ya está en el arbol, no inserto nada
                insertado[0] = false;
            }
            if (insertado[0]) {//Si se insertó
                nRetorno = ajustaAVL(nodo);
            }
        } else {
            nRetorno = new NodoAVLDicc(clave, dato);
        }
        return nRetorno;
    }

    private NodoAVLDicc ajustaAVL(NodoAVLDicc nodo) {
        /*Función auxiliar para implementar las funciones de inserción y de borrado. 
        Consiste en ajustar los nodos que existen desde el nodo conteniendo la clave e hasta el nodo raiz del subarbol actual */
        //La versión anterior bajaba hasta el nodo, acá se modifica directamente
        if (nodo != null) {
            nodo.recalcularAltura(); // Primero se recalcula la altura del nodo actual
            int balance = nodo.calcularBalance();
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
        boolean[] eliminado = new boolean[1];
        eliminado[0] = false;//Solo es true cuando el elemento se econtró
        this.raiz = eliminarAux(this.raiz, clave, eliminado);
        return eliminado[0];
    }

    private NodoAVLDicc eliminarAux(NodoAVLDicc n, Comparable clave, boolean[] eliminado) {
        NodoAVLDicc nRetorno = n; //Si no hay cambios retorna el mismo n de entrada, sirve para retornar los subarboles con las rotaciones
        int comparacion;
        if (n != null) {
            //Si n no es nulo. Sino se retorna nulo
            comparacion = clave.compareTo(n.getClave()); //Para no calcularlo de más
            if (comparacion == 0) {
                //Cuando estamos sobre el elemento buscado lo eliminamos y reacomodamos el arbol según se necesite.
                eliminado[0] = true;//Se encontro el elemento, por ende se puede eliminar
                if (n.getIzquierdo() == null && n.getDerecho() == null) //caso 1, el elemento es una hoja del arbol.
                {
                    nRetorno = null;//Ya no hace falta caso 1, solo eliminaba una hoja
                } else if ((n.getIzquierdo() != null && n.getDerecho() == null)) //caso 2 Izq, el elemento tiene solo hijo izquierdo.
                {
                    nRetorno = n.getIzquierdo();//Se reemplaza el padre con el hijo ezquierdo
                } else if (n.getIzquierdo() == null && n.getDerecho() != null) //caso 2 Der, el elemento tiene solo hijo derecho.
                {
                    nRetorno = n.getDerecho();//Se reemplaza el padre con el hijo derecho
                } else if (n.getIzquierdo() != null && n.getDerecho() != null) //caso 3, el elemento tiene 2 hijos.
                {
                    //CASO 3
                    NodoAVLDicc nSiguiente = n.getDerecho();//Subarbol derecho de n
                    boolean[] auxB = new boolean[1];
                    auxB[0] = false; //ya se sabe que existe lo que se busca eliminar, no es relevante esto
                    //buscarPadreCandidato
                    while (nSiguiente.getIzquierdo() != null) {
                        //se busca el menor elemento de su subarbol derecho y se guarda en nSiguiente.
                        nSiguiente = nSiguiente.getIzquierdo();
                    }
                    //Se reemplaza el nodo por nSiguiente.
                    n.auxCambio(nSiguiente.getClave(), nSiguiente.getDato());
                    //Ahora elimino el nodo que quedó duplicado después de actualizar el nodo a reemplazar y se balancea el subarbol afectado
                    n.setDerecho(eliminarAux(n.getDerecho(), nSiguiente.getClave(), auxB));
                    //Se va desde el hijo derecho hasta el que hay que eliminar porque está duplicado (el nuevo padre)
                    nRetorno = ajustaAVL(n);
                }
                if (nRetorno != null) {
                    //si n no era una hoja
                    nRetorno = ajustaAVL(nRetorno);
                }

            } else if (comparacion < 0) {
                //Si n no es el nodo, se continua el recorrido del arbol hasta encontrarlo o terminarlo.
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), clave, eliminado));//Se sigue por izquierda y se reconecta el subarbol izquierdo
                nRetorno = ajustaAVL(nRetorno);//Se reajusta el arbol al subir
            } else {
                n.setDerecho(eliminarAux(n.getDerecho(), clave, eliminado));//Se sigue por derecha y se reconecta el subarbol derecho
                nRetorno = ajustaAVL(nRetorno);//Se reajusta el arbol al subir
            }
        }
        return nRetorno;
    }

    //fin ELIMINAR.
    public Object obtenerInformacion(Comparable buscado) {
        /* si en la estructura se encuentra almacenado un elemento con la clave 
        recibida por parámetro,devuelve la información asociada a ella. Si no 
        existe esa clave, no se puede asegurar que no exista mediante este metodo*/
        Object contenidoBuscado = null;
        if (raiz != null) {
            if (buscado.compareTo(raiz.getClave()) == 0) {
                contenidoBuscado = raiz.getDato();
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

    private Object obtenerInformacionAux(NodoAVLDicc nodo, Comparable idBuscado) {
        //Recursiva hasta encontrar datos, o fin de recorrido
        Object contenidoBuscado = null;
        boolean aparece = false;
        if (nodo != null) {
            aparece = idBuscado.compareTo(nodo.getClave()) == 0;
            if (nodo != null && !aparece) {
                if (idBuscado.compareTo(nodo.getClave()) < 0) {
                    contenidoBuscado = obtenerInformacionAux(nodo.getIzquierdo(), idBuscado);
                } else {
                    contenidoBuscado = obtenerInformacionAux(nodo.getDerecho(), idBuscado);
                }
            } else {
                contenidoBuscado = nodo.getDato();
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

    private void listarClavesAux(NodoAVLDicc n, Lista listado) {
        //Carga la lista de claves.
        if (n != null) {
            listarClavesAux(n.getIzquierdo(), listado);
            listado.insertar(n.getClave(), listado.longitud() + 1);
            listarClavesAux(n.getDerecho(), listado);
        }
    }

    public Lista listarDatos() {
        //Retorna una lista ordenada de los datos de cada elemento.
        Lista listado = new Lista();
        listarDatosAux(this.raiz, listado);
        return listado;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista listado) {
        //Carga la lista de datos.
        if (n != null) {
            listarDatosAux(n.getIzquierdo(), listado);
            listado.insertar(n.getDato(), listado.longitud() + 1);
            listarDatosAux(n.getDerecho(), listado);
        }
    }

    public Lista listarDatosRango(Comparable limInferior, Comparable limiteSuperior) {
        //Lista todos los datos dentro del rango dado
        Lista listado = new Lista();
        listarDatosRangoAux(this.raiz, listado, limInferior, limiteSuperior);
        return listado;
    }

    private void listarDatosRangoAux(NodoAVLDicc n, Lista listado, Comparable limInf, Comparable limSup) {
        //Carga la lista con los datos que esten dentro del rango.
        if (n != null) {
            Comparable valorClaveActual = n.getClave();

            if (valorClaveActual.compareTo(limInf) >= 0) {
                //Recorre para la izquierda solo si puede haber valores mayores al limInferior
                listarDatosRangoAux(n.getIzquierdo(), listado, limInf, limSup);
            }

            if (valorClaveActual.compareTo(limInf) >= 0 && valorClaveActual.compareTo(limSup) <= 0) {
                //Si el valo actual está dentro del rango inserta
                listado.insertar(n.getDato(), listado.longitud() + 1);
            }
            if (valorClaveActual.compareTo(limSup) <= 0) {
                //REcorre para la derecha solo si puede haber valores menores al limSuperior
                listarDatosRangoAux(n.getDerecho(), listado, limInf, limSup);
            }

        }
    }

    //Metodo de testeo:
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVLDicc n) {
        String s = "";
        if (n != null) {
            NodoAVLDicc der = n.getDerecho(), izq = n.getIzquierdo();
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
    private NodoAVLDicc rotacionIzquierda(NodoAVLDicc r) {
        //el parámetro r representa al pivote. Una vez efectuada la rotación, el algoritmo devuelve la nueva raíz del subárbol.
        NodoAVLDicc h = r.getDerecho();
        NodoAVLDicc temp = h.getIzquierdo();
        h.setIzquierdo(r); //El hijo izquierdo del nodo h pasa a ser el valor original de r
        r.setDerecho(temp); //El hijo derecho del nodo r pasa a ser el valor original de h
        r.recalcularAltura();//ajusta la altura de la raiz original
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDicc rotacionDerecha(NodoAVLDicc r) {
        //el parámetro r representa al pivote. Una vez efectuada la rotación, el algoritmo devuelve la nueva raíz del subárbol.
        NodoAVLDicc h = r.getIzquierdo();
        NodoAVLDicc temp = h.getDerecho();
        h.setDerecho(r); //El hijo derecho del nodo h pasa a ser el valor original de r
        r.setIzquierdo(temp); //El hijo izquierdo del nodo r pasa a ser el valor original de h
        r.recalcularAltura();//ajusta la altura de la raiz original
        h.recalcularAltura();
        return h;
    }

    private NodoAVLDicc rotacionIzquierdaDerecha(NodoAVLDicc r) {
        //Esta rotación se aplica cuando el nodo padre está caído hacia la izquierda (balance 2) y su hijo izquierdo está caído hacia el lado contrario (balance -1).
        r.setIzquierdo(rotacionIzquierda(r.getIzquierdo()));//Se rota a izquierda el nodo izquierdo de r (y se actualiza con el set)
        return rotacionDerecha(r); //Se rota a derecha la actualización anterior
    }

    private NodoAVLDicc rotacionDerechaIzquierda(NodoAVLDicc r) {
        //Esta rotación se aplica cuando el nodo padre está caído a la derecha (balance -2) y su hijo derecho está caído hacia el lado contrario (balance 1).
        r.setDerecho(rotacionDerecha(r.getDerecho()));//Se rota a derecha el nodo derecho de r (y se actualiza con el set)
        return rotacionIzquierda(r); //Se rota a izquierda la actualización anterior
    }

}
/* Eran innecesarios
    private NodoAVLDicc caso1(NodoAVLDicc padre, Comparable elemento) {
        //Compara el elemento del hijo Derecho, si es el solicitado lo borra, sino elimina el izquierdo.
        if (padre.getDerecho() != null && elemento.compareTo(padre.getDerecho().getClave()) == 0) {
            padre.setDerecho(null);
        } else {
            padre.setIzquierdo(null);
        }
        padre = ajustaAVL(padre);//Se recalcula y ajusta al padre
        return padre;
    }
     
    private NodoAVLDicc caso2(NodoAVLDicc nuevoHijo, NodoAVLDicc padre, Comparable elemento) {
        //Elimina el nodo solicitado y su hijo pasa a tomar su lugar en el arbol.
        if (padre.getDerecho() != null && elemento.compareTo(padre.getDerecho().getClave()) == 0) {
            padre.setDerecho(nuevoHijo);
        } else {
            padre.setIzquierdo(nuevoHijo);
        }
        padre = ajustaAVL(padre);//Se recalcula y ajusta al padre
        return padre;
    }
 */
 /* Metodo viejo
    private NodoAVLDicc rotacionDerechaIzquierda(NodoAVLDicc r) {
        //Esta rotación se aplica cuando el nodo padre está caído a la derecha (balance -2) y su hijo derecho está caído hacia el lado contrario (balance 1).
        NodoAVLDicc hijoDer = null;
        NodoAVLDicc nodoRetorno = null;
        if (r != null && r.getDerecho() != null) {
            hijoDer = r.getDerecho();
            nodoRetorno = hijoDer.getIzquierdo();//Nodo que queda como raiz.
            this.rotacionDerecha(hijoDer);
            r.setDerecho(nodoRetorno);
            this.rotacionIzquierda(r);
        }
        return nodoRetorno;
    }*/
 /* Metodo viejo
    private NodoAVLDicc rotacionIzquierdaDerecha(NodoAVLDicc r) {
        //Esta rotación se aplica cuando el nodo padre está caído hacia la izquierda (balance 2) y su hijo izquierdo está caído hacia el lado contrario (balance -1).
        NodoAVLDicc hijoIzq = null;
        NodoAVLDicc nodoRetorno = null;
        if (r != null && r.getIzquierdo() != null) {
            hijoIzq = r.getIzquierdo();
            nodoRetorno = r.getIzquierdo().getDerecho();//Nodo que queda como raiz.
            this.rotacionIzquierda(hijoIzq);
            r.setIzquierdo(nodoRetorno);
            this.rotacionDerecha(r);
        }
        return nodoRetorno;
    }
 */
 /* Metodo viejo (Bastante cambiado igual)
    private boolean insertarAux(NodoAVLDicc nodo, Comparable clave, Object dato) {
        //precondicion de n no nulo
        boolean insertado = true;
        if (clave.compareTo(nodo.getClave()) == 0) {
            //Si el elemento ya está en el arbol no se puede agegar, se retorna false.
            insertado = false;
        } else if (clave.compareTo(nodo.getClave()) < 0) {
            //Si el elemento es menor que el elemento en el nodo actual se pasa a su HI
            //Si no tiene HI se crea un nodo para almacenar "elemento" y se coloca como HI del actual
            if (nodo.getIzquierdo() != null) {
                insertado = insertarAux(nodo.getIzquierdo(), clave, dato);
                if (insertado) {
                    nodo.setIzquierdo(ajustaAVL(clave, nodo.getIzquierdo()));
                }
            } else {
                nodo.setIzquierdo(new NodoAVLDicc(clave, dato));
            }
        } else {
            //Si el elemento es mayor que el elemento en el nodo actual se pasa a su HD
            //Si no tiene HD se crea un nodo para almacenar "elemento" y se coloca como HD del actual
            if (nodo.getDerecho() != null) {
                insertado = insertarAux(nodo.getDerecho(), clave, dato);
                if (insertado) {
                    nodo.setDerecho(ajustaAVL(clave, nodo.getDerecho()));
                }
            } else {
                nodo.setDerecho(new NodoAVLDicc(clave, dato));
            }
        }
        return insertado;
    }
 */
 /* Metodo viejo 
    private NodoAVLDicc ajustaAVL(Comparable clave, NodoAVLDicc nodo) {
        /* 
        Función auxiliar para implementar las funciones de inserción y de borrado. 
        Consiste en ajustar los nodos que existen desde el nodo conteniendo la clave e hasta el nodo raiz del subarbol actual
         /
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
    }*/
