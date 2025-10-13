package be.kdg.prog6.ordering.port.in;

import java.util.UUID;

public record DishMarkedAvailableCommand(
        UUID dishId
) {
}
