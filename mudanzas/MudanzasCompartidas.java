package mudanzas;

import java.util.Scanner;
import mudanzas.gestores.*;
import tdas.Lista;

public class MudanzasCompartidas {

    //Todos los gestores 
    GestorCiudades gestorCiudades = new GestorCiudades();
    GestorRutas gestorRutas = new GestorRutas();
    GestorCliente gestorClientes = new GestorCliente(100);//Tamaño de la lista hash

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String opcion;

        while (true) {
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
                    + "10. Mostrar Sistema\n");
            System.out.println("Ingrese una opción: ");
            opcion = sc.nextLine();
            //Opciones del menu
            switch (opcion) {
                case "1":

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
                    break;
                case "7":
                    consultasCiudades();
                    break;
                case "8":
                    consultaViaje();
                    break;
                case "9":
                    break;
                case "10":
                    break;
                default:
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
        System.out.println("MODIFICAR CIUDAD\n"
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
        String tipoDoc, nombre, apellido, mail;
        int nroDoc, telefono;
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
                    telefono = sc.nextInt();
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
        String tipoDoc, nombre, apellido, mail;
        int nroDoc, telefono;
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
                    telefono = sc.nextInt();
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
                 codPostalLlegada;
                System.out.println("ALTA PEDIDO:");
                try {
                    System.out.println("Ingrese el codigo postal de la ciudad de salida:");
                    codPostalSalida = sc.nextInt();
                    System.out.println("Ingrese el codigo postal de la ciudad de llegada:");
                    codPostalLlegada = sc.nextInt();
                    if (gestorRutas.existeCamino(codPostalSalida, codPostalLlegada)) {
                        //Si existe un camino entre dichos codigos postales, agregamos el pedido

                    } else {
                        System.out.println("Ruta inexistente o ciudad no econtrada");
                    }
                } catch (Exception e) {
                    System.out.println("Numero invalido");
                }
                break;
        }
    }
}
