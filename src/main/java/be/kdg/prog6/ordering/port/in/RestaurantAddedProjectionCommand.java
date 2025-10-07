package be.kdg.prog6.ordering.port.in;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record RestaurantAddedProjectionCommand(
        UUID restaurantId,
        UUID ownerId,
        String ownerName,
        String name,
        String street,
        String number,
        String postalCode,
        String city,
        String country,
        String emailAddress,
        List<String> pictures,
        String cuisineType,
        int minPrepTime,
        int maxPrepTime,
        LocalTime openingTime,
        LocalTime closingTime,
        List<String> openDays
) {
    // Optionally, you can add a static 'fromEvent' method for convenience
    public static RestaurantAddedProjectionCommand fromEvent(be.kdg.prog6.common.events.RestaurantCreatedEvent event) {
        return new RestaurantAddedProjectionCommand(
                UUID.fromString(event.restaurantId()),
                UUID.fromString(event.ownerId()),
                event.ownerName(),
                event.name(),
                event.street(),
                event.number(),
                event.postalCode(),
                event.city(),
                event.country(),
                event.emailAddress(),
                event.pictures(),
                event.cuisineType(),
                event.minPrepTime(),
                event.maxPrepTime(),
                event.openingTime(),
                event.closingTime(),
                event.openDays()
        );
    }
}
