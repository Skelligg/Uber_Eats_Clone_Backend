package be.kdg.prog6.restaurant.domain.vo.restaurant;

import java.util.UUID;

public record OwnerId(
        UUID id,
        String name
) {
    public static final OwnerId MICHAEL = OwnerId.of(UUID.fromString("8c8c87c1-9b5f-4f73-9fa7-7f9b5c7f2296"),"Michael");

    public static OwnerId of(UUID uuid, String name) {
        return new OwnerId(uuid,name);
    }


}
