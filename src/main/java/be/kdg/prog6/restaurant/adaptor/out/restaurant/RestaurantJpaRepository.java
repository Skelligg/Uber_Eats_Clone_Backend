package be.kdg.prog6.restaurant.adaptor.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {
    @Override
    Optional<RestaurantJpaEntity> findById(UUID uuid);

    Optional<RestaurantJpaEntity> findByOwnerId(UUID ownerId);
}
