package be.kdg.prog6.ordering.core.order;

import be.kdg.prog6.ordering.domain.Order;
import be.kdg.prog6.ordering.domain.vo.OrderId;
import be.kdg.prog6.ordering.port.in.order.GetOrdersUseCase;
import be.kdg.prog6.ordering.port.out.order.LoadOrderPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetOrdersUseCaseImpl implements GetOrdersUseCase {
    private final LoadOrderPort loadOrderPort;

    public GetOrdersUseCaseImpl(LoadOrderPort loadOrderPort) {
        this.loadOrderPort = loadOrderPort;
    }

    @Override
    public Order getOrder(UUID orderId) {
        return loadOrderPort.findById(OrderId.of(orderId)).orElseThrow( () -> new IllegalArgumentException("Order does not exist"));
    }
}
