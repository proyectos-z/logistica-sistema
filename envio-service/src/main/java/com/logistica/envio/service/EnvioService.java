package com.logistica.envio.service;

import com.logistica.envio.dto.NotificacionRequest;
import com.logistica.envio.dto.TarifaResponse;
import com.logistica.envio.model.Envio;
import com.logistica.envio.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EnvioService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final EnvioRepository envioRepository;

    @Value("${tarifa.service.url:http://localhost:8082}")
    private String tarifaServiceUrl;

    @Value("${kafka.topic.envio-creado}")
    private String topicEnvioCreado;

    public EnvioService(RestTemplate restTemplate,
                        KafkaTemplate<String, Object> kafkaTemplate,
                        EnvioRepository envioRepository) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.envioRepository = envioRepository;
    }

    public Envio procesarEnvio(Envio envio) {
        envio.setEstado("PENDIENTE");

        // ORQUESTACION: llamada sincrona a tarifa-service
        TarifaResponse tarifa = consultarTarifa(envio);
        envio.setTarifa(tarifa.getCosto());
        envio.setEstado("TARIFA_CALCULADA");

        // Persiste en BD propia (Database per Service)
        Envio envioPersistido = envioRepository.save(envio);

        // COREOGRAFIA: publica evento EnvioCreado al broker Kafka (fire-and-forget)
        publicarEventoEnvioCreado(envioPersistido);

        return envioPersistido;
    }

    private TarifaResponse consultarTarifa(Envio envio) {
        String url = tarifaServiceUrl + "/api/tarifas/calcular?envioId={envioId}&destino={destino}&peso={peso}";
        Map<String, Object> params = new HashMap<>();
        params.put("envioId", envio.getId() != null ? envio.getId() : "temp");
        params.put("destino", envio.getDestino());
        params.put("peso", envio.getPeso());
        return restTemplate.getForObject(url, TarifaResponse.class, params);
    }

    private void publicarEventoEnvioCreado(Envio envio) {
        String mensaje = "Envio creado con tarifa calculada: $" + envio.getTarifa() + " USD";

        NotificacionRequest evento = new NotificacionRequest(
                envio.getId(), "ENVIO_CREADO", mensaje, envio.getTarifa()
        );

        // Evento JSON publicado al topic Kafka — toda comunicacion en JSON
        kafkaTemplate.send(topicEnvioCreado, envio.getId(), evento);
    }
}
