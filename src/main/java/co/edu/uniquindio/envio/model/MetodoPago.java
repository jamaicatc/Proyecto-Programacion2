package co.edu.uniquindio.envio.model;

/**
 * Representa un método de pago asociado a un usuario.
 */
public class MetodoPago {
    private String alias;
    private String numero;
    private String tipo; // Ej: "Tarjeta de Crédito", "PSE"

    // Constructores
    public MetodoPago() {}

    public MetodoPago(String alias, String numero, String tipo) {
        this.alias = alias;
        this.numero = numero;
        this.tipo = tipo;
    }

    // Getters y Setters
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
