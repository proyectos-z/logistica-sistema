package com.logistica.tarifa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_tarifas")
public class HistorialTarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String envioId;
    private String destino;
    private double peso;
    private double costo;
    private String moneda;
    private LocalDateTime calculadoEn;

    public HistorialTarifa() {}

    public HistorialTarifa(String envioId, String destino, double peso, double costo) {
        this.envioId = envioId;
        this.destino = destino;
        this.peso = peso;
        this.costo = costo;
        this.moneda = "USD";
        this.calculadoEn = LocalDateTime.now();
    }

    public Long getId() { return id; }
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
    public LocalDateTime getCalculadoEn() { return calculadoEn; }
    public void setCalculadoEn(LocalDateTime calculadoEn) { this.calculadoEn = calculadoEn; }
}
