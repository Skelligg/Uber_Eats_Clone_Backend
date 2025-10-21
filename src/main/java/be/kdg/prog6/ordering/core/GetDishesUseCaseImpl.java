package be.kdg.prog6.ordering.core;

import be.kdg.prog6.ordering.domain.projection.DishProjection;
import be.kdg.prog6.ordering.port.in.dish.GetDishesUseCase;
import be.kdg.prog6.ordering.port.out.LoadDishesPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetDishesUseCaseImpl implements GetDishesUseCase {
    private final LoadDishesPort loadDishesPort;

    public GetDishesUseCaseImpl(LoadDishesPort loadDishesPort) {
        this.loadDishesPort = loadDishesPort;
    }

    @Override
    public List<DishProjection> getDishes(UUID restaurantId) {
        return loadDishesPort.loadAllByRestaurantId(restaurantId);
    }
}
