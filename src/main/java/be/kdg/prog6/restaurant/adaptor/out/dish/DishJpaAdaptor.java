package be.kdg.prog6.restaurant.adaptor.out.dish;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.port.out.UpdateDishPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DishJpaAdaptor implements UpdateDishPort {
    private final DishJpaRepository repository;

    public DishJpaAdaptor(DishJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addDish(Dish dish, UUID restaurantId) {
        repository.save(new DishJpaEntity(dish, restaurantId));
    }
}
