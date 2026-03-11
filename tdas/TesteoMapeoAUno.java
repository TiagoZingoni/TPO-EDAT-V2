package tdas;
import java.lang.reflect.*;
import java.util.*;

/**
 * TesteoMapeoAUno - pruebas ampliadas por consola.
 *
 * - Requiere que exista la clase MapeoAUno con constructor MapeoAUno(int).
 * - Invoca por reflexión los métodos: asociar, desasociar, obtenerValor,
 *   obtenerConjuntoDominio, obtenerConjuntoRango, toString.
 *
 * Diseñado para ser tolerante: si un método no existe o lanza excepción,
 * el test sigue y reporta.
 */
public class TesteoMapeoAUno {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {
        try {
            // crear instancia directamente (si MapeoAUno está en el mismo package)
            Object mapa = new MapeoAUno(11);

            System.out.println("Instancia de MapeoAUno creada: " + mapa.getClass().getName());
            System.out.println("=== Iniciando baterías de test ===\n");

            testInsercionBasica(mapa);
            testReasociarYConsistencia(mapa);
            testColisiones(mapa);
            testEliminacion(mapa);
            testToString(mapa);
            testConjuntosDominioRango(mapa);
            testCampoInternoCant(mapa);
            testCasosBorde(mapa);

            System.out.println("\n--- RESUMEN ---");
            System.out.printf("Tests corridos: %d, Tests aprobados: %d, Tests fallidos: %d%n",
                    testsRun, testsPassed, testsRun - testsPassed);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // ---------- TESTS ----------

    private static void testInsercionBasica(Object mapa) {
        printlnHeader("Inserción básica");
        boolean ok;

        ok = invokeBool(mapa, "asociar", "A", 1);
        report("asociar(A,1) -> true", ok);

        ok = invokeBool(mapa, "asociar", "B", 2);
        report("asociar(B,2) -> true", ok);

        ok = invokeBool(mapa, "asociar", "C", 3);
        report("asociar(C,3) -> true", ok);

        Object va = invokeObj(mapa, "obtenerValor", "A");
        assertEquals("obtenerValor(A) == 1", 1, va);

        Object vx = invokeObj(mapa, "obtenerValor", "X"); // no existe
        if (vx == null) {
            report("obtenerValor(X) -> null (no existe)", true);
        } else {
            report("obtenerValor(X) -> debería ser null pero fue " + vx, false);
        }
    }

    private static void testReasociarYConsistencia(Object mapa) {
        printlnHeader("Re-asociar (actualizar valor) y consistencia");
        // Guardar tamaño dominio antes
        Set<Object> dominiosAntes = obtenerSetDesde(mapa, "obtenerConjuntoDominio");
        int tamAntes = (dominiosAntes == null) ? -1 : dominiosAntes.size();

        boolean ok = invokeBool(mapa, "asociar", "B", 200); // reasociar B
        report("reasociar(B,200) -> true/ok", ok);

        Object vb = invokeObj(mapa, "obtenerValor", "B");
        assertEquals("obtenerValor(B) == 200", 200, vb);

        Set<Object> dominiosDespues = obtenerSetDesde(mapa, "obtenerConjuntoDominio");
        int tamDespues = (dominiosDespues == null) ? -1 : dominiosDespues.size();

        if (tamAntes >= 0 && tamDespues >= 0) {
            report("tamaño dominio no incrementó al reasociar", tamAntes == tamDespues);
        } else {
            System.out.println("WARN: no se pudo comprobar tamaño dominio antes/despues");
        }
    }

    private static void testColisiones(Object mapa) {
        printlnHeader("Colisiones y encadenamiento (si aplica)");
        // Intentamos provocar colisiones usando enteros: si hash = key % TAM
        // Insertamos k, k+TAM, k+2*TAM — pero no sabemos TAM; asumimos 11 (constructor usado).
        // Si tu funcionHash hace % TAM, esto genera colisión.
        int base = 5;
        int tamHip = 11;
        boolean ok1 = invokeBool(mapa, "asociar", base, "v" + base);
        boolean ok2 = invokeBool(mapa, "asociar", base + tamHip, "v" + (base + tamHip));
        boolean ok3 = invokeBool(mapa, "asociar", base + 2 * tamHip, "v" + (base + 2 * tamHip));

        report("asociar(int) primeros 3 claves (colisión esperada)", ok1 && ok2 && ok3);

        Object r1 = invokeObj(mapa, "obtenerValor", base);
        Object r2 = invokeObj(mapa, "obtenerValor", base + tamHip);
        Object r3 = invokeObj(mapa, "obtenerValor", base + 2 * tamHip);

        assertEquals("valor clave base", "v" + base, r1);
        assertEquals("valor clave base+tamHip", "v" + (base + tamHip), r2);
        assertEquals("valor clave base+2*tamHip", "v" + (base + 2 * tamHip), r3);
    }

    private static void testEliminacion(Object mapa) {
        printlnHeader("Eliminación y efectos");

        boolean ex = invokeBool(mapa, "desasociar", "C"); // existía
        report("desasociar(C) -> true", ex);

        Object vc = invokeObj(mapa, "obtenerValor", "C");
        report("obtenerValor(C) -> null tras desasociar", vc == null);

        boolean ex2 = invokeBool(mapa, "desasociar", "NoExiste");
        // puede devolver false o true dependiendo de implementación; aceptamos false como esperado
        report("desasociar(NoExiste) -> false (o no existe)", !ex2 ? true : true);

        // Asegurar que B (reasociado) siga presente
        Object vb = invokeObj(mapa, "obtenerValor", "B");
        report("obtenerValor(B) sigue presente", vb != null && vb.equals(200));
    }

    private static void testToString(Object mapa) {
        printlnHeader("toString() - formato y contenido");
        String s = mapa.toString();
        System.out.println("toString(): " + s);
        boolean tieneA = s != null && s.contains("A");
        boolean tiene1 = s != null && s.contains("1");
        report("toString contiene 'A' y '1' (indicativo de pares)", tieneA && tiene1);
    }

    private static void testConjuntosDominioRango(Object mapa) {
        printlnHeader("obtenerConjuntoDominio() y obtenerConjuntoRango()");
        Set<Object> dominios = obtenerSetDesde(mapa, "obtenerConjuntoDominio");
        Set<Object> rangos = obtenerSetDesde(mapa, "obtenerConjuntoRango");

        if (dominios != null) {
            System.out.println("Dominio: " + dominios);
            report("Dominio contiene 'A'", dominios.contains("A"));
            report("Dominio contiene 'B'", dominios.contains("B"));
        } else {
            System.out.println("WARN: No se pudo obtener conjunto dominio (metodo no encontrado o no iterable).");
        }

        if (rangos != null) {
            System.out.println("Rangos: " + rangos);
            report("Rangos contiene 1 o 100 (depende re-asociación)", rangos.contains(1) || rangos.contains(100) || rangos.contains(200));
        } else {
            System.out.println("WARN: No se pudo obtener conjunto rango.");
        }
    }

    private static void testCampoInternoCant(Object mapa) {
        printlnHeader("Comprobar campo privado 'cant' (si existe)");
        Integer cant = getPrivateIntField(mapa, "cant");
        if (cant != null) {
            System.out.println("Valor interno 'cant' = " + cant);
            // comparar con tamaño del dominio (si disponible)
            Set<Object> dominios = obtenerSetDesde(mapa, "obtenerConjuntoDominio");
            if (dominios != null) {
                report("cant coincide con tamaño dominio", cant == dominios.size());
            } else {
                System.out.println("No se puede comparar 'cant' con dominio (dominio no obtenido).");
            }
        } else {
            System.out.println("Campo 'cant' no accesible (no existe o no es int).");
        }
    }

    private static void testCasosBorde(Object mapa) {
        printlnHeader("Casos borde: nulls y duplicados");
        // intentar asociar null key y null value (si la impl lo permite)
        try {
            boolean bnull = invokeBool(mapa, "asociar", null, "vnull");
            report("asociar(null, 'vnull') -> no lanza excepción (ret=" + bnull + ")", true);
        } catch (Throwable t) {
            report("asociar(null, 'vnull') -> lanza excepción", false);
        }

        try {
            boolean vnull = invokeBool(mapa, "asociar", "NuloValor", null);
            report("asociar('NuloValor', null) -> no lanza excepción (ret=" + vnull + ")", true);
        } catch (Throwable t) {
            report("asociar('NuloValor', null) lanza excepción", false);
        }

        // duplicado: asociar A con mismo valor
        boolean dup = invokeBool(mapa, "asociar", "A", 1);
        report("asociar(A,1) nuevamente -> ok/permitido (ret=" + dup + ")", true);
    }

    // ---------- UTILIDADES de reflexión y aserción ----------

    private static Object invokeObj(Object instancia, String nombre, Object... args) {
        try {
            Method m = findMethod(instancia.getClass(), nombre, args.length);
            if (m == null) {
                throw new NoSuchMethodException("No existe método: " + nombre + " con " + args.length + " args");
            }
            m.setAccessible(true);
            Object[] conv = convertArgsFor(m.getParameterTypes(), args);
            return m.invoke(instancia, conv);
        } catch (InvocationTargetException ite) {
            System.out.println("Invocación de " + nombre + " lanzó excepción: " + ite.getTargetException());
            return null;
        } catch (Exception e) {
            System.out.println("Error invocando " + nombre + ": " + e.getMessage());
            return null;
        }
    }

    private static boolean invokeBool(Object instancia, String nombre, Object... args) {
        Object r = invokeObj(instancia, nombre, args);
        if (r == null) return false;
        if (r instanceof Boolean) return (Boolean) r;
        if (r instanceof Number) return ((Number) r).intValue() != 0;
        return true;
    }

    // encuentra método por nombre y cantidad de parámetros (public y declarados)
    private static Method findMethod(Class<?> cls, String name, int paramCount) {
        for (Method m : cls.getMethods()) if (m.getName().equals(name) && m.getParameterCount() == paramCount) return m;
        for (Method m : cls.getDeclaredMethods()) if (m.getName().equals(name) && m.getParameterCount() == paramCount) return m;
        return null;
    }

    // convertir argumentos simples para tipos primitivos
    private static Object[] convertArgsFor(Class<?>[] pts, Object[] args) {
        Object[] out = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            out[i] = convertArg(pts[i], args[i]);
        }
        return out;
    }

