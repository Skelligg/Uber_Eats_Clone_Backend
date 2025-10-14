package be.kdg.prog6.restaurant.adaptor.out.dish;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_TYPE;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.port.out.LoadDishPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DishJpaAdaptor implements LoadDishPort {
    private final DishJpaRepository dishRepository;

    public DishJpaAdaptor(DishJpaRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Optional<Dish> loadDish(DishId dishId) {
        return dishRepository.findById(dishId.id())
                .map(this::toDomain);
    }

    private Dish toDomain(DishJpaEntity entity) {
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
                null,
                null
        );
    }
}