package mudanzas;

import java.util.Scanner;
import mudanzas.gestores.GestorCiudades;
import mudanzas.gestores.GestorRutas;

public class MudanzasCompartidas {

    GestorCiudades gestorCiudades = new GestorCiudades();
    GestorRutas gestorRutas = new GestorRutas();

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String opcion;

        while (true) {
            //Menú
            System.out.println("Menu:");
            System.out.println(imprimirMenu());
            System.out.print("Ingrese una opción: ");
            opcion = sc.nextLine();
            switchMenu(opcion);

        }
    }

    public static String imprimirMenu() {
        //Retorna el string del menú
        String menu;
        menu = ("1. Carga inicial del sistema.\n"
                + "2. ABM de Ciudades\n"
                + "3. ABM de la red de rutas\n"
                + "4. ABM de clientes\n"
                + "5. ABM de pedidos\n"
                + "6. Consulta sobre clientes\n"
                + "7. Consultas sobre ciudades\n"
                + "8. Consultas sobre viajes\n"
                + "9. Verificar viaje\n"
                + "10. Mostrar Sistema");
        return menu;
    }

    public void switchMenu(String opcion) {
        //Opcion seleccionada del menú:
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
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                consultasCiudades();
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            default:
                break;
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
                    System.out.println(gestorCiudades.obtenerCiudad(intCodPostal));
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
                    System.out.println("Ciudades encontradas:");
                    System.out.println(gestorCiudades.obtenerCiudadPorPrefijo(intCodPostal));
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
}
