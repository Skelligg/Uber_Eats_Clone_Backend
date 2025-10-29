package be.kdg.prog6.restaurant.core.orderProjection;

import be.kdg.prog6.common.events.order.OrderAcceptedEvent;
import be.kdg.prog6.common.events.order.OrderRejectedEvent;
import be.kdg.prog6.common.vo.Coordinates;
import be.kdg.prog6.common.vo.RabbitMQAddress;
import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.orderProjection.HandleOrderUseCase;
import be.kdg.prog6.restaurant.port.out.geocoder.ExternalGeoCoder;
import be.kdg.prog6.restaurant.port.out.orderProjection.LoadOrdersPort;
import be.kdg.prog6.restaurant.port.out.orderProjection.UpdateOrdersPort;
import be.kdg.prog6.restaurant.port.out.restaurant.LoadRestaurantPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class HandleOrderUseCaseImpl implements HandleOrderUseCase {
    private final LoadOrdersPort loadOrdersPort;
    private final List<UpdateOrdersPort> updateOrdersPorts;
    private final LoadRestaurantPort loadRestaurantPort;
    private final ExternalGeoCoder externalGeoCoder;

    public HandleOrderUseCaseImpl(LoadOrdersPort loadOrdersPort, List<UpdateOrdersPort> updateOrdersPorts, LoadRestaurantPort loadRestaurantPort, ExternalGeoCoder externalGeoCoder) {
        this.loadOrdersPort = loadOrdersPort;
        this.updateOrdersPorts = updateOrdersPorts;
        this.loadRestaurantPort = loadRestaurantPort;
        this.externalGeoCoder = externalGeoCoder;
    }

    @Override
    public OrderProjection acceptOrder(UUID orderId, UUID ownerId) {
        OrderProjection orderProjection = loadOrdersPort.loadOrderById(orderId);

        var restaurant = loadRestaurantPort.findById(RestaurantId.of(orderProjection.getRestaurantId()));

        if (restaurant.isEmpty() || !restaurant.get().getOwnerId().id().equals(ownerId)) {
            throw new IllegalArgumentException("Order does not belong to restaurant");
        }

        Optional<Coordinates> restaurantCoords = externalGeoCoder.fetchCoordinates(restaurant.get().getAddress());
        Optional<Coordinates> orderCoords = externalGeoCoder.fetchCoordinates(orderProjection.getAddress());

        if (restaurantCoords.isEmpty() || orderCoords.isEmpty()) {
            if (restaurantCoords.isEmpty()) {
                throw new IllegalArgumentException("Could not find coordinates for restaurant address.");
            } else {
                throw new IllegalArgumentException("Could not find coordinates for order address.");
            }
        }

        orderProjection.accept();

        orderProjection.addDomainEvent(new OrderAcceptedEvent(
                orderProjection.getRestaurantId(),
                orderProjection.getOrderId(),
                RabbitMQAddress.from(restaurant.get().getAddress()),
                restaurantCoords.get(),
                RabbitMQAddress.from(orderProjection.getAddress()),
                orderCoords.get()
                )
        );

        this.updateOrdersPorts.forEach(port -> port.update(orderProjection));

        return orderProjection;
    }

    @Override
    public OrderProjection rejectOrder(UUID orderId, UUID ownerId, String reason) {
        OrderProjection orderProjection = loadOrdersPort.loadOrderById(orderId);

        var restaurant = loadRestaurantPort.findById(RestaurantId.of(orderProjection.getRestaurantId()));

        if (restaurant.isEmpty() || !restaurant.get().getOwnerId().id().equals(ownerId)) {
            throw new IllegalArgumentException("Order does not belong to restaurant");
        }

        orderProjection.reject(reason);

        orderProjection.addDomainEvent(new OrderRejectedEvent(
                        orderProjection.getOrderId(),
                        reason
                )
        );

        this.updateOrdersPorts.forEach(port -> port.update(orderProjection));

        return orderProjection;
    }

    @Override
    public OrderProjection acceptLastOrder(UUID ownerId) {

        var restaurant = loadRestaurantPort.findByOwnerId(OwnerId.of(ownerId,"owner"));

        if (restaurant.isEmpty() || !restaurant.get().getOwnerId().id().equals(ownerId)) {
            throw new IllegalArgumentException("Order does not belong to restaurant");
        }

        OrderProjection orderProjection = loadOrdersPort.loadAllByRestaurantId(restaurant.get().getRestaurantId()).getFirst();

        Optional<Coordinates> restaurantCoords = externalGeoCoder.fetchCoordinates(restaurant.get().getAddress());
        Optional<Coordinates> orderCoords = externalGeoCoder.fetchCoordinates(orderProjection.getAddress());

        if (restaurantCoords.isEmpty() || orderCoords.isEmpty()) {
            if (restaurantCoords.isEmpty()) {
                throw new IllegalArgumentException("Could not find coordinates for restaurant address.");
            } else {
                throw new IllegalArgumentException("Could not find coordinates for order address.");
            }
        }

        orderProjection.accept();

        orderProjection.addDomainEvent(new OrderAcceptedEvent(
                        orderProjection.getRestaurantId(),
                        orderProjection.getOrderId(),
                        RabbitMQAddress.from(restaurant.get().getAddress()),
                        restaurantCoords.get(),
                        RabbitMQAddress.from(orderProjection.getAddress()),
                        orderCoords.get()
                )
        );

        this.updateOrdersPorts.forEach(port -> port.update(orderProjection));

        return orderProjection;
    }
}
