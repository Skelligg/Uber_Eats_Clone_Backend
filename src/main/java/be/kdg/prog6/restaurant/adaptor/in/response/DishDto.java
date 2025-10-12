package be.kdg.prog6.restaurant.adaptor.in.response;

import java.util.UUID;

public record DishDto(
        UUID dishId,
        DishVersionDto publishedVersion,
        DishVersionDto draftVersion,
        String state
) {}