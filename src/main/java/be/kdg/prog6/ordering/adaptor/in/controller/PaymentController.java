package be.kdg.prog6.ordering.adaptor.in.controller;

import be.kdg.prog6.ordering.adaptor.in.response.AddressDto;
import be.kdg.prog6.ordering.adaptor.in.response.OrderDto;
import be.kdg.prog6.ordering.adaptor.in.response.OrderLineDto;
import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.port.in.payment.MakePaymentUseCase;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final MakePaymentUseCase makePaymentUseCase;


    public PaymentController(MakePaymentUseCase makePaymentUseCase) {
        this.makePaymentUseCase = makePaymentUseCase;
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<Map<String, Object>> createCheckoutSession(
            @PathVariable UUID orderId
    ) throws StripeException {

        String checkoutUrl = makePaymentUseCase.makePayment(orderId);

        return ResponseEntity.ok(Map.of("checkoutUrl", checkoutUrl));
    }

    @GetMapping("/order/{orderId}/verify/{sessionId}")
    public ResponseEntity<OrderDto> verifyPayment(@PathVariable UUID orderId, @PathVariable String sessionId) {
        Order paid = makePaymentUseCase.verifyPayment(orderId,sessionId);

        OrderDto dto = new OrderDto(
                paid.getRestaurantId().id().toString(),
                paid.getLines().stream()
                        .map(l -> new OrderLineDto(
                                l.dishId().id().toString(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice().price().doubleValue(),
                                l.linePrice().price().doubleValue()
                        ))
                        .toList(),
                paid.getTotalPrice().price().doubleValue(),
                paid.getCustomer().name(),
                paid.getCustomer().email(),
                new AddressDto(
                        paid.getDeliveryAddress().street(),
                        paid.getDeliveryAddress().number(),
                        paid.getDeliveryAddress().postalCode(),
                        paid.getDeliveryAddress().city(),
                        paid.getDeliveryAddress().country()
                ),
                paid.getPlacedAt(),
                paid.getStatus().toString()
        );

        return ResponseEntity.ok(dto);
    }

}
