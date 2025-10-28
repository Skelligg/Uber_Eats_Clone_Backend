package be.kdg.prog6.ordering.port.in.order;

public interface OrderProjectionEventProjector {
    void project(OrderAcceptedCommand orderAcceptedCommand);
    void project(OrderRejectedCommand orderRejectedCommand);
    void project(OrderReadyForPickupCommand orderReadyForPickupCommand);
    void project(OrderPickedUpCommand orderPickedUpCommand);
    void project(OrderDeliveredCommand orderDeliveredCommand);
    void project(OrderLocationUpdatedCommand orderLocationUpdatedCommand);

}
