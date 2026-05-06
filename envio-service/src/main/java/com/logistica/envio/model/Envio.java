package com.logistica.envio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String origen;
    private String destino;
    private double peso;
    private String estado;
    private Double tarifa;

    public Envio() {}

    public Envio(String origen, String destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.estado = "PENDIENTE";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getTarifa() { return tarifa; }
    public void setTarifa(Double tarifa) { this.tarifa = tarifa; }
}
