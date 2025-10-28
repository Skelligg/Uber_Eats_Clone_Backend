package be.kdg.prog6.ordering.port.in.order;

import be.kdg.prog6.common.vo.Coordinates;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderLocationUpdatedCommand (
        UUID orderId,
        LocalDateTime occurredAt,
        double lat,
        double lng
) {
}
