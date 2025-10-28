package be.kdg.prog6.common.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    public static final String EXCHANGE_NAME = "kdg.events";

    public static final String ORDER_ACCEPTED_QUEUE = "restaurant.order.accepted";

    @Bean
    public TopicExchange deliveryExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    // Queues
    @Bean
    Queue orderAcceptedQueue() {
        return QueueBuilder.durable(ORDER_ACCEPTED_QUEUE).build();
    }

    // Bindings (Publishing to delivery service)
    @Bean
    Binding bindOrderAcceptedToExchange() {
        return BindingBuilder
                .bind(orderAcceptedQueue())
                .to(deliveryExchange())
                .with("restaurant.*.order.accepted.v1");
    }
}
