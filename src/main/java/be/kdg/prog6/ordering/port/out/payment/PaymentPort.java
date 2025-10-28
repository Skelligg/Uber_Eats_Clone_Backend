package be.kdg.prog6.ordering.port.out.payment;

import java.math.BigDecimal;
import com.stripe.model.checkout.Session;

public interface PaymentPort {
    Session makePayment(BigDecimal amount, String currency, String successUrl, String cancelUrl);
    boolean verifyPayment(String sessionId);
}
