package be.kdg.prog6.restaurant.port.in.dish;

import be.kdg.prog6.restaurant.domain.vo.dish.DISH_STATE;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleChangesCommand(
        RestaurantId restaurantId,
        List<DishId> dishIds,
        LocalDateTime publicationTime,
        DISH_STATE stateToBecome
        ) {
}
