package mudanzas.gestores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import mudanzas.Ciudad;
import mudanzas.ClaveCliente;
import mudanzas.DatosCliente;

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
                    case "C":
                        int intCodPostal;
                        String codPostal,
                         nombreCiudad,
                         nombreProvincia;
                        //Si es una Ciudad
                        codPostal = st.nextToken();//Siguiente elemnto de la lista
                        intCodPostal = Integer.parseInt(codPostal);//se pasa a int
                        nombreCiudad = st.nextToken();
                        nombreProvincia = st.nextToken();
                        gestorCiudades.altaCiudad(new Ciudad(intCodPostal, nombreCiudad, nombreProvincia));
                        gestorRutas.altaCiudad(intCodPostal);
                        break;
                    case "P":
                        //Si es una Persona
                        int nroDoc,
                         tel;
                        String tipoDoc,
                         nroDocSt,
                         nombre,
                         apellido,
                         telSt,
                         email;
                        tipoDoc = st.nextToken();//Siguiente elemnto de la lista
                        nroDocSt = st.nextToken();
                        apellido = st.nextToken();
                        nombre = st.nextToken();
                        telSt = st.nextToken();
                        email = st.nextToken();
                        nroDoc = Integer.parseInt(nroDocSt);
                        tel = Integer.parseInt(telSt);
                        gestorClientes.altaCliente(new ClaveCliente(tipoDoc, nroDoc), new DatosCliente(nombre, apellido, tel, email));
                        break;
                    case "R":
                        //Si es una Ruta
                        int codPostalSalida,
                         codPostalLlegada;
                        String codPostalSalidaSt,
                         codPostalLlegadaSt,
                         distanciaSt;
                        double distancia;
                        codPostalSalidaSt = st.nextToken();
                        codPostalLlegadaSt = st.nextToken();
                        distanciaSt = st.nextToken();
                        codPostalSalida = Integer.parseInt(codPostalSalidaSt);
                        codPostalLlegada = Integer.parseInt(codPostalLlegadaSt);
                        distancia = Double.parseDouble(distanciaSt);
                        gestorRutas.altaRuta(codPostalSalida, codPostalLlegada, distancia);
                        break;
                    case "S":
                        //Si es una Solicitud

                        break;
                    default:
                        //Otro caso no hace nada
                        break;

                }

            }
            br.close();
        } catch (Exception exception) {

        }
    }

}
