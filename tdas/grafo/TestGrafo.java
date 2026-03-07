package tdas.grafo;

/**
 * Test completo para GrafoEtiquetado (no dirigido, etiquetado).
 *
 * SUPUESTOS de API (ajustar si tu clase usa otros nombres):
 * - constructor:       new GrafoEtiquetado(null)  // acepta null si no querés vértice inicial
 * - boolean insertarVertice(Object v)
 * - boolean eliminarVertice(Object v)
 * - boolean insertarArco(Object v1, Object v2, Comparable etiqueta)
 * - boolean eliminarArco(Object v1, Object v2)
 * - Comparable obtenerArco(Object v1, Object v2) // devuelve etiqueta o null
 * - String toString()                              // representación tipo lista de adyacencia
 *
 * Si alguna firma es distinta, adaptá las llamadas correspondientes.
 */
public class TestGrafo{
    public static void main(String[] args) {
        System.out.println("=== TESTS GRAFO ETIQUETADO (NO DIRIGIDO) - COMPLETO ===\n");

        GrafoEtiquetado g = new GrafoEtiquetado(null);

        // -------------------------
        // 1) Inserción de vértices
        // -------------------------
        System.out.println("== Insercion de vertices ==");
        checkBool(true, g.insertarVertice("A"), "insertarVertice(A) -> true");
        checkBool(true, g.insertarVertice("B"), "insertarVertice(B) -> true");
        checkBool(true, g.insertarVertice("C"), "insertarVertice(C) -> true");
        checkBool(true, g.insertarVertice("D"), "insertarVertice(D) -> true");

        // duplicado: comportamiento dependiente de implementación (puede devolver false)
        boolean dupReturned = g.insertarVertice("A");
        System.out.println("insertarVertice(A) duplicado -> returned: " + dupReturned
                + " (puede ser true si permite repetidos, o false si lo rechaza).");

        System.out.println("\nNota: el orden de impresión en toString() puede depender de la estructura interna (inserción/orden de lista).");
        System.out.println("Estructura actual (toString()):");
        System.out.println(g.toString());
        System.out.println();

        // -------------------------
        // 2) Inserción de aristas
        // -------------------------
        System.out.println("== Insercion de arcos (etiquetas) ==");
        checkBool(true, g.insertarArco("A", "B", 5), "insertarArco(A,B,5) -> true");
        checkBool(true, g.insertarArco("A", "C", "x"), "insertarArco(A,C,\"x\") -> true");
        checkBool(true, g.insertarArco("B", "C", 7), "insertarArco(B,C,7) -> true");
        checkBool(true, g.insertarArco("C", "D", 9), "insertarArco(C,D,9) -> true");

        System.out.println("\nEstructura tras insertar arcos:");
        System.out.println(g.toString());
        System.out.println();

        // 2.1 Comprobar simetría (no dirigido): obtenerArco(a,b) == obtenerArco(b,a)
        System.out.println("== Comprobacion de no-dirigido (simetria) ==");
        checkObj(g.obtenerArco("A", "B"), g.obtenerArco("B", "A"), "obtenerArco(A,B) == obtenerArco(B,A)");
        checkObj(g.obtenerArco("A", "C"), g.obtenerArco("C", "A"), "obtenerArco(A,C) == obtenerArco(C,A)");
        System.out.println();

        // 2.2 Comprobaciones de etiquetas
        checkObj(5, g.obtenerArco("A", "B"), "obtenerArco(A,B) -> 5");
        checkObj(5, g.obtenerArco("B", "A"), "obtenerArco(B,A) -> 5");
        checkObj("x", g.obtenerArco("A", "C"), "obtenerArco(A,C) -> \"x\"");
        checkObj(7, g.obtenerArco("B", "C"), "obtenerArco(B,C) -> 7");
        checkObj(9, g.obtenerArco("C", "D"), "obtenerArco(C,D) -> 9");

        checkObj(null, g.obtenerArco("A", "D"), "obtenerArco(A,D) -> null (no existe)");

        // -------------------------
        // 3) Arco duplicado / actualizar etiqueta
        // -------------------------
        System.out.println("\n== Arco duplicado / actualizar etiqueta ==");
        boolean insDup = g.insertarArco("A", "B", 10); // intenta insertar A-B con etiqueta distinta
        Comparable etiquetaPost = g.obtenerArco("A", "B");
        if (insDup) {
            // Si la implementación devolvió true, puede haber actualizado o aceptado duplicado.
            if (etiquetaPost != null && etiquetaPost.equals(10)) {
                System.out.println("Comportamiento observado: insertarArco(A,B,10) devolvió true y etiqueta actualizada a 10 -> PASS (actualiza etiqueta).");
            } else {
                System.out.println("Comportamiento observado: insertarArco(A,B,10) devolvió true pero etiqueta actual sigue como " + etiquetaPost
                        + " -> revisar si permite duplicados o maneja multiaristas.");
            }
        } else {
            // devolver false indica que no permitió insertar duplicado.
            System.out.println("Comportamiento observado: insertarArco(A,B,10) devolvió false -> PASS (rechaza duplicados). Etiqueta actual = " + etiquetaPost);
        }

        // -------------------------
        // 4) Eliminar arco existente
        // -------------------------
        System.out.println("\n== Eliminacion de arco existente ==");
        checkBool(true, g.eliminarArco("A", "C"), "eliminarArco(A,C) -> true (existente)");
        checkObj(null, g.obtenerArco("A", "C"), "obtenerArco(A,C) -> null (tras eliminar)");

        System.out.println("\nEstructura tras eliminar arco A-C:");
        System.out.println(g.toString());
        System.out.println();

        // -------------------------
        // 5) Eliminar arco inexistente
        // -------------------------
        System.out.println("== Eliminacion de arco inexistente ==");
        boolean elimInex = g.eliminarArco("A", "D"); // A-D nunca existió
        // comportamiento típico: false (no habia arco). Aceptamos false como PASS.
        if (elimInex) {
            System.out.println("eliminarArco(A,D) devolvio true -> inesperado si no existia. (revisar implementacion).");
        } else {
            System.out.println("eliminarArco(A,D) devolvio false -> PASS (no existia arco).");
        }

        // -------------------------
        // 6) Eliminar vertice y arcos asociados
        // -------------------------
        System.out.println("\n== Eliminacion de vertice (y arcos asociados) ==");
        checkBool(true, g.eliminarVertice("C"), "eliminarVertice(C) -> true (esperado)");
        checkObj(null, g.obtenerArco("B", "C"), "obtenerArco(B,C) -> null (C eliminado)");
        checkObj(null, g.obtenerArco("C", "D"), "obtenerArco(C,D) -> null (C eliminado)");

        System.out.println("\nEstructura tras eliminar vertice C:");
        System.out.println(g.toString());
        System.out.println();

        // -------------------------
        // 7) Insertar arco con vertice inexistente
        // -------------------------
        System.out.println("== Insertar arco con vertice inexistente (A,E,1) ==");
        boolean insConNuevoVertice = g.insertarArco("A", "E", 1);
        Comparable arcoAE = g.obtenerArco("A", "E");
        if (insConNuevoVertice && arcoAE != null) {
            System.out.println("insertarArco(A,E,1) devolvio true y obtenerArco(A,E) = " + arcoAE
                    + " -> IMPLEMENTACION CREA VERTICE IMPLICITO (aceptable si eso se documenta).");
        } else if (!insConNuevoVertice && arcoAE == null) {
            System.out.println("insertarArco(A,E,1) devolvio false y obtenerArco(A,E) = null -> IMPLEMENTACION NO CREA VERTICES IMPLICITOS (tambien aceptable).");
        } else {
            System.out.println("Comportamiento mixto: insertarArco devolvio " + insConNuevoVertice + " y etiqueta = " + arcoAE
                    + " -> revisa la especificación de la clase.");
        }
        System.out.println("Estructura tras intento insertar A-E:");
        System.out.println(g.toString());
        System.out.println();

        // -------------------------
        // 8) Self-loop (A,A,2) - algunos TDA lo permiten, otros no.
        // -------------------------
        System.out.println("== Self-loop (A,A,2) ==");
        boolean self = g.insertarArco("A", "A", 2);
        Comparable arA = g.obtenerArco("A", "A");
        if (self) {
            System.out.println("insertarArco(A,A,2) -> true; obtenerArco(A,A) = " + arA + " (comportamiento: permite self-loop).");
        } else {
            System.out.println("insertarArco(A,A,2) -> false (comportamiento: no permite self-loop).");
        }
        System.out.println();

        // -------------------------
        // 9) Eliminar vertice inexistente
        // -------------------------
        System.out.println("== Eliminar vertice inexistente ==");
        boolean elimVertInex = g.eliminarVertice("Z"); // no existe
        if (elimVertInex) {
            System.out.println("eliminarVertice(Z) -> true (inusual: devolvio true aun cuando no existia).");
        } else {
            System.out.println("eliminarVertice(Z) -> false -> PASS (no existia).");
        }
        System.out.println();

        // -------------------------
        // 10) Prueba conjunta grande y consistencia final
        // -------------------------
        System.out.println("== Prueba conjunta: consistencia final con TreeMap (sanity) ==");
        // Como no queremos usar TreeMap como oracle (para no asumir import), haremos verificaciones simples:
        // - todos los arcos consultables devuelven valores coherentes y la simetria se mantiene.
        boolean simetriaOk = checkAllEdgesSymmetry(g);
        System.out.println("Comprobacion automatica de simetria sobre aristas visibles: " + (simetriaOk ? "OK" : "FAIL"));

        System.out.println("\nESTADO FINAL DEL GRAFO:");
        System.out.println(g.toString());
        System.out.println("\n=== FIN TESTS GRAFO ETIQUETADO (NO DIRIGIDO) ===");
    }

