package be.kdg.prog6.ordering.core.restaurantProjection;

import be.kdg.prog6.common.vo.CUISINE_TYPE;
import be.kdg.prog6.ordering.adaptor.in.request.FilterRestaurantsRequest;
import be.kdg.prog6.ordering.domain.projection.FoodMenuProjection;
import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import be.kdg.prog6.ordering.domain.vo.PRICE_RANGE;
import be.kdg.prog6.ordering.port.in.restaurantProjection.GetRestaurantsUseCase;
import be.kdg.prog6.ordering.port.out.foodmenuProjection.LoadFoodMenusPort;
import be.kdg.prog6.ordering.port.out.restaurantProjection.LoadRestaurantsPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRestaurantsUseCaseImpl implements GetRestaurantsUseCase {

    private final LoadRestaurantsPort loadRestaurantsPort;
    private final LoadFoodMenusPort loadFoodMenusPort;

    public GetRestaurantsUseCaseImpl(LoadRestaurantsPort loadRestaurantsPort, LoadFoodMenusPort loadFoodMenusPort) {
        this.loadRestaurantsPort = loadRestaurantsPort;
        this.loadFoodMenusPort = loadFoodMenusPort;
    }

    public List<RestaurantProjection> getRestaurants(FilterRestaurantsRequest request) {
        List<RestaurantProjection> allRestaurants = loadRestaurantsPort.loadAll();
        List<FoodMenuProjection> allMenus = loadFoodMenusPort.loadAll();

        return allRestaurants.stream()
                // Filter by cuisine type
                .filter(r ->
                        request.cuisineType() == null || request.cuisineType().isBlank() ||
                                matchesCuisineType(r.getCuisineType(), request.cuisineType())
                )
                // Filter by price range (join via restaurantId)
                .filter(r -> {
                    if (request.priceRange() == null || request.priceRange().isBlank()) {
                        return true;
                    }
                    // Find the menu(s) for this restaurant
                    return allMenus.stream()
                            .filter(m -> m.restaurantId().equals(r.getRestaurantId()))
                            .anyMatch(m -> matchesPriceRange(m.priceRange(), request.priceRange()));
                })
                .toList();
    }

    private boolean matchesCuisineType(CUISINE_TYPE restaurantType, String requestType) {
        try {
            return restaurantType == CUISINE_TYPE.valueOf(requestType.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Invalid type in request; ignore filter
            return true;
        }
    }

    private boolean matchesPriceRange(PRICE_RANGE restaurantPrice, String requestPrice) {
        try {
            return restaurantPrice == PRICE_RANGE.valueOf(requestPrice.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Invalid range; ignore filter
            return true;
        }
    }
}