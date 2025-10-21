package be.kdg.prog6.ordering.domain.vo;

import java.util.UUID;

public record RestaurantId(UUID id) {
    public static RestaurantId of(UUID id) {
        return new RestaurantId(id);
    }
}
