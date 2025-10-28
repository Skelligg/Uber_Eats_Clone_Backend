package be.kdg.prog6.ordering.domain.projection;

import java.math.BigDecimal;
import java.util.UUID;

public record FoodMenuProjection(
        UUID restaurantId,
        BigDecimal averageMenuPrice
) {}
