package be.kdg.prog6.common.events.dish;

import be.kdg.prog6.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishMarkedAvailableEvent(
        LocalDateTime eventPit,
        UUID dishId
) implements DomainEvent {
    public DishMarkedAvailableEvent(UUID dishId) {
        this(LocalDateTime.now(), dishId);
    }
}
