package be.kdg.prog6.restaurant.adaptor.in;

import be.kdg.prog6.common.events.order.OrderCreatedEvent;
import be.kdg.prog6.restaurant.port.in.order.OrderEventProjector;
import be.kdg.prog6.restaurant.port.in.order.OrderPlacedCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private final OrderEventProjector projecter;

    public OrderEventListener(OrderEventProjector projecter) {
        this.projecter = projecter;
    }

    @EventListener(OrderCreatedEvent.class)
    public void onOrderCreated(OrderCreatedEvent event){
        log.info("OrderCreatedEvent received");
        projecter.project(new OrderPlacedCommand(
                event.orderId(),
                event.restaurantId(),
                event.lines(),
                event.totalPrice(),
                event.deliveryAddress(),
                event.placedAt(),
                event.orderStatus()
        ));
    }
}
