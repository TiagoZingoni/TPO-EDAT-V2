package mudanzas;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;
import mudanzas.gestores.*;
import tdas.Cola;
import tdas.Lista;

public class MudanzasCompartidas {

    //Todos los gestores 
    GestorEscritura gestorEscritura;
    GestorCiudades gestorCiudades;
    GestorRutas gestorRutas;
    GestorCliente gestorClientes;//Tamaño de la lista hash
    GestorDePedidos gestorPedidos;
    int idSolicitud = 0;
    GestorLectura gestorLectura;
    boolean cargaInicial;

    public void menu(File archivoLectura, File archivoEscritura) {
        gestorLectura = new GestorLectura(gestorEscritura, gestorCiudades, gestorRutas, gestorClientes, gestorPedidos, archivoLectura, idSolicitud);
        gestorEscritura = new GestorEscritura(archivoEscritura);
        gestorCiudades = new GestorCiudades(gestorEscritura);
        gestorRutas = new GestorRutas();
        gestorClientes = new GestorCliente(100);//Tamaño de la lista hash
        gestorPedidos = new GestorDePedidos(gestorRutas, gestorCiudades, gestorEscritura);
        cargaInicial = false;//Cambia a true cuando se hizo la carga inicial
        Scanner sc = new Scanner(System.in);
        String opcion;
        boolean seguir = true;

        while (seguir == true) {
            //Menú
            //String menu
            System.out.println("MENÚ:");
            System.out.println("1. Carga inicial del sistema.\n"
                    + "2. ABM de Ciudades\n"
                    + "3. ABM de la red de rutas\n"
                    + "4. ABM de clientes\n"
                    + "5. ABM de pedidos\n"
                    + "6. Consulta sobre clientes\n"
                    + "7. Consultas sobre ciudades\n"
                    + "8. Consultas sobre viajes\n"//debería ser rutas?
                    + "9. Verificar viaje\n"
                    + "10. Mostrar Sistema\n"
                    + "11. Salir");
            System.out.println("Ingrese una opción: ");
            opcion = sc.nextLine();
            //Opciones del menu
            switch (opcion) {
                case "1":
                    cargaInicialDelSistema();
                    break;
                case "2":
                    abmCiudades();
                    break;
                case "3":
                    abmRedRutas();
                    break;
                case "4":
                    abmClientes();
                    break;
                case "5":
                    abmPedidos();
                    break;
                case "6":
                    consultasClientes();
                    break;
                case "7":
                    consultasCiudades();
                    break;
                case "8":
                    consultaViaje();
                    break;
                case "9":
                    verificarViaje();
                    break;
                case "10":
                    mostrarSistema();
                    break;
                default:
                    cargaFinDelSistema();
                    seguir = false;
                    break;
            }

        }
    }

//==========================CIUDADES==========================\\
    private void abmCiudades() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        int intCodPostal;
        String codPostal, nombreCiudad, nombreProvincia;
        Ciudad unaCiudad;
        boolean exito;
        System.out.println("Menú Ciudad: \n"
                + "1. Alta Ciudad\n"
                + "2. Baja Ciudad\n"
                + "3. Modificación Ciudad\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                //===============ALTA CIUDAD===============\\
                System.out.println("ALTA CIUDAD:");
                System.out.println("Ingrese un código postal (número):");
                codPostal = sc.nextLine();
                try {
                    intCodPostal = Integer.parseInt(codPostal);
                    System.out.println("Ingrese el nombre de la Ciudad:");
                    nombreCiudad = sc.nextLine();
                    System.out.println("Ingrese el nombre de la Provincia:");
                    nombreProvincia = sc.nextLine();
                    unaCiudad = new Ciudad(intCodPostal, nombreCiudad, nombreProvincia);
                    exito = gestorCiudades.altaCiudad(unaCiudad);
                    if (exito) {
                        System.out.println("Ciudad " + unaCiudad.toString() + " creada con exito.");
                        gestorRutas.altaCiudad(intCodPostal);//se agrega la ciudad (solo su clave) al grafo de rutas
                    } else {
                        System.out.println("Error, " + intCodPostal + " ya es un código postal en uso.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                abmCiudades();//volvemos al menu actual
                break;
            case "2":
                //===============BAJA CIUDAD===============\\
                System.out.println("BAJA CIUDAD:");
                System.out.println("Ingrese un código postal (número):");
                codPostal = sc.nextLine();
                try {
                    intCodPostal = Integer.parseInt(codPostal);
                    exito = gestorCiudades.bajaCiudad(intCodPostal);
                    if (exito) {
                        System.out.println("Ciudad " + intCodPostal + " eliminada con exito.");
                        gestorRutas.bajaCiudad(intCodPostal);//se elimina la ciudad del grafo de rutas
                    } else {
                        System.out.println("Ciudad " + intCodPostal + " no encontrada.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                abmCiudades();//volvemos al menu actual
                break;
            case "3":
                //===============MODIFICAR CIUDAD===============\\
                modificarCiudad();
                abmCiudades();//volvemos al menu actual
                break;
            default:
                break;
        }
    }

    private void modificarCiudad() {
        //opciones de modificación ciudades
        Scanner sc = new Scanner(System.in);
        String opcion;
        int intCodPostal;
        String codPostal, nombreCiudad, nombreProvincia;
        System.out.println("Menu Modificación Ciudad:\n"
                + "1. Modificar Nombre Ciudad\n"
                + "2. Modificar Nombre Provincia\n"
                + "3. Modificar Nombre Ciudad y Provincia\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                System.out.println("MODIFICACIÓN NOMBRE CIUDAD:");
                System.out.println("Ingrese el código postal de la Ciudad a modificar:");
                codPostal = sc.nextLine();
                try {
                    intCodPostal = Integer.parseInt(codPostal);
                    System.out.println("Ingrese el nuevo nombre de la Ciudad:");
                    nombreCiudad = sc.nextLine();
                    if (gestorCiudades.modificarNombreCiudad(intCodPostal, nombreCiudad)) {
                        System.out.println("Ciudad actualizada");
                    } else {
                        System.out.println("Ciudad no encontrada");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                break;
            case "2":
                System.out.println("MODIFICACIÓN NOMBRE PROVINCIA:");
                System.out.println("Ingrese el código postal de la Ciudad a modificar:");
                codPostal = sc.nextLine();
                try {
                    intCodPostal = Integer.parseInt(codPostal);
                    System.out.println("Ingrese el nuevo nombre de la Provincia:");
                    nombreProvincia = sc.nextLine();
                    if (gestorCiudades.modificarNombreProvincia(intCodPostal, nombreProvincia)) {
                        System.out.println("Ciudad actualizada");
                    } else {
                        System.out.println("Ciudad no encontrada");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                break;
            case "3":
                System.out.println("MODIFICACIÓN NOMBRE CIUDAD Y PROVINCIA:");
                System.out.println("Ingrese el código postal de la Ciudad a modificar:");
                codPostal = sc.nextLine();
                try {
                    intCodPostal = Integer.parseInt(codPostal);
                    System.out.println("Ingrese el nuevo nombre de la Provincia:");
                    nombreProvincia = sc.nextLine();
                    System.out.println("Ingrese el nuevo nombre de la Ciudad:");
                    nombreCiudad = sc.nextLine();
                    if (gestorCiudades.modificarNombreCiudadYNombreProvincia(intCodPostal, nombreCiudad, nombreProvincia)) {
                        System.out.println("Ciudad actualizada");
                    } else {
                        System.out.println("Ciudad no encontrada");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                break;
            default:
                break;

        }
    }

    private void consultasCiudades() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        int intCodPostal;
        String codPostal;
        System.out.println("Menú Constula Ciudad: \n"
                + "1. Obtener ciudad\n"
                + "2. Lista ciudadades por prefijo\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                System.out.println("OBTENER CIUDAD:");
                System.out.println("Ingrese un código postal (número):");
                codPostal = sc.nextLine();
                try {
                    intCodPostal = Integer.parseInt(codPostal);
                    Ciudad ciudadAux = gestorCiudades.obtenerCiudad(intCodPostal); //Buscamos la ciudad
                    if (ciudadAux != null) {
                        System.out.println(ciudadAux.toString());
                    } else {
                        System.out.println("Ciudad no encontrada");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                break;
            case "2":
                System.out.println("OBTENER CIUDADES POR PREFIJO:");
                System.out.println("Ingrese un prefijo de código postal (número):");
                codPostal = sc.nextLine();
                try {
                    intCodPostal = Integer.parseInt(codPostal);
                    Lista listaAux = gestorCiudades.obtenerCiudadPorPrefijo(intCodPostal);
                    if (!listaAux.esVacia()) {
                        //Si la lista no es vacía, se imprimen sus elementos
                        System.out.println("Ciudades encontradas:");
                        System.out.println(listaAux.toStringElementos());
                    } else {
                        System.out.println("No se encontraron ciudades con el prefijo: " + intCodPostal);
                    }

                    System.out.println();
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                break;
            default:
                break;

        }
    }

//==========================RED DE RUTAS==========================\\
    private void abmRedRutas() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        int intCiudad1, intCiudad2;
        double kms;
        System.out.println("Menú Red de Rutas: \n"
                + "1. Alta Ruta\n"
                + "2. Baja Ruta\n"
                + "3. Modificación Ruta\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                try {
                    System.out.println("ALTA RUTA:");
                    System.out.println("Ingrese el codigo postal de la primer ciudad:");
                    intCiudad1 = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la segunda ciudad:");
                    intCiudad2 = sc.nextInt();
                    System.out.println("Ingrese la cantidad de kms de la ruta de manera 'xx.yy', sin comillas:");
                    kms = sc.nextDouble();
                    if (gestorRutas.altaRuta(intCiudad1, intCiudad2, kms)) {
                        System.out.println("Ruta agregada con exito");
                    } else {
                        System.out.println("Alguna de las ciudades dadas no existe o Ruta existente");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmRedRutas();
                break;
            case "2":
                System.out.println("BAJA RUTA:");
                try {
                    System.out.println("Ingrese el codigo postal de la primer ciudad:");
                    intCiudad1 = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la segunda ciudad:");
                    intCiudad2 = sc.nextInt();
                    if (gestorRutas.bajaRuta(intCiudad1, intCiudad2)) {
                        System.out.println("Ruta eliminada con exito");
                    } else {
                        System.out.println("Ruta o Ciudad/es inexistente, no se elimino nada");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmRedRutas();
                break;

            case "3":
                try {
                    System.out.println("MODIFICACIÓN RUTA:");
                    System.out.println("Ingrese el codigo postal de la primer ciudad:");
                    intCiudad1 = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la segunda ciudad:");
                    intCiudad2 = sc.nextInt();
                    System.out.println("Ingrese la cantidad de kms de la ruta de manera 'xx.yy', sin comillas:");
                    kms = sc.nextDouble();
                    if (gestorRutas.modificarRuta(intCiudad1, intCiudad2, kms)) {
                        System.out.println("Ruta modificada con exito");
                    } else {
                        System.out.println("Ruta o Ciudad/es inexistente, no hubieron modificaciones");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmRedRutas();
                break;

            default:
                break;
        }
    }

    private void consultaViaje() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        int ciudadA, ciudadB, ciudadC;
        double kms;
        Lista listaAux;
        System.out.println("Menú Constula Viaje: \n"
                + "1. Obtener camino que llegue de A a B, que pasa por menos ciudades\n"
                + "2. Obtener camino que llegue de A a B, recorriendo menos kms\n"
                + "3. Obtener todos los caminos que lleguen de A a B, pasando por C\n"
                + "4. Verificar si es posible llegar de A a B recorriendo como maximo n kms\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                System.out.println("MENOS CIUDADES:");
                try {
                    System.out.println("Ingrese el codigo postal de la primer ciudad:");
                    ciudadA = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la segunda ciudad:");
                    ciudadB = sc.nextInt();
                    listaAux = gestorRutas.caminoPorMenosCiudades(ciudadA, ciudadB);//Cargamos la lista con el camino
                    if (!listaAux.esVacia()) {
                        //Si la lista no está vacía, entonces existe un camino:
                        System.out.println("Camino que pasa por menos ciudades:\n" + listaAux.toStringElementos());
                    } else {
                        System.out.println("No se encontró ningun camino entre " + ciudadA + " y " + ciudadB);
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                consultaViaje();
                break;
            case "2":
                System.out.println("MENOS Kms:");
                try {
                    System.out.println("Ingrese el codigo postal de la primer ciudad:");
                    ciudadA = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la segunda ciudad:");
                    ciudadB = sc.nextInt();
                    listaAux = gestorRutas.caminoConMenorDistancia(ciudadA, ciudadB);//Cargamos la lista, el ultimo elemento es la cantidad de kms
                    if (!listaAux.esVacia()) {
                        //Si la lista no es vacía
                        System.out.println("Camino que recorre menos kilometros:\n" + listaAux.toStringElementos() + "kms");
                    } else {
                        System.out.println("No se encontró ningun camino entre " + ciudadA + " y " + ciudadB);
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                consultaViaje();
                break;
            case "3":
                Lista caminoAux;
                System.out.println("PASAN POR C:");
                try {
                    System.out.println("Ingrese el codigo postal de la primer ciudad:");
                    ciudadA = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la segunda ciudad:");
                    ciudadB = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la tercer ciudad:");
                    ciudadC = sc.nextInt();
                    listaAux = gestorRutas.caminosPasanPorCiudad(ciudadA, ciudadB, ciudadC);//Cargamos la lista con la lista de caminos
                    if (!listaAux.esVacia()) {
                        //Si la lista no es vacía
                        System.out.println("Camino pasa por " + ciudadC + ":\n");
                        for (int i = 1; i < listaAux.longitud(); i++) {
                            //Para cada camino de la lista caminos
                            caminoAux = (Lista) listaAux.recuperar(i);
                            System.out.println(caminoAux.toStringElementos());
                        }
                    } else {
                        System.out.println("No se encontró ningun camino entre " + ciudadA + " y " + ciudadB);
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                consultaViaje();
                break;
            case "4":
                System.out.println("ES POSIBLE:");
                try {
                    System.out.println("Ingrese el codigo postal de la primer ciudad:");
                    ciudadA = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la segunda ciudad:");
                    ciudadB = sc.nextInt();
                    System.out.println("Ingrese la cantidad de kms que no debe superar:");
                    kms = sc.nextDouble();
                    if (gestorRutas.recorridoMenorA(ciudadA, ciudadB, kms)) {
                        System.out.println("Si, existe por lo menos un camino de " + ciudadA + " a " + ciudadB
                                + " que requiere menos de " + kms + "kms ");
                    } else {
                        System.out.println("No existe ningun camino de " + ciudadA + " a " + ciudadB
                                + " que requiera menos de " + kms + "kms ");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                consultaViaje();
                break;
            default:
                break;
        }
    }

//==========================CLIENTES==========================\\
    private void abmClientes() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        String tipoDoc, nombre, apellido, mail, telefono;
        int nroDoc;
        System.out.println("Menú Clientes: \n"
                + "1. Alta Cliente\n"
                + "2. Baja Cliente\n"
                + "3. Modificación Cliente\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                System.out.println("ALTA CLIENTE:");
                try {
                    //Pedimos clave
                    System.out.println("Ingrese el tipo de documento:");
                    tipoDoc = sc.nextLine();
                    System.out.println("Ingrese el numero de documento:");
                    nroDoc = sc.nextInt();
                    //Pedimos datos
                    System.out.println("Ingrese el nombre de la persona:");
                    nombre = sc.nextLine();
                    System.out.println("Ingrese el apellido:");
                    apellido = sc.nextLine();
                    System.out.println("Ingrese el numero de telefono:");
                    telefono = sc.nextLine();
                    System.out.println("Ingrese el mail:");
                    mail = sc.nextLine();
                    //intentamos insertar
                    if (gestorClientes.altaCliente(new ClaveCliente(tipoDoc, nroDoc), new DatosCliente(nombre, apellido, telefono, mail))) {
                        System.out.println("Persona agregada correctamente");
                    } else {
                        System.out.println("Error: persona ya existente");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmClientes();
                break;
            case "2":
                System.out.println("BAJA CLIENTE:");
                try {
                    //Pedimos clave
                    System.out.println("Ingrese el tipo de documento:");
                    tipoDoc = sc.nextLine();
                    System.out.println("Ingrese el numero de documento:");
                    nroDoc = sc.nextInt();
                    //intentamos desasociar
                    if (gestorClientes.bajaCliente(new ClaveCliente(tipoDoc, nroDoc))) {
                        System.out.println("Cliente eliminado exitosamente");
                    } else {
                        System.out.println("Error: Cliente inexistente");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmClientes();
                break;
            case "3":
                modificacionCliente();
                abmClientes();
                break;
            default:
                break;
        }
    }

    private void modificacionCliente() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        String tipoDoc, nombre, apellido, mail, telefono;
        int nroDoc;
        System.out.println("MODIFICACIÓN CLIENTE:");
        System.out.println("Ingrese el tipo de documento de la persona a modificar:");
        tipoDoc = sc.nextLine();
        try {
            System.out.println("Ingrese el numero de documento:");
            nroDoc = sc.nextInt();
            System.out.println("1. Modificar Nombre\n"
                    + "2. Modificar Apellido\n"
                    + "3. Modificar Telefono\n"
                    + "4. Modificar mail\n"
                    + "0. Retroceder");
            System.out.print("Ingrese una opción: ");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println("MODIFICACIÓN NOMBRE:");
                    System.out.println("Ingrese el nuevo nombre");
                    nombre = sc.nextLine();
                    if (gestorClientes.modificarNombre(new ClaveCliente(tipoDoc, nroDoc), nombre)) {
                        System.out.println("Nombre modificado con exito");
                    } else {
                        System.out.println("Error: Cliente inexistente");
                    }
                    break;
                case "2":
                    System.out.println("MODIFICACIÓN APELLIDO:");
                    System.out.println("Ingrese el nuevo apellido");
                    apellido = sc.nextLine();
                    if (gestorClientes.modificarApellido(new ClaveCliente(tipoDoc, nroDoc), apellido)) {
                        System.out.println("Apellido modificado con exito");
                    } else {
                        System.out.println("Error: Cliente inexistente");
                    }
                    break;
                case "3":
                    System.out.println("MODIFICACIÓN TELEFONO:");
                    System.out.println("Ingrese el nuevo telefono");
                    telefono = sc.nextLine();
                    if (gestorClientes.modificarTelefono(new ClaveCliente(tipoDoc, nroDoc), telefono)) {
                        System.out.println("Telefono modificado con exito");
                    } else {
                        System.out.println("Error: Cliente inexistente");
                    }
                    break;
                case "4":
                    System.out.println("MODIFICACIÓN MAIL:");
                    System.out.println("Ingrese el nuevo mail");
                    mail = sc.nextLine();
                    if (gestorClientes.modificarEmail(new ClaveCliente(tipoDoc, nroDoc), mail)) {
                        System.out.println("Mail modificado con exito");
                    } else {
                        System.out.println("Error: Cliente inexistente");
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Numero invalido");
        }
    }

    private void consultasClientes() {
        Scanner sc = new Scanner(System.in);
        String opcion, tipoDoc;
        int nroDoc;
        ClaveCliente unaClaveCliente;
        DatosCliente unosDatos;
        System.out.println("Consulta Cliente:");
        try {

            System.out.println("Ingrese el tipo de documento del cliente:");
            tipoDoc = sc.nextLine();
            System.out.println("Ingrese el numero de documento del cliente:");
            nroDoc = sc.nextInt();
            unaClaveCliente = new ClaveCliente(tipoDoc, nroDoc);
            unosDatos = (DatosCliente) gestorClientes.obtenerCliente(unaClaveCliente);
            if (unosDatos != null) {
                //Si se obtuvieron los datos
                System.out.println("P;" + unaClaveCliente.toString() + unosDatos.toString());
            } else {
                System.out.println("Cliente: " + unaClaveCliente.toString() + " no encontrado");
            }
        } catch (Exception e) {
            System.out.println("Numero invalido");
        }
    }
//===========================PEDIDOS===========================\\

    private void abmPedidos() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        System.out.println("Menú Pedidos: \n"
                + "1. Alta Pedido\n"
                + "2. Baja Pedido\n"
                + "3. Modificación Pedido\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                int codPostalSalida,
                 codPostalLlegada,
                 nroDoc,
                 unaCantBultos,
                 opcionAux;
                String tipoDoc,
                 domRetiro,
                 domEntrega;
                ClaveCliente unaClaveCliente;
                double cantMtsCubicos;
                boolean estaPago;
                System.out.println("ALTA PEDIDO:");
                try {
                    System.out.println("Ingrese el codigo postal de la ciudad de salida:");
                    codPostalSalida = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la ciudad de llegada:");
                    codPostalLlegada = sc.nextInt();
                    System.out.println("Ingrese el tipo de documento del cliente:");
                    tipoDoc = sc.nextLine();
                    System.out.println("Ingrese el numero de documento del cliente:");
                    nroDoc = sc.nextInt();
                    unaClaveCliente = new ClaveCliente(tipoDoc, nroDoc);
                    if (gestorClientes.existeCliente(unaClaveCliente)) {
                        System.out.println("Ingrese la cantidad de metros cubicos: (de la forma: numero.numero o numero)");
                        cantMtsCubicos = sc.nextDouble();
                        System.out.println("Ingrese la cantidad de bultos:");
                        unaCantBultos = sc.nextInt();
                        System.out.println("Ingrese el domicilio de retiro:");
                        domRetiro = sc.nextLine();
                        System.out.println("Ingrese el domicilio de entrega:");
                        domEntrega = sc.nextLine();
                        System.out.println("Si esta pago ingrese '1', sino '2' (Solo el numero):");
                        opcionAux = sc.nextInt();
                        estaPago = (opcionAux == 1);
                        if (gestorPedidos.altaPedido(codPostalLlegada, codPostalSalida, new SolicitudViaje((LocalDate.now()).toString(), unaClaveCliente, Math.abs(cantMtsCubicos), Math.abs(unaCantBultos), domRetiro, domEntrega, estaPago, idSolicitud++))) {
                            //Si se pudo insertar:
                            System.out.println("Pedido insertado con exito: ");
                        } else {
                            System.out.println("Ruta inexistente o ciudad no econtrada");
                        }
                    } else {
                        System.out.println("Cliente inexistente");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmPedidos();
                break;
            case "2":
                int idSolicitud;
                System.out.println("BAJA PEDIDO:");
                try {
                    System.out.println("Ingrese el codigo postal de la ciudad de salida:");
                    codPostalSalida = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la ciudad de llegada:");
                    codPostalLlegada = sc.nextInt();
                    System.out.println("Ingrese el identificador de la solicitud a eliminar:");
                    idSolicitud = sc.nextInt();
                    if (gestorPedidos.bajaPedido(codPostalSalida, codPostalLlegada, idSolicitud)) {
                        System.out.println("Pedido eliminado con exito");
                    } else {
                        System.out.println("Pedido no econtrado");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmPedidos();
                break;
            case "3":
                SolicitudViaje solicitudAModificar;
                //Se piden los datos de la soliciutd a modificar antes de permitir modificar, ya que puede no existir
                try {
                    System.out.println("Modifcación Pedidos:");
                    System.out.println("Ingrese el codigo postal de la ciudad de salida:");
                    codPostalSalida = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la ciudad de llegada:");
                    codPostalLlegada = sc.nextInt();
                    System.out.println("Ingrese el identificador de la solicitud a modificar:");
                    idSolicitud = sc.nextInt();
                    solicitudAModificar = gestorPedidos.obtenerSolicitud(codPostalSalida, codPostalLlegada, idSolicitud);
                    if (solicitudAModificar != null) {
                        modificacionPedido(solicitudAModificar, codPostalSalida, codPostalLlegada);
                    } else {
                        System.out.println("Solicitud: " + idSolicitud + " de " + codPostalSalida + " a " + codPostalLlegada + " no encontrada");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                abmPedidos();
                break;

            default:
                break;
        }
    }

    private void modificacionPedido(SolicitudViaje solicitudAModificar, int ciudadSalida, int ciudadLlegada) {
        Scanner sc = new Scanner(System.in);
        String opcion;
        System.out.println("Menú Modifcación Pedidos: \n"
                + "Para el pedido: " + solicitudAModificar.toString(ciudadSalida, ciudadLlegada) + "\n"
                + "1. Modificar Fecha de Solicitud\n"
                + "2. Modificar Cliente\n"
                + "3. Modificar Cantidad de Metros Cúbicos\n"
                + "4. Modificar Cantidad de Bultos"
                + "5. Modificar Domicilio Entrega"
                + "6. Modificar Domicilio Retiro"
                + "7. Modificar Estado del Pago"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                int año,
                 mes,
                 dia;
                String fechaNueva;
                System.out.println("MODIFICACIÓN FECHA:");
                try {
                    System.out.println("La fecha debe ser del tipo dd/mm/yyyy, solo ingresar numeros");
                    System.out.println("Ingrese el nuevo día:");
                    dia = sc.nextInt();
                    System.out.println("Ingrese el nuevo mes:");
                    mes = sc.nextInt();
                    System.out.println("Ingrese el nuevo año:");
                    año = sc.nextInt();
                    fechaNueva = LocalDate.of(año, mes, dia).toString();
                    gestorPedidos.modificarFecha(solicitudAModificar, fechaNueva);
                    System.out.println("Fecha modificada con exito");
                } catch (Exception e) {
                    System.out.println("Tipo de fecha invalida");
                }
                modificacionPedido(solicitudAModificar, ciudadSalida, ciudadLlegada);
                break;
            case "2":
                String tipoDoc;
                int nroDoc;
                ClaveCliente unaClaveCliente;
                System.out.println("MODIFICACIÓN CLIENTE:");
                try {
                    System.out.println("Ingrese el tipo de documento del cliente:");
                    tipoDoc = sc.nextLine();
                    System.out.println("Ingrese el numero de documento del cliente:");
                    nroDoc = sc.nextInt();
                    unaClaveCliente = new ClaveCliente(tipoDoc, nroDoc);
                    if (gestorClientes.existeCliente(unaClaveCliente)) {
                        //Si el cliente existe
                        gestorPedidos.modificarCliente(solicitudAModificar, unaClaveCliente);
                        System.out.println("Cliente modificado con exito");
                    } else {
                        System.out.println("Cliente " + unaClaveCliente.toString() + " no encontrado");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                modificacionPedido(solicitudAModificar, ciudadSalida, ciudadLlegada);
                break;
            case "3":
                double cantMts;
                System.out.println("MODFICACIÓN MTS CÚBICOS:");
                try {
                    System.out.println("Ingrese la nueva cantidad de metros cúbicos:");
                    cantMts = sc.nextDouble();
                    gestorPedidos.modificarMtsCubicos(solicitudAModificar, Math.abs(cantMts));
                    System.out.println("Pedido modificado con exito");
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                modificacionPedido(solicitudAModificar, ciudadSalida, ciudadLlegada);
                break;
            case "4":
                int nuevaCantBultos;
                System.out.println("MODFICACIÓN CANTIDAD BULTOS:");
                try {
                    System.out.println("Ingrese la nueva cantidad de bultos:");
                    nuevaCantBultos = sc.nextInt();
                    gestorPedidos.modificarCantidadBultos(solicitudAModificar, Math.abs(nuevaCantBultos));
                    System.out.println("Pedido modificado con exito");
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                modificacionPedido(solicitudAModificar, ciudadSalida, ciudadLlegada);
                break;
            case "5":
                String nuevoDom;
                System.out.println("MODIFICACIÓN DOMICILIO ENTREGA:");
                try {
                    System.out.println("Ingrese el nuevo domicilio:");
                    nuevoDom = sc.nextLine();
                    gestorPedidos.modificarDomEntrega(solicitudAModificar, nuevoDom);
                    System.out.println("Pedido modificado con exito");
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                modificacionPedido(solicitudAModificar, ciudadSalida, ciudadLlegada);
                break;
            case "6":
                System.out.println("MODIFICACIÓN DOMICILIO RETIRO:");
                try {
                    System.out.println("Ingrese el nuevo domicilio:");
                    nuevoDom = sc.nextLine();
                    gestorPedidos.modificarDomRetiro(solicitudAModificar, nuevoDom);
                    System.out.println("Pedido modificado con exito");
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                modificacionPedido(solicitudAModificar, ciudadSalida, ciudadLlegada);
                break;
            case "7":
                gestorPedidos.modificarPago(solicitudAModificar);
                System.out.println("Pago modificado con exito");
                modificacionPedido(solicitudAModificar, ciudadSalida, ciudadLlegada);
                break;
            default:
                break;
        }
    }

    private void verificarViaje() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        int codPostalSalida, codPostalLlegada;
        System.out.println("Menú Verificar Viaje: \n"
                + "1. Pedidos entre dos ciudades y espacio necesario\n"
                + "2. Espacio sobrante para pedidos entre ciudades y posibles soliciutdes a sumar\n"
                + "3. Camino perfecto entre dos ciudades y una capacidad\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                Lista listaAux;
                double mtsNecesarios = 0;
                SolicitudViaje solActual;
                try {
                    System.out.println("Pedidos entre dos ciudades y espacio necesario:");
                    System.out.println("Ingrese el codigo postal de la ciudad de salida:");
                    codPostalSalida = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la ciudad de llegada:");
                    codPostalLlegada = sc.nextInt();
                    listaAux = gestorPedidos.listaDePedidos(codPostalSalida, codPostalLlegada);
                    if (listaAux != null) {
                        System.out.println("Lista de Pedidos entre: " + codPostalSalida + " y " + codPostalLlegada);
                        for (int i = 1; i <= listaAux.longitud(); i++) {
                            solActual = (SolicitudViaje) listaAux.recuperar(i);
                            mtsNecesarios += solActual.getCantidadMetrosCubicos();
                            System.out.println(solActual.toString(codPostalSalida, codPostalLlegada));
                        }
                        System.out.println("Se necesita un espacio minimo de " + mtsNecesarios + " mts cubicos");
                    } else {
                        System.out.println("No se encontraron pedidos entre: " + codPostalSalida + " y " + codPostalLlegada);
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                verificarViaje();
                break;
            case "2":
                /*Dada una ciudad A y una ciudad B y una cantidad en metros cúbicos (espacio en
                un camión), verificar si sobra espacio en el camión y hacer un listado de posibles
                solicitudes a ciudades intermedias que se podrían aprovechar a cubrir,
                considerando el camino más corto en kilómetros. */
                Lista listaCiudades;
                mtsNecesarios = 0;
                double mtsDisponibles;
                Cola colaRutaAux = new Cola();
                int ciudadSalidaAux,
                 ciudadLlegadaAux;
                SolicitudViaje solicitudAux;
                System.out.println("Espacio sobrante para pedidos entre ciudades y posibles soliciutdes a sumar");
                try {
                    System.out.println("Ingrese el codigo postal de la ciudad de salida:");
                    codPostalSalida = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la ciudad de llegada:");
                    codPostalLlegada = sc.nextInt();
                    System.out.println("Ingrese la cantidad de metros cubicos disponibles en el camión:");
                    mtsDisponibles = sc.nextDouble();
                    listaCiudades = gestorRutas.caminoConMenorDistancia(codPostalSalida, codPostalLlegada);

                    if (listaCiudades != null && !listaCiudades.esVacia()) {
                        //Obtenemos cuanto sobra de espacio, si es que sobra
                        listaAux = gestorPedidos.listaDePedidos(codPostalSalida, codPostalLlegada);
                        if (listaAux != null && !listaAux.esVacia()) {//Si hay pedidos:
                            System.out.println("Para los Pedidos entre: " + codPostalSalida + " y " + codPostalLlegada + " sobran: ");
                            for (int i = 1; i <= listaAux.longitud(); i++) {
                                //Para cada solicitud viaje de la lista:
                                solActual = (SolicitudViaje) listaAux.recuperar(i);
                                mtsNecesarios += solActual.getCantidadMetrosCubicos();
                            }
                        } else {
                            System.out.println("No hay pedidos entre" + codPostalSalida + " y " + codPostalLlegada + " sobran:");
                        }
                        mtsDisponibles = mtsDisponibles - mtsNecesarios; //Metros cubicos que nos quedan disponible post proceso
                        System.out.println(mtsDisponibles + "mts cubicos");
                        if (mtsDisponibles > 0) {
                            System.out.println("Pedidos que ocupan menos que eso y quedan de pasada:");
                            for (int i = 1; i <= listaCiudades.longitud(); i++) {
                                //agrego cada ciudad a una cola, para poder después obtener los pedidos
                                colaRutaAux.poner(listaCiudades.recuperar(i));
                            }
                            int nAux = 1;
                            while (!colaRutaAux.esVacia()) {
                                //Buscamos todas las solicitudes de viaje dentro de la ruta obtenida
                                ciudadSalidaAux = (int) colaRutaAux.obtenerFrente();//Para la ciudad actual chequeamos las que faltan de la ruta
                                nAux++;//Se empieza desde 2
                                for (int a = nAux; a <= listaCiudades.longitud(); a++) {
                                    //Para cada elemento de la lista chequeamos sus siguientes solicitudes
                                    ciudadLlegadaAux = (int) listaCiudades.recuperar(a);
                                    if (!(ciudadSalidaAux == codPostalSalida && ciudadLlegadaAux == codPostalLlegada)) {
                                        //Para no volver a chequear todos los pedidos de la ciudadA y B original entre si
                                        listaAux = gestorPedidos.listaDePedidos(ciudadSalidaAux, ciudadLlegadaAux);
                                        if (listaAux != null) {
                                            for (int i = 1; i <= listaAux.longitud(); i++) {
                                                //Para cada pedido nuevo, chequeamos si cuple con el requisito, si si la imprimimos
                                                solicitudAux = (SolicitudViaje) listaAux.recuperar(i);
                                                if (solicitudAux.getCantidadMetrosCubicos() <= mtsDisponibles) {
                                                    System.out.println(solicitudAux.toString(ciudadSalidaAux, ciudadLlegadaAux));
                                                }
                                            }
                                        }
                                    }
                                }
                                colaRutaAux.sacar();//Ya usado
                            }
                        }

                    } else {
                        System.out.println("No existen pedidos o camino entre " + codPostalSalida + " y " + codPostalLlegada);
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                verificarViaje();
                break;
            case "3":
                /*Dada una lista de ciudades y una cantidad de metros cúbicos que corresponden a
                la capacidad del camión, verificar si es un “camino perfecto”. Un camino perfecto es
                un camino que existe en el grafo y que hay por lo menos una solicitud que se pueda
                transportar entre las ciudades por las cuales pasará el camión. Por ejemplo, si la
                lista de ciudades es [A, B, C, D], siendo este un camino posible, el camino será
                perfecto si además de existir el camino indicado, hay por lo menos un pedido que
                vaya de la ciudad A a cualquiera de las ciudades B, C o D; por lo menos un pedido
                desde la ciudad B hacia C o D y por lo menos un pedido desde C a D. Tener en
                cuenta que la capacidad del camión debe ser suficiente para cubrir los traslados a
                lo largo de todo el camino. */
                int bucle = 0,
                 ciudadActual,
                 iCiudad = 0;
                double metrosRestantes;
                Cola colaCiudadesEntrada = new Cola();
                boolean caminoPerfecto = true;
                System.out.println("Camino perfecto entre dos ciudades y una capacidad:");
                try {
                    while (bucle == 0) {
                        //Se carga la ruta a evaluar
                        iCiudad++;//nro de ciudad a agregar
                        System.out.println("Ingrese la ciudad " + iCiudad + ":");
                        ciudadActual = sc.nextInt();
                        colaCiudadesEntrada.poner(ciudadActual);//Agregamos la ciudad a la lista
                        System.out.println("Ruta actual: " + colaCiudadesEntrada.toString());//Para que lo vea el usuario
                        System.out.println("Si quiere agregar otra ciudad ingrese 0 (Numero):");
                        bucle = sc.nextInt();
                    }
                    if (gestorRutas.caminoPosible(colaCiudadesEntrada.clone())) {
                        System.out.println("Ingrese la capacidad del camion: (de la forma: numero.numero o numero)");
                        metrosRestantes = sc.nextDouble();
                        //Si el camino ingresado por parametro existe
                        while (caminoPerfecto && !colaCiudadesEntrada.esVacia()) {
                            //Para cada ciudad de colaCiudadesEntrada, menos la ultima, y mientras siga siendo un camino perfecto verificamos que:
                            ciudadActual = (int) colaCiudadesEntrada.obtenerFrente();
                            colaCiudadesEntrada.sacar();
                            if (!colaCiudadesEntrada.esVacia()) {
                                //Si el sacado no es el ultimo de la lista, se revisa camino perfecto
                                metrosRestantes = gestorPedidos.tramoPerfecto(ciudadActual, colaCiudadesEntrada, metrosRestantes);
                            }
                            caminoPerfecto = metrosRestantes >= 0;
                        }
                        if (caminoPerfecto) {
                            System.out.println("El camino dado SI es un camino perfecto");
                        } else {
                            System.out.println("El camino dado NO es un camino perfecto");
                        }
                    } else {
                        System.out.println("Ruta no encontrada");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                verificarViaje();
                break;
            default:
                break;
        }
    }

//===========================SISTEMA===========================\\
    private void mostrarSistema() {
        Scanner sc = new Scanner(System.in);
        String opcion;
        System.out.println("Menú Mostrar Sistema: \n"
                + "1. Mostrar Ciudades (Diccionario)\n"
                + "2. Mostrar Clientes (MapeoAUno)\n"
                + "3. Mostrar Rutas (Grafo Etiquetado no dirigido)\n"
                + "4. Mostrar Pedidos(Ciudades con MapeoAMuchos)\n"
                + "0. Retroceder");
        System.out.print("Ingrese una opción: ");
        opcion = sc.nextLine();
        switch (opcion) {
            case "1":
                System.out.println("ESTRUCTURA CIUDADES:");
                System.out.println(gestorCiudades.toStringEstructura());
                mostrarSistema();
                break;
            case "2":
                System.out.println("ESTRUCTURA CLIENTES:");
                System.out.println(gestorClientes.toStringEstructura());
                mostrarSistema();
                break;
            case "3":
                System.out.println("ESTRUCTURA RUTAS:");
                System.out.println(gestorRutas.toStringEstructura());
                mostrarSistema();
                break;
            case "4":
                System.out.println("ESTRUCTURA PEDIDOS:");
                System.out.println(gestorPedidos.toStringEstructura());
                mostrarSistema();
                break;
            default:
                break;
        }
    }

    private void cargaInicialDelSistema() {
        if (!cargaInicial) {//Si la carga inicial todavía no se hizo
            idSolicitud = gestorLectura.leer();//Se hace la carga inicial
            //Se guarda en el log la estado del sistema luego de la carga incial
            gestorEscritura.escrbirTexto("Estado del sistema luego de la carga inicial:");
            gestorEscritura.escrbirTexto("ESTRUCTURA CIUDADES:\n" + gestorCiudades.toStringEstructura());
            gestorEscritura.escrbirTexto("ESTRUCTURA CLIENTES:\n" + gestorClientes.toStringEstructura());
            gestorEscritura.escrbirTexto("ESTRUCTURA RUTAS:\n" + gestorRutas.toStringEstructura());
            gestorEscritura.escrbirTexto("ESTRUCTURA PEDIDOS:\n" + gestorPedidos.toStringEstructura());
            System.out.println("Sistema cargado con exito");
        } else {
            System.out.println("Carga inicial realizada previamente");
        }
    }

    private void cargaFinDelSistema() {
        gestorEscritura.escrbirTexto("Estado del sistema al final de la ejecución:");
        gestorEscritura.escrbirTexto("ESTRUCTURA CIUDADES:\n" + gestorCiudades.toStringEstructura());
        gestorEscritura.escrbirTexto("ESTRUCTURA CLIENTES:\n" + gestorClientes.toStringEstructura());
        gestorEscritura.escrbirTexto("ESTRUCTURA RUTAS:\n" + gestorRutas.toStringEstructura());
        gestorEscritura.escrbirTexto("ESTRUCTURA PEDIDOS:\n" + gestorPedidos.toStringEstructura());
    }
}
