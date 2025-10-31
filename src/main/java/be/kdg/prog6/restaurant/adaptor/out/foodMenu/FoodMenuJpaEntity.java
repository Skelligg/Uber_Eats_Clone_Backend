package be.kdg.prog6.restaurant.adaptor.out.foodMenu;

import be.kdg.prog6.restaurant.adaptor.out.dish.DishJpaEntity;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "food_menu", schema = "restaurant")
public class FoodMenuJpaEntity {
    @Id
    private UUID restaurantId;

    private double averageMenuPrice;

    @OneToMany(mappedBy = "foodMenu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DishJpaEntity> dishes = new ArrayList<>();

    // empty constructor for JPA
    protected FoodMenuJpaEntity() {}

    public FoodMenuJpaEntity(FoodMenu menu) {
        this.restaurantId = menu.getRestaurantId().id();
        this.averageMenuPrice = menu.getAverageMenuPrice().asDouble();
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<DishJpaEntity> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishJpaEntity> dishes) {
        this.dishes = dishes;
    }

    public void addDish(DishJpaEntity dish) {
        dishes.add(dish);
        dish.setFoodMenu(this);
    }

    public void removeDish(DishJpaEntity dish) {
        dishes.remove(dish);
        dish.setFoodMenu(null);
    }
}