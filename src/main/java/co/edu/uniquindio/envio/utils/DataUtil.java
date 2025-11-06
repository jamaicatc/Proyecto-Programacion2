package co.edu.uniquindio.envio.utils;

import co.edu.uniquindio.envio.model.*;

import java.time.LocalDate;

public class DataUtil {
    public static final String ADMIN_USUARIO = "santiago";
    public static final String ADMIN_CONTRASENA = "admin123";
    public static final String ADMIN_ROL = "Administrador";
    public static final String USUARIO_USUARIO = "juan";
    public static final String USUARIO_CONTRASENA = "user123";
    public static final String USUARIO_ROL = "Usuario";
    public static final String REPARTIDOR_USUARIO = "oscar";
    public static final String REPARTIDOR_CONTRASENA = "repartidor123";
    public static final String REPARTIDOR_ROL = "Repartidor";


    public static EmpresaLogistica inicializarDatos() {
        EmpresaLogistica empresaLogistica = new EmpresaLogistica();
        Usuario usuario1 = new Usuario("1234","Santiago Trujillo","santi@gmail.com","3145822540");
        Usuario usuario2 = new Usuario("2245","Juan David","juancho@gmail.com","3235878540");
        Usuario usuario3 = new Usuario("4871","Robinson","robin@gmail.com","3505824871");

        // Agregando direcciones para Juan David
        Direccion direccion1 = new Direccion("dir1", "Casa", "Calle 15 #23-45", "Armenia", "4.5467,-75.6673");
        Direccion direccion2 = new Direccion("dir2", "Trabajo", "Carrera 19 #12-34", "Armenia", "4.5412,-75.6721");
        Direccion direccion3 = new Direccion("dir3", "Universidad", "Calle 12 #15-00", "Armenia", "4.5534,-75.6589");

        usuario2.getDireccionesFrecuentes().put("Casa", direccion1);
        usuario2.getDireccionesFrecuentes().put("Trabajo", direccion2);
        usuario2.getDireccionesFrecuentes().put("Universidad", direccion3);

        // Agregando métodos de pago para Juan David
        MetodoPago metodoPago1 = new MetodoPago("Principal", "**** 1234", "Tarjeta de Crédito");
        MetodoPago metodoPago2 = new MetodoPago("Secundaria", "PSE", "Cuenta de Ahorros");
        usuario2.getMetodosPago().put("Principal", metodoPago1);
        usuario2.getMetodosPago().put("Secundaria", metodoPago2);

        // Agregando envíos para Juan David
        Envio envio1 = new Envio("env1", LocalDate.now(), LocalDate.now().plusDays(3), "Casa", "Trabajo", "Solicitado", 2.5, 30, 20, 10, 13250.0, null);
        Envio envio2 = new Envio("env2", LocalDate.now().minusDays(1), LocalDate.now().plusDays(2), "Universidad", "Casa", "Entregado", 1.0, 20, 15, 5, 3500.0, null);
        Envio envio3 = new Envio("env3", LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), "Trabajo", "Universidad", "En ruta", 5.0, 50, 40, 20, 82500.0, null);

        usuario2.getEnvios().add(envio1);
        usuario2.getEnvios().add(envio2);
        usuario2.getEnvios().add(envio3);

        Repartidor repartidor1 = new Repartidor("1234", "juan", "4455", "311", EstadoDisponibilidad.ACTIVO, "Armenia");
        Repartidor repartidor2 = new Repartidor("7788", "jose", "7610", "314", EstadoDisponibilidad.EN_RUTA, "Calarcá");
        Repartidor repartidor3 = new Repartidor("2971", "isabela", "1098", "350", EstadoDisponibilidad.INACTIVO, "Circasia");

        empresaLogistica.getListaRepartidores().add(repartidor1);
        empresaLogistica.getListaRepartidores().add(repartidor2);
        empresaLogistica.getListaRepartidores().add(repartidor3);
        empresaLogistica.getListaUsuarios().add(usuario1);
        empresaLogistica.getListaUsuarios().add(usuario2);
        empresaLogistica.getListaUsuarios().add(usuario3);

        // Agregando todos los envíos a la lista general de envíos de la empresa
        empresaLogistica.getListaEnvios().add(envio1);
        empresaLogistica.getListaEnvios().add(envio2);
        empresaLogistica.getListaEnvios().add(envio3);

        return empresaLogistica;
    }
}
