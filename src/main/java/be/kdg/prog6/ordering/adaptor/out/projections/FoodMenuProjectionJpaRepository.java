package be.kdg.prog6.ordering.adaptor.out.projections;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoodMenuProjectionJpaRepository extends JpaRepository<FoodMenuProjectionJpaEntity, UUID> {
    List<FoodMenuProjectionJpaEntity> findByRestaurantId(UUID restaurantId);
}
