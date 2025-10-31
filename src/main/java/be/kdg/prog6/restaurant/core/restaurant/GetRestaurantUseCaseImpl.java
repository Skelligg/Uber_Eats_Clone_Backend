package be.kdg.prog6.restaurant.core.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.port.in.restaurant.GetRestaurantUseCase;
import be.kdg.prog6.restaurant.port.out.restaurant.LoadRestaurantPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetRestaurantUseCaseImpl implements GetRestaurantUseCase {
    private final LoadRestaurantPort loadRestaurantPort;

    public GetRestaurantUseCaseImpl(LoadRestaurantPort loadRestaurantPort) {
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    public Restaurant getRestaurant(UUID ownerId) {
        return loadRestaurantPort.findByOwnerId(ownerId).orElseThrow(() -> new IllegalArgumentException("Restaurant not found for ownerId: " + ownerId));
    }
}
