package mudanzas;

public class SolicitudViaje {

    private String fechaSolicitud;
    private ClaveCliente clienteSolicitud;
    private double cantidadMetrosCubicos;
    private int cantidadBultos;
    private String domicilioRetiro;
    private String domicilioEntrega;
    private boolean pago;
    private int idSolicitud;

    public SolicitudViaje(String unaFechaSolicitud, ClaveCliente unCliente, double unaCantidadMtsCb,
            int unaCantBultos, String unDomRetiro, String unDomEntrega, boolean estaPago, int idSolicitud) {
        this.fechaSolicitud = unaFechaSolicitud;
        this.clienteSolicitud = unCliente;
        this.cantidadMetrosCubicos = unaCantidadMtsCb;
        this.cantidadBultos = unaCantBultos;
        this.domicilioEntrega = unDomEntrega;
        this.domicilioRetiro = unDomRetiro;
        this.pago = estaPago;
        this.idSolicitud = idSolicitud; //será dada por una variable global
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public ClaveCliente getClienteSolicitud() {
        return clienteSolicitud;
    }

    public double getCantidadMetrosCubicos() {
        return cantidadMetrosCubicos;
    }

    public int getCantidadBultos() {
        return cantidadBultos;
    }

    public String getDomicilioRetiro() {
        return domicilioRetiro;
    }

    public String getDomicilioEntrega() {
        return domicilioEntrega;
    }

    public boolean isPago() {
        return pago;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setPago() {
        //Un pago no puede ser cancelado
        pago = true;
    }

    public String toString(int ciudadA, int ciudadB) {
        /*ciudad origen, ciudad destino, fecha de solicitud, identificador del
        cliente, cantidad de metros cúbicos, cantidad de bultos, domicilio de retiro y domicilio de
        entrega, y si el envío está pago (Si/No) */
        String estado, respuesta;
        if (pago) {
            estado = "T";
        } else {
            estado = "F";
        }
        return "S;" + ciudadA + ";" + ciudadB + ";" + fechaSolicitud + ";" + clienteSolicitud.toString()
                + ";" + cantidadMetrosCubicos + ";" + cantidadBultos + ";" + domicilioRetiro + ";"
                + domicilioEntrega + ";" + estado;
    }

}
