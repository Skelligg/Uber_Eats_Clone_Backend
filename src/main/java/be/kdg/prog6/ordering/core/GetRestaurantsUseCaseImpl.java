package be.kdg.prog6.ordering.core;

import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import be.kdg.prog6.ordering.port.in.restaurant.GetRestaurantsUseCase;
import be.kdg.prog6.ordering.port.out.restaurant.LoadRestaurantsPort;
import be.kdg.prog6.ordering.port.out.restaurant.UpdateRestaurantsPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetRestaurantsUseCaseImpl implements GetRestaurantsUseCase {

    private final LoadRestaurantsPort loadRestaurantsPort;
    private final UpdateRestaurantsPort updateRestaurantsPort;

    public GetRestaurantsUseCaseImpl(LoadRestaurantsPort loadRestaurantsPort, UpdateRestaurantsPort updateRestaurantsPort) {
        this.loadRestaurantsPort = loadRestaurantsPort;
        this.updateRestaurantsPort = updateRestaurantsPort;
    }

    public List<RestaurantProjection> getRestaurants(Optional<String> cuisineType, Optional<Boolean> onlyOpen) {
        List<RestaurantProjection> all = loadRestaurantsPort.loadAll();

        return all.stream()
//                .filter(r -> cuisineType.isEmpty() || r.getCuisineType().equalsIgnoreCase(cuisineType.get()))
//                .filter(r -> onlyOpen.isEmpty() || (onlyOpen.get() && r.isOpenNow()))
                .toList();
    }

}
