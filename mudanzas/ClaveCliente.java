package mudanzas;

public class ClaveCliente {

    //atributos
    private final String tipoDocumento;
    private final int numeroDocumento;

    public ClaveCliente(String tipoDoc, int numDoc) {
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
    public String toString() {
        return tipoDocumento + ";" + numeroDocumento;
    }

    @Override
    public boolean equals(Object clave2) {
        return (this.toString()).equals(clave2.toString());
    }
}
