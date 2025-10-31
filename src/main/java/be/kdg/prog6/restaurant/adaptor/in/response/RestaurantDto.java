package be.kdg.prog6.restaurant.adaptor.in.response;

import java.util.List;

public record RestaurantDto(
        String id,
        String name,
        String street,
        String number,
        String postalCode,
        String city,
        String country,
        String emailAddress,
        List<String >pictureList,
        String cuisineType,
        int minPrepTime,
        int maxPrepTime,
        String openingTime,
        String closingTime,
        List<String> openDays
) {
}
