package be.kdg.prog6.restaurant.port.in.dish;

import java.util.UUID;

public interface DeleteDishUseCase {
    boolean deleteDish(UUID dishId);
}
