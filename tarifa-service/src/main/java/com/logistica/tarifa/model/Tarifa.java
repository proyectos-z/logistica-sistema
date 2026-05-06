package com.logistica.tarifa.model;

public class Tarifa {

    private String envioId;
    private String destino;
    private double peso;
    private double costo;
    private String moneda;

    public Tarifa() {}

    public Tarifa(String envioId, String destino, double peso, double costo) {
        this.envioId = envioId;
        this.destino = destino;
        this.peso = peso;
        this.costo = costo;
        this.moneda = "USD";
    }

    public String getEnvioId() { return envioId; }
    public void setEnvioId(String envioId) { this.envioId = envioId; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}
