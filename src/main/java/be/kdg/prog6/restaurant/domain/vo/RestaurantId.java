package be.kdg.prog6.restaurant.domain.vo;

import java.util.UUID;

public record RestaurantId(
        UUID id
) {
    public static RestaurantId newId() {
        return new RestaurantId(UUID.randomUUID());
    }
}
