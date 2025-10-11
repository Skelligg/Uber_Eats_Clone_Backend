
package be.kdg.prog6.restaurant.adaptor.out.dish;

import be.kdg.prog6.restaurant.adaptor.out.foodMenu.FoodMenuJpaRepository;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.port.out.UpdateDishPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DishJpaAdaptor implements UpdateDishPort {
    private final DishJpaRepository dishRepository;
    private final FoodMenuJpaRepository foodMenuRepository;

    public DishJpaAdaptor(DishJpaRepository dishRepository, FoodMenuJpaRepository foodMenuRepository) {
        this.dishRepository = dishRepository;
        this.foodMenuRepository = foodMenuRepository;
    }

    @Override
    public void addDish(Dish dish, UUID foodMenuId) {
        var foodMenu = foodMenuRepository.findById(foodMenuId)
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found with id: " + foodMenuId));

        DishJpaEntity dishEntity = new DishJpaEntity(dish, foodMenu);
        foodMenu.addDish(dishEntity);
//        dishRepository.save(dishEntity);
    }
}