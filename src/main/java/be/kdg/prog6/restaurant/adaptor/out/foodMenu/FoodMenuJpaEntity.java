package be.kdg.prog6.restaurant.adaptor.out.foodMenu;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "food_menu", schema = "restaurant")
public class FoodMenuJpaEntity {
    @Id
    private UUID id;

    private UUID restaurantId;

    private double averageMenuPrice;

    // empty constructor for JPA
    protected FoodMenuJpaEntity() {}

    public FoodMenuJpaEntity(FoodMenu menu) {
        this.id = UUID.randomUUID();
        this.restaurantId = menu.getRestaurantId().id();
        this.averageMenuPrice = menu.getAverageMenuPrice().asDouble();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getAverageMenuPrice() {
        return averageMenuPrice;
    }

    public void setAverageMenuPrice(double averageMenuPrice) {
        this.averageMenuPrice = averageMenuPrice;
    }
}
