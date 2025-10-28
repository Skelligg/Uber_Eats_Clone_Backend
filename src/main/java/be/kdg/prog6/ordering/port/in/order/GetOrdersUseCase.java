package be.kdg.prog6.ordering.port.in.order;

import be.kdg.prog6.ordering.domain.Order;

import java.util.UUID;

public interface GetOrdersUseCase {
    Order getOrder(UUID orderId);
}
