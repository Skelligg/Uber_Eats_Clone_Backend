package be.kdg.prog6.ordering.adaptor.in.listener;

import be.kdg.prog6.common.events.order.OrderAcceptedEvent;
import be.kdg.prog6.ordering.port.in.order.OrderProjectionEventProjector;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
        projector.project(event.orderId());
    }

}
