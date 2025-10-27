package be.kdg.prog6.restaurant.port.in.order;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;

import java.util.UUID;

public interface AcceptOrderUseCase {
    OrderProjection acceptOrder(UUID orderId, UUID ownerId);
}
