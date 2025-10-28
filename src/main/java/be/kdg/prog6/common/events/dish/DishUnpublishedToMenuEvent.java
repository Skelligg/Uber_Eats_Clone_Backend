package be.kdg.prog6.common.events.dish;

import be.kdg.prog6.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishUnpublishedToMenuEvent(
        LocalDateTime eventPit,
        UUID dishId) implements DomainEvent {
    public DishUnpublishedToMenuEvent(UUID dishId) {
        this(LocalDateTime.now(), dishId);
    }
}
