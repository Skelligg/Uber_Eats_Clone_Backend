package be.kdg.prog6.ordering.core;

import be.kdg.prog6.common.events.OrderCreatedEvent;
import be.kdg.prog6.common.vo.OrderLineEventInfo;
import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.OrderId;
import be.kdg.prog6.ordering.domain.vo.OrderLine;
import be.kdg.prog6.ordering.domain.vo.RestaurantId;
import be.kdg.prog6.ordering.port.in.order.CreateOrderCommand;
import be.kdg.prog6.ordering.port.in.order.CreateOrderUseCase;
import be.kdg.prog6.ordering.port.out.LoadDishesPort;
import be.kdg.prog6.ordering.port.out.LoadOrderPort;
import be.kdg.prog6.ordering.port.out.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.UpdateOrderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final Logger logger = LoggerFactory.getLogger(CreateOrderUseCaseImpl.class.getName());

    private final LoadOrderPort loadOrderPort;
    private final List<UpdateOrderPort> updateOrderPorts;
    private final LoadRestaurantsPort loadRestaurantsPort;
    private final LoadDishesPort loadDishesPort;

    public CreateOrderUseCaseImpl(LoadOrderPort loadOrderPort, List<UpdateOrderPort> updateOrderPorts, LoadRestaurantsPort loadRestaurantsPort, LoadDishesPort loadDishesPort) {
        this.loadOrderPort = loadOrderPort;
        this.updateOrderPorts = updateOrderPorts;
        this.loadRestaurantsPort = loadRestaurantsPort;
        this.loadDishesPort = loadDishesPort;
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderCommand command) {
        UUID restaurantId = command.restaurantId().id();
        if (loadRestaurantsPort.findById(restaurantId).isEmpty()) {
            logger.info("Restaurant {} does not exist", restaurantId);
            throw new IllegalArgumentException("Restaurant does not exist");
        }
        for(OrderLine line : command.lines()) {
            if (loadDishesPort.loadDish(line.dishId().id()).isEmpty()) {
                logger.info("Dish {} does not exist", line.dishId().id());
                throw new IllegalArgumentException("Dish does not exist");
            }
        }

        Order order = new Order(
                OrderId.newId(),
                RestaurantId.of(command.restaurantId().id()),
                command.lines(),
                command.totalPrice(),
                command.customer(),
                command.deliveryAddress(),
                command.placedAt()
        );


        order.addDomainEvent(new OrderCreatedEvent(
                order.getOrderId().id(),
                order.getRestaurantId().id(),
                order.getLines().stream()
                        .map(l -> new OrderLineEventInfo(
                                l.dishId().id(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice().price().doubleValue(),
                                l.linePrice().price().doubleValue()
                        ))
                        .toList(),
                order.getTotalPrice().price().doubleValue(),
                order.getDeliveryAddress(),
                order.getPlacedAt(),
                order.getStatus().name()
        ));



        this.updateOrderPorts.forEach(port -> port.update(order));
        return order;
    }
}
