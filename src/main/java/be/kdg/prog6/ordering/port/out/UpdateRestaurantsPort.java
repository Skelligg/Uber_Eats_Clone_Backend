package be.kdg.prog6.ordering.port.out;

import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;

public interface UpdateRestaurantsPort {

    RestaurantProjection update(RestaurantProjection restaurantProjection);
}
