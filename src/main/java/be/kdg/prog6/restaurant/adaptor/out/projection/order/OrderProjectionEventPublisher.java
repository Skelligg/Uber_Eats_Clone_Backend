package be.kdg.prog6.restaurant.adaptor.out.projection.order;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.port.out.order.UpdateOrdersPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderProjectionEventPublisher implements UpdateOrdersPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderProjectionEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public OrderProjection update(OrderProjection orderProjection) {
        orderProjection.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        orderProjection.clearDomainEvents();
        return orderProjection;
    }
}