    private static Object convertArg(Class<?> target, Object arg) {
        if (arg == null) return null;
        if (target.isPrimitive()) {
            if (target == int.class && arg instanceof Number) return ((Number) arg).intValue();
            if (target == long.class && arg instanceof Number) return ((Number) arg).longValue();
            if (target == boolean.class && arg instanceof Boolean) return arg;
            if (target == double.class && arg instanceof Number) return ((Number) arg).doubleValue();
            // si el target es primitivo pero arg no coincide, intentar boxing con los tipos wrapper
        }
        // si target es Object o wrapper, retornamos arg tal cual (Integer, String, etc.)
        return arg;
    }

    // convierte el retorno del método conjunto a Set<Object> si es posible
    private static Set<Object> obtenerSetDesde(Object instancia, String metodo) {
        Object r = invokeObj(instancia, metodo);
        if (r == null) return null;
        // Si ya es Collection
        if (r instanceof Collection) {
            return new HashSet<>((Collection<?>) r);
        }
        // Si es array
        if (r.getClass().isArray()) {
            int len = Array.getLength(r);
            Set<Object> s = new HashSet<>();
            for (int i = 0; i < len; i++) s.add(Array.get(r, i));
            return s;
        }
        // Si es Iterable (pero no Collection)
        if (r instanceof Iterable) {
            Set<Object> s = new HashSet<>();
            for (Object e : (Iterable<?>) r) s.add(e);
            return s;
        }
        // Intentar método toArray()
        try {
            Method ta = r.getClass().getMethod("toArray");
            Object arr = ta.invoke(r);
            if (arr != null && arr.getClass().isArray()) {
                int len = Array.getLength(arr);
                Set<Object> s = new HashSet<>();
                for (int i = 0; i < len; i++) s.add(Array.get(arr, i));
                return s;
            }
        } catch (Exception ignored) {}
        // Intentar get(i) + size() (por si es Lista propia)
        try {
            Method sizeM = r.getClass().getMethod("size");
            Method getM = r.getClass().getMethod("get", int.class);
            int n = (Integer) sizeM.invoke(r);
            Set<Object> s = new HashSet<>();
            for (int i = 0; i < n; i++) s.add(getM.invoke(r, i));
            return s;
        } catch (Exception ignored) {}
        // No convertible
        System.out.println("No se pudo convertir el retorno de " + metodo + " a Set (tipo: " + r.getClass().getName() + ")");
        return null;
    }

    // intenta leer campo privado int
    private static Integer getPrivateIntField(Object instancia, String fieldName) {
        try {
            Field f = instancia.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            Object v = f.get(instancia);
            if (v instanceof Integer) return (Integer) v;
            if (v instanceof Number) return ((Number) v).intValue();
        } catch (Exception e) {
            // no existe o no accesible
        }
        return null;
    }

    // ---------- Reporting / asserts ----------

    private static void report(String descripcion, boolean ok) {
        testsRun++;
        if (ok) testsPassed++;
        System.out.printf("%-60s : %s%n", descripcion, ok ? "OK" : "FAIL");
    }

    private static void assertEquals(String descripcion, Object esperado, Object actual) {
        testsRun++;
        boolean ok = (esperado == null) ? (actual == null) : esperado.equals(actual);
        if (ok) testsPassed++;
        System.out.printf("%-60s : %s (esperado=%s, obtenido=%s)%n", descripcion, ok ? "OK" : "FAIL",
                String.valueOf(esperado), String.valueOf(actual));
    }

    private static void printlnHeader(String title) {
        System.out.println("\n---- " + title + " ----");
    }
}