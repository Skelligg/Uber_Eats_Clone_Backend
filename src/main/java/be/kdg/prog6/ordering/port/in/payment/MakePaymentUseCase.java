package be.kdg.prog6.ordering.port.in.payment;

import be.kdg.prog6.ordering.domain.Order;

import java.util.UUID;

public interface MakePaymentUseCase {
    String makePayment(UUID orderId);
    Order verifyPayment(UUID orderId, String sessionId);
}
