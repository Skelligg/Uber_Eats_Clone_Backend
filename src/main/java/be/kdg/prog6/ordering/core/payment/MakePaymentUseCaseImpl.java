package be.kdg.prog6.ordering.core.payment;

import be.kdg.prog6.common.events.order.OrderCreatedEvent;
import be.kdg.prog6.common.vo.OrderLineEventInfo;
import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.OrderId;
import be.kdg.prog6.ordering.port.in.payment.MakePaymentUseCase;
import be.kdg.prog6.ordering.port.out.order.LoadOrderPort;
import be.kdg.prog6.ordering.port.out.order.UpdateOrderPort;
import be.kdg.prog6.ordering.port.out.payment.PaymentPort;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MakePaymentUseCaseImpl implements MakePaymentUseCase {

    private final PaymentPort paymentPort;
    private final LoadOrderPort loadOrderPort;
    private final List<UpdateOrderPort> updateOrderPorts;

    public MakePaymentUseCaseImpl(PaymentPort paymentPort, LoadOrderPort loadOrderPort, List<UpdateOrderPort> updateOrderPorts) {
        this.paymentPort = paymentPort;
        this.loadOrderPort = loadOrderPort;
        this.updateOrderPorts = updateOrderPorts;
    }

    @Override
    public String makePayment(UUID orderId) {
        String successUrl = String.format(
                "http://localhost:5173/payment-success?orderId=%s",
                orderId
        );

        var order = loadOrderPort.findById(OrderId.of(orderId));
        if( order.isEmpty()) {
            throw new IllegalArgumentException("Order does not exist");
        }
        BigDecimal amount = order.get().getTotalPrice().price();

        String cancelUrl = "https://cancel.url";
        Session session = paymentPort.makePayment(amount, "EUR", successUrl, cancelUrl);

        order.get().assignPaymentSession(session.getId());

        this.updateOrderPorts.forEach(port -> port.update(order.get()));

        return session.getUrl();
    }

    @Override
    public Order verifyPayment(UUID orderId,String sessionId) {
        Order order = loadOrderPort.findById(OrderId.of(orderId)).orElseThrow( () -> new IllegalArgumentException("Order does not exist"));

        if (!sessionId.equals(order.getPaymentSessionId())) {
            throw new IllegalArgumentException("Session ID mismatch");
        }

        if(!paymentPort.verifyPayment(sessionId)) {
            throw new IllegalArgumentException("Payment failed");
        }
        order.markPaid();

        order.addDomainEvent(new OrderCreatedEvent(
                order.getOrderId().id(),
                order.getRestaurantId().id(),
                order.getLines().stream()
                        .map(l -> new OrderLineEventInfo(
                                l.dishId().id(),
                                l.dishName(),
                                l.quantity(),
                                l.unitPrice().price().doubleValue(),
                                l.linePrice().price().doubleValue()
                        ))
                        .toList(),
                order.getTotalPrice().price().doubleValue(),
                order.getDeliveryAddress(),
                order.getPlacedAt(),
                order.getStatus().name()
        ));

        this.updateOrderPorts.forEach(port -> port.update(order));

        return order;
    }

}
