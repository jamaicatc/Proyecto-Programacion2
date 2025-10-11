package co.edu.uniquindio.envio.model.builder;

import co.edu.uniquindio.envio.model.Direccion;

public class DireccionBuilder {
    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private String coordenadas;

    public DireccionBuilder setIdDireccion(String idDireccion){
        this.idDireccion = idDireccion;
        return this;
    }

    public DireccionBuilder setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public DireccionBuilder setCalle(String calle) {
        this.calle = calle;
        return this;
    }

    public DireccionBuilder setCiudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public DireccionBuilder setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
        return this;
    }

    public Direccion build() {
        return new Direccion(idDireccion, alias, calle, ciudad, coordenadas);
    }
}
