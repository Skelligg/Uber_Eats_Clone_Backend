package be.kdg.prog6.restaurant.core.order;

import be.kdg.prog6.common.events.order.OrderReadyForPickupEvent;
import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.order.MarkOrderReadyForPickUpUseCase;
import be.kdg.prog6.restaurant.port.out.order.LoadOrdersPort;
import be.kdg.prog6.restaurant.port.out.order.UpdateOrdersPort;
import be.kdg.prog6.restaurant.port.out.restaurant.LoadRestaurantPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MarkOrderReadyForPickUpUseCaseImpl implements MarkOrderReadyForPickUpUseCase {
    private final LoadOrdersPort loadOrdersPort;
    private final List<UpdateOrdersPort> updateOrdersPorts;
    private final LoadRestaurantPort loadRestaurantPort;

    public MarkOrderReadyForPickUpUseCaseImpl(LoadOrdersPort loadOrdersPort, List<UpdateOrdersPort> updateOrdersPorts, LoadRestaurantPort loadRestaurantPort) {
        this.loadOrdersPort = loadOrdersPort;
        this.updateOrdersPorts = updateOrdersPorts;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    public OrderProjection markOrderReadyForPickUp(UUID orderId, UUID ownerId) {
        OrderProjection orderProjection = loadOrdersPort.loadOrderById(orderId);

        var restaurant = loadRestaurantPort.findById(RestaurantId.of(orderProjection.getRestaurantId()));

        if (restaurant.isEmpty() || !restaurant.get().getOwnerId().id().equals(ownerId)) {
            throw new IllegalArgumentException("Order does not belong to restaurant");
        }

        orderProjection.markReadyForPickUp();

        orderProjection.addDomainEvent(new OrderReadyForPickupEvent(
                    orderProjection.getRestaurantId(),
                    orderId
                )
        );

        updateOrdersPorts.forEach(port -> port.update(orderProjection));

        return orderProjection;
    }
}
