package Mudanzas;

public class DatosCliente {
    //Atributos
    private String nombre;
    private String apellido;
    private int telefono;
    private String email;

    public DatosCliente(String nombre, String apellido, int telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }
    //getters
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

    // lista de datos
    public String toString(){
        //para el listado de clientes
        return ";"+nombre+";"+apellido+";"+telefono+";"+email+";";
    }
}
