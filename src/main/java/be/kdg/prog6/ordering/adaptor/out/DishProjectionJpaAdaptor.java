package be.kdg.prog6.ordering.adaptor.out;

import be.kdg.prog6.ordering.domain.projection.DishProjection;
import be.kdg.prog6.ordering.port.out.UpdateDishesPort;
import org.springframework.stereotype.Repository;

@Repository
public class DishProjectionJpaAdaptor implements UpdateDishesPort {
    private final DishProjectionJpaRepository repository;

    public DishProjectionJpaAdaptor(DishProjectionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateDishes(DishProjection projection) {
        var entity = new DishProjectionJpaEntity();
        entity.setDishId(projection.dishId());
        entity.setRestaurantId(projection.foodMenuId());
        entity.setName(projection.name());
        entity.setDescription(projection.description());
        entity.setPrice(projection.price());
        entity.setPictureUrl(projection.pictureUrl());
        entity.setTags(projection.tags());
        entity.setDishType(projection.dishType());
        repository.save(entity);
    }
}
