package co.edu.uniquindio.envio.model.decorator;

public class TarifaPrioritaria extends TarifaDecorator {
    private String nivelPrioridad;

    public TarifaPrioritaria(Tarifa tarifa, String nivelPrioridad) {
        super(tarifa);
        this.nivelPrioridad = nivelPrioridad;
    }

    @Override
    public double calcularCosto() {
        double costoBase = super.calcularCosto();
        switch (nivelPrioridad.toLowerCase()) {
            case "alta":
                return costoBase * 1.3; // 30% adicional
            case "urgente":
                return costoBase * 1.5; // 50% adicional
            default:
                return costoBase; // Prioridad normal
        }
    }
}
