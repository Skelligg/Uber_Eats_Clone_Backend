package be.kdg.prog6.ordering.adaptor.in.request;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderRequest (
    String restaurantId,
    List<OrderLineRequest> lines,
    double totalPrice,
    String customerName,
    String customerEmail,
    AddressRequest deliveryAddress
){
}
