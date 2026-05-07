package tdas.diccionario;

import tdas.lineales.Lista;

public class TestDiccionario {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    private static int totalChecks = 0;
    private static int totalOK = 0;

    public static void main(String[] args) {
        System.out.println(ANSI_CYAN + "==================================================");
        System.out.println("   BATERÍA DE PRUEBAS EXTREMA - DICCIONARIO AVL   ");
        System.out.println("==================================================" + ANSI_RESET);

        testDiccionarioVacio();
        testInsercionBasicaYBusqueda();
        testInsercionDuplicados();
        testRotacionesSimples();
        testRotacionesDobles();
        testBalanceoComplejoRecursivo();
        testEliminacionCasosClasicos();
        testEliminacionConRebalanceo();
        testListadosYRangos();
        testSecuenciaCompletaInsertarEliminar();

        System.out.println(ANSI_CYAN + "\n==================================================");
        System.out.println("            PRUEBAS FINALIZADAS                     ");
        System.out.println("==================================================" + ANSI_RESET);

        System.out.println(ANSI_YELLOW + "Checks OK: " + totalOK + " / " + totalChecks + ANSI_RESET);
    }

    private static void testDiccionarioVacio() {
        imprimirTitulo("TEST 1: CASOS LÍMITE (VACÍO / NULOS)");
        Diccionario dicc = new Diccionario();

        evaluar("esVacio en árbol nuevo", dicc.esVacio());
        evaluar("Eliminar en vacío no explota y devuelve false", !dicc.eliminar(10));
        evaluar("ExisteClave sobre clave inexistente", !dicc.existeClave(10));
        evaluar("ExisteClave(null) no explota y devuelve false", !dicc.existeClave(null));
        evaluar("Listar claves en vacío da lista vacía", dicc.listarClaves().esVacia());
        System.out.println(dicc.toString());
    }

    private static void testInsercionBasicaYBusqueda() {
        imprimirTitulo("TEST 2: INSERCIÓN BÁSICA, BÚSQUEDA E INORDEN");
        Diccionario dicc = new Diccionario();

        int[] claves = {40, 20, 60, 10, 30, 50, 70};
        boolean todoOk = true;

        for (int c : claves) {
            boolean ok = dicc.insertar(c, "v" + c);
            todoOk = todoOk && ok;
        }

        evaluar("Todas las inserciones básicas devolvieron true", todoOk);

        boolean todasExisten = true;
        for (int c : claves) {
            todasExisten = todasExisten && dicc.existeClave(c);
        }
        evaluar("Todas las claves insertadas existen", todasExisten);

        evaluar("Clave inexistente no se encuentra", !dicc.existeClave(999));

        Object[] esperado = {10, 20, 30, 40, 50, 60, 70};
        evaluar("El inorden de claves es exactamente el esperado",
                listaEquals(dicc.listarClaves(), esperado));
        System.out.println(dicc.toString());
    }

    private static void testInsercionDuplicados() {
        imprimirTitulo("TEST 3: INSERCIÓN DE DUPLICADOS");
        Diccionario dicc = new Diccionario();

        evaluar("Inserta 25 por primera vez", dicc.insertar(25, "A"));
        evaluar("Inserta 10 por primera vez", dicc.insertar(10, "B"));
        evaluar("Inserta 40 por primera vez", dicc.insertar(40, "C"));

        boolean dup = dicc.insertar(25, "Z");
        evaluar("Insertar clave duplicada devuelve false", !dup);

        Object[] esperado = {10, 25, 40};
        evaluar("La estructura no duplicó la clave", listaEquals(dicc.listarClaves(), esperado));
        System.out.println(dicc.toString());
    }

    private static void testRotacionesSimples() {
        imprimirTitulo("TEST 4: ROTACIONES SIMPLES (INVARIANTE DE ORDEN)");
        Diccionario dicc = new Diccionario();

        dicc.insertar(30, "A");
        dicc.insertar(20, "B");
        dicc.insertar(10, "C");

        evaluar("Tras 30,20,10 están las 3 claves",
                dicc.existeClave(30) && dicc.existeClave(20) && dicc.existeClave(10));

        evaluar("Inorden correcto tras caso LL (rotación simple a derecha)",
                listaEquals(dicc.listarClaves(), new Object[]{10, 20, 30}));

        dicc = new Diccionario();
        dicc.insertar(10, "A");
        dicc.insertar(20, "B");
        dicc.insertar(30, "C");

        evaluar("Inorden correcto tras caso RR (rotación simple a izquierda)",
                listaEquals(dicc.listarClaves(), new Object[]{10, 20, 30}));
        System.out.println(dicc.toString());
    }

