package Mudanzas;

public class ClaveCliente {
    //atributos
    private final String tipoDocumento;
    private final int numeroDocumento;

    public ClaveCliente(String tipoDoc, int numDoc){
        this.tipoDocumento = tipoDoc;
        this.numeroDocumento = numDoc;
    }
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    public int getNumeroDocumento() {
        return numeroDocumento;
    }
    //para hashmap
    public String getClaveString(){
        return tipoDocumento + numeroDocumento;
    }
    /* BORRAR? 
    @Override
    public int hashCode() {
        return (tipoDocumento + numeroDocumento).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ClaveCliente)) return false;
        ClaveCliente c = (ClaveCliente) o;
        return c.tipoDocumento.equals(this.tipoDocumento)
                && c.numeroDocumento == this.numeroDocumento;
    }
    */
}

