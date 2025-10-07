package be.kdg.prog6.ordering.domain.projection;

import java.math.BigDecimal;
import java.util.UUID;

public record DishProjection(
        UUID dishId,
        UUID foodMenuId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String tags,
        String dishType
) {
}
