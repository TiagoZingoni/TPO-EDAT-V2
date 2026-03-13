package mudanzas.tests;

import mudanzas.Ciudad;
import mudanzas.gestores.GestorCiudades;

public class testeoGestorCiudades {

    public static void main(String[] args) {
        GestorCiudades gestorCiudades = new GestorCiudades();
        //cargamos 20 ciudades      
        gestorCiudades.altaCiudad(new Ciudad(1000, "Buenos Aires", "Buenos Aires"));
        gestorCiudades.altaCiudad(new Ciudad(5000, "Córdoba", "Córdoba"));
        gestorCiudades.altaCiudad(new Ciudad(2000, "Rosario", "Santa Fe"));
        gestorCiudades.altaCiudad(new Ciudad(7600, "Mar del Plata", "Buenos Aires"));
        gestorCiudades.altaCiudad(new Ciudad(4000, "San Miguel de Tucumán", "Tucumán"));
        gestorCiudades.altaCiudad(new Ciudad(5500, "Mendoza", "Mendoza"));
        gestorCiudades.altaCiudad(new Ciudad(3400, "Corrientes", "Corrientes"));
        gestorCiudades.altaCiudad(new Ciudad(3000, "Santa Fe", "Santa Fe"));
        gestorCiudades.altaCiudad(new Ciudad(8300, "Neuquén", "Neuquén"));
        gestorCiudades.altaCiudad(new Ciudad(9100, "Trelew", "Chubut"));
        gestorCiudades.altaCiudad(new Ciudad(9400, "Río Gallegos", "Santa Cruz"));
        gestorCiudades.altaCiudad(new Ciudad(5700, "San Luis", "San Luis"));
        gestorCiudades.altaCiudad(new Ciudad(4700, "San Fernando del Valle de Catamarca", "Catamarca"));
        gestorCiudades.altaCiudad(new Ciudad(4600, "San Salvador de Jujuy", "Jujuy"));
        gestorCiudades.altaCiudad(new Ciudad(3500, "Resistencia", "Chaco"));
        gestorCiudades.altaCiudad(new Ciudad(3600, "Formosa", "Formosa"));
        gestorCiudades.altaCiudad(new Ciudad(5400, "San Juan", "San Juan"));
        gestorCiudades.altaCiudad(new Ciudad(3100, "Paraná", "Entre Ríos"));
        gestorCiudades.altaCiudad(new Ciudad(4400, "Salta", "Salta"));
        gestorCiudades.altaCiudad(new Ciudad(8500, "Viedma", "Río Negro"));
        gestorCiudades.altaCiudad(new Ciudad(2100, "Paraná", "Entre Ríos"));
        gestorCiudades.altaCiudad(new Ciudad(3000, "Paraná", "Entre Ríos"));
        gestorCiudades.altaCiudad(new Ciudad(3100, "Paraná", "Entre Ríos"));
        System.out.println(gestorCiudades.toStringEstructura());

        System.out.println(gestorCiudades.listarClaves().toString());
    }
}
