package com.bookshelf.application.infra;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka producer configuration.
 *
 * This class defines the necessary beans to enable
 * message production to a Kafka broker using Spring Kafka.
 *
 * It configures the producer factory and the KafkaTemplate
 * used by application services to publish messages to Kafka topics.
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Creates and configures a Kafka ProducerFactory.
     *
     * This factory is responsible for creating Kafka producer instances
     * with the required configuration such as bootstrap servers and
     * serializers for message keys and values.
     *
     * @return a ProducerFactory configured for String key-value messages
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates a KafkaTemplate used to send messages to Kafka topics.
     *
     * The KafkaTemplate provides a high-level API for producing messages
     * and relies on the configured ProducerFactory to create Kafka producers.
     *
     * @return a KafkaTemplate configured for String key-value messages
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
