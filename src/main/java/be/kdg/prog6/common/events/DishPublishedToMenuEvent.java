package be.kdg.prog6.common.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DishPublishedToMenuEvent(
        LocalDateTime eventPit,
        UUID dishId,
        UUID foodMenuId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String tags,
        String dishType,
        String dishState
) implements DomainEvent {

    public DishPublishedToMenuEvent(UUID dishId, UUID foodMenuId, String name, String description,
                                    BigDecimal price, String pictureUrl, String tags, String dishType, String dishState) {
        this(LocalDateTime.now(), dishId, foodMenuId, name, description, price, pictureUrl, tags, dishType,dishState);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}