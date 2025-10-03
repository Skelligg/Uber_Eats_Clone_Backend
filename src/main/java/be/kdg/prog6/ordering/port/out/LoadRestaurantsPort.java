package be.kdg.prog6.ordering.port.out;

import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;

import java.util.List;

public interface LoadRestaurantsPort {
    List<RestaurantProjection> loadAll();

}
