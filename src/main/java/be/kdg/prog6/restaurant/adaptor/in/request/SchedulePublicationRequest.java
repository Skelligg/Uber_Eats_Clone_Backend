package be.kdg.prog6.restaurant.adaptor.in.request;

import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;

import java.time.LocalDateTime;
import java.util.List;

public record SchedulePublicationRequest (
        String restaurantId,
        List<String> dishIds,
        LocalDateTime publicationTime,
        DISH_STATE stateToBecome
)
    {
    }
