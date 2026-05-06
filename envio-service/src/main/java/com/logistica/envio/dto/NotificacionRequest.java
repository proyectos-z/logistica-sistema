package com.logistica.envio.dto;

import java.time.LocalDateTime;

public class NotificacionRequest {

    private String envioId;
    private String tipo;
    private String mensaje;
    private Double tarifa;
    private LocalDateTime timestamp;

    public NotificacionRequest() {}

    public NotificacionRequest(String envioId, String tipo, String mensaje, Double tarifa) {
        this.envioId = envioId;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.tarifa = tarifa;
        this.timestamp = LocalDateTime.now();
    }

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
