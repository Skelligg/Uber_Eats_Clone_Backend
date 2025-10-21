package be.kdg.prog6.restaurant.adaptor.out.projection.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProjectionJpaRepository extends JpaRepository<OrderProjectionJpaEntity , UUID>{
}
