package be.kdg.prog6.ordering.port.in.restaurantProjection;

import be.kdg.prog6.ordering.adaptor.in.request.FilterRestaurantsRequest;
import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;

import java.util.List;

public interface GetRestaurantsUseCase {
    List<RestaurantProjection> getRestaurants(FilterRestaurantsRequest request);
}
