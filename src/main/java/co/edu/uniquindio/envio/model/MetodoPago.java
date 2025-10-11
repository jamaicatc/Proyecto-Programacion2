package co.edu.uniquindio.envio.model;

public class MetodoPago {
    private String idMetodoPago;
    private String tipo;
    private String numeroEnmascarado;
    private String titular;

    public MetodoPago() {
    }

    public MetodoPago(String idMetodoPago, String tipo, String numeroEnmascarado, String titular) {
        this.idMetodoPago = idMetodoPago;
        this.tipo = tipo;
        this.numeroEnmascarado = numeroEnmascarado;
        this.titular = titular;
    }
}
