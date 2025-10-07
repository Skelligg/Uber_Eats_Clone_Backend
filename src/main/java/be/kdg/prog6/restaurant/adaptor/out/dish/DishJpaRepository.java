package be.kdg.prog6.restaurant.adaptor.out.dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DishJpaRepository extends JpaRepository<DishJpaEntity, UUID> {
}
