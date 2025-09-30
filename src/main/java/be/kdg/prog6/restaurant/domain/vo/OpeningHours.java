package be.kdg.prog6.restaurant.domain.vo;

import java.time.LocalTime;

public record OpeningHours(
        LocalTime openingTime,
        LocalTime closingTime
) { public OpeningHours {
    if (openingTime == null || closingTime == null) {
        throw new IllegalArgumentException("Opening Time and Closing Time cannot be null");
    }
    else if (closingTime.isBefore(openingTime)) {
        throw new IllegalArgumentException("Closing Time cannot be before Opening Time");
    }
}
}
