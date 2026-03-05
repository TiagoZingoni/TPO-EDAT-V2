package mudanzas;

public class Ciudad {

    private final int codPostal;
    private String nombreCiudad;
    private String provincia;

    public Ciudad(int codigoPostal, String nombreCiudad, String nombreProvincia) {
        this.codPostal = codigoPostal;
        this.nombreCiudad = nombreCiudad;
        this.provincia = nombreProvincia;
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
}
