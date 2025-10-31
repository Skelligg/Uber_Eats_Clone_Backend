package be.kdg.prog6.ordering.core.order;

import be.kdg.prog6.common.events.order.OrderCreatedEvent;
import be.kdg.prog6.common.vo.OrderLineEventInfo;
import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.OrderId;
import be.kdg.prog6.ordering.domain.vo.OrderLine;
import be.kdg.prog6.ordering.domain.vo.RestaurantId;
import be.kdg.prog6.ordering.port.in.order.CreateOrderCommand;
import be.kdg.prog6.ordering.port.in.order.CreateOrderUseCase;
import be.kdg.prog6.ordering.port.out.dishProjection.LoadDishesPort;
import be.kdg.prog6.ordering.port.out.restaurantProjection.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.order.UpdateOrderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final Logger logger = LoggerFactory.getLogger(CreateOrderUseCaseImpl.class.getName());

    private final List<UpdateOrderPort> updateOrderPorts;
    private final LoadRestaurantsPort loadRestaurantsPort;
    private final LoadDishesPort loadDishesPort;

    public CreateOrderUseCaseImpl(List<UpdateOrderPort> updateOrderPorts, LoadRestaurantsPort loadRestaurantsPort, LoadDishesPort loadDishesPort) {
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
        // go to constructor to change PLACED TO UNPAID
        Order order = new Order(
                OrderId.newId(),
                RestaurantId.of(command.restaurantId().id()),
                command.lines(),
                command.totalPrice(),
                command.customer(),
                command.deliveryAddress()
        );

        this.updateOrderPorts.forEach(port -> port.update(order));
        return order;
    }
}
