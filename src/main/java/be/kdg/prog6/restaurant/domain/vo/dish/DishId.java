package be.kdg.prog6.restaurant.domain.vo.dish;

import java.util.UUID;

public record DishId(
    UUID id
) {
    public static DishId newId() {
        return new DishId(UUID.randomUUID());
    }
    public static DishId of(UUID id) {
        return new DishId(id);
    }
}
