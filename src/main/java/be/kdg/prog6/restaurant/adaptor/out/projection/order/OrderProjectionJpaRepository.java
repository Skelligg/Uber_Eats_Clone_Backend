package be.kdg.prog6.restaurant.adaptor.out.projection.order;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProjectionJpaRepository extends JpaRepository<OrderProjectionJpaEntity , UUID>{
    List<OrderProjectionJpaEntity> findAllByRestaurantId(UUID restaurantId);
}