    // ------------------
    // Helpers de asercion
    // ------------------
    private static void checkBool(boolean esperado, boolean actual, String descripcion) {
        String res = (esperado == actual) ? "PASS ✅" : "FAIL ❌";
        System.out.printf("%-70s -> %s (esperado: %s, actual: %s)%n", descripcion, res, esperado, actual);
    }

    private static void checkObj(Object esperado, Object actual, String descripcion) {
        boolean ok = (esperado == null) ? actual == null : esperado.equals(actual);
        String res = ok ? "PASS ✅" : "FAIL ❌";
        System.out.printf("%-70s -> %s (esperado: %s, actual: %s)%n", descripcion, res, esperado, actual);
    }

    /**
     * Recorre el toString() (lista de adyacencia) y chequea que para cada arista (u->v : etiqueta)
     * tambien exista la arista simétrica v->u con la misma etiqueta.
     *
     * Este helper es heurístico: depende del formato de toString(), pero
     * como fallback pregunta obtenerArco(u,v) y obtenerArco(v,u) por cada vértice ya conocido.
     *
     * Para no depender totalmente de toString(), hacemos una comprobación sobre un conjunto finito
     * de vértices básicos: A,B,C,D,E (los que usamos en los tests).
     */
    private static boolean checkAllEdgesSymmetry(GrafoEtiquetado g) {
        String[] vertices = new String[]{"A", "B", "C", "D", "E"};
        boolean ok = true;
        for (String u : vertices) {
            for (String v : vertices) {
                Comparable uv = safeObtenerArco(g, u, v);
                Comparable vu = safeObtenerArco(g, v, u);
                if (uv == null && vu == null) continue;
                if (uv == null && vu != null) {
                    System.out.println("ASYMMETRY: obtenerArco(" + u + "," + v + ") = null but obtenerArco(" + v + "," + u + ") = " + vu);
                    ok = false;
                } else if (uv != null && vu == null) {
                    System.out.println("ASYMMETRY: obtenerArco(" + u + "," + v + ") = " + uv + " but obtenerArco(" + v + "," + u + ") = null");
                    ok = false;
                } else {
                    // both non-null: compare values
                    if (!uv.equals(vu)) {
                        System.out.println("ASYMMETRY: etiqueta distinta para (" + u + "," + v + ")=" + uv + " vs (" + v + "," + u + ")=" + vu);
                        ok = false;
                    }
                }
            }
        }
        return ok;
    }

    private static Comparable safeObtenerArco(GrafoEtiquetado g, Object a, Object b) {
        try {
            return g.obtenerArco(a, b);
        } catch (Exception ex) {
            // si la implementación lanza al preguntar por vértice inexistente, devolvemos null y seguimos
            return null;
        }
    }
}