package be.kdg.prog6.ordering.domain.vo;

import java.util.UUID;

public record DishId(UUID id) {
    public static DishId of(UUID id) {
        return new DishId(id);
    }
}
