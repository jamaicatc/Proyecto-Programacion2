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
    public static final String REPARTIDOR_CONTRASENA = "r123";
    public static final String REPARTIDOR_ROL = "Repartidor";


    public static EmpresaLogistica inicializarDatos() {
        EmpresaLogistica empresaLogistica = new EmpresaLogistica();

        // Creación de usuarios con credenciales de inicio de sesión
        Usuario usuario1 = new Usuario("1234", "Santiago Trujillo", "santi@gmail.com", "3145822540", ADMIN_USUARIO, ADMIN_CONTRASENA);
        Usuario usuario2 = new Usuario("2245", "Juan David", "juancho@gmail.com", "3235878540", USUARIO_USUARIO, USUARIO_CONTRASENA);
        Usuario usuario3 = new Usuario("4871", "Robinson", "robin@gmail.com", "3505824871", "robin", "robin123");

        // Creación de repartidores con credenciales de inicio de sesión
        Repartidor repartidor1 = new Repartidor("repartidor1", "Jhon Oscar", "1094556789", "3146789012", REPARTIDOR_USUARIO, REPARTIDOR_CONTRASENA, "Activo", "Armenia");
        Repartidor repartidor2 = new Repartidor("repartidor2", "Juan David Cardona", "1092345678", "3112345678", "juanc", "juanc123", "En ruta", "Armenia");
        Repartidor repartidor3 = new Repartidor("repartidor3", "Amerika Esmeralda", "1098765432", "35025401891", "ame", "ame123", "Inactivo", "Montenegro");


        // Agregando direcciones para Juan David (usuario2)
        Direccion direccion1 = new Direccion("dir1", "Casa", "Calle 15 #23-45", "Armenia", "4.5467,-75.6673");
        Direccion direccion2 = new Direccion("dir2", "Trabajo", "Carrera 19 #12-34", "Armenia", "4.5412,-75.6721");
        Direccion direccion3 = new Direccion("dir3", "Universidad", "Calle 12 #15-00", "Armenia", "4.5534,-75.6589");

        usuario2.getDireccionesFrecuentes().put("Casa", direccion1);
        usuario2.getDireccionesFrecuentes().put("Trabajo", direccion2);
        usuario2.getDireccionesFrecuentes().put("Universidad", direccion3);

        // Agregando métodos de pago para Juan David (usuario2)
        MetodoPago metodoPago1 = new MetodoPago("Principal", "**** 1234", "Tarjeta de Crédito");
        MetodoPago metodoPago2 = new MetodoPago("Secundaria", "PSE", "Cuenta de Ahorros");
        usuario2.getMetodosPago().put("Principal", metodoPago1);
        usuario2.getMetodosPago().put("Secundaria", metodoPago2);


        // Creación de envíos
        Envio envio1 = new Envio("env1", LocalDate.now(), LocalDate.now().plusDays(3), "Casa", "Trabajo", "Solicitado", 2.5, 30, 20, 10, 13250.0, repartidor1, false);
        envio1.getHistorial().add("2024-05-20 10:00 - Envío creado");
        Envio envio2 = new Envio("env2", LocalDate.now().minusDays(1), LocalDate.now().plusDays(2), "Universidad", "Casa", "Entregado", 1.0, 20, 15, 5, 3500.0, null, false);
        envio2.getHistorial().add("2024-05-19 14:30 - Envío creado");
        envio2.getHistorial().add("2024-05-20 08:00 - En bodega");
        envio2.getHistorial().add("2024-05-20 11:00 - En reparto");
        envio2.getHistorial().add("2024-05-20 16:00 - Entregado");
        Envio envio3 = new Envio("env3", LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), "Trabajo", "Universidad", "En ruta", 5.0, 50, 40, 20, 82500.0, repartidor2, false);
        envio3.getHistorial().add("2024-05-18 09:00 - Envío creado");
        envio3.getHistorial().add("2024-05-19 12:00 - En bodega");
        Envio envio4 = new Envio("env4", LocalDate.now(), LocalDate.now().plusDays(5), "Casa", "Universidad", "En bodega", 3.0, 40, 30, 15, 25000.0, null, false);
        envio4.getHistorial().add("2024-05-21 08:00 - Envío creado");
        Envio envio5 = new Envio("env5", LocalDate.now().minusDays(1), LocalDate.now().plusDays(4), "Trabajo", "Casa", "En ruta", 1.5, 25, 20, 10, 15000.0, repartidor1, false);
        envio5.getHistorial().add("2024-05-20 15:00 - Envío creado");
        envio5.getHistorial().add("2024-05-21 09:00 - En bodega");

        // Asociar envíos a los repartidores (¡ESTA ES LA PARTE QUE FALTABA!)
        repartidor1.getEnviosAsignados().add(envio1);
        repartidor1.getEnviosAsignados().add(envio5);
        repartidor2.getEnviosAsignados().add(envio3);

        // Asociar envíos al usuario
        usuario2.getEnvios().add(envio1);
        usuario2.getEnvios().add(envio2);
        usuario2.getEnvios().add(envio3);
        usuario2.getEnvios().add(envio4);
        usuario2.getEnvios().add(envio5);

        // Agregar repartidores y usuarios a la empresa
        empresaLogistica.getListaRepartidores().add(repartidor1);
        empresaLogistica.getListaRepartidores().add(repartidor2);
        empresaLogistica.getListaRepartidores().add(repartidor3);
        empresaLogistica.getListaUsuarios().add(usuario1);
        empresaLogistica.getListaUsuarios().add(usuario2);
        empresaLogistica.getListaUsuarios().add(usuario3);

        // Agregar todos los envíos a la lista general de envíos de la empresa
        empresaLogistica.getListaEnvios().add(envio1);
        empresaLogistica.getListaEnvios().add(envio2);
        empresaLogistica.getListaEnvios().add(envio3);
        empresaLogistica.getListaEnvios().add(envio4);
        empresaLogistica.getListaEnvios().add(envio5);

        return empresaLogistica;
    }
}
