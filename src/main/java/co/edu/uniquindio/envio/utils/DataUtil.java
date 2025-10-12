package co.edu.uniquindio.envio.utils;

import co.edu.uniquindio.envio.model.EmpresaLogistica;
import co.edu.uniquindio.envio.model.Usuario;

public class DataUtil {
    public static EmpresaLogistica inicializarDatos() {
        EmpresaLogistica empresaLogistica = new EmpresaLogistica();
        Usuario usuario1 = new Usuario("1234","Santiago Trujillo","santi@gmail.com","3145822540");

        Usuario usuario2 = new Usuario("2245","Juan David","juancho@gmail.com","3235878540");

        Usuario usuario3 = new Usuario("1234","Robinson","robin@gmail.com","3505824871");

        empresaLogistica.getListaUsuarios().add(usuario1);
        empresaLogistica.getListaUsuarios().add(usuario2);
        empresaLogistica.getListaUsuarios().add(usuario3);
        return empresaLogistica;
    }
}
