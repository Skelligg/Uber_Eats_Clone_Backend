package be.kdg.prog6.common.events.order;

import be.kdg.prog6.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRejectedEvent(
        UUID eventId,
        UUID orderId,
        String reason,
        LocalDateTime occurredAt
) implements DomainEvent {

    public OrderRejectedEvent(UUID orderId,
                              String reason) {
        this(UUID.randomUUID(), orderId, reason, LocalDateTime.now());
    }


    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}