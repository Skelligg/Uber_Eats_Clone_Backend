package be.kdg.prog6.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishMarkedOutOfStockEvent(
        LocalDateTime eventPit,
        UUID dishId
) implements DomainEvent{
    public DishMarkedOutOfStockEvent(UUID dishId) {
        this(LocalDateTime.now(), dishId);
    }
}
