package be.kdg.prog6.restaurant.adaptor.in.response;

import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishDto(
        UUID dishId,
        DishVersionDto publishedVersion,
        DishVersionDto draftVersion,
        String state,
        LocalDateTime scheduledPublishTime,
        DISH_STATE scheduledToBecomeState
) {}