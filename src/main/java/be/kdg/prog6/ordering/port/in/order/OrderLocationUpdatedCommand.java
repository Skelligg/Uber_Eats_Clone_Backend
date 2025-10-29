package be.kdg.prog6.ordering.port.in.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderLocationUpdatedCommand (
        UUID orderId,
        LocalDateTime occurredAt,
        double lat,
        double lng
) {
}
