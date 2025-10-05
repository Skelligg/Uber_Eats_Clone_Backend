package be.kdg.prog6.restaurant.domain.vo.restaurant;

import java.time.LocalTime;
import java.util.List;

public record OpeningHours(
        LocalTime openingTime,
        LocalTime closingTime,
        List<DAY> openDays
) { public OpeningHours {
    if (openingTime == null || closingTime == null) {
        throw new IllegalArgumentException("Opening Time and Closing Time cannot be null");
    }
    else if (closingTime.isBefore(openingTime)) {
        throw new IllegalArgumentException("Closing Time cannot be before Opening Time");
    }
}

    public static OpeningHours of (LocalTime openingTime, LocalTime closingTime, List<DAY> openDays) {
    return new OpeningHours(openingTime, closingTime, openDays);
    }
}
