package be.kdg.prog6.restaurant.adaptor.out.dish;

import be.kdg.prog6.restaurant.adaptor.out.foodMenu.FoodMenuJpaEntity;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "dish", schema = "restaurant")
public class DishJpaEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_menu_id", nullable = false)
    private FoodMenuJpaEntity foodMenu;

    private String name;
    private String description;
    private BigDecimal price;
    private String pictureUrl;
    private String tags;

    @Enumerated(EnumType.STRING)
    private DISH_STATE state;

    private String dishType;

    protected DishJpaEntity() {}

    public DishJpaEntity(Dish dish, FoodMenuJpaEntity foodMenu) {
        this.id = dish.getDishId().id();
        this.foodMenu = foodMenu;

        DishVersion draft = dish.getDraftVersion().orElseThrow(() -> new IllegalArgumentException("Dish has no draft version"));
        this.name = draft.name();
        this.description = draft.description();
        this.price = BigDecimal.valueOf(draft.price().asDouble());
        this.pictureUrl = draft.pictureUrl();
        this.tags = draft.tags();
        this.dishType = draft.dishType().name();
        this.state = dish.getState();
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public DISH_STATE getState() {
        return state;
    }

    public void setState(DISH_STATE state) {
        this.state = state;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodMenuJpaEntity getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(FoodMenuJpaEntity foodMenu) {
        this.foodMenu = foodMenu;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
