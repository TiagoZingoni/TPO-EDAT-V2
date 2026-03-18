package mudanzas;

public class SolicitudViaje {
    private String fechaSolicitud;
    private ClaveCliente clienteSolicitud;
    private double cantidadMetrosCubicos;
    private int cantidadBultos;
    private String domicilioRetiro;
    private String domicilioEntrega;
    private boolean pago;

    public SolicitudViaje(String unaFechaSolicitud, ClaveCliente unCliente, double unaCantidadMtsCb, 
        int unaCantBultos, String unDomRetiro, String unDomEntrega, boolean estaPago){
            this.fechaSolicitud = unaFechaSolicitud;
            this.clienteSolicitud = unCliente;
            this.cantidadMetrosCubicos = unaCantidadMtsCb;
            this.cantidadBultos = unaCantBultos;
            this.domicilioEntrega = unDomEntrega;
            this.domicilioRetiro = unDomRetiro;
            this.pago = estaPago;
    }

}
