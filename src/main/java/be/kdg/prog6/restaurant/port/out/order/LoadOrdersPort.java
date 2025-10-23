package be.kdg.prog6.restaurant.port.out.order;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

import java.util.List;

public interface LoadOrdersPort {
    List<OrderProjection> loadAllByRestaurantId(RestaurantId restaurantId);
}
