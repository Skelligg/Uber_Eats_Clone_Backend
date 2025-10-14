package be.kdg.prog6.restaurant.adaptor.in.request;

import java.time.LocalDateTime;
import java.util.List;

public record SchedulePublicationRequest (
        String restaurantId,
        List<String> dishIds,
        LocalDateTime publicationTime
)
    {
    }
