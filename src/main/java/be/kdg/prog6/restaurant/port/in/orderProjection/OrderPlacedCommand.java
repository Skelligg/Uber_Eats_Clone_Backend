package be.kdg.prog6.restaurant.port.in.orderProjection;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.common.vo.OrderLineEventInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderPlacedCommand(
        UUID orderId,
        UUID restaurantId,
        List<OrderLineEventInfo> lines,
        double totalPrice,
        Address deliveryAddress,
        LocalDateTime placedAt,
        String orderStatus
) {
}
