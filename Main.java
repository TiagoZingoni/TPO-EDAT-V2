
import java.io.File;
import mudanzas.MudanzasCompartidas;

public class Main {

    public static void main(String[] args) {
        try {
            File archivoLog = new File("archivoLog");
            File archivoLectura = new File("archivoLectura");
            MudanzasCompartidas sistema = new MudanzasCompartidas();
            sistema.menu(archivoLectura, archivoLog);
        } catch (Exception e) {
            System.out.println("Error de archivos");
        }
    }
}
