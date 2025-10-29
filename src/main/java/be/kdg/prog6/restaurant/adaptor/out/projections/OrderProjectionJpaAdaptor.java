package be.kdg.prog6.restaurant.adaptor.out.projections;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.restaurant.domain.projection.OrderLineProjection;
import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.out.orderProjection.LoadOrdersPort;
import be.kdg.prog6.restaurant.port.out.orderProjection.UpdateOrdersPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderProjectionJpaAdaptor implements LoadOrdersPort, UpdateOrdersPort {

    private final OrderProjectionJpaRepository repository;

    public OrderProjectionJpaAdaptor(OrderProjectionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderProjection update(OrderProjection orderProjection) {
        repository.save(toJpaEntity(orderProjection));
        return orderProjection;
    }

    public OrderProjectionJpaEntity toJpaEntity(OrderProjection projection) {
        List<OrderLineProjectionEmbeddable> lineEntities = projection.getLines().stream()
                .map(l -> new OrderLineProjectionEmbeddable(
                        l.dishId().id(),
                        l.dishName(),
                        l.quantity(),
                        l.unitPrice(),
                        l.linePrice()
                ))
                .collect(Collectors.toList());

        OrderProjectionJpaEntity entity = new OrderProjectionJpaEntity(
                projection.getOrderId(),
                projection.getRestaurantId(),
                projection.getAddress().street(),
                projection.getAddress().number(),
                projection.getAddress().postalCode(),
                projection.getAddress().city(),
                projection.getAddress().country(),
                projection.getTotalPrice(),
                projection.getPlacedAt(),
                projection.getStatus(),
                projection.getRejectionReason(),
                projection.getAcceptedAt(),
                projection.getReadyAt(),
                projection.getRejectedAt(),
                projection.getPickedUpAt(),
                projection.getDeliveredAt(),
                lineEntities
        );

        return entity;
    }

    @Override
    public List<OrderProjection> loadAllByRestaurantId(RestaurantId restaurantId) {
        return repository.findAllByRestaurantId(restaurantId.id()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public OrderProjection loadOrderById(UUID orderId) {
        return repository.findById(orderId).map(this::toDomain).orElse(null);
    }

    @Override
    public List<OrderProjection> loadAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    public OrderProjection toDomain(OrderProjectionJpaEntity entity) {
        List<OrderLineProjection> lines = entity.getLines().stream()
                .map(l -> new OrderLineProjection(
                        DishId.of(l.getDishId()),
                        l.getDishName(),
                        l.getQuantity(),
                        l.getUnitPrice(),
                        l.getLinePrice()
                ))
                .collect(Collectors.toList());

        return new OrderProjection(
                entity.getOrderId(),
                entity.getRestaurantId(),
                Address.of(
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getCountry()),
                entity.getTotalPrice(),
                entity.getPlacedAt(),
                entity.getStatus(),
                entity.getRejectionReason(),
                entity.getAcceptedAt(),
                entity.getReadyAt(),
                entity.getRejectedAt(),
                entity.getPickedUpAt(),
                entity.getDeliveredAt(),
                lines
        );
    }

}
