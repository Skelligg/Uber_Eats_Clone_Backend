package be.kdg.prog6.restaurant.adaptor.in.request;

import be.kdg.prog6.restaurant.domain.vo.restaurant.DAY;

import java.time.LocalTime;
import java.util.List;

public record CreateRestaurantRequest(
        String ownerId,
        String name,
        AddressRequest address,
        String emailAddress,
        List<String> pictures,
        String cuisineType,
        Integer minPrepTime,
        Integer maxPrepTime,
        LocalTime openingTime,
        LocalTime closingTime,
        List<DAY> openDays
) {
}

