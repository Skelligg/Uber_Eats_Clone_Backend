package be.kdg.prog6.ordering.adaptor.out.projections;

import be.kdg.prog6.ordering.domain.projection.FoodMenuProjection;
import be.kdg.prog6.ordering.port.out.foodmenu.LoadFoodMenusPort;
import be.kdg.prog6.ordering.port.out.foodmenu.UpdateFoodMenusPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodMenuProjectionJpaAdaptor implements UpdateFoodMenusPort, LoadFoodMenusPort {
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

    @Override
    public List<FoodMenuProjection> loadAll() {
        return repository.findAll().stream()
                .map(f -> new FoodMenuProjection(
                        f.getRestaurantId(),
                        f.getAverageMenuPrice()
                )).toList();
    }
}