    private static void testRotacionesDobles() {
        imprimirTitulo("TEST 5: ROTACIONES DOBLES (INVARIANTE DE ORDEN)");
        Diccionario dicc = new Diccionario();

        dicc.insertar(30, "A");
        dicc.insertar(10, "B");
        dicc.insertar(20, "C");

        evaluar("Caso LR: están las 3 claves",
                dicc.existeClave(30) && dicc.existeClave(10) && dicc.existeClave(20));
        evaluar("Inorden correcto tras caso LR",
                listaEquals(dicc.listarClaves(), new Object[]{10, 20, 30}));

        dicc = new Diccionario();
        dicc.insertar(10, "A");
        dicc.insertar(30, "B");
        dicc.insertar(20, "C");

        evaluar("Caso RL: están las 3 claves",
                dicc.existeClave(10) && dicc.existeClave(30) && dicc.existeClave(20));
        evaluar("Inorden correcto tras caso RL",
                listaEquals(dicc.listarClaves(), new Object[]{10, 20, 30}));
        System.out.println(dicc.toString());
    }

    private static void testBalanceoComplejoRecursivo() {
        imprimirTitulo("TEST 6: BALANCEO EN CADENA (PROPAGACIÓN)");
        Diccionario dicc = new Diccionario();

        int[] datos = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35, 1, 6, 14, 16};
        boolean todoOk = true;

        for (int n : datos) {
            todoOk = todoOk && dicc.insertar(n, "val" + n);
        }
        evaluar("Inserción masiva con rebalanceos encadenados", todoOk);

