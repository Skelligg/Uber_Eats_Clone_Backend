package be.kdg.prog6.ordering.adaptor.out;

import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.*;
import be.kdg.prog6.ordering.port.out.LoadOrderPort;
import be.kdg.prog6.ordering.port.out.UpdateOrderPort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderJpaAdaptor implements LoadOrderPort, UpdateOrderPort {

    private final OrderJpaRepository orders;

    public OrderJpaAdaptor(OrderJpaRepository orders) {
        this.orders = orders;
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orders.findById(orderId.id())
                .map(this::toDomain);
    }

    @Override
    public Order addOrder(Order order) {
        orders.save(toJpaEntity(order));
        return order;
    }

    private OrderJpaEntity toJpaEntity(Order order) {
        return new OrderJpaEntity(
                order.getOrderId().id(),
                order.getRestaurantId().id(),
                order.getCustomer().name(),
                order.getCustomer().email(),
                order.getDeliveryAddress().street(),
                order.getDeliveryAddress().number(),
                order.getDeliveryAddress().postalCode(),
                order.getDeliveryAddress().city(),
                order.getDeliveryAddress().country(),
                order.getLines().stream()
                        .map(l -> new OrderLineEmbeddable(
                                l.dishId().id(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice().price().doubleValue(),
                                l.linePrice().price().doubleValue()
                        ))
                        .collect(Collectors.toList()),
                order.getTotalPrice().price().doubleValue(),
                order.getStatus(),
                order.getRejectionReason().orElse(null),
                order.getPlacedAt(),
                order.getAcceptedAt().orElse(null),
                order.getRejectedAt().orElse(null),
                order.getReadyAt().orElse(null),
                order.getPickedUpAt().orElse(null),
                order.getDeliveredAt().orElse(null),
                order.getEstimatedDeliveryMinutes(),
                order.getCourierLocations().stream()
                        .map(l -> new CourierLocationEmbeddable(
                                l.lat(),
                                l.lon(),
                                l.when()
                        )).toList()
        );
    }

    // ---------- Mapping: JPA -> Domain ----------

    private Order toDomain(OrderJpaEntity entity) {
        return new Order(
                new OrderId(entity.getId()),
                new RestaurantId(entity.getRestaurantId()),
                entity.getLines().stream()
                        .map(l -> new OrderLine(
                                new DishId(l.getDishId()),
                                l.getDishName(),
                                l.getQuantity(),
                                Money.of(l.getUnitPrice()),
                                Money.of(l.getLinePrice())
                        ))
                        .collect(Collectors.toList()),
                Money.of(entity.getTotalPrice()),
                new CustomerInfo(entity.getCustomerName(), entity.getCustomerEmail()),
                new Address(
                        entity.getStreet(),
                        entity.getNumber(),
                        entity.getPostalCode(),
                        entity.getCity(),
                        entity.getCountry()
                ),
                entity.getPlacedAt(),
                entity.getAcceptedAt(),
                entity.getRejectedAt(),
                entity.getReadyAt(),
                entity.getPickedUpAt(),
                entity.getDeliveredAt(),
                entity.getStatus(),
                entity.getRejectionReason(),
                entity.getEstimatedDeliveryMinutes()
        );
    }

}
