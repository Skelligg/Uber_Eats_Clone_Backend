package be.kdg.prog6.ordering.port.in.dish;

import java.util.UUID;

public record DishUnpublishedCommand(
        UUID dishId
) {
}
