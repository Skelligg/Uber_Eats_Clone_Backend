package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.Dish;

import java.util.UUID;

public interface CreateDishDraftUseCase {
    Dish createDishDraftForFoodMenu(CreateDishDraftCommand command);
}
