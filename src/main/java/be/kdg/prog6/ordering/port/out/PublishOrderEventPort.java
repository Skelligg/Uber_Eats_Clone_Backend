package be.kdg.prog6.ordering.port.out;

import be.kdg.prog6.ordering.domain.Order;

public interface PublishOrderEventPort {
    void publishOrderCreated(Order order);

}