        Object[] esperado = {1, 5, 6, 10, 14, 15, 16, 25, 27, 30, 35, 50, 60, 75, 80};
        evaluar("El inorden se mantiene correcto tras múltiples inserciones",
                listaEquals(dicc.listarClaves(), esperado));
        System.out.println(dicc.toString());
    }

    private static void testEliminacionCasosClasicos() {
        imprimirTitulo("TEST 7: ELIMINACIÓN DE HOJA / UN HIJO / DOS HIJOS / RAÍZ");
        Diccionario dicc = new Diccionario();

        /*
         * Árbol base:
         *          50
         *        /    \
         *      30      70
         *     /  \    /  \
         *   20   40  60  80
         */
        int[] claves = {50, 30, 70, 20, 40, 60, 80};
        for (int c : claves) {
            dicc.insertar(c, "v" + c);
        }

        evaluar("Eliminar hoja (20)", dicc.eliminar(20));
        evaluar("20 ya no existe", !dicc.existeClave(20));
        evaluar("Inorden luego de borrar hoja",
                listaEquals(dicc.listarClaves(), new Object[]{30, 40, 50, 60, 70, 80}));

        dicc.insertar(20, "v20");
        dicc.insertar(35, "v35");

        evaluar("Eliminar nodo con un hijo (40, si 35 queda como único hijo)", dicc.eliminar(40));
        evaluar("40 ya no existe", !dicc.existeClave(40));
        evaluar("35 sigue existiendo", dicc.existeClave(35));

        evaluar("Eliminar nodo con dos hijos (30)", dicc.eliminar(30));
        evaluar("30 ya no existe", !dicc.existeClave(30));
        evaluar("50 sigue existiendo", dicc.existeClave(50));

        evaluar("Eliminar raíz (50)", dicc.eliminar(50));
        evaluar("50 ya no existe", !dicc.existeClave(50));
        System.out.println(dicc.toString());
    }

    private static void testEliminacionConRebalanceo() {
        imprimirTitulo("TEST 8: ELIMINACIÓN Y RE-BALANCEO");
        Diccionario dicc = new Diccionario();

        int[] datos = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 5};
        for (int n : datos) {
            dicc.insertar(n, "v" + n);
        }

        evaluar("Eliminar 80", dicc.eliminar(80));
        evaluar("Eliminar 70", dicc.eliminar(70));
        evaluar("Eliminar 60", dicc.eliminar(60));

        evaluar("Claves restantes se siguen encontrando",
                dicc.existeClave(50) && dicc.existeClave(30) && dicc.existeClave(20) && dicc.existeClave(10));

        evaluar("El inorden sigue ordenado tras varias eliminaciones",
                listaEsAscendente(dicc.listarClaves()));
        System.out.println(dicc.toString());
    }

    private static void testListadosYRangos() {
        imprimirTitulo("TEST 9: LISTADOS Y RANGOS");
        Diccionario dicc = new Diccionario();

        dicc.insertar(100, "X");
        dicc.insertar(50, "Y");
        dicc.insertar(150, "Z");
        dicc.insertar(25, "A");
        dicc.insertar(75, "B");
        dicc.insertar(125, "C");
        dicc.insertar(175, "D");

        evaluar("Rango muy pequeño [51, 99] vacío",
                dicc.listarDatosRango(51, 99).esVacia());

        evaluar("Rango [50, 50] contiene 1 elemento",
                dicc.listarDatosRango(50, 50).longitud() == 1);

        evaluar("Rango [25, 175] contiene todos",
                dicc.listarDatosRango(25, 175).longitud() == 7);

        evaluar("Rango que incluye todo [-100, 500] contiene todos",
                dicc.listarDatosRango(-100, 500).longitud() == 7);
        System.out.println(dicc.toString());
    }

    private static void testSecuenciaCompletaInsertarEliminar() {
        imprimirTitulo("TEST 10: CICLO COMPLETO INSERTAR / BORRAR / REINSERTAR");
        Diccionario dicc = new Diccionario();

        int[] base = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};

        boolean todoOk = true;
        for (int n : base) {
            todoOk = todoOk && dicc.insertar(n, "v" + n);
        }
        evaluar("Inserción completa del árbol perfecto", todoOk);

        int[] borrar = {1, 3, 5, 7, 9, 11, 13, 15, 8, 4, 12};
        for (int n : borrar) {
            evaluar("Eliminar " + n, dicc.eliminar(n));
        }

        evaluar("Claves restantes correctas",
                listaEquals(dicc.listarClaves(), new Object[]{2, 6, 10, 14}));

        evaluar("Reinsertar claves eliminadas funciona",
                dicc.insertar(8, "v8") && dicc.insertar(4, "v4") && dicc.insertar(12, "v12"));

        evaluar("La estructura sigue ordenada al final",
                listaEsAscendente(dicc.listarClaves()));
        System.out.println(dicc.toString());
    }

    // ----------------- AUXILIARES -----------------
    private static void imprimirTitulo(String titulo) {
        System.out.println("\n" + ANSI_PURPLE + ">>> " + titulo + ANSI_RESET);
    }

    private static void evaluar(String desc, boolean res) {
        totalChecks++;
        String status = res ? ANSI_GREEN + "[OK]" : ANSI_RED + "[ERROR]";
        if (res) {
            totalOK++;
        }
        System.out.println(status + ANSI_RESET + " " + desc);
    }

    private static boolean listaEquals(Lista lista, Object[] esperado) {
        if (lista == null) {
            return false;
        }
        if (lista.longitud() != esperado.length) {
            return false;
        }

        for (int i = 1; i <= esperado.length; i++) {
            Object actual = lista.recuperar(i);
            Object exp = esperado[i - 1];

            if (actual == null && exp != null) {
                return false;
            }
            if (actual != null && !actual.equals(exp)) {
                return false;
            }
        }
        return true;
    }

    private static boolean listaEsAscendente(Lista lista) {
        if (lista == null || lista.longitud() <= 1) {
            return true;
        }

        for (int i = 1; i < lista.longitud(); i++) {
            Comparable a = (Comparable) lista.recuperar(i);
            Comparable b = (Comparable) lista.recuperar(i + 1);
            if (a.compareTo(b) > 0) {
                return false;
            }
        }
        return true;
    }
}
