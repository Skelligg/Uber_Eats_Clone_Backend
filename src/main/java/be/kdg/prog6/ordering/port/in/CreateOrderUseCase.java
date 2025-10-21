package be.kdg.prog6.ordering.port.in;

import be.kdg.prog6.ordering.domain.Order;

public interface CreateOrderUseCase {

    Order createOrder(CreateOrderCommand command);
}
