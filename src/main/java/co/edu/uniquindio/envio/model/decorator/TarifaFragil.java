package co.edu.uniquindio.envio.model.decorator;

public class TarifaFragil extends TarifaDecorator {
    public TarifaFragil(Tarifa tarifa) {
        super(tarifa);
    }

    @Override
    public double calcularCosto() {
        // Agregar 20% adicional por manejo especial de artículos frágiles
        return super.calcularCosto() * 1.20;
    }
}
