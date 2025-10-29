package be.kdg.prog6.restaurant.adaptor.in.listener;

import be.kdg.prog6.common.events.order.OrderCreatedEvent;
import be.kdg.prog6.restaurant.port.in.orderProjection.OrderEventProjector;
import be.kdg.prog6.restaurant.port.in.orderProjection.OrderPlacedCommand;
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

//    @RabbitListener(queues = RabbitMQTopology.ORDER_LOCATION_QUEUE)
//    public void orderLocationUpdated(DeliveryJobLocationUpdatedEvent event) {
//        log.info("LOCATION UPDATE: order={} lat={} lng={}", event.orderId(), event.lat(), event.lng());
//
//        try{
//            orderPublishedPort.project(new OrderPickedUpEventCommand(
//                    event.restaurantId(),
//                    event.orderId()
//            ));
//        } catch (Exception e){
//            log.debug("Ignoring location update for order {}: {}", event.orderId(), e.getMessage());
//        }
//    }
}
