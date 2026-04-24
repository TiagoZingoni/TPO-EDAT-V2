package mudanzas.tests;

import mudanzas.gestores.*;

public class TestGestorRutas {

    public static void main(String[] args) {
        GestorRutas gestor = new GestorRutas();

        // ==========================================
        // 1. ALTA DE CIUDADES Y RUTAS
        // ==========================================
        System.out.println("--- Cargando Ciudades ---");
        gestor.altaCiudad("Neuquen");
        gestor.altaCiudad("Cipolletti");
        gestor.altaCiudad("Plottier");
        gestor.altaCiudad("Roca");
        gestor.altaCiudad("Allen");
        gestor.altaCiudad("Viedma");

        System.out.println("--- Cargando Rutas (ABM Rutas) ---");
        // Ruta 1: Directa pero larga en km
        gestor.altaRuta("Neuquen", "Cipolletti", 5.0);
        gestor.altaRuta("Cipolletti", "Roca", 100.0);

        // Ruta 2: Más ciudades pero menos km en total (35.0 km)
        gestor.altaRuta("Cipolletti", "Allen", 15.0);
        gestor.altaRuta("Allen", "Roca", 15.0);

        gestor.altaRuta("Neuquen", "Plottier", 15.0);

        // ==========================================
        // 2. TEST DE CONSULTAS (MÉTODOS DE LA IMAGEN)
        // ==========================================
        System.out.println("\n--- RESULTADOS DE LAS CONSULTAS ---");

        // A. Menos Ciudades (Debería usar BFS)
        System.out.println("1. Camino por menos ciudades (Neuquen -> Roca):");
        System.out.println(gestor.caminoPorMenosCiudades("Neuquen", "Roca"));

        // B. Menor Distancia (Debería usar DFS con etiquetas)[cite: 1, 2]
        System.out.println("\n2. Camino con menor distancia en KM (Neuquen -> Roca):");
        System.out.println(gestor.caminoConMenorDistancia("Neuquen", "Roca"));

        // C. Caminos pasando por ciudad específica (Escalas)
        System.out.println("\n3. Caminos de Neuquen a Roca pasando por Allen:");
        System.out.println(gestor.caminosPasanPorCiudad("Neuquen", "Roca", "Allen"));

        // D. Verificar si es posible por menos de X km
        System.out.println("\n4. ¿Existe recorrido menor a 50.0 km de Neuquen a Roca?");
        System.out.println(gestor.recorridoMenorA("Neuquen", "Roca", 50.0));

        System.out.println("\n5. ¿Existe recorrido menor a 20.0 km de Neuquen a Roca?");
        System.out.println(gestor.recorridoMenorA("Neuquen", "Roca", 20.0));

        // ==========================================
        // 3. BAJA Y MODIFICACIÓN
        // ==========================================
        System.out.println("\n--- TEST DE BAJA Y MODIFICACIÓN ---");
        System.out.println("Eliminando ruta directa Cipolletti -> Roca...");
        gestor.bajaRuta("Cipolletti", "Roca");

        System.out.println("Nuevo camino con menos ciudades:");
        System.out.println(gestor.caminoPorMenosCiudades("Neuquen", "Roca"));
    }
}
