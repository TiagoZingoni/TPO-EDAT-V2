package mudanzas;

import java.util.Scanner;
import mudanzas.gestores.GestorCiudades;

public class MudanzasCompartidas {

    GestorCiudades gestorCiudades = new GestorCiudades();

    public void menu() {
        Scanner sc = new Scanner(System.in);
        String opcion;

        while (true) {
            //Menú
            System.out.println("Menu:");
            System.out.println(imprimirMenu());
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

                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                abmCiudades();
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            default:
                throw new AssertionError();
        }
    }

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
                    } else {
                        System.out.println("Error, " + intCodPostal + " ya es un código postal en uso.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
                break;
            case "2":
                System.out.println("BAJA CIUDAD:");
                break;
        }
    }
}
