package be.kdg.prog6.restaurant.port.in.orderProjection;

public interface OrderEventProjector {
    void project(OrderPlacedCommand command);

}
