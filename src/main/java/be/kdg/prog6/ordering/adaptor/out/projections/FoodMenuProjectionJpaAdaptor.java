package be.kdg.prog6.ordering.adaptor.out.projections;

import be.kdg.prog6.ordering.domain.projection.FoodMenuProjection;
import be.kdg.prog6.ordering.port.out.UpdateFoodMenusPort;
import org.springframework.stereotype.Repository;

@Repository
public class FoodMenuProjectionJpaAdaptor implements UpdateFoodMenusPort {
    private final FoodMenuProjectionJpaRepository repository;

    public FoodMenuProjectionJpaAdaptor(FoodMenuProjectionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateFoodMenus(FoodMenuProjection projection) {
        var entity = new FoodMenuProjectionJpaEntity();
        entity.setRestaurantId(projection.restaurantId());
        entity.setAverageMenuPrice(projection.averageMenuPrice());
        repository.save(entity);
    }
}
