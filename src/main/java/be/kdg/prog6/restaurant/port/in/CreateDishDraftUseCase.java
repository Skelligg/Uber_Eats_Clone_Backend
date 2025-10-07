package be.kdg.prog6.restaurant.port.in;

import java.util.UUID;

public interface CreateDishDraftUseCase {
    void createDishDraftForFoodMenu(CreateDishDraftCommand command);
}
