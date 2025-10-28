package be.kdg.prog6.restaurant.adaptor.out.foodMenu;

import be.kdg.prog6.restaurant.adaptor.out.dish.DishJpaEntity;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_TYPE;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FoodMenuJpaAdaptor implements UpdateFoodMenuPort, LoadFoodMenuPort {
    private final FoodMenuJpaRepository repository;

    public FoodMenuJpaAdaptor(FoodMenuJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addFoodMenu(FoodMenu menu) {
        repository.save(new FoodMenuJpaEntity(menu));
    }

    @Override
    public void updateFoodMenu(FoodMenu menu) {
        FoodMenuJpaEntity entity = repository.findById(menu.getRestaurantId().id())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + menu.getRestaurantId()));

        entity.setAverageMenuPrice(menu.getAverageMenuPrice().asDouble());

        for (Dish domainDish : menu.getAllDishes()) {
            updateDishEntity(entity, domainDish);
        }

        repository.save(entity);
    }

    private void updateDishEntity(FoodMenuJpaEntity foodMenuEntity, Dish domainDish) {
        DishJpaEntity dishEntity = foodMenuEntity.getDishes().stream()
                .filter(d -> d.getId().equals(domainDish.getDishId().id()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dish not found in menu: " + domainDish.getDishId()));

        dishEntity.setState(domainDish.getState());
        dishEntity.setScheduledPublishTime(domainDish.getScheduledPublishTime().orElse(null));
        dishEntity.setScheduledToBecomeState(domainDish.getScheduledToBecomeState());

        // Update published version
        if (domainDish.getPublishedVersion().isPresent()) {
            DishVersion published = domainDish.getPublishedVersion().get();
            dishEntity.setPublishedName(published.name());
            dishEntity.setPublishedDescription(published.description());
            dishEntity.setPublishedPrice(java.math.BigDecimal.valueOf(published.price().asDouble()));
            dishEntity.setPublishedPictureUrl(published.pictureUrl());
            dishEntity.setPublishedTags(published.tags());
            dishEntity.setPublishedDishType(published.dishType().name());
        } else {
            // Clear published version
            dishEntity.setPublishedName(null);
            dishEntity.setPublishedDescription(null);
            dishEntity.setPublishedPrice(null);
            dishEntity.setPublishedPictureUrl(null);
            dishEntity.setPublishedTags(null);
            dishEntity.setPublishedDishType(null);
        }

        // Update draft version
        if (domainDish.getDraftVersion().isPresent()) {
            DishVersion draft = domainDish.getDraftVersion().get();
            dishEntity.setDraftName(draft.name());
            dishEntity.setDraftDescription(draft.description());
            dishEntity.setDraftPrice(java.math.BigDecimal.valueOf(draft.price().asDouble()));
            dishEntity.setDraftPictureUrl(draft.pictureUrl());
            dishEntity.setDraftTags(draft.tags());
            dishEntity.setDraftDishType(draft.dishType().name());
        } else {
            // Clear draft version
            dishEntity.setDraftName(null);
            dishEntity.setDraftDescription(null);
            dishEntity.setDraftPrice(null);
            dishEntity.setDraftPictureUrl(null);
            dishEntity.setDraftTags(null);
            dishEntity.setDraftDishType(null);
        }
    }

    @Override
    public void addDishToMenu(Dish dish, FoodMenu menu) {
        FoodMenuJpaEntity foodMenu = repository.findById(menu.getRestaurantId().id())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found with id: " + menu.getRestaurantId().id()));

        DishJpaEntity dishEntity = new DishJpaEntity(dish, foodMenu);
        foodMenu.addDish(dishEntity);
    }

    @Override
    public Optional<FoodMenu> loadBy(RestaurantId restaurantId) {
        return repository.findById(restaurantId.id())
                .map(this::toDomain);
    }

    @Override
    public List<FoodMenu> loadAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private FoodMenu toDomain(FoodMenuJpaEntity entity) {
        FoodMenu foodMenu = new FoodMenu(new RestaurantId(entity.getRestaurantId()));

        for (DishJpaEntity dishEntity : entity.getDishes()) {
            Dish dish = toDishDomain(dishEntity);
            foodMenu.addDish(dish);
        }

        return foodMenu;
    }

    private Dish toDishDomain(DishJpaEntity entity) {
        // Reconstruct published version if exists
        DishVersion publishedVersion = null;
        if (entity.getPublishedName() != null) {
            publishedVersion = new DishVersion(
                    entity.getPublishedName(),
                    entity.getPublishedDescription(),
                    Price.of(entity.getPublishedPrice().doubleValue()),
                    entity.getPublishedPictureUrl(),
                    entity.getPublishedTags(),
                    DISH_TYPE.valueOf(entity.getPublishedDishType())
            );
        }

        // Reconstruct draft version if exists
        DishVersion draftVersion = null;
        if (entity.getDraftName() != null) {
            draftVersion = new DishVersion(
                    entity.getDraftName(),
                    entity.getDraftDescription(),
                    Price.of(entity.getDraftPrice().doubleValue()),
                    entity.getDraftPictureUrl(),
                    entity.getDraftTags(),
                    DISH_TYPE.valueOf(entity.getDraftDishType())
            );
        }

        return new Dish(
                DishId.of(entity.getId()),
                publishedVersion,
                draftVersion,
                entity.getState(),
                entity.getScheduledPublishTime(),
                entity.getScheduledToBecomeState()
        );
    }
}