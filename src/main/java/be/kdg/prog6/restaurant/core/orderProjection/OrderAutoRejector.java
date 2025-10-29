package be.kdg.prog6.restaurant.core.orderProjection;

import be.kdg.prog6.common.events.order.OrderRejectedEvent;
import be.kdg.prog6.common.vo.ORDER_STATUS;
import be.kdg.prog6.restaurant.domain.projection.OrderProjection;
import be.kdg.prog6.restaurant.port.out.orderProjection.LoadOrdersPort;
import be.kdg.prog6.restaurant.port.out.orderProjection.UpdateOrdersPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderAutoRejector {
    private final LoadOrdersPort loadOrdersPort;
    private final List<UpdateOrdersPort> updateOrdersPorts;

    public OrderAutoRejector(LoadOrdersPort loadOrdersPort, List<UpdateOrdersPort> updateOrdersPorts) {
        this.loadOrdersPort = loadOrdersPort;
        this.updateOrdersPorts = updateOrdersPorts;
    }

    @Scheduled(fixedRate = 60000) // every 5 minutes
    @Transactional
    public void autoRejectOrders() {
        List<OrderProjection> allOrders = loadOrdersPort.loadAll();

        LocalDateTime now = LocalDateTime.now();

        allOrders.forEach(order -> {
            if (order.getStatus() == ORDER_STATUS.PLACED) {

                Duration duration = Duration.between(order.getPlacedAt(), now);
                if (duration.toMinutes() >= 5) {
                    String reason = "Auto-rejected after 5 minutes.";
                    order.reject(reason);

                    order.addDomainEvent(new OrderRejectedEvent(
                            order.getOrderId(),
                            reason
                    ));
                    updateOrdersPorts.forEach(port -> port.update(order));
                }
            }
        });
    }
}
