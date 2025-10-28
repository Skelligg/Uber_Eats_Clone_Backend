package be.kdg.prog6.ordering.domain.projection;

import be.kdg.prog6.ordering.domain.vo.PRICE_RANGE;

import java.math.BigDecimal;
import java.util.UUID;

public record FoodMenuProjection(
        UUID restaurantId,
        BigDecimal averageMenuPrice
) {
    public PRICE_RANGE priceRange() {
        if (averageMenuPrice == null) {
            throw new IllegalStateException("Average menu price cannot be null");
        }

        if (averageMenuPrice.compareTo(BigDecimal.valueOf(10)) < 0) {
            return PRICE_RANGE.CHEAP;
        } else if (averageMenuPrice.compareTo(BigDecimal.valueOf(30)) <= 0) {
            return PRICE_RANGE.REGULAR;
        } else if (averageMenuPrice.compareTo(BigDecimal.valueOf(60)) <= 0) {
            return PRICE_RANGE.EXPENSIVE;
        } else {
            return PRICE_RANGE.PREMIUM;
        }

    }

}
