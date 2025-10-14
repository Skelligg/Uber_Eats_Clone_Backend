package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.FoodMenu;

public interface SchedulePendingChangesUseCase {
    FoodMenu scheduleChanges(ScheduleChangesCommand command);
}
