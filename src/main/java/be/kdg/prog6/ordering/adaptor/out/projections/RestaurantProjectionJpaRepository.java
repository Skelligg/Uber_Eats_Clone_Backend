package be.kdg.prog6.ordering.adaptor.out.projections;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantProjectionJpaRepository extends JpaRepository<RestaurantProjectionJpaEntity, UUID> {
}
