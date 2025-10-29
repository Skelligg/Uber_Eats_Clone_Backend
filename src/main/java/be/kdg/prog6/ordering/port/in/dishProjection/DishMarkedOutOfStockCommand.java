package be.kdg.prog6.ordering.port.in.dishProjection;

import java.util.UUID;

public record DishMarkedOutOfStockCommand(
        UUID dishId
) {
}
