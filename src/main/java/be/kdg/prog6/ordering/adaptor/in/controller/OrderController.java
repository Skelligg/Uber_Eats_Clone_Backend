package be.kdg.prog6.ordering.adaptor.in.controller;

import be.kdg.prog6.common.vo.Address;
import be.kdg.prog6.ordering.adaptor.in.request.CreateOrderRequest;
import be.kdg.prog6.ordering.adaptor.in.response.AddressDto;
import be.kdg.prog6.ordering.adaptor.in.response.OrderDto;
import be.kdg.prog6.ordering.adaptor.in.response.OrderLineDto;
import be.kdg.prog6.ordering.adaptor.in.response.OrderPaidDto;
import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.*;
import be.kdg.prog6.ordering.port.in.order.CreateOrderCommand;
import be.kdg.prog6.ordering.port.in.order.CreateOrderUseCase;
import be.kdg.prog6.ordering.port.in.order.GetOrdersUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrdersUseCase getOrdersUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetOrdersUseCase getOrdersUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrdersUseCase = getOrdersUseCase;
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
                        )
        );

        Order orderCreated = createOrderUseCase.createOrder(command);

        return ResponseEntity.ok(new OrderDto(
                orderCreated.getOrderId().id().toString(),
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
        ));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderPaidDto> getOrder(@PathVariable UUID orderId) {
        Order order = getOrdersUseCase.getOrder(orderId);

        return ResponseEntity.ok(new OrderPaidDto(
                order.getOrderId().id().toString(),
                order.getRestaurantId().id().toString(),
                order.getLines().stream()
                        .map(l -> new OrderLineDto(
                                l.dishId().id().toString(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice().price().doubleValue(),
                                l.linePrice().price().doubleValue()
                        ))
                        .toList(),
                order.getTotalPrice().price().doubleValue(),
                order.getCustomer().name(),
                order.getCustomer().email(),
                new AddressDto(
                        order.getDeliveryAddress().street(),
                        order.getDeliveryAddress().number(),
                        order.getDeliveryAddress().postalCode(),
                        order.getDeliveryAddress().city(),
                        order.getDeliveryAddress().country()
                ),
                order.getPlacedAt(),
                order.getStatus().toString(),
                order.getRejectionReason(),
                order.getPaymentSessionId()
        ));
    }
}
