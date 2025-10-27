package be.kdg.prog6.restaurant.port.out.order;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

import java.util.List;
import java.util.UUID;

public interface LoadOrdersPort {
    List<OrderProjection> loadAllByRestaurantId(RestaurantId restaurantId);
    OrderProjection loadOrderById(UUID orderId);
}
