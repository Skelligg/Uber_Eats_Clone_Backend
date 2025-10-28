package be.kdg.prog6.common.events.order;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.common.vo.Coordinates;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("kdg.events::#{'delivery.' + #this.restaurantId() + '.order.location.v1'}")
public record OrderLocationEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID restaurantId,
        UUID orderId,
        double lat,
        double lng
) implements DomainEvent {

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}
