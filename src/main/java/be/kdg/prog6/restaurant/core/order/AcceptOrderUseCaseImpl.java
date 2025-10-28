package be.kdg.prog6.restaurant.core.order;

import be.kdg.prog6.common.events.order.OrderAcceptedEvent;
import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.Coordinates;
import be.kdg.prog6.common.vo.RabbitMQAddress;
import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.order.AcceptOrderUseCase;
import be.kdg.prog6.restaurant.port.out.ExternalGeoCoder;
import be.kdg.prog6.restaurant.port.out.order.LoadOrdersPort;
import be.kdg.prog6.restaurant.port.out.order.UpdateOrdersPort;
import be.kdg.prog6.restaurant.port.out.restaurant.LoadRestaurantPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AcceptOrderUseCaseImpl implements AcceptOrderUseCase {
    private final LoadOrdersPort loadOrdersPort;
    private final List<UpdateOrdersPort> updateOrdersPorts;
    private final LoadRestaurantPort loadRestaurantPort;
    private final ExternalGeoCoder externalGeoCoder;

    public AcceptOrderUseCaseImpl(LoadOrdersPort loadOrdersPort, List<UpdateOrdersPort> updateOrdersPorts, LoadRestaurantPort loadRestaurantPort, ExternalGeoCoder externalGeoCoder) {
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

        try {
            // Wait 1.1 seconds to respect Nominatim's 1-per-second rate limit
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

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
    public OrderProjection acceptLastOrder(UUID ownerId) {

        var restaurant = loadRestaurantPort.findByOwnerId(OwnerId.of(ownerId,"owner"));

        if (restaurant.isEmpty() || !restaurant.get().getOwnerId().id().equals(ownerId)) {
            throw new IllegalArgumentException("Order does not belong to restaurant");
        }

        OrderProjection orderProjection = loadOrdersPort.loadAllByRestaurantId(restaurant.get().getRestaurantId()).getFirst();

        Optional<Coordinates> restaurantCoords = externalGeoCoder.fetchCoordinates(restaurant.get().getAddress());

//        try {
//            // Wait 1.1 seconds to respect Nominatim's 1-per-second rate limit
//            Thread.sleep(1100);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

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
