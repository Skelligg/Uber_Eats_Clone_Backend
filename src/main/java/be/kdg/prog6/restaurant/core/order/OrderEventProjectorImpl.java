package be.kdg.prog6.restaurant.core.order;

import be.kdg.prog6.common.vo.ORDER_STATUS;
import be.kdg.prog6.restaurant.domain.projection.OrderLineProjection;
import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.port.in.order.OrderEventProjector;
import be.kdg.prog6.restaurant.port.in.order.OrderPlacedCommand;
import be.kdg.prog6.restaurant.port.out.order.UpdateOrdersPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderEventProjectorImpl implements OrderEventProjector {

    private static final Logger log = LoggerFactory.getLogger(OrderEventProjectorImpl.class);
    private final UpdateOrdersPort updateOrdersPort;

    public OrderEventProjectorImpl(UpdateOrdersPort updateOrdersPort) {
        this.updateOrdersPort = updateOrdersPort;
    }

    @Override
    @Transactional
    public void project(OrderPlacedCommand command) {
        log.info("Projecting OrderPlacedEvent for order {}", command.orderId());
        updateOrdersPort.update(new OrderProjection(
                command.orderId(),
                command.restaurantId(),
                command.deliveryAddress().street(),
                command.deliveryAddress().number(),
                command.deliveryAddress().postalCode(),
                command.deliveryAddress().city(),
                command.deliveryAddress().country(),
                command.totalPrice(),
                command.placedAt(),
                ORDER_STATUS.valueOf(command.orderStatus().toUpperCase()),
                command.lines().stream()
                        .map(l -> new OrderLineProjection(
                                DishId.of(l.dishId()),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice(),
                                l.linePrice()
                        )).toList()
        ));
    }
}
