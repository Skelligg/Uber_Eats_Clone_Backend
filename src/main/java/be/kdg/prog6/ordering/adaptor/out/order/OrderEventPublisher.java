package be.kdg.prog6.ordering.adaptor.out.order;

import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.port.out.order.UpdateOrderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher implements UpdateOrderPort {

    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Order update(Order order) {
        //log.info("Publishing {} events for order {}", order.getDomainEvents().size(), order.getOrderId());
        order.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        order.clearDomainEvents();
        return order;
    }
}
