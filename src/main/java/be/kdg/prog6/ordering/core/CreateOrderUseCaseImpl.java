package be.kdg.prog6.ordering.core;

import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.OrderId;
import be.kdg.prog6.ordering.domain.vo.RestaurantId;
import be.kdg.prog6.ordering.port.in.CreateOrderCommand;
import be.kdg.prog6.ordering.port.in.CreateOrderUseCase;
import be.kdg.prog6.ordering.port.out.LoadOrderPort;
import be.kdg.prog6.ordering.port.out.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.PublishOrderEventPort;
import be.kdg.prog6.ordering.port.out.UpdateOrderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final Logger logger = LoggerFactory.getLogger(CreateOrderUseCaseImpl.class.getName());

    private final LoadOrderPort loadOrderPort;
    private final UpdateOrderPort updateOrderPort;
    private final LoadRestaurantsPort loadRestaurantsPort;

    public CreateOrderUseCaseImpl(LoadOrderPort loadOrderPort, UpdateOrderPort updateOrderPort, LoadRestaurantsPort loadRestaurantsPort) {
        this.loadOrderPort = loadOrderPort;
        this.updateOrderPort = updateOrderPort;
        this.loadRestaurantsPort = loadRestaurantsPort;
    }

    @Override
    public Order createOrder(CreateOrderCommand command) {
        UUID restaurantId = command.restaurantId().id();
        if (loadRestaurantsPort.findById(restaurantId).isEmpty()) {
            logger.info("Restaurant {} does not exist", restaurantId);
            throw new IllegalArgumentException("Restaurant does not exist");
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

        this.updateOrderPort.addOrder(order);
        return order;
    }
}
