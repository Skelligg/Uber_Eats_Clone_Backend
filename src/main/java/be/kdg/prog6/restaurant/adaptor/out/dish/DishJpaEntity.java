package be.kdg.prog6.restaurant.adaptor.out.dish;

import be.kdg.prog6.restaurant.adaptor.out.foodMenu.FoodMenuJpaEntity;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dish", schema = "restaurant")
public class DishJpaEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_menu_id", nullable = false)
    private FoodMenuJpaEntity foodMenu;
    @Enumerated(EnumType.STRING)
    private DISH_STATE state;

    private String publishedName;
    private String publishedDescription;
    private BigDecimal publishedPrice;
    private String publishedPictureUrl;
    private String publishedTags;
    private String publishedDishType;

    // Draft version fields
    private String draftName;
    private String draftDescription;
    private BigDecimal draftPrice;
    private String draftPictureUrl;
    private String draftTags;
    private String draftDishType;
    private LocalDateTime scheduledPublishTime;
    private DISH_STATE scheduledToBecomeState;

    protected DishJpaEntity() {}

    public DishJpaEntity(Dish dish, FoodMenuJpaEntity foodMenu) {
        this.id = dish.getDishId().id();
        this.foodMenu = foodMenu;
        this.state = dish.getState();

        // Set published version if exists
        dish.getPublishedVersion().ifPresent(published -> {
            this.publishedName = published.name();
            this.publishedDescription = published.description();
            this.publishedPrice = BigDecimal.valueOf(published.price().asDouble());
            this.publishedPictureUrl = published.pictureUrl();
            this.publishedTags = published.tags();
            this.publishedDishType = published.dishType().name();
        });

        // Set draft version if exists
        dish.getDraftVersion().ifPresent(draft -> {
            this.draftName = draft.name();
            this.draftDescription = draft.description();
            this.draftPrice = BigDecimal.valueOf(draft.price().asDouble());
            this.draftPictureUrl = draft.pictureUrl();
            this.draftTags = draft.tags();
            this.draftDishType = draft.dishType().name();
        });

        this.scheduledPublishTime= dish.getScheduledPublishTime().orElse(null);
        this.scheduledToBecomeState = dish.getScheduledToBecomeState();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FoodMenuJpaEntity getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(FoodMenuJpaEntity foodMenu) {
        this.foodMenu = foodMenu;
    }

    public DISH_STATE getState() {
        return state;
    }

    public void setState(DISH_STATE state) {
        this.state = state;
    }

    public String getPublishedName() {
        return publishedName;
    }

    public void setPublishedName(String publishedName) {
        this.publishedName = publishedName;
    }

    public String getPublishedDescription() {
        return publishedDescription;
    }

    public void setPublishedDescription(String publishedDescription) {
        this.publishedDescription = publishedDescription;
    }

    public BigDecimal getPublishedPrice() {
        return publishedPrice;
    }

    public void setPublishedPrice(BigDecimal publishedPrice) {
        this.publishedPrice = publishedPrice;
    }

    public String getPublishedPictureUrl() {
        return publishedPictureUrl;
    }

    public void setPublishedPictureUrl(String publishedPictureUrl) {
        this.publishedPictureUrl = publishedPictureUrl;
    }

    public String getPublishedTags() {
        return publishedTags;
    }

    public void setPublishedTags(String publishedTags) {
        this.publishedTags = publishedTags;
    }

    public String getPublishedDishType() {
        return publishedDishType;
    }

    public void setPublishedDishType(String publishedDishType) {
        this.publishedDishType = publishedDishType;
    }

    public String getDraftName() {
        return draftName;
    }

    public void setDraftName(String draftName) {
        this.draftName = draftName;
    }

    public String getDraftDescription() {
        return draftDescription;
    }

    public void setDraftDescription(String draftDescription) {
        this.draftDescription = draftDescription;
    }

    public BigDecimal getDraftPrice() {
        return draftPrice;
    }

    public void setDraftPrice(BigDecimal draftPrice) {
        this.draftPrice = draftPrice;
    }

    public String getDraftPictureUrl() {
        return draftPictureUrl;
    }

    public void setDraftPictureUrl(String draftPictureUrl) {
        this.draftPictureUrl = draftPictureUrl;
    }

    public String getDraftTags() {
        return draftTags;
    }

    public void setDraftTags(String draftTags) {
        this.draftTags = draftTags;
    }

    public String getDraftDishType() {
        return draftDishType;
    }

    public void setDraftDishType(String draftDishType) {
        this.draftDishType = draftDishType;
    }

    public LocalDateTime getScheduledPublishTime() {
        return scheduledPublishTime;
    }

    public void setScheduledPublishTime(LocalDateTime scheduledPublishTime) {
        this.scheduledPublishTime = scheduledPublishTime;
    }

    public DISH_STATE getScheduledToBecomeState() {
        return scheduledToBecomeState;
    }

    public void setScheduledToBecomeState(DISH_STATE scheduledToBecomeState) {
        this.scheduledToBecomeState = scheduledToBecomeState;
    }
}
