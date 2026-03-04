package tdas.diccionario;

public class TesteoDiccionario {

    public static void main(String[] args) {
        Diccionario arbol = new Diccionario();
        arbol.insertar(5, "n");
        arbol.insertar(4, "n");
        arbol.insertar(3, "n");
        System.out.println(arbol.toString());
        System.out.println(arbol.listarClaves());
    }
}
