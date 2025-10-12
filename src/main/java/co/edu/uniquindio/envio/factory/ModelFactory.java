package co.edu.uniquindio.envio.factory;

import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.mapping.mappers.EmpresaLogisticaMappingImpl;
import co.edu.uniquindio.envio.model.EmpresaLogistica;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.services.IModelFactory;
import co.edu.uniquindio.envio.services.IEmpresaLogisticaMapping;
import co.edu.uniquindio.envio.utils.DataUtil;

import java.util.List;

public class ModelFactory implements IModelFactory {
    private static ModelFactory modelFactory;
    private EmpresaLogistica empresaLogistica;
    private IEmpresaLogisticaMapping mapper;

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
        mapper = new EmpresaLogisticaMappingImpl();
        empresaLogistica = DataUtil.inicializarDatos();
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return mapper.getUsuariosDto(empresaLogistica.getListaUsuarios());
    }

//    @Override
//    public boolean agregarUsuario(UsuarioDto usuarioDto) {
//        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
//        return empresaLogistica.agregarUsuario(usuario);
//    }
//
//    @Override
//    public boolean eliminarUsuario(String idUsuario) {
//        return empresaLogistica.eliminarUsuario(idUsuario);
//    }
}
