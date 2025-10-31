package be.kdg.prog6.restaurant.adaptor.out.foodMenu;

import be.kdg.prog6.restaurant.adaptor.out.dish.DishJpaEntity;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.common.vo.DISH_TYPE;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class FoodMenuJpaAdaptor implements UpdateFoodMenuPort, LoadFoodMenuPort {
    private final FoodMenuJpaRepository repository;

    public FoodMenuJpaAdaptor(FoodMenuJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public FoodMenu updateFoodMenu(FoodMenu menu) {
        FoodMenuJpaEntity entity = repository.findById(menu.getRestaurantId().id())
                .orElse(null);

        if (entity == null) {
            // Create new FoodMenu
            entity = new FoodMenuJpaEntity(menu);

            for (Dish domainDish : menu.getAllDishes()) {
                entity.addDish(new DishJpaEntity(domainDish, entity));
            }

            repository.save(entity);
            return toDomain(entity);
        }

        for (Dish domainDish : menu.getAllDishes()) {
            // Find existing dish in JPA entity
            DishJpaEntity existingDish = entity.getDishes().stream()
                    .filter(d -> d.getId().equals(domainDish.getDishId().id()))
                    .findFirst()
                    .orElse(null);

            if (existingDish != null) {
                existingDish.setScheduledPublishTime(domainDish.getScheduledPublishTime().orElse(null));
                existingDish.setScheduledToBecomeState(domainDish.getScheduledToBecomeState());
                existingDish.setState(domainDish.getState());

                // Optional: update other fields if draft/published versions changed
                if (domainDish.getPublishedVersion().isPresent()) {
                    var v = domainDish.getPublishedVersion().get();
                    existingDish.setPublishedName(v.name());
                    existingDish.setPublishedDescription(v.description());
                    existingDish.setPublishedPrice(BigDecimal.valueOf(v.price().asDouble()));
                    existingDish.setPublishedPictureUrl(v.pictureUrl());
                    existingDish.setPublishedTags(v.tags());
                    existingDish.setPublishedDishType(v.dishType().name());
                }
                if (domainDish.getDraftVersion().isPresent()) {
                    var v = domainDish.getDraftVersion().get();
                    existingDish.setDraftName(v.name());
                    existingDish.setDraftDescription(v.description());
                    existingDish.setDraftPrice(BigDecimal.valueOf(v.price().asDouble()));
                    existingDish.setDraftPictureUrl(v.pictureUrl());
                    existingDish.setDraftTags(v.tags());
                    existingDish.setDraftDishType(v.dishType().name());
                }
            } else {
                // 🧩 Add new dish if not found
                entity.addDish(new DishJpaEntity(domainDish, entity));
            }
        }

        repository.save(entity);
        return toDomain(entity);
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