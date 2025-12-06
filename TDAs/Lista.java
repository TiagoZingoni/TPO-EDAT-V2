package TDAs;

public class Lista {
    private Nodo cabecera;
    private int longitud;

    //constructor
    public Lista() {
        cabecera = null;
        longitud = 0;
    }
    //propias de tipo

    public boolean insertar(Object elemento, int pos) {
        //inserta un elemento en la lista
        boolean exito = true;
        if (pos < 1 || pos > longitud + 1) {//Detecta si     la pos ingresada es invalida
            exito = false;
        } else {
            if (pos == 1) {//Caso especial, crear el primer nodo
                this.cabecera = new Nodo(elemento, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(elemento, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            longitud++;
        }
        return exito;
    }

    public int longitud() {
        return longitud;
    }

    public boolean eliminar(int pos) {
        boolean exito = true;

        if (pos < 1 || pos > longitud) {
            exito = false;
        } else {
            if (pos == 1) {
                //Si existe mÃ¡s de un elemento enlaza la cabecera con el siguiente.
                if (cabecera.getEnlace() != null) {
                    this.cabecera = cabecera.getEnlace();
                }
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            longitud--;
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elemento;
        if (pos < 1 || pos > longitud) {
            elemento = "Posicion invalida";
        } else {
            Nodo aux = this.cabecera;
            int i = 1;
            while (i < pos - 1) {
                aux = aux.getEnlace();
                i++;
            }
            elemento = aux.getEnlace().getElemento();
        }
        return elemento;
    }

    public int localizar(Object elemento) {
        Object elementoPrueba;
        Nodo aux = this.cabecera;
        int i = 1, pos = -1;
        boolean encontrado = false;

        while (i <= longitud && !encontrado) {
            elementoPrueba = aux.getElemento();
            if (elemento == elementoPrueba) {
                encontrado = true;
                pos = i;
            }
            aux = aux.getEnlace();
            i++;
        }
        return pos;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public boolean esVacia() {
        boolean vacia = true;

        if (cabecera != null) {
            vacia = false;
        }
        return vacia;
    }

    public Lista clone() {
        Lista clon = new Lista();
        int longitud = 0;
        
        if (this.cabecera != null) {
            Nodo aux = this.cabecera;
            clon.cabecera = new Nodo(aux.getElemento(), null);
            Nodo aux2 = clon.cabecera;
            longitud = 1;

            while (aux.getEnlace() != null) {
                aux = aux.getEnlace();
                aux2.setEnlace(new Nodo(aux.getElemento(), null));
                aux2 = aux2.getEnlace();
                longitud++;
            }
            clon.longitud = longitud;
        }
        return clon;
    }

    public String toString() {
        Nodo aux = this.cabecera;
        String cadena = "[ ";
        for (int i = 1; i <= longitud; i++) {
            cadena += aux.getElemento().toString() + " ";
            aux = aux.getEnlace();
        }
        cadena += "]";
        return cadena;
    }
    
}
