package com.sidd.inventory.inventory_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Map> inventoryKafkaListenerFactory(
            ConsumerFactory<String, Map> cf) {

        ConcurrentKafkaListenerContainerFactory<String, Map> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(cf);
        factory.setConcurrency(5); // 5 threads
        factory.getContainerProperties()
               .setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }
}
