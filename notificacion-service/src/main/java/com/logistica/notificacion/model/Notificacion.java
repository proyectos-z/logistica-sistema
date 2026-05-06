package com.logistica.notificacion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String envioId;
    private String tipo;
    private String mensaje;
    private Double tarifa;
    private LocalDateTime timestamp;

    public Notificacion() {}

    public Long getId() { return id; }

    public String getEnvioId() { return envioId; }
    public void setEnvioId(String envioId) { this.envioId = envioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Double getTarifa() { return tarifa; }
    public void setTarifa(Double tarifa) { this.tarifa = tarifa; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
