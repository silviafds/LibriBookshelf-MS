package com.bookshelf.adapters.out.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer responsible for publishing review-related events.
 *
 * This service sends messages to Kafka topics to notify other systems
 * when a new book review is created.
 */
@Service
@RequiredArgsConstructor
public class ReviewKafkaProducer {

    private static final String TOPIC = "review-created-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to Kafka confirming that a review was created.
     *
     * @param message message to be published to the review-created topic
     */
    public void sendReviewCreatedMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
