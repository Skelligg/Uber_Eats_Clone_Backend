package be.kdg.prog6.restaurant.domain.vo.restaurant;

import java.util.UUID;

public record OwnerId(
        UUID id,
        String name
) {

    public static OwnerId of(UUID uuid, String name) {
        return new OwnerId(uuid,name);
    }

    public static OwnerId of(String name) {
        return new OwnerId(UUID.randomUUID(), name);
    }

}
