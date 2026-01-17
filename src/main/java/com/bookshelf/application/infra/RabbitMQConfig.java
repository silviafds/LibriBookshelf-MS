package com.bookshelf.application.infra;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig  {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 🔥 Use SimpleMessageConverter para strings
        rabbitTemplate.setMessageConverter(new SimpleMessageConverter());

        // Timeout para resposta (30 segundos)
        rabbitTemplate.setReplyTimeout(30000);

        return rabbitTemplate;
    }
}
