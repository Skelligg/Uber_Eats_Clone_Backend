package be.kdg.prog6.ordering.adaptor.in.listener;

import be.kdg.prog6.common.events.order.OrderAcceptedEvent;
import be.kdg.prog6.common.events.order.OrderReadyForPickupEvent;
import be.kdg.prog6.ordering.port.in.order.OrderAcceptedCommand;
import be.kdg.prog6.ordering.port.in.order.OrderProjectionEventProjector;
import be.kdg.prog6.ordering.port.in.order.OrderReadyForPickupCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import be.kdg.prog6.common.config.RabbitMQTopology;
import be.kdg.prog6.common.events.order.*;
import be.kdg.prog6.ordering.port.in.order.*;

@Component
public class OrderProjectionEventListener {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderProjectionEventListener.class);
    private final OrderProjectionEventProjector projector;

    public OrderProjectionEventListener(OrderProjectionEventProjector projector) {
        this.projector = projector;
    }

    @EventListener(OrderAcceptedEvent.class)
    public void onOrderAccepted(OrderAcceptedEvent event){
        logger.info("OrderAcceptedEvent received");
        projector.project(new OrderAcceptedCommand(event.orderId()));
    }

    @EventListener(OrderReadyForPickupEvent.class)
    public void onOrderReadyForPickup(OrderReadyForPickupEvent event){
        logger.info("OrderReadyForPickupEvent received");
        projector.project(new OrderReadyForPickupCommand(event.orderId()));
    }

    @EventListener(OrderRejectedEvent.class)
    public void onOrderRejected(OrderRejectedEvent event){
        logger.info("OrderRejectedEvent received");
        projector.project(new OrderRejectedCommand(event.orderId(),
                event.reason()));
    }

//    @RabbitListener(queues = RabbitMQTopology.ORDER_PICKED_UP_QUEUE)
//    public void orderPickedUp(OrderPickedUpEvent event) {
//        logger.info("orderPickedUpEvent received");
//
//        projector.project(new OrderPickedUpCommand(
//                event.orderId()
//        ));
//    }

    @RabbitListener(queues = RabbitMQTopology.ORDER_LOCATION_QUEUE)
    public void orderLocationUpdated(OrderLocationEvent event) {
        // Add null check for event itself
        if (event == null) {
            logger.warn("Received null OrderLocationEvent, ignoring");
            return;
        }

        logger.info("Location Update: order={} lat={} lng={}", event.orderId(), event.lat(), event.lng());

        try{
            projector.project(new OrderLocationUpdatedCommand(
                    event.orderId(),
                    event.occurredAt(),
                    event.lat(),
                    event.lng()
            ));
        } catch (Exception e){
            // Changed from debug to warn and only log once per order to reduce spam
            logger.warn("Ignoring location update for order {}: {}", event.orderId(), e.getMessage());
        }

    }

    @RabbitListener(queues = RabbitMQTopology.ORDER_DELIVERED_QUEUE)
    public void orderDelivered(OrderDeliveredEvent event) {
        if (event == null) {
            logger.warn("Received null OrderDeliveredEvent, ignoring");
            return;
        }

        try {
            projector.project(new OrderDeliveredCommand(
                    event.orderId()
            ));
            logger.info("Order {} delivered", event.orderId());
        } catch (IllegalStateException e) {
            logger.warn("Cannot mark order {} as delivered: {}", event.orderId(), e.getMessage());
        }
    }

}
