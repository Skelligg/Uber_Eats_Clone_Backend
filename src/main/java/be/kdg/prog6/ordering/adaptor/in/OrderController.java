package be.kdg.prog6.ordering.adaptor.in;

import be.kdg.prog6.ordering.adaptor.in.request.CreateOrderRequest;
import be.kdg.prog6.ordering.adaptor.in.response.AddressDto;
import be.kdg.prog6.ordering.adaptor.in.response.OrderDto;
import be.kdg.prog6.ordering.adaptor.in.response.OrderLineDto;
import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.*;
import be.kdg.prog6.ordering.port.in.CreateOrderCommand;
import be.kdg.prog6.ordering.port.in.CreateOrderUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        CreateOrderCommand command = new CreateOrderCommand(
                RestaurantId.of(UUID.fromString(request.restaurantId())),
                request.lines().stream()
                        .map(l -> new OrderLine(
                                DishId.of(UUID.fromString(l.dishId())),
                                l.dishName(),
                                l.quantity(),
                                Money.of(l.unitPrice()),
                                Money.of(l.linePrice())
                        )).toList(),
                Money.of(request.totalPrice()),
                new CustomerInfo(request.customerName(), request.customerEmail()),
                new Address(
                        request.deliveryAddress().street(),
                        request.deliveryAddress().number(),
                        request.deliveryAddress().postalCode(),
                        request.deliveryAddress().city(),
                        request.deliveryAddress().country()
                        ),
                request.placedAt()
        );

        Order orderCreated = createOrderUseCase.createOrder(command);

        OrderDto dto = new OrderDto(
                orderCreated.getRestaurantId().id().toString(),
                orderCreated.getLines().stream()
                        .map(l -> new OrderLineDto(
                                l.dishId().id().toString(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice().price().doubleValue(),
                                l.linePrice().price().doubleValue()
                        ))
                        .toList(),
                orderCreated.getTotalPrice().price().doubleValue(),
                orderCreated.getCustomer().name(),
                orderCreated.getCustomer().email(),
                new AddressDto(
                        orderCreated.getDeliveryAddress().street(),
                        orderCreated.getDeliveryAddress().number(),
                        orderCreated.getDeliveryAddress().postalCode(),
                        orderCreated.getDeliveryAddress().city(),
                        orderCreated.getDeliveryAddress().country()
                ),
                orderCreated.getPlacedAt(),
                orderCreated.getStatus().toString()
        );

        return ResponseEntity.ok(dto);
    }
}
