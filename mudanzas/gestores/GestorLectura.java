package mudanzas.gestores;

import java.io.FileReader;
import java.util.StringTokenizer;

public class GestorLectura {

    //
    GestorEscritura gestorEscritura;
    GestorCiudades gestorCiudades;
    GestorRutas gestorRutas;
    GestorCliente gestorClientes;
    GestorDePedidos gestorPedidos;

    public GestorLectura(GestorEscritura unGestorEscritura, GestorCiudades unGestorCiudades, GestorRutas unGestorRutas, GestorCliente unGestorCliente, GestorDePedidos unGestorDePedidos) {
        gestorEscritura = unGestorEscritura;
        gestorCiudades = unGestorCiudades;
        gestorRutas = unGestorRutas;
        gestorClientes = unGestorCliente;
        gestorPedidos = unGestorDePedidos;
    }

}
