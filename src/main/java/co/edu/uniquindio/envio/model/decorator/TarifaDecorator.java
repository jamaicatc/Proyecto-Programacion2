package co.edu.uniquindio.envio.model.decorator;

public abstract class TarifaDecorator implements Tarifa {
    protected Tarifa tarifaDecorada;

    public TarifaDecorator(Tarifa tarifa) {
        this.tarifaDecorada = tarifa;
    }

    @Override
    public double calcularCosto() {
        return tarifaDecorada.calcularCosto();
    }
}
