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

    private static final String TOPIC_CREATE = "review-created-topic";

    private static final String TOPIC_EDIT = "review-edit-topic";

    private static final String TOPIC_DELETE_REVIEW = "review-delete-topic";

    private static final String TOPIC_LIST_ALL_REVIEW = "review-list-all-topic";

    private static final String TOPIC_LIST_FOR_ID_REVIEW = "review-list-for-id-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to Kafka confirming that a review was created.
     *
     * @param message message to be published to the review-created topic
     */
    public void sendReviewCreatedMessage(String message) {
        kafkaTemplate.send(TOPIC_CREATE, message);
    }

    public void sendReviewEditMessage(String message) {
        kafkaTemplate.send(TOPIC_EDIT, message);
    }

    public void sendReviewDeleteMessage(String message) {
        kafkaTemplate.send(TOPIC_DELETE_REVIEW, message);
    }

    public void sendReviewTopicAllMessage(String message) {
        kafkaTemplate.send(TOPIC_LIST_ALL_REVIEW, message);
    }

    public void sendReviewTopicForIdMessage(String message) {
        kafkaTemplate.send(TOPIC_LIST_FOR_ID_REVIEW, message);
    }
}
