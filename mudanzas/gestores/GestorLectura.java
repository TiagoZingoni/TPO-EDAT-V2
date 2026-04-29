package mudanzas.gestores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class GestorLectura {

    //
    GestorEscritura gestorEscritura;
    GestorCiudades gestorCiudades;
    GestorRutas gestorRutas;
    GestorCliente gestorClientes;
    GestorDePedidos gestorPedidos;
    File archivoLectura;

    public GestorLectura(GestorEscritura unGestorEscritura, GestorCiudades unGestorCiudades, GestorRutas unGestorRutas, GestorCliente unGestorCliente, GestorDePedidos unGestorDePedidos, File archivo) {
        gestorEscritura = unGestorEscritura;
        gestorCiudades = unGestorCiudades;
        gestorRutas = unGestorRutas;
        gestorClientes = unGestorCliente;
        gestorPedidos = unGestorDePedidos;
        archivoLectura = new File("archivoLectura");
    }

    public void leer() {
        try {
            //Para leer el archivo y no hacerlo de caracter a caracter se suma buffered
            BufferedReader br = new BufferedReader(new FileReader("archivoLectura"));
            String lineaArchivoLectura = "", stActual;
            StringTokenizer st;
            while ((lineaArchivoLectura = br.readLine()) != null) {
                //Mientras sigan habiendo lineas para leer
                st = new StringTokenizer(lineaArchivoLectura, ";");

                //Mientras sigan habiendo strings
                stActual = st.nextToken();//Le cargamos el string actual
                switch (stActual) {
                    case "S":
                        //Si es una Ciudad
                        while (st.hasMoreTokens()) {
                            //Para cada elemento del string
                            stActual = st.nextToken();//Siguiente elemnto de la lista:

                        }
                        break;
                }

            }
            br.close();
        } catch (Exception exception) {

        }
    }

}
