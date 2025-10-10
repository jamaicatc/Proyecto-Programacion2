package co.edu.uniquindio.envio.model.builder;

public class Direccion {
    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private String coordenadas;

    public Direccion() {
    }

    public Direccion(String idDireccion, String alias, String calle, String ciudad, String coordenadas) {
        this.idDireccion = idDireccion;
        this.alias = alias;
        this.calle = calle;
        this.ciudad = ciudad;
        this.coordenadas = coordenadas;
    }
}
