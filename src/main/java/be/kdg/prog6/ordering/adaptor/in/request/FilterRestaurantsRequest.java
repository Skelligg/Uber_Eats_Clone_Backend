package be.kdg.prog6.ordering.adaptor.in.request;

import be.kdg.prog6.common.vo.CUISINE_TYPE;
import be.kdg.prog6.ordering.domain.vo.PRICE_RANGE;

public record FilterRestaurantsRequest(
        String cuisineType,
        String priceRange
) {
}
