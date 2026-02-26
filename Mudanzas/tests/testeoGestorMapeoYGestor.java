package mudanzas.tests;

import mudanzas.ClaveCliente;
import mudanzas.DatosCliente;
import mudanzas.GestorCliente;

public class testeoGestorMapeoYGestor {

    public static void main(String[] args) {
        GestorCliente gestorCliente = new GestorCliente(100);
        int lim = 20;

        gestorCliente.altaCliente(new ClaveCliente("DNI", 43910234), new DatosCliente("Walter", "Mazzantti", 1141234567, "walterMazzantti@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 44778901), new DatosCliente("Santiago", "Montiel", 1142345678, "santiagoMontiel@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 45590022), new DatosCliente("Diego", "Tarzia", 1143456789, "diegoTarzia@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 46234567), new DatosCliente("Ignacio", "Pussetto", 1144567890, "ignacioPussetto@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 47012345), new DatosCliente("Gabriel", "Ávalos", 1145678901, "gabrielAvalos@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 47865432), new DatosCliente("Felipe", "Loyola", 1146789012, "felipeLoyola@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 48543210), new DatosCliente("Matías", "Abaldo", 1147890123, "matiasAbaldo@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 49321098), new DatosCliente("Milton", "Valenzuela", 1148901234, "miltonValenzuela@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 40112233), new DatosCliente("Federico", "Vera", 1149012345, "federicoVera@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 40987654), new DatosCliente("Kevin", "Lomónaco", 1150123456, "kevinLomonaco@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 41567890), new DatosCliente("Pablo", "Galdames", 1151234567, "pabloGaldames@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 42345678), new DatosCliente("Rodrigo", "Fernández Cedrés", 1152345678, "rodrigoCedres@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 43123456), new DatosCliente("Joaquín", "Blázquez", 1153456789, "joaquinBlazquez@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 43876543), new DatosCliente("Lucas", "Lavagnino", 1154567890, "lucasLavagnino@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 44654321), new DatosCliente("Mateo", "Morro", 1155678901, "mateoMorro@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 45432109), new DatosCliente("Álvaro", "Banquero", 1156789012, "alvaroBanquero@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 46210987), new DatosCliente("Simón", "Pinto", 1157890123, "simonPinto@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 47098765), new DatosCliente("Ezequiel", "Duarte", 1158901234, "ezequielDuarte@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 47809876), new DatosCliente("Martín", "Caseres", 1159012345, "martinCaseres@gmail.com"));
        gestorCliente.altaCliente(new ClaveCliente("DNI", 48567890), new DatosCliente("Tomás", "Agustín", 1160123456, "tomasAgustin@gmail.com"));

        System.out.println(gestorCliente.listarClientes());//debería dar la lista de 20 jugadores
        System.out.println("Cantidad de elementos cargados: " + gestorCliente.cantidadPersonas());
        gestorCliente.bajaCliente(new ClaveCliente("DNI", 47098765));
        //debería eliminar a uno y bajar la cant de personas:
        System.out.println("Se elimina uno");
        System.out.println("Cantidad de elementos cargados: " + gestorCliente.cantidadPersonas());
        System.out.println("Estructura actual:");
        System.out.println(gestorCliente.estructuraToString());
        gestorCliente.vaciar();
        System.out.println("Vaciado. Debería dar 0: " + gestorCliente.cantidadPersonas());
        System.out.println("Estructura actual:");
        System.out.println(gestorCliente.estructuraToString());
        gestorCliente.altaCliente(new ClaveCliente("DNI", 48567890), new DatosCliente("Tomás", "Agustín", 1160123456, "tomasAgustin@gmail.com"));

        System.out.println("Chequeo una sola alta. Debería dar 1: " + gestorCliente.cantidadPersonas());
        System.out.println("Estructura actual:");
        System.out.println(gestorCliente.estructuraToString());
    }
}
