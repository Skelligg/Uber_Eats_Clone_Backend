package be.kdg.prog6.ordering.adaptor.out.projections;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DishProjectionJpaRepository extends JpaRepository<DishProjectionJpaEntity, UUID> {
    List<DishProjectionJpaEntity> findByFoodMenuId(UUID foodMenuId);
    List<DishProjectionJpaEntity> findAllByFoodMenuId(UUID foodMenuId);
}
