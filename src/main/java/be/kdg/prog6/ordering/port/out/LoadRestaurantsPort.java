package be.kdg.prog6.ordering.port.out;

import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantsPort {
    List<RestaurantProjection> loadAll();
    Optional<RestaurantProjection> findById(UUID restaurantId);

}
