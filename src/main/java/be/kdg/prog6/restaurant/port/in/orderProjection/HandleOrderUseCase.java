package be.kdg.prog6.restaurant.port.in.orderProjection;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;

import java.util.UUID;

public interface HandleOrderUseCase {
    OrderProjection acceptOrder(UUID orderId, UUID ownerId);
    OrderProjection rejectOrder(UUID orderId, UUID ownerId, String reason);
    OrderProjection acceptLastOrder( UUID ownerId);
}
