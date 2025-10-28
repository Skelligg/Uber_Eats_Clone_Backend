package be.kdg.prog6.restaurant.port.in.order;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;

import java.util.UUID;

public interface MarkOrderReadyForPickUpUseCase {
    OrderProjection markOrderReadyForPickUp(UUID orderIdv, UUID ownerId);
}
