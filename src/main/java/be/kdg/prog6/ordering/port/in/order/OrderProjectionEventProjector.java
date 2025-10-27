package be.kdg.prog6.ordering.port.in.order;

import java.util.UUID;

public interface OrderProjectionEventProjector {
    void project(UUID orderId);
}
