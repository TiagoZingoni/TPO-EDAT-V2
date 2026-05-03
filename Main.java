
import java.io.File;
import java.io.FileWriter;
import mudanzas.MudanzasCompartidas;

public class Main {

    public static void main(String[] args) {
        try {
            File archivoLog = new File("archivos/archivoLog");
            FileWriter fw = new FileWriter(archivoLog, false);//Vacío el archivo en cada ejecución nueva 
            File archivoLectura = new File("archivos/archivoLectura");
            MudanzasCompartidas sistema = new MudanzasCompartidas();
            sistema.menu(archivoLectura, archivoLog);
        } catch (Exception e) {
            System.out.println("Error de archivos");
        }
    }
}
