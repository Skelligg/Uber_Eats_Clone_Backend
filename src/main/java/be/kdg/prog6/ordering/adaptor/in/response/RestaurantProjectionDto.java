package be.kdg.prog6.ordering.adaptor.in.response;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record RestaurantProjectionDto (
    UUID restaurantId,
    UUID ownerId,
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
){
}
