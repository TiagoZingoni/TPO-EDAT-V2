package mudanzas.gestores;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime; //Para la lectura de archivos (carga inicial)

public class GestorEscritura {

    /*Aca se ecuentran los metodos encargados la carga inicial del sistema, así como los de
    escritura y lectura*/
    File archivoLog;

    public GestorEscritura() {
        //Requiere de los otros gestores para carga directa al sistema
        //Acá se crea el archivo de texto de log, después hay metodos que serviran para agregar lo realizado
        try {
            archivoLog = new File("arhivo.log");
        } catch (Exception e) {
        }
    }

    public Boolean objetoAgregado(String tipoObjeto, String objeto, boolean logrado) {
        //Agrega al archivo log.txt se pudo agregar el objeto, cual era, y de que tipo
        boolean exito = true;
        if (logrado) {
            //Si se logró se pone que el tipo objeto se pudo agregar, la hora añade detalle 
            escrituraLog(tipoObjeto + " agregado/a con exito: " + objeto + "\n");
        } else {
            escrituraLog("Fallo al agregar el/la" + tipoObjeto + ": " + objeto + "\n");
        }
        return exito;
    }

    public Boolean objetoEliminado(String tipoObjeto, String objeto, boolean logrado) {
        //Agrega al archivo log.txt se pudo agregar el objeto, cual era, y de que tipo
        boolean exito = true;
        if (logrado) {
            //Si se logró se pone que el tipo objeto se pudo agregar, la hora añade detalle 
            escrituraLog(tipoObjeto + " eliminado/a con exito: " + objeto + "\n");
        } else {
            escrituraLog("Fallo al eliminar el/la" + tipoObjeto + ": " + objeto + "\n");
        }
        return exito;
    }

    public Boolean objetoModificado(String tipoObjeto, String objetoViejo, String objetoNuevo, boolean logrado) {
        //Agrega al archivo log.txt se pudo agregar el objeto, cual era, y de que tipo
        boolean exito = true;
        if (logrado) {
            //Si se logró se pone que el tipo objeto se pudo agregar, la hora añade detalle
            escrituraLog(tipoObjeto + " modificado/a con exito de " + objetoViejo + " a " + objetoNuevo + "\n");
        } else {
            escrituraLog("Fallo al modificar el/la" + tipoObjeto + ": " + objetoViejo + "\n");//Si se intenta modificar algo que no existe
        }
        return exito;
    }

    private void escrituraLog(String txt) {
        try {
            FileWriter escritor = new FileWriter(archivoLog, true);//Se agrega al .log
            escritor.write(LocalDateTime.now().toString() + ": " + txt);
            escritor.close();
        } catch (Exception e) {
        }
    }

    public void escrbirTexto(String texto) {
        try {
            FileWriter escritor = new FileWriter(archivoLog, true);//Se agrega al .log
            escritor.write(texto + "\n");
            escritor.close();
        } catch (Exception e) {
        }
    }
}
