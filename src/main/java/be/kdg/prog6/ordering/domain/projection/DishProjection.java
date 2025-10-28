package be.kdg.prog6.ordering.domain.projection;

import be.kdg.prog6.common.vo.DISH_TYPE;

import java.math.BigDecimal;
import java.util.UUID;

public class DishProjection {
    private UUID dishId;
    private UUID foodMenuId;
    private String name;
    private String description;
    private BigDecimal price;
    private String pictureUrl;
    private String tags;
    private DISH_TYPE dishType;
    private DISH_AVAILABILITY dishState;

    // All-args constructor
    public DishProjection(UUID dishId, UUID foodMenuId, String name, String description,
                          BigDecimal price, String pictureUrl, String tags,
                          DISH_TYPE dishType, DISH_AVAILABILITY dishState) {
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

    // Getters
    public UUID getDishId() {
        return dishId;
    }

    public UUID getFoodMenuId() {
        return foodMenuId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTags() {
        return tags;
    }

    public DISH_TYPE getDishType() {
        return dishType;
    }

    public DISH_AVAILABILITY getDishState() {
        return dishState;
    }

    // Setters
    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public void setFoodMenuId(UUID foodMenuId) {
        this.foodMenuId = foodMenuId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setDishType(DISH_TYPE dishType) {
        this.dishType = dishType;
    }

    public void setDishState(DISH_AVAILABILITY dishState) {
        this.dishState = dishState;
    }
}