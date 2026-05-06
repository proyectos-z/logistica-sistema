package com.logistica.notificacion.service;

import com.logistica.notificacion.model.Notificacion;
import com.logistica.notificacion.repository.NotificacionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    // COREOGRAFIA: reacciona autonomamente al evento EnvioCreado del broker Kafka
    @KafkaListener(
        topics = "${kafka.topic.envio-creado}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void procesarEventoEnvioCreado(Notificacion notificacion) {
        System.out.println("[KAFKA] Evento 'EnvioCreado' recibido del topic envio-creado");
        System.out.println("[NOTIFICACION] envioId=" + notificacion.getEnvioId() +
                " | tipo=" + notificacion.getTipo() +
                " | tarifa=$" + notificacion.getTarifa());

        notificacion.setTimestamp(LocalDateTime.now());

        // Persiste en BD propia (Database per Service) — aislada de envio-service
        notificacionRepository.save(notificacion);
    }

    public List<Notificacion> obtenerHistorial() {
        return notificacionRepository.findAll();
    }
}
