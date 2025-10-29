package be.kdg.prog6.ordering.adaptor.out.projections;

import be.kdg.prog6.common.vo.DISH_TYPE;
import be.kdg.prog6.ordering.domain.projection.DishProjection;
import be.kdg.prog6.ordering.port.out.dishProjection.LoadDishesPort;
import be.kdg.prog6.ordering.port.out.dishProjection.UpdateDishesPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DishProjectionJpaAdaptor implements UpdateDishesPort, LoadDishesPort {
    private final DishProjectionJpaRepository repository;

    public DishProjectionJpaAdaptor(DishProjectionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateDishes(DishProjection projection) {
        var entity = new DishProjectionJpaEntity();
        entity.setDishId(projection.getDishId());
        entity.setRestaurantId(projection.getFoodMenuId());
        entity.setName(projection.getName());
        entity.setDescription(projection.getDescription());
        entity.setPrice(projection.getPrice());
        entity.setPictureUrl(projection.getPictureUrl());
        entity.setTags(projection.getTags());
        entity.setDishType(projection.getDishType().toString());
        entity.setDishState(projection.getDishState());
        repository.save(entity);
    }

    @Override
    public void removeDish(DishProjection projection) {
        repository.deleteById(projection.getDishId());
    }

    @Override
    public Optional<DishProjection> loadDish(UUID dishId) {
        return repository.findById(dishId)
                .map(this::mapToProjection);
    }

    @Override
    public List<DishProjection> loadAllByRestaurantId(UUID restaurantId) {
        return repository.findAllByFoodMenuId(restaurantId)
                .stream()
                .map(this::mapToProjection)
                .toList();
    }

    private DishProjection mapToProjection(DishProjectionJpaEntity entity) {
        return new DishProjection(
                entity.getDishId(),
                entity.getRestaurantId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getPictureUrl(),
                entity.getTags(),
                DISH_TYPE.valueOf(entity.getDishType()),
                entity.getDishState()
        );
    }

}
