package be.kdg.prog6.restaurant.port.in.order;

public interface OrderEventProjector {
    void project(OrderPlacedCommand command);

}
