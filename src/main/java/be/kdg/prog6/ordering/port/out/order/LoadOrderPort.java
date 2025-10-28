package be.kdg.prog6.ordering.port.out.order;

import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.OrderId;

import java.util.Optional;

public interface LoadOrderPort {
    Optional<Order> findById(OrderId orderId);
}
