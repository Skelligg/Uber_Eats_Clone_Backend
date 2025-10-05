package be.kdg.prog6.restaurant.adaptor.out.foodMenu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodMenuJpaRepository extends JpaRepository<FoodMenuJpaEntity, UUID> {
}
