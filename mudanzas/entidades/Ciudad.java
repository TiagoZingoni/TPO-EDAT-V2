package mudanzas.entidades;

public class Ciudad {

    private final int codPostal;
    private String nombreCiudad;
    private String provincia;
    private Pedidos solicitudesViajes;

    public Ciudad(int codigoPostal, String nombreCiudad, String nombreProvincia) {
        this.codPostal = codigoPostal;
        this.nombreCiudad = nombreCiudad;
        this.provincia = nombreProvincia;
        this.solicitudesViajes = new Pedidos();
    }

    //setters
    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.provincia = nombreProvincia;
    }

    //getters
    public int getCodigoPostal() {
        return this.codPostal;
    }

    public String getNombreCiudad() {
        return this.nombreCiudad;
    }

    public String getNombreProvincia() {
        return this.provincia;
    }

    public Pedidos getSolicitudesViajes() {
        return solicitudesViajes;
    }

    //String
    public String toString() {
        return "C;" + codPostal + ";" + nombreCiudad + ";" + provincia;
    }
}
