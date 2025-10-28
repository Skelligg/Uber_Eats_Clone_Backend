package be.kdg.prog6.restaurant.port.in.restaurant;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.restaurant.domain.vo.restaurant.*;

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
