package be.kdg.prog6.common.events.foodmenu;

import be.kdg.prog6.common.events.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FoodMenuCreatedEvent(
        LocalDateTime eventPit,
        UUID restaurantId,
        BigDecimal averageMenuPrice
) implements DomainEvent {

    public FoodMenuCreatedEvent(UUID restaurantId) {
        this(LocalDateTime.now(), restaurantId,BigDecimal.valueOf(0.0));
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }

}
