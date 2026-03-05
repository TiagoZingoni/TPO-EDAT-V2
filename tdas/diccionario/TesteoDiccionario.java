package tdas.diccionario;

public class TesteoDiccionario {

    public static void main(String[] args) {
        Diccionario arbol = new Diccionario();
        //testeos:
        //Rotacion izquierda en medio de arbol
        arbol.insertar(4, "n");
        arbol.insertar(3, "n");
        arbol.insertar(5, "n");
        arbol.insertar(1, "n");
        arbol.insertar(2, "n");
        //arbol.insertar(0, "n");
        System.out.println(arbol.toString());
        System.out.println(arbol.listarClaves());
        
        arbol.eliminar(3);
        System.out.println(arbol.toString());
        System.out.println(arbol.listarClaves());

    }
}
