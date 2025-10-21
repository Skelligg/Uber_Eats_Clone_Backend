package be.kdg.prog6.ordering.port.in.dish;

import java.math.BigDecimal;
import java.util.UUID;

public record DishPublishedCommand(
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
