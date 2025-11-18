package co.edu.uniquindio.envio.model;

public class Tarifa {
    private String idTarifa;
    private double distancia;
    private double peso;
    private double volumen;
    private String prioridad;
    private boolean fragil;
    private boolean entregaUrgente;
    private double base;
    private double costoPeso;
    private double costoVolumen;
    private double recargoPrioridad;
    private double recargosExtra;
    private double costoTotal;

    public Tarifa() {
    }

    public Tarifa(String idTarifa, double distancia, double peso, double volumen, String prioridad, boolean fragil, boolean entregaUrgente, double base, double costoPeso, double costoVolumen, double recargoPrioridad, double recargosExtra, double costoTotal) {
        this.idTarifa = idTarifa;
        this.distancia = distancia;
        this.peso = peso;
        this.volumen = volumen;
        this.prioridad = prioridad;
        this.fragil = fragil;
        this.entregaUrgente = entregaUrgente;
        this.base = base;
        this.costoPeso = costoPeso;
        this.costoVolumen = costoVolumen;
        this.recargoPrioridad = recargoPrioridad;
        this.recargosExtra = recargosExtra;
        this.costoTotal = costoTotal;
    }

    public String getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isFragil() {
        return fragil;
    }

    public void setFragil(boolean fragil) {
        this.fragil = fragil;
    }

    public boolean isEntregaUrgente() {
        return entregaUrgente;
    }

    public void setEntregaUrgente(boolean entregaUrgente) {
        this.entregaUrgente = entregaUrgente;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getCostoPeso() {
        return costoPeso;
    }

    public void setCostoPeso(double costoPeso) {
        this.costoPeso = costoPeso;
    }

    public double getCostoVolumen() {
        return costoVolumen;
    }

    public void setCostoVolumen(double costoVolumen) {
        this.costoVolumen = costoVolumen;
    }

    public double getRecargoPrioridad() {
        return recargoPrioridad;
    }

    public void setRecargoPrioridad(double recargoPrioridad) {
        this.recargoPrioridad = recargoPrioridad;
    }

    public double getRecargosExtra() {
        return recargosExtra;
    }

    public void setRecargosExtra(double recargosExtra) {
        this.recargosExtra = recargosExtra;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}
