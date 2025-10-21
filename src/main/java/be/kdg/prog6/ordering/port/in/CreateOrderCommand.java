package be.kdg.prog6.ordering.port.in;

import be.kdg.prog6.ordering.domain.vo.*;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderCommand(
        RestaurantId restaurantId,
        List<OrderLine> lines,
        Money totalPrice,
        CustomerInfo customer,
        Address deliveryAddress,
        LocalDateTime placedAt
) { }
