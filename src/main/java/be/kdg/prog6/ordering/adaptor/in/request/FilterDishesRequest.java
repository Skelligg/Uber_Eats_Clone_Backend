package be.kdg.prog6.ordering.adaptor.in.request;

import java.util.UUID;

public record FilterDishesRequest(
        UUID restaurantId,
        String dishType,
        String tags
) {
}
