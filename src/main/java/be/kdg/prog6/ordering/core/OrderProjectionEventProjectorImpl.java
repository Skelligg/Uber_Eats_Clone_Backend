package be.kdg.prog6.ordering.core;

import be.kdg.prog6.ordering.domain.vo.OrderId;
import be.kdg.prog6.ordering.port.in.order.OrderProjectionEventProjector;

import be.kdg.prog6.ordering.port.out.LoadOrderPort;
import be.kdg.prog6.ordering.port.out.UpdateOrderPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderProjectionEventProjectorImpl implements OrderProjectionEventProjector {

    private final LoadOrderPort loadOrdersPort;
    private final List<UpdateOrderPort> updateOrderPorts;

    public OrderProjectionEventProjectorImpl(LoadOrderPort loadOrdersPort, List<UpdateOrderPort> updateOrderPorts) {
        this.loadOrdersPort = loadOrdersPort;
        this.updateOrderPorts = updateOrderPorts;
    }

    @Override
    @Transactional
    public void project(UUID orderId) {
        var order = loadOrdersPort.findById(OrderId.of(orderId));
        if( order.isEmpty()) {
            throw new IllegalArgumentException("Order does not exist");
        }
        order.get().accepted();
        this.updateOrderPorts.forEach(port -> port.update(order.get()));
    }
}
