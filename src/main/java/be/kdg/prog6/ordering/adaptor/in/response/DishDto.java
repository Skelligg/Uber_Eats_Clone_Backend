package be.kdg.prog6.ordering.adaptor.in.response;

import be.kdg.prog6.ordering.domain.projection.DISH_AVAILABILITY;

import java.math.BigDecimal;
import java.util.UUID;

public record DishDto(
        UUID dishId,
        UUID foodMenuId,
        String name,
        String description,
        BigDecimal price,
        String pictureUrl,
        String tags,
        String dishType,
        DISH_AVAILABILITY dishState
) {
}
