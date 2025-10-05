package be.kdg.prog6.restaurant.adaptor.out.foodMenu;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.springframework.stereotype.Component;

@Component
public class FoodMenuJpaAdaptor implements UpdateFoodMenuPort {
    private final FoodMenuJpaRepository repository;

    public FoodMenuJpaAdaptor(FoodMenuJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addFoodMenu(FoodMenu menu) {
        repository.save(new FoodMenuJpaEntity(menu));
    }
}
