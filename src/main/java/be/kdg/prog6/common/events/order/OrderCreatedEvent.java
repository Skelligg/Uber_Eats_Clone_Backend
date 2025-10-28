package be.kdg.prog6.common.events.order;

import be.kdg.prog6.common.events.DomainEvent;
import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.OrderLineEventInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID restaurantId,
        List<OrderLineEventInfo> lines,
        double totalPrice,
        Address deliveryAddress,
        LocalDateTime placedAt,
        String orderStatus

        ) implements DomainEvent {
    public OrderCreatedEvent (UUID orderId, UUID restaurantId, List<OrderLineEventInfo> lines, double totalPrice, Address deliveryAddress, LocalDateTime placedAt, String orderStatus){
        this(LocalDateTime.now(), orderId, restaurantId, lines, totalPrice, deliveryAddress, placedAt, orderStatus);
    }
}
