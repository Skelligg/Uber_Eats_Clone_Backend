package be.kdg.prog6.common.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DishCreatedEvent(
        LocalDateTime eventPit,
        UUID dishId,
        UUID foodMenuId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String tags,
        String dishType
) implements DomainEvent {

    public DishCreatedEvent(UUID dishId, UUID foodMenuId, String name, String description,
                            BigDecimal price, String pictureUrl, String tags, String dishType) {
        this(LocalDateTime.now(), dishId, foodMenuId, name, description, price, pictureUrl, tags, dishType);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}