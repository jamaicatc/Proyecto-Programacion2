package co.edu.uniquindio.envio.model.decorator;

public class TarifaBase implements Tarifa {
    private double peso;
    private double volumen;
    private int cantidad;
    private boolean esDocumento;
    private String origen;
    private String destino;

    public TarifaBase(double peso, double volumen, int cantidad, boolean esDocumento, String origen, String destino) {
        this.peso = peso;
        this.volumen = volumen;
        this.cantidad = cantidad;
        this.esDocumento = esDocumento;
        this.origen = origen;
        this.destino = destino;
    }

    @Override
    public double calcularCosto() {
        double costoBase;
        if (esDocumento) {
            costoBase = 5000; // Tarifa base para documentos
        } else {
            // Para paquetes, consideramos peso y volumen
            double costoPorPeso = peso * 2000;
            double costoPorVolumen = volumen * 100;
            costoBase = Math.max(costoPorPeso, costoPorVolumen);
        }

        // Multiplicar por la cantidad
        costoBase *= cantidad;

        // Agregar costo por distancia (simplificado)
        costoBase += calcularCostoPorDistancia(origen, destino);

        return costoBase;
    }

    private double calcularCostoPorDistancia(String origen, String destino) {
        // Simplificado: podría implementarse con una matriz de distancias real
        return 10000; // Costo base por envío entre ciudades
    }
}
