package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleChangesCommand(
        RestaurantId restaurantId,
        List<DishId> dishIds,
        LocalDateTime publicationTime
        ) {
}
