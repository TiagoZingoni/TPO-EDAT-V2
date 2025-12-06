package Mudanzas;

public class Cliente {
    // Atributos de cliente.
    private final String tipoDocumento;
    private final int numeroDocumento;
    private String nombre;
    private String apellido;
    private int telefono;
    private String email;

    public Cliente(String tipoDocumento, int numeroDocumento, String nombre, String apellido, int telefono, String email) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }
    // Getters
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public int getNumeroDocumento() {
        return numeroDocumento;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public int getTelefono() {
        return telefono;
    }
    public String getEmail() {
        return email;
    }
    public String getClave() {
        // Devuelve una clave única para el cliente basada en su tipo y número de documento. Sirve para hashmap
        return tipoDocumento +";" +numeroDocumento;
    }
    public String getDatosComunes(){
        // Devuelve los datos comunes del cliente como una cadena.
        return nombre + ";" + apellido + ";" + telefono + ";" + email;
    }
    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public void setEmail(String mail){
        this.email = mail;
    }
}
