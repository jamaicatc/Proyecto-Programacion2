package co.edu.uniquindio.envio.factory;

import co.edu.uniquindio.envio.model.builder.EmpresaLogistica;

public class ModelFactory {
    private static ModelFactory modelFactory;
    private EmpresaLogistica empresaLogistica;

    private ModelFactory(){
        empresaLogistica = new EmpresaLogistica();
    }

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }
}
