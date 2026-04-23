package tdas.grafo;

import tdas.Lista;

public class TestGrafo {

    public static void main(String[] args) {
        GrafoEtiquetado mapa = new GrafoEtiquetado(null);

        // ==========================================
        // 1. CARGA DE VÉRTICES (CIUDADES)
        // ==========================================
        System.out.println("--- Insertando Ciudades ---");
        mapa.insertarVertice("Neuquen");
        mapa.insertarVertice("Cipolletti");
        mapa.insertarVertice("Oro");
        mapa.insertarVertice("Allen");
        mapa.insertarVertice("Roca");
        mapa.insertarVertice("Viedma"); // Ciudad aislada para testear fallos

        // ==========================================
        // 2. CARGA DE ARCOS (DISTANCIAS EN KM)
        // ==========================================
        System.out.println("--- Insertando Rutas (Arcos) ---");
        // Ruta 1: Directa pero larga en km
        mapa.insertarArco("Neuquen", "Cipolletti", 5.0);
        mapa.insertarArco("Cipolletti", "Roca", 100.0); // Pocos saltos, muchos km

        // Ruta 2: Muchos saltos pero corta en km (ideal para testear MenorRecorrido)
        mapa.insertarArco("Cipolletti", "Oro", 10.0);
        mapa.insertarArco("Oro", "Allen", 10.0);
        mapa.insertarArco("Allen", "Roca", 10.0);

        System.out.println("\nEstado del Grafo:\n" + mapa.toString());

        // ==========================================
        // 3. TEST: CAMINO MÁS CORTO (BFS - Pocos Saltos)
        // ==========================================
        // Debería devolver: [Neuquen, Cipolletti, Roca]
        System.out.println("\n--- TEST 1: Camino con menos saltos (BFS) ---");
        Lista corto = mapa.caminoMasCorto("Neuquen", "Roca");
        System.out.println("Resultado (esperado 3 nodos): " + corto.toString());

        // ==========================================
        // 4. TEST: TODOS LOS CAMINOS (DFS)
        // ==========================================
        System.out.println("\n--- TEST 2: Listar todos los caminos posibles ---");
        Lista todos = mapa.listarCaminos("Neuquen", "Roca");
        System.out.println("Caminos hallados:\n" + todos.toString());

        // ==========================================
        // 5. TEST: CAMINO MENOR RECORRIDO (DFS + Pesos)
        // ==========================================
        // Debería devolver la ruta por Oro y Allen (35km total) 
        // a pesar de tener más nodos que la ruta directa (105km).
        System.out.println("\n--- TEST 3: Camino de menor distancia (km) ---");
        Lista menorKm = mapa.caminoMenorRecorrido("Neuquen", "Roca");
        System.out.println("Resultado (esperado el de 35.0 km): " + menorKm.toString());

        // ==========================================
        // 6. TEST: CASOS DE BORDE
        // ==========================================
        System.out.println("\n--- TEST 4: Casos de Borde ---");

        System.out.print("Camino a ciudad inexistente: ");
        System.out.println(mapa.caminoMasCorto("Neuquen", "Madrid").toString()); // Esperado: []

        System.out.print("Camino a ciudad aislada (Viedma): ");
        System.out.println(mapa.caminoMenorRecorrido("Neuquen", "Viedma").toString()); // Esperado: []

        System.out.print("Eliminando arco Cipolletti-Oro... ");
        mapa.eliminarArco("Cipolletti", "Oro");
        System.out.println("Nuevo camino menor:");
        System.out.println(mapa.caminoMenorRecorrido("Neuquen", "Roca").toString()); // Ahora debe ser el de 105km
    }
}
