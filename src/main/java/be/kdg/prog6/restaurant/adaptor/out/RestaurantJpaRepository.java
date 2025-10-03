package be.kdg.prog6.restaurant.adaptor.out;

import be.kdg.prog6.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {
}
