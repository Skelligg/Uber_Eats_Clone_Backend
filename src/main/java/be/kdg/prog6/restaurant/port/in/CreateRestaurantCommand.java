package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.vo.*;

import java.util.List;

public record CreateRestaurantCommand(
        OwnerId ownerId,
        String name,
        Address address,
        EmailAddress emailAddress,
        List<Picture> pictureList,
        CUISINE_TYPE CUISINETYPE,
        PrepTime defaultPrepTime,
        OpeningHours openingHours
) {
}
