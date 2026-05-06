package com.logistica.envio.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.envio-creado}")
    private String topicEnvioCreado;

    @Bean
    public NewTopic topicEnvioCreado() {
        return TopicBuilder.name(topicEnvioCreado)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
