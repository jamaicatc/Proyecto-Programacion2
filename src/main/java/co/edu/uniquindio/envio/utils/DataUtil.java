package co.edu.uniquindio.envio.utils;

import co.edu.uniquindio.envio.model.EmpresaLogistica;
import co.edu.uniquindio.envio.model.EstadoDisponibilidad;
import co.edu.uniquindio.envio.model.Repartidor;
import co.edu.uniquindio.envio.model.Usuario;

import static co.edu.uniquindio.envio.model.Repartidor.builder;

public class DataUtil {
    public static EmpresaLogistica inicializarDatos() {
        EmpresaLogistica empresaLogistica = new EmpresaLogistica();
        Usuario usuario1 = new Usuario("1234","Santiago Trujillo","santi@gmail.com","3145822540");
        Usuario usuario2 = new Usuario("2245","Juan David","juancho@gmail.com","3235878540");
        Usuario usuario3 = new Usuario("1234","Robinson","robin@gmail.com","3505824871");
        Repartidor repartidor1 = new Repartidor("1234", "juan", "4455", "311", EstadoDisponibilidad.ACTIVO, "Armenia");
        Repartidor repartidor2 = new Repartidor("7788", "jose", "7610", "314", EstadoDisponibilidad.EN_RUTA, "Calarcá");
        Repartidor repartidor3 = new Repartidor("2971", "isabela", "1098", "350", EstadoDisponibilidad.INACTIVO, "Circasia");

        empresaLogistica.getListaRepartidores().add(repartidor1);
        empresaLogistica.getListaRepartidores().add(repartidor2);
        empresaLogistica.getListaRepartidores().add(repartidor3);
        empresaLogistica.getListaUsuarios().add(usuario1);
        empresaLogistica.getListaUsuarios().add(usuario2);
        empresaLogistica.getListaUsuarios().add(usuario3);
        return empresaLogistica;
    }
}
