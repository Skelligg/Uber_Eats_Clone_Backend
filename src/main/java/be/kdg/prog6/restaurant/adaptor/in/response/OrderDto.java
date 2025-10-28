package be.kdg.prog6.restaurant.adaptor.in.response;

import be.kdg.prog6.common.vo.ORDER_STATUS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID orderId,

        String street,
        String number,
        String postalCode,
        String city,
        String country,

        double totalPrice,
        LocalDateTime placedAt,

        ORDER_STATUS status,

        String rejectionReason,

        LocalDateTime acceptedAt,
        LocalDateTime readyAt,
        LocalDateTime rejectedAt,
        LocalDateTime pickedUpAt,
        LocalDateTime deliveredAt,

        List<OrderlineDto>lines
) {
}
