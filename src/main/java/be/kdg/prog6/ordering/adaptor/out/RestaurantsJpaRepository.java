package be.kdg.prog6.ordering.adaptor.out;

import be.kdg.prog6.ordering.domain.projection.RestaurantProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsJpaRepository extends JpaRepository<RestaurantProjectionJpaEntity, UUID> {
}
