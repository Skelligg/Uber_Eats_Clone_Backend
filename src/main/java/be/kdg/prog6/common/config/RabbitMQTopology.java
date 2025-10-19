package be.kdg.prog6.common.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    public static final String EXCHANGE_NAME = "kdg.events";

    @Bean
    public TopicExchange kdgExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    // Example queue for listening to dish changes (ordering context)
    @Bean
    public Queue dishChangedQueue() {
        return new Queue("ordering.dishes.changed.queue", true);
    }

    // Example binding: this context listens for dish events from restaurant context
    @Bean
    public Binding bindDishEvents(Queue dishChangedQueue, TopicExchange kdgExchange) {
        return BindingBuilder
                .bind(dishChangedQueue)
                .to(kdgExchange)
                .with("restaurant.*.dish.*.v1");
    }
}
