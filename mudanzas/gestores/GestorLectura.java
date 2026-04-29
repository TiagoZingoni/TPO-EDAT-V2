package mudanzas.gestores;

import mudanzas.Ciudad;
import mudanzas.ClaveCliente;
import mudanzas.DatosCliente;
import mudanzas.SolicitudViaje;
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
    int idSol = 0;

    public GestorLectura(GestorEscritura unGestorEscritura, GestorCiudades unGestorCiudades, GestorRutas unGestorRutas, GestorCliente unGestorCliente, GestorDePedidos unGestorDePedidos, File archivo, int idSolicitud) {
        gestorEscritura = unGestorEscritura;
        gestorCiudades = unGestorCiudades;
        gestorRutas = unGestorRutas;
        gestorClientes = unGestorCliente;
        gestorPedidos = unGestorDePedidos;
        archivoLectura = new File("archivoLectura");
        idSol = idSolicitud;
    }

    public int leer() {
        //Retorna la el id nuevo de solicitud
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
                        //Si es una Ciudad
                        /*Forma: C;5000;Córdoba;Córdoba */
                        int intCodPostal;
                        String codPostal,
                         nombreCiudad,
                         nombreProvincia;
                        codPostal = st.nextToken();//Siguiente elemnto de la lista
                        intCodPostal = Integer.parseInt(codPostal);//se pasa a int
                        nombreCiudad = st.nextToken();
                        nombreProvincia = st.nextToken();
                        gestorCiudades.altaCiudad(new Ciudad(intCodPostal, nombreCiudad, nombreProvincia));
                        gestorRutas.altaCiudad(intCodPostal);
                        break;
                    case "P":
                        //Si es una Persona
                        /*Forma:P;DNI;35678965;FERNANDEZ;JUAN CARLOS;299-4495117 */
                        int nroDoc,
                         tel;
                        String tipoDoc,
                         nroDocSt,
                         nombre,
                         apellido,
                         telSt,
                         email;
                        tipoDoc = st.nextToken();
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
                        /*Forma:R;5000;8324;1108.5 */
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
                        /*Forma:S;5000;8300;15/06/2023;DNI;35678965;13;5;Sarmiento 3400;Roca 2100;T */
                        String fecha,
                         mtsCubicosSt,
                         cantBultosSt,
                         domRetiro,
                         domEntrega,
                         estadoSt;
                        double cantMtsCubicos;
                        int cantBultos;
                        boolean estado;
                        idSol++;//Cada vez que se agrega una solicitud se aumenta
                        codPostalSalidaSt = st.nextToken();
                        codPostalLlegadaSt = st.nextToken();
                        fecha = st.nextToken();
                        tipoDoc = st.nextToken();
                        nroDocSt = st.nextToken();
                        mtsCubicosSt = st.nextToken();
                        cantBultosSt = st.nextToken();
                        domRetiro = st.nextToken();
                        domEntrega = st.nextToken();
                        estadoSt = st.nextToken();
                        //Pasajes a no St
                        codPostalSalida = Integer.parseInt(codPostalSalidaSt);
                        codPostalLlegada = Integer.parseInt(codPostalLlegadaSt);
                        nroDoc = Integer.parseInt(nroDocSt);
                        cantMtsCubicos = Double.parseDouble(mtsCubicosSt);
                        cantBultos = Integer.parseInt(cantBultosSt);
                        estado = (estadoSt.equals("T"));//Si es T es verdadero, sino Falso
                        gestorPedidos.altaPedido(codPostalSalida, codPostalLlegada, new SolicitudViaje(fecha, new ClaveCliente(tipoDoc, nroDoc), cantMtsCubicos, cantBultos, domRetiro, domEntrega, false, idSol));
                        break;
                    default:
                        //Otro caso no hace nada
                        break;

                }

            }
            br.close();
        } catch (Exception exception) {

        }
        return idSol;
    }

}
