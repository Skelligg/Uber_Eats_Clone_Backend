package be.kdg.prog6.common.events;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record RestaurantCreatedEvent(
        LocalDateTime eventPit,
        String restaurantId,
        String ownerId,
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
) implements DomainEvent {

    public RestaurantCreatedEvent(
            String restaurantId,
            String ownerId,
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
        this(LocalDateTime.now(), restaurantId, ownerId, name, street, number, postalCode, city, country,
                emailAddress, pictures, cuisineType, minPrepTime, maxPrepTime, openingTime, closingTime,openDays);
    }


    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}