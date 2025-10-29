package be.kdg.prog6.restaurant.port.out.orderProjection;

import be.kdg.prog6.restaurant.domain.projection.OrderProjection;

public interface UpdateOrdersPort {
    OrderProjection update(OrderProjection orderProjection);
}
