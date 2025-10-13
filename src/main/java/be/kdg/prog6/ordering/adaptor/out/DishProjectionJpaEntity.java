package be.kdg.prog6.ordering.adaptor.out;

import be.kdg.prog6.ordering.domain.projection.DISH_AVAILABILITY;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "dish_projection", schema = "ordering")
public class DishProjectionJpaEntity {
    @Id
    private UUID dishId;

    @Column(nullable = false)
    private UUID foodMenuId;

    private String name;
    private String description;
    private BigDecimal price;
    private String pictureUrl;
    private String tags;
    private String dishType;
    @Enumerated(EnumType.STRING)
    private DISH_AVAILABILITY dishState;

    public DishProjectionJpaEntity(UUID dishId, UUID foodMenuId, String name, String description,
                                   BigDecimal price, String pictureUrl, String tags, String dishType, DISH_AVAILABILITY dishState) {
        this.dishId = dishId;
        this.foodMenuId = foodMenuId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.tags = tags;
        this.dishType = dishType;
        this.dishState = dishState;
    }

    public DishProjectionJpaEntity() {
    }

    public UUID getDishId() {
        return dishId;
    }

    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public UUID getRestaurantId() {
        return foodMenuId;
    }

    public void setRestaurantId(UUID foodMenuId) {
        this.foodMenuId = foodMenuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public DISH_AVAILABILITY getDishState() {
        return dishState;
    }

    public void setDishState(DISH_AVAILABILITY dishState) {
        this.dishState = dishState;
    }
}
