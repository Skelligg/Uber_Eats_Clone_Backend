package be.kdg.prog6.restaurant.core.foodmenu;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.foodmenu.GetFoodMenuUseCase;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetFoodMenuUseCaseImpl implements GetFoodMenuUseCase {
    private final LoadFoodMenuPort loadFoodMenuPort;

    public GetFoodMenuUseCaseImpl(LoadFoodMenuPort loadFoodMenuPort) {
        this.loadFoodMenuPort = loadFoodMenuPort;
    }

    @Override
    public FoodMenu getFoodMenu(UUID restaurantId) {
        if(loadFoodMenuPort.loadBy(RestaurantId.of(restaurantId)).isEmpty()){
            throw new IllegalArgumentException("Menu does not exist");
        }

        return loadFoodMenuPort.loadBy(RestaurantId.of(restaurantId)).get();
    }
}
