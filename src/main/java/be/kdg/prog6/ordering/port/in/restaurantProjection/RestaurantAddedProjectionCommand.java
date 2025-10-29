package be.kdg.prog6.ordering.port.in.restaurantProjection;

import be.kdg.prog6.common.events.restaurant.RestaurantCreatedEvent;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record RestaurantAddedProjectionCommand(
        UUID restaurantId,
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

    public static RestaurantAddedProjectionCommand fromEvent(RestaurantCreatedEvent event) {
        return new RestaurantAddedProjectionCommand(
                UUID.fromString(event.restaurantId()),
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
