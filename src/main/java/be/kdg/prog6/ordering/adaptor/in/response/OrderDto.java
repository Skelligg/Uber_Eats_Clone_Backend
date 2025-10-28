package be.kdg.prog6.ordering.adaptor.in.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
    String restaurantId,
    List<OrderLineDto> lines,
    double totalPrice,
    String customerName,
    String customerEmail,
    AddressDto deliveryAddress,
    LocalDateTime placedAt,
    String orderStatus
    ){
}
