// ordering/adaptor/out/FoodMenuProjectionJpaEntity.java
package be.kdg.prog6.ordering.adaptor.out.projections;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "food_menu_projection", schema = "ordering")
public class FoodMenuProjectionJpaEntity {
    @Id
    private UUID restaurantId;

    @Column(nullable = false)
    private BigDecimal averageMenuPrice;


    public FoodMenuProjectionJpaEntity(UUID restaurantId, BigDecimal averageMenuPrice) {
        this.restaurantId = restaurantId;
        this.averageMenuPrice = averageMenuPrice;
    }

    public FoodMenuProjectionJpaEntity() {

    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getAverageMenuPrice() {
        return averageMenuPrice;
    }

    public void setAverageMenuPrice(BigDecimal averageMenuPrice) {
        this.averageMenuPrice = averageMenuPrice;
    }
}
