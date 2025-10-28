package be.kdg.prog6.ordering.port.in.restaurant;

import be.kdg.prog6.ordering.adaptor.in.request.FilterRestaurantsRequest;
import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;

import java.util.List;
import java.util.Optional;

public interface GetRestaurantsUseCase {
    List<RestaurantProjection> getRestaurants(FilterRestaurantsRequest request);
}
